package ceos.backend.global.util;

import ceos.backend.domain.application.domain.Interview;

import java.time.format.DateTimeFormatter;

public class InterviewDateFormatter {
    public static String interviewDateFormatter(Interview interview) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return interview.getFromDate().format(formatter) + " - " + interview.getToDate().format(formatter);
    }
}
