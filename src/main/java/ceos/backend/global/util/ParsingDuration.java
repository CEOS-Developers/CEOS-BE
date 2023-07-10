package ceos.backend.global.util;

import ceos.backend.global.common.dto.ParsedDuration;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ParsingDuration {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    private static final DateTimeFormatter yearDateFormmatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter dateFormmatter = DateTimeFormatter.ofPattern("MM/dd");
    private static final DateTimeFormatter timeFormmatter = DateTimeFormatter.ofPattern("HH:mm");

    public static ParsedDuration parsingDuration(String duration) {
        final String[] strTimes = duration.split(" - ");
        final String date = doDateFormatting(strTimes, false);
        final String time = doTimeFormatting(strTimes);
        return ParsedDuration.of(date, time);
    }

    public static ParsedDuration parsingYearDuration(String duration) {
        final String[] strTimes = duration.split(" - ");
        final String date = doDateFormatting(strTimes, true);
        final String time = doTimeFormatting(strTimes);
        return ParsedDuration.of(date, time);
    }

    private static String doDateFormatting(String[] times, Boolean includeYear) {
        return Arrays.stream(times)
                .map(time -> LocalDateTime.parse(time, formatter))
                .map(formattedTime -> formattedTime.toLocalDate().format(selectFormatter(includeYear)))
                .findFirst()
                .orElseThrow();
    }

    private static DateTimeFormatter selectFormatter(Boolean includeYear) {
        return includeYear ? yearDateFormmatter : dateFormmatter;
    }

    private static String doTimeFormatting(String[] strTimes) {
        final List<String> times = Arrays.stream(strTimes)
                .map(time -> LocalDateTime.parse(time, formatter))
                .map(formattedTime -> formattedTime.format(timeFormmatter))
                .toList();
        return times.get(0) + "-" + times.get(1);
    }
}
