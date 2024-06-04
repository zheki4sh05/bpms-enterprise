package com.example.bpmsenterprise.components.assignment.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AssignmentProcessor {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, formatter);
    }

}
