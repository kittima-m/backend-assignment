package com.ata.salary.utils;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ConverterUtils {

    /**
     * Converts a String to a Double.
     * Returns null if the String is null, empty, or not a valid number.
     *
     * @param value the String to convert
     * @return the converted Double, or null if conversion fails
     */
    public static Double convertStringToDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Double.valueOf(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + value);
            return null;
        }
    }

    /**
     * Converts a String array to a List<String>.
     * If the array is null, returns an empty list.
     *
     * @param value the String array
     * @return the converted List<String>, or an empty list if input is null
     */
    public List<String> convertStringArrayToStringArrayList(String[] value) {
        if (value == null) {
            return Collections.emptyList();
        }

        List<String> list = new ArrayList<>();
        for (String str : value) {
            if (str != null) {
                list.add(str);
            }
        }
        return list;
    }

}