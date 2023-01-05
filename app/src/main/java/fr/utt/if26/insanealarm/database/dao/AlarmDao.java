package fr.utt.if26.insanealarm.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.insanealarm.model.Alarm;

@Dao
public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(Alarm alarm);

    @Query("DELETE FROM alarm")
    void deleteAll();

    @Query("DELETE from alarm WHERE id = :id")
    void deleteAlarmById(Integer id);

    @Update(entity = Alarm.class)
    void updateAlarm(Alarm alarm);

    @Query("SELECT * FROM alarm ORDER BY nextRing ASC")
    LiveData<List<Alarm>> getAlarm();
}
