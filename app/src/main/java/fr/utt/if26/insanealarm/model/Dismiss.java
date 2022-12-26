package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Dismiss")
public class Dismiss {

    @PrimaryKey(autoGenerate = true)
    private Integer dismissId;
    @Embedded
    private Control control;
    @Embedded
    private Task task;
    @ColumnInfo(name = "autoDismiss")
    private Boolean autoDismiss;


    public Dismiss(Control control, Task task, Boolean autoDismiss) {
        this.control = control;
        this.task = task;
        this.autoDismiss = autoDismiss;
    }

    public Integer getDismissId() {
        return dismissId;
    }

    public void setDismissId(Integer dismissId) {
        this.dismissId = dismissId;
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

    public Boolean getAutoDismiss() {
        return autoDismiss;
    }

    public void setAutoDismiss(Boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    @NonNull
    @Override
    public String toString() {
        return "Dismiss{" +
                "control=" + control +
                ", task=" + task +
                ", autoDismiss=" + autoDismiss +
                '}';
    }
}