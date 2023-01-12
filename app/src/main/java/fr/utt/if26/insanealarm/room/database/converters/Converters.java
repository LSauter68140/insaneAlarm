package fr.utt.if26.insanealarm.room.database.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Converters {
    @TypeConverter
    public static LocalDateTime fromTimeStamp(String stringDate) {
        return LocalDateTime.parse(stringDate);
    }

    @TypeConverter
    public static String fromLocalDateTimeToStr(LocalDateTime dateTime) {
        return dateTime.toString();
    }

    @TypeConverter
    public static LocalTime fromStrToTime(String stringDate) {
        return LocalTime.parse(stringDate);
    }

    @TypeConverter
    public static String fromTimeToStr(LocalTime time) {
        return time.toString();
    }
}
