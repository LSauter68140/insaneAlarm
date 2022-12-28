package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sound")
public class Sound {
    @PrimaryKey(autoGenerate = true)
    private Integer soundId;
    @ColumnInfo(name = "ringtonePath")
    private String ringtonePath;
    @ColumnInfo(name = "drawShape")
    private Boolean needVibration;
    @ColumnInfo(name = "alarmVolume")
    private Integer alarmVolume; // between 0 to 100
    @ColumnInfo(name = "increaseVolume")
    private Boolean increaseVolume; // to 0 to alarmVolume gradually
    @ColumnInfo(name = "isSameAsPhone")
    private Boolean isSameAsPhone;
    @ColumnInfo(name = "flashLight")
    private Integer flashLight; // 0 none - 1 stay - 2 flash

    public Sound(String ringtonePath, Boolean needVibration, Integer alarmVolume, Boolean increaseVolume, Boolean isSameAsPhone, Integer flashLight) {
        this.ringtonePath = ringtonePath;
        this.needVibration = needVibration;
        this.alarmVolume = alarmVolume;
        this.increaseVolume = increaseVolume;
        this.isSameAsPhone = isSameAsPhone;
        this.flashLight = flashLight;
    }

    public Integer getSoundId() {
        return soundId;
    }

    public void setSoundId(Integer soundId) {
        this.soundId = soundId;
    }

    public String getRingtonePath() {
        return ringtonePath;
    }

    public void setRingtonePath(String ringtonePath) {
        this.ringtonePath = ringtonePath;
    }

    public Boolean getNeedVibration() {
        return needVibration;
    }

    public void setNeedVibration(Boolean needVibration) {
        this.needVibration = needVibration;
    }

    public Integer getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(Integer alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    public Boolean getIncreaseVolume() {
        return increaseVolume;
    }

    public void setIncreaseVolume(Boolean increaseVolume) {
        this.increaseVolume = increaseVolume;
    }

    public Boolean getSameAsPhone() {
        return isSameAsPhone;
    }

    public void setSameAsPhone(Boolean sameAsPhone) {
        isSameAsPhone = sameAsPhone;
    }

    public Integer getFlashLight() {
        return flashLight;
    }

    public void setFlashLight(Integer flashLight) {
        this.flashLight = flashLight;
    }

    @NonNull
    @Override
    public String toString() {
        return "Sound{" +
                "soundId=" + soundId +
                ", ringtonePath='" + ringtonePath + '\'' +
                ", needVibration=" + needVibration +
                ", alarmVolume=" + alarmVolume +
                ", increaseVolume=" + increaseVolume +
                ", isSameAsPhone=" + isSameAsPhone +
                ", flashLight=" + flashLight +
                '}';
    }
}
