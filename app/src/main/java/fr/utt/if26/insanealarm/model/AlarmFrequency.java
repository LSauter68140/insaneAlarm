package fr.utt.if26.insanealarm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "AlarmFrenquency")
public class AlarmFrequency {

    @PrimaryKey(autoGenerate = true)
    protected String id;
    @ColumnInfo(name = "nextRing")
    private Date nextRing;
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

    public AlarmFrequency(Date nextRing, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.nextRing = nextRing;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public String getId() {
        return id;
    }

    public Date getNextRing() {
        return nextRing;
    }

    public void setNextRing(Date nextRing) {
        this.nextRing = nextRing;
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

}
