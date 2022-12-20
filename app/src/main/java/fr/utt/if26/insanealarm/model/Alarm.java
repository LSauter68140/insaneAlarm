package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Alarm")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    protected String id;

    @NonNull
    @ColumnInfo(name = "nextRing")
    private Date nextRing;
    @NonNull
    @ColumnInfo(name = "isActivate", defaultValue = "true")
    private Boolean isActivate;
    @NonNull
    @ColumnInfo(name = "Frequency")
    private String frequency;
    @NonNull
    @ColumnInfo(name = "alarmType", defaultValue = "1")
    private Integer alarmType;
    @NonNull
    @ColumnInfo(name = "ringtonePath")
    private String ringtonePath;
    @NonNull
    @ColumnInfo(name = "snoozeId")
    private String snoozeId;
    @NonNull
    @ColumnInfo(name = "dismissMode", defaultValue = "false")
    private Boolean dismissMode;

    public Alarm( @NonNull Date nextRing, @NonNull Boolean isActivate, @NonNull String frequency, @NonNull Integer alarmType, @NonNull String ringtonePath, @NonNull String snoozeId, @NonNull Boolean dismissMode) {
        this.nextRing = nextRing;
        this.isActivate = isActivate;
        this.frequency = frequency;
        this.alarmType = alarmType;
        this.ringtonePath = ringtonePath;
        this.snoozeId = snoozeId;
        this.dismissMode = dismissMode;
    }

    public Alarm() {
        this.nextRing = new Date(0);
        this.isActivate = false;
        this.frequency = "";
        this.alarmType = 0;
        this.ringtonePath = "";
        this.snoozeId = "";
        this.dismissMode = false;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public Date getNextRing() {
        return nextRing;
    }

    public void setNextRing(@NonNull Date nextRing) {
        this.nextRing = nextRing;
    }

    @NonNull
    public Boolean getActivate() {
        return isActivate;
    }

    public void setActivate(@NonNull Boolean activate) {
        isActivate = activate;
    }

    @NonNull
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(@NonNull String frequency) {
        this.frequency = frequency;
    }

    @NonNull
    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(@NonNull Integer alarmType) {
        this.alarmType = alarmType;
    }

    @NonNull
    public String getRingtonePath() {
        return ringtonePath;
    }

    public void setRingtonePath(@NonNull String ringtonePath) {
        this.ringtonePath = ringtonePath;
    }

    @NonNull
    public String getSnoozeId() {
        return snoozeId;
    }

    public void setSnoozeId(@NonNull String snoozeId) {
        this.snoozeId = snoozeId;
    }

    @NonNull
    public Boolean getDismissMode() {
        return dismissMode;
    }

    public void setDismissMode(@NonNull Boolean dismissMode) {
        this.dismissMode = dismissMode;
    }

    public void affiche() {
        System.out.println(this);
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm{" +
                "id='" + id + '\'' +
                ", nextRing=" + nextRing +
                ", isActivate=" + isActivate +
                ", frequency='" + frequency + '\'' +
                ", alarmType=" + alarmType +
                ", ringtonePath='" + ringtonePath + '\'' +
                ", snoozeId='" + snoozeId + '\'' +
                ", dismissMode=" + dismissMode +
                '}';
    }
}
