package fr.utt.if26.insanealarm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = Snooze.class, parentColumns = "snoozeId", childColumns = "id", onDelete = CASCADE))
@Entity(tableName = "Snooze")
public class Snooze {

    @ColumnInfo(name = "snoozeId")
    @PrimaryKey(autoGenerate = true)
    private Integer snoozeId = 0;
    @ColumnInfo(name = "activateNxtSnoozeMode")
    private Integer activateNxtSnoozeMode;
    @ColumnInfo(name = "deactivateMode")
    private Integer deactivateMode;
    @ColumnInfo(name = "snoozeSecTime")
    private Integer snoozeSecTime;
    @ColumnInfo(name = "snoozeLimit")
    private Integer snoozeLimit;

    /*public Snooze() {
        this.activateNxtSnoozeMode = 0;
        this.deactivateMode = 0;
        this.snoozeLimit = 0;
        this.snoozeSecTime = 0;
    }*/

    public Snooze(Integer activateNxtSnoozeMode, Integer deactivateMode, Integer snoozeSecTime, Integer snoozeLimit) {
        this.activateNxtSnoozeMode = activateNxtSnoozeMode;
        this.deactivateMode = deactivateMode;
        this.snoozeSecTime = snoozeSecTime;
        this.snoozeLimit = snoozeLimit;
    }

    public void setSnoozeId(Integer snoozeId) {
        this.snoozeId = snoozeId;
    }

    public Integer getSnoozeId() {
        return snoozeId;
    }

    public Integer getActivateNxtSnoozeMode() {
        return activateNxtSnoozeMode;
    }

    public void setActivateNxtSnoozeMode(Integer activateNxtSnoozeMode) {
        this.activateNxtSnoozeMode = activateNxtSnoozeMode;
    }

    public Integer getDeactivateMode() {
        return deactivateMode;
    }

    public void setDeactivateMode(Integer deactivateMode) {
        this.deactivateMode = deactivateMode;
    }

    public Integer getSnoozeSecTime() {
        return snoozeSecTime;
    }

    public void setSnoozeSecTime(Integer snoozeSecTime) {
        this.snoozeSecTime = snoozeSecTime;
    }

    public Integer getSnoozeLimit() {
        return snoozeLimit;
    }

    public void setSnoozeLimit(Integer snoozeLimit) {
        this.snoozeLimit = snoozeLimit;
    }
}
