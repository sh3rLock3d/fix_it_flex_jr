package com.example.fixitflexjr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingActivity extends Activity {
    private boolean checkMusic = true;
    private boolean checkSounds = true;
    private boolean checkNotifications = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        MainActivity.getGameSong().start();
        Switch musicSwitch = findViewById(R.id.switch_music);
        musicSwitch.setChecked(checkMusic);
        Switch soundsSwitch = findViewById(R.id.switch_sounds);
        soundsSwitch.setChecked(checkSounds);
        Switch notificationsSwitch = findViewById(R.id.switch_notifications);
        notificationsSwitch.setChecked(checkNotifications);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMusic = isChecked;
                if (!isChecked) {
                    MainActivity.getGameSong().stop();
                } else {
                    MainActivity.getGameSong().reset();
                }
            }
        });
        soundsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSounds = isChecked;
            }
        });
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkNotifications = isChecked;
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getGameSong().pause();
    }

}
