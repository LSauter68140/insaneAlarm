package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

import fr.utt.if26.insanealarm.database.converters.Converters;
import fr.utt.if26.insanealarm.utils.DayTimeTranslator;

//@Entity(foreignKeys = @ForeignKey(entity = AlarmFrequency.class, parentColumns = "frequencyID", childColumns = "alarmFrequencyId", onDelete = CASCADE))
@Entity(tableName = "AlarmFrequency")
public class AlarmFrequency implements Serializable {

    @ColumnInfo(name = "alarmFrequencyId")
    @PrimaryKey(autoGenerate = true)
    private Integer alarmFrequencyId = 0;
    @NonNull
    @TypeConverters(Converters.class)
    private LocalDateTime nextRing;
    @ColumnInfo(name = "monday", defaultValue = "false")
    private Boolean monday;
    @ColumnInfo(name = "tuesday", defaultValue = "false")
    private Boolean tuesday;
    @ColumnInfo(name = "wednesday", defaultValue = "false")
    private Boolean wednesday;
    @ColumnInfo(name = "thursday", defaultValue = "false")
    private Boolean thursday;
    @ColumnInfo(name = "friday", defaultValue = "false")
    private Boolean friday;
    @ColumnInfo(name = "saturday", defaultValue = "false")
    private Boolean saturday;
    @ColumnInfo(name = "sunday", defaultValue = "false")
    private Boolean sunday;

/*    public AlarmFrequency() {
        // this.nextRing = LocalDateTime.now();
        this.monday = false;
        this.tuesday = false;
        this.wednesday = false;
        this.thursday = false;
        this.friday = false;
        this.saturday = false;
        this.sunday = false;
    }*/

    public AlarmFrequency(@NonNull LocalDateTime nextRing, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.nextRing = nextRing;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;

    }

    public AlarmFrequency() {
        this.nextRing = LocalDateTime.now();
        this.monday = false;
        this.tuesday = false;
        this.wednesday = false;
        this.thursday = false;
        this.friday = false;
        this.saturday = false;
        this.sunday = false;
    }

    @NonNull
    public LocalDateTime getNextRing() {
        return nextRing;
    }

    public void setNextRing(@NonNull LocalDateTime nextRing) {
        this.nextRing = nextRing;
    }

    public void setAlarmFrequencyId(Integer alarmFrequencyId) {
        this.alarmFrequencyId = alarmFrequencyId;
    }

    public Integer getAlarmFrequencyId() {
        return alarmFrequencyId;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public ArrayList<Boolean> getAllWeek() {

        ArrayList<Boolean> week = new ArrayList<>();
        Class<? extends AlarmFrequency> clazz = this.getClass();
        Field[] f = clazz.getDeclaredFields();
        try {
            int i = 0;
            int fieldIndex = 0;
            Field field;
            do {
                field = f[fieldIndex];
                // to put days in the correct order
                if (field.getType() == Boolean.class && DayTimeTranslator.weekDays[i].equals(field.getName())) {
                    week.add((Boolean) field.get(this));
                    i++;
                }
                fieldIndex++;
                fieldIndex = fieldIndex % f.length;
            } while (i < 7);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return week;
    }

    public void setArrayToWeek(ArrayList<Boolean> week) {
        Class<? extends AlarmFrequency> clazz = this.getClass();
        Field[] f = clazz.getDeclaredFields();
        try {
            int i = 0;
            int fieldIndex = 0;
            Field field;
            do {
                field = f[fieldIndex];
                // to put days in the correct order
                if (field.getType() == Boolean.class && DayTimeTranslator.weekDays[i].equals(field.getName())) {
                    field.set(this, week.get(i));
                    i++;
                }
                fieldIndex++;
                fieldIndex = fieldIndex % f.length;
            } while (i < 7);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getWeekSchedule() {

        ArrayList<String> validDays = new ArrayList<>();
        if (this.monday) {
            validDays.add("mo");
        }
        if (this.tuesday) {
            validDays.add("tu");
        }
        if (this.wednesday) {
            validDays.add("we");
        }
        if (this.thursday) {
            validDays.add("th");
        }
        if (this.friday) {
            validDays.add("fr");
        }
        if (this.saturday) {
            validDays.add("sa");
        }
        if (this.sunday) {
            validDays.add("su");
        }

        return validDays;
    }

    @NonNull
    @Override
    public String toString() {
        return "AlarmFrequency{" +
                "alarmFrequencyId=" + alarmFrequencyId +
                ", nextRing=" + nextRing +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }
}
