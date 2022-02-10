package com.patitosoft.encora.patitosoftapp.domain;

import com.patitosoft.encora.patitosoftapp.resource.PositionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;

    @OneToMany(mappedBy = "position")
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

        public Position build() {
            return new Position(this);
        }
    }
}
