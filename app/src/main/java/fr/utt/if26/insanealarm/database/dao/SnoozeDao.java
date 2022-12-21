/*package fr.utt.if26.insanealarm.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.insanealarm.model.AlarmFrequency;
import fr.utt.if26.insanealarm.model.Snooze;

public interface SnoozeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSnooze(Snooze snooze);

    @Query("DELETE FROM Snooze")
    void deleteAll();

    @Query("DELETE from Snooze WHERE id = :id")
    void deleteSnoozeById(String id);

    @Update(entity = Snooze.class)
    void updateSnooze(Snooze... snooze);

    @Query("SELECT * FROM Snooze")
    LiveData<List<AlarmFrequency>> getSnooze();

    @Query("SELECT * FROM Snooze WHERE id = :snoozeId")
    LiveData<List<AlarmFrequency>> getSnoozeForAlarm(final String snoozeId);
}
*/