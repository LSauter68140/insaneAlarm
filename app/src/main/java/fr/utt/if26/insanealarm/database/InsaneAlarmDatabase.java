package fr.utt.if26.insanealarm.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.insanealarm.database.dao.AlarmDao;
import fr.utt.if26.insanealarm.database.dao.UserSettingDao;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.model.AlarmFrequency;
import fr.utt.if26.insanealarm.model.Control;
import fr.utt.if26.insanealarm.model.Dismiss;
import fr.utt.if26.insanealarm.model.Snooze;
import fr.utt.if26.insanealarm.model.Task;
import fr.utt.if26.insanealarm.model.UserSetting;
import fr.utt.if26.insanealarm.model.WakeupCheck;

@Database(entities = {Alarm.class, AlarmFrequency.class, UserSetting.class}, version = 1, exportSchema = false)
public abstract class InsaneAlarmDatabase extends RoomDatabase {


    // DAO
    public abstract AlarmDao alarmDao();

    public abstract UserSettingDao userSettingDao();


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // singleton
    private static volatile InsaneAlarmDatabase INSTANCE;

    static InsaneAlarmDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InsaneAlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    InsaneAlarmDatabase.class, "insaneAlarmDB")
                            .addCallback(InsaneAlarmDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // init database
    private static final RoomDatabase.Callback InsaneAlarmDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                AlarmDao alarmDao = INSTANCE.alarmDao();
                alarmDao.deleteAll();
                alarmDao.insertAlarm(
                        new Alarm(
                                "Alarme 1",
                                LocalTime.now().minusHours(1),
                                false,
                                new AlarmFrequency(
                                        LocalDateTime.now().plusDays(2),
                                        false,
                                        true,
                                        true,
                                        true,
                                        false,
                                        false,
                                        false
                                ),
                                "./ringtone.mp3",
                                new WakeupCheck(false, 0, 0),
                                new Snooze(
                                        0,
                                        new Control(false, 0, true, true, false),

                                        0,
                                        0,
                                        new Task("math", 1, 2, 60, true)
                                ),
                                new Dismiss(
                                        new Control(true, 0, false, false, false),
                                        new Task("write", 2, 1, 0, true), true)
                        )
                );
                alarmDao.insertAlarm(
                        new Alarm(
                                "Encore une alarme",
                                LocalTime.now().minusHours(1),
                                true,
                                new AlarmFrequency(
                                        LocalDateTime.now().plusDays(2),
                                        false,
                                        true,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false
                                ),

                                "./apero.mp3",
                                new WakeupCheck(true, 300, 120),
                                new Snooze(
                                        0,
                                        new Control(false, 0, true, true, false),

                                        0,
                                        0,
                                        new Task("math", 1, 2, 60, true)
                                ),
                                new Dismiss(
                                        new Control(true, 0, false, false, false),
                                        new Task("write", 2, 1, 0, true), true)
                        )
                );
            });
        }
    };
}
