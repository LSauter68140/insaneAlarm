/*package fr.utt.if26.insanealarm.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.insanealarm.model.AlarmFrequency;

public interface AlarmFrequencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlarmFrequency(AlarmFrequency alarmFrequency);

    @Query("DELETE FROM AlarmFrequency")
    void deleteAll();

    @Query("DELETE from AlarmFrequency WHERE alarmFrequencyId = :id")
    void deleteAlarmById(String id);

    @Update(entity = AlarmFrequency.class)
    void updateAlarmFrequency(AlarmFrequency... alarmFrequency);

    @Query("SELECT * FROM AlarmFrequency order by nextRing")
    LiveData<List<AlarmFrequency>> getAlarmFrenquency();

    @Query("SELECT * FROM ALARMFREQUENCY WHERE alarmFrequencyId = :frequencyId")
    LiveData<List<AlarmFrequency>> getAlarmFrequencyForAlarm(final String frequencyId);
}*/
