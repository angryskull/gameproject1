package com.example.gameproject1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BGMService extends Service {

    private static MediaPlayer bgmPlayer= null;

    @Override
    public void onCreate() {
        AppConfig.printLOG("BGMService onCreate");
        InitService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        AppConfig.printLOG("BGMService onDestroy");
        if(bgmPlayer != null) {
            bgmPlayer = null;
        }
    }

    private void InitService()
    {
        AppConfig.printLOG("BGMService Init");

        bgmPlayer = MediaPlayer.create(this, R.raw.wavesample);
        bgmPlayer.setLooping(true);
        if(AppConfig.getBGMState()) {
            bgmPlayer.start();
        }
    }

    public static void setBGMStatus(boolean on){
        if(bgmPlayer == null) {
            return;
        }
        AppConfig.printLOG("setBGMStatus - " + on);

        if (on){
            bgmPlayer.start();
        }
        else{
            bgmPlayer.pause();
        }
    }
}
