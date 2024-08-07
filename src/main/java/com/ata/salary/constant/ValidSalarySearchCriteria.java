package com.ata.salary.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ValidSalarySearchCriteria {

    MIN_SALARY("minSalary"),
    MAX_SALARY("maxSalary"),
    SALARY("salary"),
    ANNUAL_BONUS("annualBonus"),
    SIGNING_BONUS("signingBonus"),
    ANNUAL("annualStockValue"),
    EMPLOYER_NAME("employerName"),
    LOCATION("location"),
    YEARS_AT_EMPLOYER("yearsAtEmployer"),
    YEARS_OF_EXPERIENCE("yearsOfExperience"),
    ADDITIONAL_COMMENTS("additionalComments"),
    JOB_TITLE("jobTitle"),
    GENDER("gender"),
    FIELDS("fields"),
    SORT("sort"),
    PAGE("page"),
    SIZE("size");


    private final String fieldName;

    private static final Map<String, ValidSalarySearchCriteria> fieldNameMap = new HashMap<>();

    static {
        for (ValidSalarySearchCriteria validField : values()) {
            fieldNameMap.put(validField.getFieldName().replaceAll("_", "").toLowerCase(), validField);
        }

    }

    ValidSalarySearchCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public static boolean isValidField(String field) {
        return fieldNameMap.containsKey(field);
    }

    public static boolean isValidFieldValue(String field) {
        return fieldNameMap.containsValue(field);
    }

    public static String getValidFieldName(String field) {
        ValidSalarySearchCriteria validSalaryFields = fieldNameMap.get(
                field.replaceAll("_", "")
                        .toLowerCase()
        );
        return validSalaryFields != null ? validSalaryFields.getFieldName() : null;
    }
}
