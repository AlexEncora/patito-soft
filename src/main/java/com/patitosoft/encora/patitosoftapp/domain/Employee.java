package com.patitosoft.encora.patitosoftapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.patitosoft.encora.patitosoftapp.resource.EmployeePositionResource;
import com.patitosoft.encora.patitosoftapp.resource.EmployeeResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"corporateEmail"})
})
public class Employee implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotEmpty(message = "The fist cannot be null")
    private String firstName;
    @NotEmpty(message = "The lastname cannot be null")
    private String lastName;
    @Enumerated
    @NotNull
    private Gender gender;
    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "Email cannot be empty")
    private String corporateEmail;
    private String personalEmail;
    private String phone;
    @NotNull
    private Integer zipCode;
    @NotEmpty(message = "Local address is mandatory")
    private String streetAddress;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Boolean isExEmployee;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @RestResource(exported = false)
    @JsonManagedReference(value = "employee-position")
    private List<EmployeePosition> employeePositions;

    @ManyToOne
    @RestResource(exported = false)
    @JsonBackReference(value = "employee-state")
    private State state;

    @PrePersist
    public void prePersist() {
        this.isExEmployee = false;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(this);
        StringBuilder builder = new StringBuilder();
        violations.forEach(employeeConstraintViolation -> builder.append(employeeConstraintViolation.getMessage()));
        if (!violations.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, builder.toString());
        }
    }

    public Employee(EmployeeBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.corporateEmail = builder.corporateEmail;
        this.personalEmail = builder.personalEmail;
        this.phone = builder.phone;
        this.state = builder.state;
        this.zipCode = builder.zipCode;
        this.streetAddress = builder.streetAddress;
        this.birthday = builder.birthday;
        this.employeePositions = builder.employeePositions;
        this.isExEmployee = builder.isExEmployee;
    }

    public static class EmployeeBuilder {
        private final String id;
        private final String firstName;
        private final String lastName;
        private final Gender gender;
        private final String corporateEmail;
        private final String personalEmail;
        private final String phone;
        private State state;
        private final Integer zipCode;
        private final String streetAddress;
        private final LocalDate birthday;
        private final Boolean isExEmployee;
        private List<EmployeePosition> employeePositions;

        public EmployeeBuilder(EmployeeResource resource) {
            this.id = resource.getId();
            this.firstName = resource.getFirstName();
            this.lastName = resource.getLastName();
            this.gender = resource.getGender();
            this.corporateEmail = resource.getCorporateEmail();
            this.personalEmail = resource.getPersonalEmail();
            this.phone = resource.getPhone();
            this.zipCode = resource.getZipCode();
            this.streetAddress = resource.getStreetAddress();
            this.birthday = resource.getBirthday();
            this.isExEmployee = resource.getIsExEmployee();
        }

        public EmployeeBuilder employeePositions(List<EmployeePositionResource> employeePositionResources) {
            this.employeePositions = employeePositionResources.stream().map(
                    employeePositionResource -> new EmployeePosition.EmployeePositionBuilder(employeePositionResource)
                            .build()).collect(Collectors.toList());
            return this;
        }


        public Employee build() {
            return new Employee(this);
        }
    }
}
