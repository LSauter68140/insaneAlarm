package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalTime;

import fr.utt.if26.insanealarm.database.converters.Converters;

@Entity(tableName = "Alarm")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @TypeConverters(Converters.class)
    private LocalTime timeToGoOff;
    @NonNull
    @ColumnInfo(name = "isActivate", defaultValue = "true")
    private Boolean isActivate;
    @Embedded
    private AlarmFrequency alarmFrequency;
    @NonNull
    @ColumnInfo(name = "ringtonePath")
    private String ringtonePath;
    @Embedded(prefix = "snooze_")
    private Snooze snooze;
    @Embedded(prefix = "dismiss_")
    private Dismiss dismiss;
    @Embedded(prefix = "wkupChk_")
    private WakeupCheck wakeupCheck;

    public Alarm(
            @NonNull String name,
            @NonNull LocalTime timeToGoOff,
            @NonNull Boolean isActivate,
            @NonNull AlarmFrequency alarmFrequency,
            @NonNull String ringtonePath,
            @NonNull WakeupCheck wakeupCheck,
            Snooze snooze,
            Dismiss dismiss
    ) {
        this.name = name;
        this.timeToGoOff = timeToGoOff;
        this.isActivate = isActivate;
        this.alarmFrequency = alarmFrequency;
        this.ringtonePath = ringtonePath;
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
        this.ringtonePath = "";
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

    public AlarmFrequency getAlarmFrequency() {
        return alarmFrequency;
    }

    public void setAlarmFrequency(AlarmFrequency alarmFrequency) {
        this.alarmFrequency = alarmFrequency;
    }

    public Snooze getSnooze() {
        return snooze;
    }

    public void setSnooze(Snooze snooze) {
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
    public String getRingtonePath() {
        return ringtonePath;
    }

    public void setRingtonePath(@NonNull String ringtonePath) {
        this.ringtonePath = ringtonePath;
    }

    @NonNull
    public Snooze getSnoozeId() {
        return snooze;
    }

    public void setSnoozeId(@NonNull Snooze snooze) {
        this.snooze = snooze;
    }
    

    public Dismiss getDismiss() {
        return dismiss;
    }

    public void setDismiss(Dismiss dismiss) {
        this.dismiss = dismiss;
    }

    public WakeupCheck getWakeupCheck() {
        return wakeupCheck;
    }

    public void setWakeupCheck(WakeupCheck wakeupCheck) {
        this.wakeupCheck = wakeupCheck;
    }

    public void affiche() {
        System.out.println(this);
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeToGoOff=" + timeToGoOff +
                ", isActivate=" + isActivate +
                ", alarmFrequency=" + alarmFrequency +
                ", ringtonePath='" + ringtonePath + '\'' +
                ", snooze=" + snooze +
                ", dismiss=" + dismiss +
                ", wakeupCheck=" + wakeupCheck +
                '}';
    }
}

/*
 * truc à rajouter
 * wake up check (vérifier qu'on est bien réveillé)
 * snoze challenge
 * dismiss challenge
 *
 * */
