package fr.utt.if26.insanealarm.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.insanealarm.model.UserSetting;

public interface UserSettingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     void insertUserSetting(UserSetting userSetting);

    @Query("DELETE FROM UserSetting")
    void deleteAll();


    @Update(entity = UserSetting.class)
     void updateUserSetting(UserSetting userSetting);

    @Query("SELECT * FROM UserSetting")
    LiveData<List<UserSetting>> getUserSetting();
}
