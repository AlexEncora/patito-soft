package com.patitosoft.encora.patitosoftapp.domain;

import com.patitosoft.encora.patitosoftapp.resource.EmployeePositionResource;
import com.patitosoft.encora.patitosoftapp.resource.EmployeeResource;
import com.patitosoft.encora.patitosoftapp.resource.PositionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class EmployeePosition {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String salary;
    private Boolean currentPosition;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Position position;

    @PrePersist
    public void prePersist() {
        this.currentPosition = true;
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
        private final String salary;
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
