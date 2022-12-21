package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserSetting")
public class UserSetting {

    @PrimaryKey(autoGenerate = true)
    private Integer userID;

    @NonNull
    @ColumnInfo(name = "displayMode", defaultValue = "light")
    private String displayMode;
    @NonNull
    @ColumnInfo(name = "timeFormat", defaultValue = "24h")
    private String timeFormat;

    public UserSetting(Integer userID, @NonNull String displayMode, @NonNull String timeFormat) {
        this.userID = userID;
        this.displayMode = displayMode;
        this.timeFormat = timeFormat;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @NonNull
    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(@NonNull String displayMode) {
        this.displayMode = displayMode;
    }

    @NonNull
    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(@NonNull String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
