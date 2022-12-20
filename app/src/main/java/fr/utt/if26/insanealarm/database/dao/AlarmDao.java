package fr.utt.if26.insanealarm.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.insanealarm.model.Alarm;

public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     void insertAlarm(Alarm alarm);

    @Query("DELETE FROM Alarm")
    void deleteAll();

    @Query("DELETE from Alarm WHERE id = :id")
    void deleteAlarmById(String id);

    @Update(entity = Alarm.class)
     void updateAlarm(Alarm m);

    @Query("SELECT * FROM Alarm ORDER BY nextRing ASC")
    LiveData<List<Alarm>>getAlarm();
}
