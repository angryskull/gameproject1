package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeMenuActivity extends AppCompatActivity {

    Button settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppConfig.printLOG("HomeMenuActivity onCreate");

        Intent serviceintent = new Intent(this, BGMService.class);
        startService(serviceintent);
        AppConfig.printLOG("start BGMService");

        InitActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConfig.printLOG("HomeMenuActivity onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.printLOG("HomeMenuActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConfig.printLOG("HomeMenuActivity onPause");
    }

    private void InitActivity(){

    }

    public void onClickPlay(View view){
        // popup으로 난이도 확인
        Intent playIntent = new Intent(this, GameActivity.class);
        startActivity(playIntent);
    }

    public void onClickSetting(View view){
        Intent settingIntent = new Intent(this, SettingActivity.class);
        startActivity(settingIntent);
    }

    public void onClickExit(View view){
        // popup으로 나가기 확인
    }
}