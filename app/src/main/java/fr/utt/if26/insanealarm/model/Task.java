package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private Integer taskId;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "difficulty")
    private Integer difficulty;
    @ColumnInfo(name = "numberTask")
    private Integer numberTask;
    @ColumnInfo(name = "timer")
    private Integer timer;
    @ColumnInfo(name = "canSkip")
    private boolean canSkip;

    public Task(String type, Integer difficulty, Integer numberTask, Integer timer, boolean canSkip) {
        this.type = type;
        this.difficulty = difficulty;
        this.numberTask = numberTask;
        this.timer = timer;
        this.canSkip = canSkip;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(Integer numberTask) {
        this.numberTask = numberTask;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public boolean isCanSkip() {
        return canSkip;
    }

    public void setCanSkip(boolean canSkip) {
        this.canSkip = canSkip;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "type='" + type + '\'' +
                ", difficulty=" + difficulty +
                ", numberTask=" + numberTask +
                ", timer=" + timer +
                ", canSkip=" + canSkip +
                '}';
    }
}
