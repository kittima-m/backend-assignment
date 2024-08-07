package com.ata.salary.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue()
    private Long id;

    @Column(name = "employer_name", length = 100)
    private String employerName;

    @Column(name = "location")
    private String location;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "years_at_employer")
    private BigDecimal yearsAtEmployer;

    @Column(name = "years_of_experience")
    private BigDecimal yearsOfExperience;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "signing_bonus")
    private BigDecimal signingBonus;

    @Column(name = "annual_bonus")
    private BigDecimal annualBonus;

    @Column(name = "annual_stock_value")
    private BigDecimal annualStockValue;

    @Column(name = "gender")
    private String gender;

    @Lob
    @Column(name = "additional_comments")
    private String additionalComments;

    @Column(name = "timestamp")
    @JsonSerialize(using = ToStringSerializer.class)
    private Timestamp timestamp;

}
