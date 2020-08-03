package com.example.fixitflexjr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        if (MainActivity.isCheckMusic()) {
            MainActivity.getGameSong().start();
        }
        Switch musicSwitch = findViewById(R.id.switch_music);
        musicSwitch.setChecked(MainActivity.isCheckMusic());
        Switch soundsSwitch = findViewById(R.id.switch_sounds);
        soundsSwitch.setChecked(MainActivity.isCheckSounds());
        Switch notificationsSwitch = findViewById(R.id.switch_notifications);
        notificationsSwitch.setChecked(MainActivity.isCheckNotifications());
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.setCheckMusic(isChecked);
                if (!isChecked) {
                    MainActivity.getGameSong().pause();
                } else {
                    MainActivity.getGameSong().start();
                }
            }
        });
        soundsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.setCheckSounds(isChecked);
            }
        });
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.setCheckNotifications(isChecked);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getGameSong().pause();
    }

}
