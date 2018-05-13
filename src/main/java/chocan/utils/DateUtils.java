package chocan.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 *
 */
public class DateUtils {

    /**
     *
     * @return
     */
    public static LocalDateTime[] getWeekRange() {
        final LocalDate now = LocalDate.now();
        final LocalDate firstDayOfWeek = now.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());
        final LocalDateTime firstTimeOfWeek = LocalDateTime.of(firstDayOfWeek, LocalTime.MIN);
        return new LocalDateTime[] { firstTimeOfWeek, firstTimeOfWeek.plusWeeks(1) };
    }

}
