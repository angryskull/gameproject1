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

    private static boolean EffectState = false;
    public static boolean getEffectState() {  return EffectState; }
    public static void setEffectState(boolean state)  {   EffectState = state; }

    private static int Lifevalue = 0;
    public static int getLifevalue(){
        return Lifevalue;
    }
    public static void setLifevalue(int value){
        Lifevalue = value;

    }
}
