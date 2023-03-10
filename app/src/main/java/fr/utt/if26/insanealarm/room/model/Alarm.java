package fr.utt.if26.insanealarm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalTime;

import fr.utt.if26.insanealarm.room.database.converters.Converters;

@Entity(tableName = "Alarm")
public class Alarm implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "label")
    private String label;
    @NonNull
    @TypeConverters(Converters.class)
    private LocalTime timeToGoOff;
    @NonNull
    @ColumnInfo(name = "isActivate", defaultValue = "true")
    private Boolean isActivate;
    @NonNull
    @Embedded
    private AlarmFrequency alarmFrequency;
    @NonNull
    @Embedded
    private Sound sound;
    @NonNull
    @Embedded(prefix = "snooze_")
    private Snooze snooze;
    @NonNull
    @Embedded(prefix = "dismiss_")
    private Dismiss dismiss;
    @NonNull
    @Embedded(prefix = "wkupChk_")
    private WakeupCheck wakeupCheck;

    public Alarm(
            @NonNull String name,
            @NonNull LocalTime timeToGoOff,
            @NonNull Boolean isActivate,
            @NonNull String label,
            @NonNull AlarmFrequency alarmFrequency,
            @NonNull Sound sound,
            @NonNull WakeupCheck wakeupCheck,
            @NonNull Snooze snooze,
            @NonNull Dismiss dismiss
    ) {
        this.name = name;
        this.timeToGoOff = timeToGoOff;
        this.isActivate = isActivate;
        this.label = label;
        this.alarmFrequency = alarmFrequency;
        this.sound = sound;
        this.snooze = snooze;
        this.dismiss = dismiss;
        this.wakeupCheck = wakeupCheck;
    }
/*
    public Alarm() {
        this.name = "";
        this.nextRing = LocalDateTime.now();
        this.isActivate = false;
        this.alarmFrequency = new AlarmFrequency(false, false, false, false, false, false, false);
        this.alarmType = 0;
        this.sound = "";
        this.snooze = new Snooze(0, 0, 0, 0);
        this.dismissMode = false;
    }*/

    @NonNull
    public LocalTime getTimeToGoOff() {
        return timeToGoOff;
    }

    public void setTimeToGoOff(@NonNull LocalTime timeToGoOff) {
        this.timeToGoOff = timeToGoOff;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public AlarmFrequency getAlarmFrequency() {
        return alarmFrequency;
    }

    public void setAlarmFrequency(@NonNull AlarmFrequency alarmFrequency) {
        this.alarmFrequency = alarmFrequency;
    }

    @NonNull

    public Snooze getSnooze() {
        return snooze;
    }

    public void setSnooze(@NonNull Snooze snooze) {
        this.snooze = snooze;
    }


    @NonNull
    public Boolean getActivate() {
        return isActivate;
    }

    public void setActivate(@NonNull Boolean activate) {
        isActivate = activate;
    }

    @NonNull
    public AlarmFrequency getFrequency() {
        return alarmFrequency;
    }

    public void setFrequency(@NonNull AlarmFrequency alarmFrequency) {
        this.alarmFrequency = alarmFrequency;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public Dismiss getDismiss() {
        return dismiss;
    }

    public void setDismiss(@NonNull Dismiss dismiss) {
        this.dismiss = dismiss;
    }

    @NonNull
    public WakeupCheck getWakeupCheck() {
        return wakeupCheck;
    }

    public void setWakeupCheck(@NonNull WakeupCheck wakeupCheck) {
        this.wakeupCheck = wakeupCheck;
    }

    @NonNull
    public String getLabel(String value) {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    @NonNull
    public Sound getSound() {
        return sound;
    }

    public void setSound(@NonNull Sound sound) {
        this.sound = sound;
    }

    public void display() {
        System.out.println(this);
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", timeToGoOff=" + timeToGoOff +
                ", isActivate=" + isActivate +
                ", alarmFrequency=" + alarmFrequency +
                ", sound=" + sound +
                ", snooze=" + snooze +
                ", dismiss=" + dismiss +
                ", wakeupCheck=" + wakeupCheck +
                '}';
    }
}

/*
 * truc ?? rajouter
 * wake up check (v??rifier qu'on est bien r??veill??)
 * snoze challenge
 * dismiss challenge
 *
 * */
