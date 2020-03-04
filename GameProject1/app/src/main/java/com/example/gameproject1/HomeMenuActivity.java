package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    public void onClickAd(View view){
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
/*
    public int pxToDp(int px) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int dp = Math.round(px / (displaymetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

 */
}
