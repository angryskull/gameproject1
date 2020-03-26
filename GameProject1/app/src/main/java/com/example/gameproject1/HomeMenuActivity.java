package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class HomeMenuActivity extends AppCompatActivity {

    Button settingButton;
    private AdView mAdView;
    //private Switch bgmSwich;
    //private Switch effectSwich;
    private ToggleButton bgmButton;
    private ToggleButton effectButton;
    private LinearLayout adButton;
    private TextView lifeView;
    private ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppConfig.printLOG("HomeMenuActivity onCreate");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        adButton = findViewById(R.id.lifeplusAD);
        lifeView = findViewById(R.id.lifevalue);
        playButton = findViewById(R.id.appplay);

/*
        mAdView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = mAdView.getHeight();
                if (height > 0) {
                    // now the height is gotten, you can do things you want
                    Log.d("adHeight", pxToDp(height)+"");
                }
            }
        });
*/
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

        if(AppConfig.getLifevalue() == 0){
            playButton.setClickable(false);
            adButton.setVisibility(View.VISIBLE);
        }
        else if(AppConfig.getLifevalue() == 5){
            playButton.setClickable(true);
            adButton.setVisibility(View.INVISIBLE);
        }
        else{
            playButton.setClickable(true);
        }
        lifeView.setText(String.valueOf(AppConfig.getLifevalue()) + "/5");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConfig.printLOG("HomeMenuActivity onPause");
    }

    private void InitActivity(){
        setBgmSwich();
    }

    public void onClickPlay(View view){
        // popup으로 난이도 확인
        AppConfig.setLifevalue(AppConfig.getLifevalue() - 1);
        Intent playIntent = new Intent(this, GameActivity.class);
        startActivity(playIntent);
    }

    public void onClickAd(View view){
        AppConfig.setLifevalue(AppConfig.getLifevalue() + 1);
        Intent adIntent = new Intent(this, MainActivity.class);
        startActivity(adIntent);
    }

    public void onClickSetting(View view){
        Intent settingIntent = new Intent(this, SettingActivity.class);
        startActivity(settingIntent);
    }

    public void onClickExit(View view){
        // popup으로 나가기 확인
        finish();
    }

    private void setBgmSwich(){
        //bgmSwich = findViewById(R.id.BGMswitch);
        bgmButton = findViewById(R.id.bgmButton);
        bgmButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                Log.d("bgm button", bgmButton.isChecked() + "");
                AppConfig.printLOG("SettingActivity, BGM Switch change state - " + on);
                if(on){
                    BGMService.setBGMStatus(true);
                    AppConfig.setBGMState(true);
                    bgmButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bgm_on));
                }
                else{
                    BGMService.setBGMStatus(false);
                    AppConfig.setBGMState(false);
                    bgmButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bgm_off));
                }
            }
        });

        //effectSwich = findViewById(R.id.Effectswitch);
        effectButton = findViewById(R.id.effectButton);

        effectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                AppConfig.printLOG("SettingActivity, effect Switch change state - " + on);
                Log.d("bgm button", effectButton.isChecked() + "");
                if(on){
                    AppConfig.setEffectState(true);
                    effectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_on));
                }
                else{
                    AppConfig.setEffectState(false);
                    effectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_off));
                }
            }
        });

        if(AppConfig.getBGMState()) {
            bgmButton.setChecked(true);
            bgmButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bgm_on));
        }
        else{
            bgmButton.setChecked(false);
            bgmButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bgm_off));
        }

        if(AppConfig.getEffectState()){
            effectButton.setChecked(true);
            effectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_on));
        }
        else{
            effectButton.setChecked(false);
            effectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_off));
        }
    }
/*
    public int pxToDp(int px) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int dp = Math.round(px / (displaymetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

 */
}
