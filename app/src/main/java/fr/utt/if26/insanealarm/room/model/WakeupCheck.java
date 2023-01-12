package fr.utt.if26.insanealarm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "wakeupCheck")
public class WakeupCheck implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer wakeupCheckId;
    @ColumnInfo(name = "isActivate")
    private Boolean isActivate;
    @ColumnInfo(name = "delayAfterDismiss")
    private Integer delayAfterDismiss;
    @ColumnInfo(name = "countdownTime")
    private Integer countdownTime;

    public WakeupCheck(Boolean isActivate, Integer delayAfterDismiss, Integer countdownTime) {
        this.isActivate = isActivate;
        this.delayAfterDismiss = delayAfterDismiss;
        this.countdownTime = countdownTime;
    }

    public Integer getWakeupCheckId() {
        return wakeupCheckId;
    }

    public void setWakeupCheckId(Integer wakeupCheckId) {
        this.wakeupCheckId = wakeupCheckId;
    }

    public Boolean getActivate() {
        return isActivate;
    }

    public void setActivate(Boolean activate) {
        isActivate = activate;
    }

    public Integer getDelayAfterDismiss() {
        return delayAfterDismiss;
    }

    public void setDelayAfterDismiss(Integer delayAfterDismiss) {
        this.delayAfterDismiss = delayAfterDismiss;
    }

    public Integer getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(Integer countdownTime) {
        this.countdownTime = countdownTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "wakeupCheck{" +
                "isActivate=" + isActivate +
                ", delayAfterDismiss=" + delayAfterDismiss +
                ", countdownTime=" + countdownTime +
                '}';
    }
}
