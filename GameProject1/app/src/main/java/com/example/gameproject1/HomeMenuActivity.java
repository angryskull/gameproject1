package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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

    File bgm_file;
    File effect_file;
    File Life_file;
    FileReader fr;
    FileWriter fw;

    private RewardedAd rewardedAd;
    boolean isLoading;
    private String AD_UNIT_ID;  //rewarded

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

        Life_file = new File(getFilesDir(), "life");
        try{
            fr = new FileReader(Life_file);
            int data;
            int value = 0;
            while((data = fr.read()) != -1){
                AppConfig.printLOG("life]read data : " + data);
                value = data;
            }
            AppConfig.setLifevalue(value);

            if(fr != null) {
                fr.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AD_UNIT_ID = getString(R.string.rewarded_ad_id_for_test);
        rewardedAd = new RewardedAd(this, AD_UNIT_ID);

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
        BGMFileRead();
        setBgmSwich();
    }

    public void onClickPlay(View view){
        // popup으로 난이도 확인
        AppConfig.setLifevalue(AppConfig.getLifevalue() - 1);

        try{
            fw  = new FileWriter(Life_file);
            fw.write(AppConfig.getLifevalue());
            fw.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        Intent playIntent = new Intent(this, GameActivity.class);
        startActivity(playIntent);
    }

    public void onClickAd(View view){
//        Intent adIntent = new Intent(this, MainActivity.class);
//        startActivity(adIntent);

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                AppConfig.printLOG("AD load success");
                AppConfig.setLifevalue(AppConfig.getLifevalue() + 1);

                try{
                    fw  = new FileWriter(Life_file);
                    fw.write(AppConfig.getLifevalue());
                    fw.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

                showRewardedVideo();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                AppConfig.printLOG("AD load fail");
            }
        };

        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        AppConfig.printLOG("AD Show");
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

                    try{
                        fw  = new FileWriter(bgm_file);
                        fw.write(1);
                        fw.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    BGMService.setBGMStatus(false);
                    AppConfig.setBGMState(false);
                    bgmButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bgm_off));

                    try{
                        fw  = new FileWriter(bgm_file);
                        fw.write(0);
                        fw.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
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

                    try{
                        fw  = new FileWriter(effect_file);
                        fw.write(1);
                        fw.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    AppConfig.setEffectState(false);
                    effectButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_off));

                    try{
                        fw  = new FileWriter(effect_file);
                        fw.write(0);
                        fw.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
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

    private void BGMFileRead()
    {
        bgm_file = new File(getFilesDir(), "bgm");
        effect_file = new File(getFilesDir(), "effect");
        fr = null;

        try{
            fr = new FileReader(bgm_file);
            int data;
            int state = 0;
            while((data = fr.read()) != -1){
                AppConfig.printLOG("bgm]read data : " + data);
                state = data;
            }
            if (state == BGMService.BGM_on){
                AppConfig.setBGMState(true);
                BGMService.setBGMStatus(true);
            }
            else AppConfig.setBGMState(false);

            if(fr != null) {
                fr.close();
            }

            fr = new FileReader(effect_file);
            while((data = fr.read()) != -1){
                AppConfig.printLOG("effect]read data : " + data);
                state = data;
            }
            if (state == BGMService.BGM_on) AppConfig.setEffectState(true);
            else AppConfig.setEffectState(false);

            if(fr != null) {
                fr.close();
            }
        } catch (Exception e){
            e.printStackTrace();
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

    private void loadRewardedAd() {
        if (rewardedAd == null || !rewardedAd.isLoaded()) {
            rewardedAd = new RewardedAd(this, AD_UNIT_ID);
            isLoading = true;
            rewardedAd.loadAd(
                    new AdRequest.Builder().build(),
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onRewardedAdLoaded() {
                            // Ad successfully loaded.
                            isLoading = false;
                        }

                        @Override
                        public void onRewardedAdFailedToLoad(int errorCode) {
                            // Ad failed to load.
                            isLoading = false;
                        }
                    });
        }
    }

    private void showRewardedVideo() {
//        reviveButton.setVisibility(View.INVISIBLE);
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback =
                    new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.
                            // Preload the next video ad.
                            loadRewardedAd();
                        }

                        @Override
                        public void onUserEarnedReward(RewardItem rewardItem) {
                            // User earned reward.
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            // Ad failed to display
                        }
                    };
            rewardedAd.show(this, adCallback);
        }
    }
}
