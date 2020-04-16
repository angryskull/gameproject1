package com.example.gameproject1;

import android.util.Log;

public class AppConfig {





    public static final int MSG_TIMER_SETTEXT = 1;
    public static final int MSG_ADD_BEE = 2;


    public static void printLOG(String log)
    {
        Log.i("GameApp", log);
    }

    //private static boolean BGMState = false;
    private static boolean BGMState = true;
    public static boolean getBGMState() {  return BGMState; }
    public static void setBGMState(boolean state)  {   BGMState = state; }

    //private static boolean EffectState = false;
    private static boolean EffectState = true;
    public static boolean getEffectState() {  return EffectState; }
    public static void setEffectState(boolean state)  {   EffectState = state; }

    private static int Lifevalue = 5;
    public static int getLifevalue(){
        return Lifevalue;
    }
    public static void setLifevalue(int value){
        Lifevalue = value;
    }

    private static boolean gameisTopActivity = true;
    public static boolean getGameisTopActivity() {  return gameisTopActivity; }
    public static void setGameisTopActivity(boolean state)  {   gameisTopActivity = state; }
}
