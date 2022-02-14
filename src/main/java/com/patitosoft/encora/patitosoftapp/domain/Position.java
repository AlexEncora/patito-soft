package com.patitosoft.encora.patitosoftapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.patitosoft.encora.patitosoftapp.resource.EmployeePositionResource;
import com.patitosoft.encora.patitosoftapp.resource.PositionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Position implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @RestResource(exported = false)
    @JsonManagedReference(value = "position-employee")
    private List<EmployeePosition> employeePositions;

    public Position(PositionBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.employeePositions = builder.employeePositions;
    }

    public static class PositionBuilder {
        private final String id;
        private final String name;
        private List<EmployeePosition> employeePositions;

        public PositionBuilder(PositionResource resource) {
            this.id = resource.getId();
            this.name = resource.getName();
        }

        public PositionBuilder employeePositions(List<EmployeePositionResource> employeePositionResources) {
            this.employeePositions = employeePositionResources.stream().map(
                    employeePositionResource -> new EmployeePosition.EmployeePositionBuilder(employeePositionResource)
                            .build()).collect(Collectors.toList());
            return this;
        }

        public Position build() {
            return new Position(this);
        }
    }
}
