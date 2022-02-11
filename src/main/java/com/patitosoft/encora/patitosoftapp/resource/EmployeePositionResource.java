package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import com.patitosoft.encora.patitosoftapp.domain.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeePositionResource {

    private String id;
    private String salary;
    private Boolean currentPosition;
    private EmployeeResource employee;
    private PositionResource position;

    public EmployeePositionResource(EmployeePositionResourceBuilder builder) {
        this.id = builder.id;
        this.salary = builder.salary;
        this.currentPosition = builder.currentPosition;
        this.employee = builder.employee;
        this.position = builder.position;
    }

    public static class EmployeePositionResourceBuilder {
        private final String id;
        private final String salary;
        private final Boolean currentPosition;
        private EmployeeResource employee;
        private PositionResource position;

        public EmployeePositionResourceBuilder(EmployeePosition entity) {
            this.id = entity.getId();
            this.salary = entity.getSalary();
            this.currentPosition = entity.getCurrentPosition();
        }

        public EmployeePositionResourceBuilder employee(Employee employee) {
            this.employee = new EmployeeResource.EmployeeResourceBuilder(employee).build();
            return this;
        }

        public EmployeePositionResourceBuilder position(Position position) {
            this.position = new PositionResource.PositionResourceBuilder(position).build();
            return this;
        }

        public EmployeePositionResource build() {
            return new EmployeePositionResource(this);
        }
    }

}
