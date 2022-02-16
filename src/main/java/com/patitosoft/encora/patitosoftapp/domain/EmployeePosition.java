package com.patitosoft.encora.patitosoftapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.patitosoft.encora.patitosoftapp.resource.EmployeePositionResource;
import com.patitosoft.encora.patitosoftapp.resource.EmployeeResource;
import com.patitosoft.encora.patitosoftapp.resource.PositionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class EmployeePosition implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private Integer salary;
    private String positionName;
    private Boolean currentPosition;

    @ManyToOne
    @RestResource(exported = false)
    @JsonBackReference(value = "employee-position")
    private Employee employee;

    @ManyToOne
    @RestResource(exported = false)
    @JsonBackReference(value = "position-employee")
    private Position position;

    @PrePersist
    public void prePersist() {
        this.currentPosition = true;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EmployeePosition>> violations = validator.validate(this);
        StringBuilder builder = new StringBuilder();
        violations.forEach(employeeConstraintViolation -> builder.append(employeeConstraintViolation.getMessage()));
        if (!violations.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, builder.toString());
        }
    }

    public EmployeePosition(EmployeePositionBuilder builder) {
        this.id = builder.id;
        this.salary = builder.salary;
        this.currentPosition = builder.currentPosition;
        this.employee = builder.employee;
        this.position = builder.position;
    }

    public static class EmployeePositionBuilder {
        private final String id;
        private final Integer salary;
        private final Boolean currentPosition;
        private Employee employee;
        private Position position;

        public EmployeePositionBuilder(EmployeePositionResource resource) {
            this.id = resource.getId();
            this.salary = resource.getSalary();
            this.currentPosition = resource.getCurrentPosition();
        }

        public EmployeePositionBuilder employee(EmployeeResource employeeResource) {
            this.employee = new Employee.EmployeeBuilder(employeeResource).build();
            return this;
        }

        public EmployeePositionBuilder position(PositionResource positionResource) {
            this.position = new Position.PositionBuilder(positionResource).build();
            return this;
        }

        public EmployeePosition build() {
            return new EmployeePosition(this);
        }
    }

}
