package com.example.gameproject1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;


public class SettingActivity extends Activity {

    private Switch bgmSwich;
    private Switch effectSwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        InitActivity();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void InitActivity(){
        setBgmSwich();
    }

    private void setBgmSwich(){
        bgmSwich = findViewById(R.id.BGMswitch);
        bgmSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                AppConfig.printLOG("SettingActivity, BGM Switch change state - " + on);
                if(on){
                    BGMService.setBGMStatus(true);
                    AppConfig.setBGMState(true);
                }
                else{
                    BGMService.setBGMStatus(false);
                    AppConfig.setBGMState(false);
                }
            }
        });

        effectSwich = findViewById(R.id.Effectswitch);
        effectSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                AppConfig.printLOG("SettingActivity, effect Switch change state - " + on);
                if(on){
                    AppConfig.setEffectState(true);
                }
                else{
                    AppConfig.setEffectState(false);
                }
            }
        });

        if(AppConfig.getBGMState()) {
            bgmSwich.setChecked(true);
        }
        else{
            bgmSwich.setChecked(false);
        }

        if(AppConfig.getEffectState()){
            effectSwich.setChecked(true);
        }
        else{
            effectSwich.setChecked(false);
        }
    }
}
