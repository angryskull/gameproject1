package com.example.gameproject1;

import android.util.Log;

public class AppConfig {





    public static final int MSG_TIMER_SETTEXT = 1;


    public static void printLOG(String log)
    {
        Log.i("GameApp", log);
    }

    private static boolean BGMState = false;
    public static boolean getBGMState() {  return BGMState; }
    public static void setBGMState(boolean state)  {   BGMState = state; }
}
