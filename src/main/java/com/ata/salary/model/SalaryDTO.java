package com.ata.salary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SalaryDTO {

    private String employerName;
    private String location;
    private String jobTitle;
    private BigDecimal yearsAtEmployer;
    private BigDecimal yearsOfExperience;
    private BigDecimal salary;
    private BigDecimal signingBonus;
    private BigDecimal annualBonus;
    private BigDecimal annualStockValue;
    private String gender;
    private String additionalComments;
}
