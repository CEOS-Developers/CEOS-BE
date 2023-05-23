package ceos.backend.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConvertor {
    public static LocalDateTime dateTimeConvertor(String strDate, String strTime) {
        final String dateTime = strDate + " " + strTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
