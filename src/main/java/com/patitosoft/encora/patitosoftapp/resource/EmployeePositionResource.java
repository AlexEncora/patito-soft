package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
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

    }

}
