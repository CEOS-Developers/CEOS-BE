package ceos.backend.global.util;

import ceos.backend.global.common.dto.ParsedDuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ParsingDuration {
    public static ParsedDuration parsingDuration(String duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        DateTimeFormatter dateFormmatter = DateTimeFormatter.ofPattern("MM/dd");
        DateTimeFormatter timeFormmatter = DateTimeFormatter.ofPattern("HH:mm");

        final String[] strTimes = duration.split(" - ");
        final String date = Arrays.stream(strTimes)
                .map(time -> LocalDateTime.parse(time, formatter))
                .map(formattedTime -> formattedTime.toLocalDate().format(dateFormmatter))
                .findFirst()
                .orElseThrow();
        final List<String> times = Arrays.stream(strTimes)
                .map(time -> LocalDateTime.parse(time, formatter))
                .map(formattedTime -> formattedTime.format(timeFormmatter))
                .toList();
        final String time = times.get(0) + "-" + times.get(1);
        return ParsedDuration.of(date, time);
    }
}
