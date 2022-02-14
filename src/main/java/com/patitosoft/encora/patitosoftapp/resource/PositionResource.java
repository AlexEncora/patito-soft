package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import com.patitosoft.encora.patitosoftapp.domain.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

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

        public PositionResourceBuilder employeePositions(List<EmployeePosition> employeePosition) {
            this.employeePositions = employeePosition.stream().map(
                            employeePositionResource -> new EmployeePositionResource.
                                    EmployeePositionResourceBuilder(employeePositionResource).build())
                    .collect(Collectors.toList());
            return this;
        }

        public PositionResource build() {
            return new PositionResource(this);
        }
    }
}
