package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat s1;

    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = findViewById(R.id.switch1);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCameraFlash){
                    if(flashOn){
                        flashOn = false;
                        s1.setChecked(true);
                        try {
                            flashLightOn();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        flashOn = true;
                        s1.setChecked(false);
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"No flash available on your device",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,true);
        Toast.makeText(MainActivity.this,"Flashlight is ON",Toast.LENGTH_SHORT).show();
    }

    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,false);
        Toast.makeText(MainActivity.this,"Flashlight is OFF",Toast.LENGTH_SHORT).show();
    }

}
