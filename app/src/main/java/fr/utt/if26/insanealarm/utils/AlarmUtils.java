package fr.utt.if26.insanealarm.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AlarmUtils {

    public static Duration getDurationNextGoOff(LocalTime timeGoOff, Boolean[] nextDayGoOff) {
        LocalTime timeCurrent =
                LocalTime.parse(
                        DayTimeTranslator.readableTime((long)
                                        LocalTime.now().getHour(),
                                (long) LocalTime.now().getMinute(), 0L)
                );

        Duration totalDuration = Duration.between(timeCurrent, timeGoOff);

        int currentDay = LocalDate.now().getDayOfWeek().getValue();
        int index = currentDay;
        // check next day
        do {
            index += 1;
            index = index % 7;
        } while (index != currentDay && !nextDayGoOff[index]);
        //compute day between
        int dayBetween = ((index - currentDay) + 7) % 7;
        totalDuration = totalDuration.plusDays(dayBetween);

        // convert into readable time;
        if (totalDuration.toMinutes() < 0) {
            if (nextDayGoOff[currentDay]) {
                // add a week if the time is done and we want an alarm the next week and only this day
                totalDuration = totalDuration.plusDays(7);
            } else {
                totalDuration = totalDuration.plusDays(1);
            }
        }
        return totalDuration;
    }
}
