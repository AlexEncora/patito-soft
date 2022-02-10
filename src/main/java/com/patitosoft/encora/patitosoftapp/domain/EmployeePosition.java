package com.patitosoft.encora.patitosoftapp.domain;

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
    public void prePersist(){
        this.currentPosition = true;
    }
}
