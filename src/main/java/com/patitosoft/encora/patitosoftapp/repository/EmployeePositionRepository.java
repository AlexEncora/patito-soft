package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, String> {
}
