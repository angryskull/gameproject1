package com.example.gameproject1;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BGMService extends Service {

    private static MediaPlayer bgmPlayer= null;
    private static MediaPlayer effectSound = null;
    private TimerRunnable tr;
    private Thread timeThread ;

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
            effectSound = null;
        }
    }

    private void InitService()
    {
        AppConfig.printLOG("BGMService Init");

        bgmPlayer = MediaPlayer.create(this, R.raw.wavesample);
        bgmPlayer.setLooping(true);

        effectSound = MediaPlayer.create(this, R.raw.bee_die);
        effectSound.setLooping(false);

        tr = new TimerRunnable();
        timeThread = new Thread(tr);
        timeThread.start();
    }

    public static int BGM_off = 0;
    public static int BGM_on = 1;
    public static void setBGMStatus(boolean on){
        if(bgmPlayer == null) {
            return;
        }
        if(bgmPlayer.isPlaying() == on){
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

    public static void playEffectSound(){
        if(effectSound == null){
            return;
        }
        if(AppConfig.getEffectState()) {
            AppConfig.printLOG("effect Sound - on");
            effectSound.start();
        }
    }

    private class TimerRunnable implements Runnable{

        @Override
        public void run() {
            AppConfig.printLOG("Timer Thread 2 run");

            while(true){
                try{
                    Thread.sleep(200);
                    ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
                    String packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
//                    AppConfig.printLOG("Top App packageName - " + packageName);

                    if(packageName.equals("com.example.gameproject1")) {
                        AppConfig.setGameisTopActivity(true);
                    }
                    else {
                        AppConfig.setGameisTopActivity(false);
                    }

                    if(packageName.equals("com.example.gameproject1") && AppConfig.getBGMState() && !AppConfig.getADopenState()){
                        setBGMStatus(true);
                    }
                    else if(!packageName.equals("com.example.gameproject1") && AppConfig.getBGMState()){
                        setBGMStatus(false);
                    }
                }
                catch (Exception e){
                    AppConfig.printLOG("mTask Error - " + e);
                }
            }
        }
    }
}
