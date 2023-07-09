package ceos.backend.global.util;

import ceos.backend.domain.application.vo.InterviewDateTimesVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DurationFormatter {
    public static List<String> toStringDuration(List<InterviewDateTimesVo> times) {
        List<String> stringDuration = new ArrayList<>();
        times.forEach(timesVo -> {
            stringDuration.addAll(dateTimeAdder(timesVo));
        });
        return stringDuration;
    }

    private static List<String> dateTimeAdder(InterviewDateTimesVo timesVo) {
        final String date = dateformatter(timesVo.getDate());
        return timesVo.getDurations().stream().map(timeVo -> toDuration(date, timeVo))
                .collect(Collectors.toList());
    }

    private static String dateformatter(String date) {
        return date.replaceAll("/", ".");
    }

    private static String toDuration(String date, String timeVo) {
        final String[] times = timeParser(timeVo);
        return date + ' ' + times[0] + ":00 - " + date + ' ' + times[1] + ":00";
    }

    private static String[] timeParser(String time) {
        return time.split("-");
    }

}
