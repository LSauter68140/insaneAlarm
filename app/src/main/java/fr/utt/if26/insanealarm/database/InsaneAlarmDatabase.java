package fr.utt.if26.insanealarm.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.insanealarm.database.dao.AlarmDao;
import fr.utt.if26.insanealarm.database.dao.UserSettingDao;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.model.AlarmFrequency;
import fr.utt.if26.insanealarm.model.Snooze;
import fr.utt.if26.insanealarm.model.UserSetting;

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
                                    InsaneAlarmDatabase.class, "cursus_database")
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
                                LocalDateTime.now().minusDays(1),
                                true,
                                new AlarmFrequency(
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false
                                ),
                                0,
                                "./ringtone.mp3",
                                new Snooze(0, 0, 0, 0),
                                false
                        )
                );
                alarmDao.insertAlarm(
                        new Alarm(
                                "Encore une alarme",
                                LocalDateTime.now(),
                                true,
                                new AlarmFrequency(
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false
                                ),
                                0,
                                "./apero.mp3",
                                new Snooze(0, 0, 0, 0),
                                false
                        )
                );
            });
        }
    };
}
