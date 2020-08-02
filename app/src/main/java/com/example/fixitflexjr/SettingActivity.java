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
        MainActivity.getGameSong().start();
        Switch musicSwitch = findViewById(R.id.switch_music);
        Switch soundsSwitch = findViewById(R.id.switch_sounds);
        Switch notificationsSwitch = findViewById(R.id.switch_notifications);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    MainActivity.getGameSong().stop();
                } else {
                    MainActivity.getGameSong().start();
                }
            }
        });
        soundsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getGameSong().pause();
    }

}
