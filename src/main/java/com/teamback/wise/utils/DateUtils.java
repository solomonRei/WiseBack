package com.teamback.wise.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String formatToRequiredDate(LocalDate date) {
        return date.format(dateFormatter);
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
