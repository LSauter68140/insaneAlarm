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
import fr.utt.if26.insanealarm.model.Sound;
import fr.utt.if26.insanealarm.model.Task;
import fr.utt.if26.insanealarm.model.UserSetting;
import fr.utt.if26.insanealarm.model.WakeupCheck;

@Database(entities = {
        Alarm.class,
        AlarmFrequency.class,
        UserSetting.class,
        Snooze.class,
        Control.class,
        Dismiss.class,
        Task.class,
        WakeupCheck.class
},
        version = 1,
        exportSchema = false
)
public abstract class InsaneAlarmDatabase extends RoomDatabase {


    // DAO
    public abstract AlarmDao alarmDao();

    public abstract UserSettingDao userSettingDao();


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // singleton
    private static volatile InsaneAlarmDatabase INSTANCE;

    public static InsaneAlarmDatabase getDatabase(final Context context) {
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
                                "C'est meme plus la peine de se lever je crois !",
                                new AlarmFrequency(
                                        LocalDateTime.now().minusHours(1),
                                        false,
                                        true,
                                        true,
                                        true,
                                        false,
                                        false,
                                        false
                                ),
                                new Sound("/apero.mp3", false, 0, true, true, 0),
                                new WakeupCheck(false, 0, 0),
                                new Snooze(
                                        true,
                                        0,
                                        new Control(false, 0, true, true, false),

                                        0,
                                        0,
                                        new Task("math", 1, 2, 60, true, false)
                                ),
                                new Dismiss(
                                        new Control(true, 0, false, false, false),
                                        new Task("write", 2, 1, 0, true, true), false, 0)
                        )
                );
                alarmDao.insertAlarm(
                        new Alarm(
                                "Encore une alarme",
                                LocalTime.now().minusHours(3),
                                true,
                                "Oh tu vas arriver en retard",
                                new AlarmFrequency(
                                        LocalDateTime.now().minusHours(3),
                                        false,
                                        true,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false
                                ),
                                new Sound("/ringtone.mp3", true, 100, true, false, 1),
                                new WakeupCheck(true, 300, 120),
                                new Snooze(
                                        false,
                                        0,
                                        new Control(false, 0, true, true, false),

                                        0,
                                        0,
                                        new Task("math", 1, 2, 60, true, true)
                                ),
                                new Dismiss(
                                        new Control(true, 0, false, false, false),
                                        new Task("write", 2, 1, 0, true, false), true, 70)
                        )
                );
            });
        }
    };
}
