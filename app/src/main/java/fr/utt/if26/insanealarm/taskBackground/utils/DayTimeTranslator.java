package fr.utt.if26.insanealarm.taskBackground.utils;

import android.content.res.Resources;

import fr.utt.if26.insanealarm.R;

public class DayTimeTranslator {

    public final static String[] weekDays = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    public static String getDay(String abr, Boolean isShort, Resources resources) {
        switch (abr) {
            case "mo":
                return isShort ? "Lu" : resources.getString(R.string.monday);
            case "tu":
                return isShort ? "Ma" : resources.getString(R.string.tuesday);
            case "we":
                return isShort ? "Me" : resources.getString(R.string.wednesday);
            case "th":
                return isShort ? "Je" : resources.getString(R.string.thursday);
            case "fr":
                return isShort ? "Ve" : resources.getString(R.string.friday);
            case "sa":
                return isShort ? "Sa" : resources.getString(R.string.saturday);
            case "su":
                return isShort ? "Di" : resources.getString(R.string.sunday);
            default:
                return "";
        }
    }

    public static String readableTime(Long h, Long m, Long s) {
        return readableTime(h, m) + ":" + (s < 10 ? "0" + s : String.valueOf(s));
    }

    public static String readableTime(Long h, Long m) {
        return (h < 10 ? "0" + h : String.valueOf(h))
                + ":"
                + (m < 10 ? "0" + m : String.valueOf(m));
    }
}
