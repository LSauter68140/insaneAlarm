package fr.utt.if26.insanealarm.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys = @ForeignKey(entity = Snooze.class, parentColumns = "snoozeId", childColumns = "id", onDelete = CASCADE))
@Entity(tableName = "Snooze")
public class Snooze {

    @ColumnInfo(name = "snoozeId")
    @PrimaryKey(autoGenerate = true)
    private Integer snoozeId = 0;
    @ColumnInfo(name = "isActivated")
    private Boolean isActivated;
    @ColumnInfo(name = "activateNxtSnoozeMode")
    private Integer activateNxtSnoozeMode;
    @ColumnInfo(name = "snoozeSecTime")
    private Integer snoozeSecTime;
    @ColumnInfo(name = "snoozeLimit")
    private Integer snoozeLimit;
    @Embedded
    private Control control;
    @Embedded
    private Task task;
    /*public Snooze() {
        this.activateNxtSnoozeMode = 0;
        this.deactivateMode = 0;
        this.snoozeLimit = 0;
        this.snoozeSecTime = 0;
    }*/

    public Snooze(Boolean isActivated, Integer activateNxtSnoozeMode, Control control, Integer snoozeSecTime, Integer snoozeLimit, Task task) {
        this.isActivated = isActivated;
        this.activateNxtSnoozeMode = activateNxtSnoozeMode;
        this.control = control;
        this.snoozeSecTime = snoozeSecTime;
        this.snoozeLimit = snoozeLimit;
        this.task = task;
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

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public void setActivateNxtSnoozeMode(Integer activateNxtSnoozeMode) {
        this.activateNxtSnoozeMode = activateNxtSnoozeMode;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
