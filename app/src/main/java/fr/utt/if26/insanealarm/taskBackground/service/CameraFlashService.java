package fr.utt.if26.insanealarm.taskBackground.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CameraFlashService extends Service {

    CameraManager cameraManager;
    String cameraId;
    Integer flashLight;
    Timer blinkTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flashLight = intent.getIntExtra("flashMode", 1);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);


        try {
            cameraId = hasFlashForFrontCamera(cameraManager);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if (cameraId.equals("none")) {
            // the camera don't have a flash
            return START_NOT_STICKY;
        }
        blinkTimer = new Timer();
        if (!Objects.equals(cameraId, "")) {
            try {
                if (flashLight == 1) {
                    cameraManager.setTorchMode(cameraId, true);
                } else {
                    blinkFlashFrontCamera();
                }

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (cameraId.equals("none")) {
            // the camera don't have a flash
            return;
        }
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        blinkTimer.cancel();
    }

    private void blinkFlashFrontCamera() {
        final boolean[] isOn = {false};
        blinkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (isOn[0]) {
                        cameraManager.setTorchMode(cameraId, false);
                        isOn[0] = false;
                    } else {
                        cameraManager.setTorchMode(cameraId, true);
                        isOn[0] = true;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 300);
    }

    private String hasFlashForFrontCamera(CameraManager cManager) throws CameraAccessException {
        for (final String cameraId : cManager.getCameraIdList()) {
            CameraCharacteristics characteristics = cManager.getCameraCharacteristics(cameraId);
            int cOrientation = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (cOrientation == CameraCharacteristics.LENS_FACING_BACK) {
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    return cameraId;
                } else {
                    return "none";
                }
            }
        }
        return "none";
    }
}
