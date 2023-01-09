package fr.utt.if26.insanealarm.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.insanealarm.database.dao.AlarmDao;
import fr.utt.if26.insanealarm.model.Alarm;

public class AlarmRepository {


    private final AlarmDao alarmDao;
    private final LiveData<List<Alarm>> allAlarm;

    public AlarmRepository(Application application) {
        InsaneAlarmDatabase db = InsaneAlarmDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        allAlarm = alarmDao.getAlarm();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return this.allAlarm;
    }

    public Alarm getAlarmById(Integer id) {
        return alarmDao.getAlarmById(id);
    }

    public void insertAlarm(Alarm alarm) {
        InsaneAlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.insertAlarm(alarm));
    }

    public void deleteAlarmById(Integer id) {
        InsaneAlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.deleteAlarmById(id));
    }

    public void updateAlarm(Alarm alarm) {
        InsaneAlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.updateAlarm(alarm));
    }

    public void deleteAll() {
        InsaneAlarmDatabase.databaseWriteExecutor.execute(alarmDao::deleteAll);
    }
}
