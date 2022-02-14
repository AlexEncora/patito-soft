package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTORangeSalaries {

    private Integer minSalary;
    private Integer maxSalary;
    private Integer countEmployeesRange;
}
