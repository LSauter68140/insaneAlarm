package fr.utt.if26.insanealarm.database.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class Converters {
    @TypeConverter
    public static LocalDateTime fromTimeStamp(String stringDate) {
        return LocalDateTime.parse(stringDate);
    }

    @TypeConverter
    public static String fromLocalDateTimeToStr(LocalDateTime dateTime) {
        return dateTime.toString();
    }
}
