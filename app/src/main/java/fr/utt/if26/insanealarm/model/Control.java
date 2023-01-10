package fr.utt.if26.insanealarm.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Control")
public class Control implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer controlId;
    @ColumnInfo(name = "buttonOnScreen")
    private boolean buttonOnScreen;
    @ColumnInfo(name = "drawShape")
    private Integer drawShape;
    @ColumnInfo(name = "volumeButton")
    private boolean volumeButton;
    @ColumnInfo(name = "powerButton")
    private boolean powerButton;
    @ColumnInfo(name = "shakeUrPhone")
    private boolean shakeUrPhone;

    public Control(boolean buttonOnScreen, Integer drawShape, boolean volumeButton, boolean powerButton, boolean shakeUrPhone) {
        this.buttonOnScreen = buttonOnScreen;
        this.drawShape = drawShape;
        this.volumeButton = volumeButton;
        this.powerButton = powerButton;
        this.shakeUrPhone = shakeUrPhone;
    }


    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public boolean isButtonOnScreen() {
        return buttonOnScreen;
    }

    public void setButtonOnScreen(boolean buttonOnScreen) {
        this.buttonOnScreen = buttonOnScreen;
    }

    public Integer getDrawShape() {
        return drawShape;
    }

    public void setDrawShape(Integer drawShape) {
        this.drawShape = drawShape;
    }

    public boolean isVolumeButton() {
        return volumeButton;
    }

    public void setVolumeButton(boolean volumeButton) {
        this.volumeButton = volumeButton;
    }

    public boolean isPowerButton() {
        return powerButton;
    }

    public void setPowerButton(boolean powerButton) {
        this.powerButton = powerButton;
    }

    public boolean isShakeUrPhone() {
        return shakeUrPhone;
    }

    public void setShakeUrPhone(boolean shakeUrPhone) {
        this.shakeUrPhone = shakeUrPhone;
    }

    @NonNull
    @Override
    public String toString() {
        return "Control{" +
                "buttonOnScreen=" + buttonOnScreen +
                ", drawShape=" + drawShape +
                ", volumeButton=" + volumeButton +
                ", powerButton=" + powerButton +
                ", shakeUrPhone=" + shakeUrPhone +
                '}';
    }
}
