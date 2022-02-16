package com.patitosoft.encora.patitosoftapp.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patitosoft.encora.patitosoftapp.domain.Employee;
import com.patitosoft.encora.patitosoftapp.domain.EmployeePosition;
import com.patitosoft.encora.patitosoftapp.domain.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResource {

    private String id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String corporateEmail;
    private String personalEmail;
    private String phone;
    private StateResource state;
    private Integer zipCode;
    private String streetAddress;
    private LocalDate birthday;
    private Boolean isExEmployee;
    private List<EmployeePositionResource> employeePositions;

    public EmployeeResource(EmployeeResourceBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.corporateEmail = builder.corporateEmail;
        this.personalEmail = builder.personalEmail;
        this.phone = builder.phone;
        this.state = builder.state;
        this.zipCode = builder.zipCode;
        this.streetAddress = builder.streetAddress;
        this.birthday = builder.birthday;
        this.employeePositions = builder.employeePositions;
        this.isExEmployee = builder.isExEmployee;
    }

    public static class EmployeeResourceBuilder {
        private final String id;
        private final String firstName;
        private final String lastName;
        private final Gender gender;
        private final String corporateEmail;
        private final String personalEmail;
        private final String phone;
        private StateResource state;
        private final Integer zipCode;
        private final String streetAddress;
        private final LocalDate birthday;
        private final Boolean isExEmployee;
        private List<EmployeePositionResource> employeePositions;

        public EmployeeResourceBuilder(Employee entity) {
            this.id = entity.getId();
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.gender = entity.getGender();
            this.corporateEmail = entity.getCorporateEmail();
            this.personalEmail = entity.getPersonalEmail();
            this.phone = entity.getPhone();
            this.zipCode = entity.getZipCode();
            this.streetAddress = entity.getStreetAddress();
            this.birthday = entity.getBirthday();
            this.isExEmployee = entity.getIsExEmployee();
        }

        public EmployeeResourceBuilder employeePositions(List<EmployeePosition> employeePosition) {
            this.employeePositions = employeePosition.stream().map(
                            employeePositionResource -> new EmployeePositionResource.
                                    EmployeePositionResourceBuilder(employeePositionResource).build())
                    .collect(Collectors.toList());
            return this;
        }

        public EmployeeResource build() {
            return new EmployeeResource(this);
        }
    }
}
