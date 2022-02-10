package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patitosoft.encora.patitosoftapp.domain.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionResource {

    private String id;
    private String name;
    private List<EmployeePositionResource> employeePositions;

    public PositionResource(PositionResourceBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.employeePositions = builder.employeePositions;
    }

    public static class PositionResourceBuilder {
        private final String id;
        private final String name;
        private List<EmployeePositionResource> employeePositions;

        public PositionResourceBuilder(Position entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }

        public PositionResource build(){
            return new PositionResource(this);
        }
    }
}
