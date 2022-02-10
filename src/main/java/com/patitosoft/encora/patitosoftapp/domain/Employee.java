package com.patitosoft.encora.patitosoftapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patitosoft.encora.patitosoftapp.resource.EmployeePositionResource;
import com.patitosoft.encora.patitosoftapp.resource.EmployeeResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"corporateEmail"})
})
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotEmpty(message = "The fist cannot be null")
    private String firstName;
    @NotEmpty(message = "The lastname cannot be null")
    private String lastName;
    @Enumerated
    @NotEmpty(message = "The gender cannot be null")
    private Gender gender;
    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "Email cannot be empty")
    private String corporateEmail;
    private String personalEmail;
    private String phone;
    @NotEmpty(message = "Country is mandatory")
    private String country;
    @NotEmpty(message = "State is mandatory")
    private String state;
    @NotEmpty(message = "Zipcode is mandatory")
    private Integer zipCode;
    @NotEmpty(message = "Local address is mandatory")
    private String streetAddress;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Boolean isExEmployee;

    @OneToMany(mappedBy = "employee")
    private List<EmployeePosition> employeePositions;

    @PrePersist
    public void prePersist() {
        this.isExEmployee = false;
    }

    public Employee(EmployeeBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.corporateEmail = builder.corporateEmail;
        this.personalEmail = builder.personalEmail;
        this.phone = builder.phone;
        this.country = builder.country;
        this.state = builder.state;
        this.zipCode = builder.zipCode;
        this.streetAddress = builder.streetAddress;
        this.birthday = builder.birthday;
        this.employeePositions = builder.employeePositions;
    }

    public static class EmployeeBuilder {
        private final String id;
        private final String firstName;
        private final String lastName;
        private final Gender gender;
        private final String corporateEmail;
        private final String personalEmail;
        private final String phone;
        private final String country;
        private final String state;
        private final Integer zipCode;
        private final String streetAddress;
        private final LocalDate birthday;
        private List<EmployeePosition> employeePositions;

        public EmployeeBuilder(EmployeeResource resource) {
            this.id = resource.getId();
            this.firstName = resource.getFirstName();
            this.lastName = resource.getLastName();
            this.gender = resource.getGender();
            this.corporateEmail = resource.getCorporateEmail();
            this.personalEmail = resource.getPersonalEmail();
            this.phone = resource.getPhone();
            this.country = resource.getCountry();
            this.state = resource.getState();
            this.zipCode = resource.getZipCode();
            this.streetAddress = resource.getStreetAddress();
            this.birthday = resource.getBirthday();
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
