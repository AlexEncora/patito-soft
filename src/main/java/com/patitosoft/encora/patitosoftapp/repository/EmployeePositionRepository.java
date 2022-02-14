package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "employee-position")
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, String> {


    @Modifying
    @Query("UPDATE EmployeePosition ep SET ep.currentPosition = false WHERE ep.employee = :employee")
    void disableOlderPositionsByEmployee(@Param("employee") Employee employee);

    Long countByPosition_IdAndCurrentPosition(String positionId, Boolean currentPosition);

    @Query("SELECT MIN(salary) FROM EmployeePosition")
    Integer minSalary();

    @Query("SELECT MAX(salary) FROM EmployeePosition")
    Integer maxSalary();

    @Query("SELECT COUNT(salary) FROM EmployeePosition ep WHERE ep.salary > :minSalary AND ep.salary < :maxSalary " +
            "AND ep.currentPosition = true")
    Integer countEmployeesRange(@Param("minSalary") Integer minSalary, @Param("maxSalary") Integer maxSalary);
}
