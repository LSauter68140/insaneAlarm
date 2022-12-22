package fr.utt.if26.insanealarm.utils;

import android.content.res.Resources;

import fr.utt.if26.insanealarm.R;

public class DayTranslator {


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
            case "so":
                return isShort ? "Di" : resources.getString(R.string.sunday);
            default:
                return "";
        }
    }
}
