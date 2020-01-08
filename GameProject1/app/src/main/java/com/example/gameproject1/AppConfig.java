package com.example.gameproject1;

import android.util.Log;

public class AppConfig {


    public static void printLOG(String log)
    {
        Log.i("GameApp", log);
    }

    private static boolean BGMState = false;
    public static boolean getBGMState() {  return BGMState; }
    public static void setBGMState(boolean state)  {   BGMState = state; }
}
