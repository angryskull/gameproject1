package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerokol.views.joystickView.JoystickView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameActivity extends AppCompatActivity{
    private TextView mText;
    private int timervalue = 3;
    private float UserSpeed = 0.3f;
    private String strTime;
    private static String bestTime;
    private static int bestTimeScore = 0;
    private ImageView mUserCharacter;
    private TextView angleTextView;
    private TextView powerTextView;
    private TextView directionTextView;



    // Importing also other views
    private JoystickView joystick;
    private View gameView;

    private CountDownTimer countdown;
    private TimerRunnable tr;
    private Thread timeThread ;
    private boolean isGamePlaying = true;
    private boolean isPause = false;
    private TextView timescore;
    private ImageButton pauseButton;

    //for debugging
    private TextView text_angle;
    private TextView text_power;

    float resolution_width = 0;
    float resolution_height = 0;
    private Boolean GameOver = false;
    private Boolean GoHome = false;
    //벌 추가
    Bee bees[] = new Bee[100];
    private int beeLevel = 5;

    File rank_file;
    FileWriter fw;
    FileReader fr;

    int totbestScore = 0;
    String str_totbestScore = "";

    //float scale = getResources().getDisplayMetrics().density;
    //float scale = 2;
    public void ReadSprite(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mText=(TextView)findViewById(R.id.TextView1);
        mUserCharacter=(ImageView)findViewById(R.id.UserCharacter);
        mUserCharacter.setImageResource(R.drawable.honey_tmp);
        pauseButton = (ImageButton)findViewById(R.id.pausebutton);
        timescore = (TextView) findViewById(R.id.timer);

        text_angle = (TextView) findViewById(R.id.angle);
        text_power = (TextView) findViewById(R.id.power);

        resolution_width = getResolutionWidth();
        resolution_height = getResolutionHeight();

        tr = new TimerRunnable();
        timeThread = new Thread(tr);
        isPause = true;
        countdown = new CountDownTimer(4 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) { // 총 시간과 주기
                mText.setText(String.valueOf(timervalue--));
                if(timervalue == -1) {
                    isPause = false;
                    mText.setVisibility(View.INVISIBLE);
                    mUserCharacter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                AppConfig.printLOG("3,2,1 count finished");
                //벌 생성
                initializeBees();

            }
        };

        timeThread.start();

//        angleTextView = (TextView) findViewById(R.id.angleTextView);
//        powerTextView = (TextView) findViewById(R.id.powerTextView);
//        directionTextView = (TextView) findViewById(R.id.directionTextView);
        //Referencing also other views
        joystick = (JoystickView) findViewById(R.id.joystickView);
        //Event listener that always returns the variation of the angle in degrees, motion power in percentage and direction of movement

        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
                //                angleTextView.setText(" " + String.valueOf(angle) + "°");
                //                powerTextView.setText(" " + String.valueOf(power) + "%");

                if(isPause){
                    AppConfig.printLOG("isPause - true, honey don't move");
                    return;
                }

                String angle_t = "angle : "+ angle;
                String power_t = "power : "+ power;

//                text_angle.setText(angle_t);
                text_power.setText(power_t);

                /*
                switch (direction) {
                    case JoystickView.FRONT:
                        //directionTextView.setText(R.string.front_lab);
                        mUserCharacter.setX(mUserCharacter.getX());
                        mUserCharacter.setY(mUserCharacter.getY() - (float)(1.0 * UserSpeed));
                        break;
                    case JoystickView.FRONT_RIGHT:
                        //directionTextView.setText(R.string.front_right_lab);
                        mUserCharacter.setX(mUserCharacter.getX() - (float)(0.8 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY() - (float)(0.8 * UserSpeed));
                        break;
                    case JoystickView.RIGHT:
                        //directionTextView.setText(R.string.right_lab);
                        mUserCharacter.setX(mUserCharacter.getX() - (float)(1.0 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY());
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        //directionTextView.setText(R.string.right_bottom_lab);
                        mUserCharacter.setX(mUserCharacter.getX() - (float)(0.8 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY() + (float)(0.8 * UserSpeed));
                        break;
                    case JoystickView.BOTTOM:
                        //directionTextView.setText(R.string.bottom_lab);
                        mUserCharacter.setX(mUserCharacter.getX());
                        mUserCharacter.setY(mUserCharacter.getY() + (float)(1.0 * UserSpeed));
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        //directionTextView.setText(R.string.bottom_left_lab);
                        mUserCharacter.setX(mUserCharacter.getX() + (float)(0.8 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY() + (float)(0.8 * UserSpeed));
                        break;
                    case JoystickView.LEFT:
                        //directionTextView.setText(R.string.left_lab);
                        mUserCharacter.setX(mUserCharacter.getX() + (float)(1.0 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY());
                        break;
                    case JoystickView.LEFT_FRONT:
                        //directionTextView.setText(R.string.left_front_lab);
                        mUserCharacter.setX(mUserCharacter.getX() + (float)(0.8 * UserSpeed));
                        mUserCharacter.setY(mUserCharacter.getY() - (float)(0.8 * UserSpeed));
                        break;
                    default:
                        //directionTextView.setText(R.string.center_lab);
                }*/

                float anglevalue_x = 0f;
                float anglevalue_y = 0f;
                float powervalue = 0f;

                if(angle >= 0){
                    if (angle <= 90){
                        anglevalue_x = angle;
                        anglevalue_y = 90 - angle;
                    }else{
                        anglevalue_x = 180 - angle;
                        anglevalue_y = 90 - angle;
                    }
                }
                else{
                    if (angle >= -90){
                        anglevalue_x = angle;
                        anglevalue_y = 90 + angle;
                    }else{
                        anglevalue_x = -180 - angle;
                        anglevalue_y = 90 + angle;
                    }
                }
                //powervalue = power<=30?power:30;
                powervalue = power / 2;

                float dx = anglevalue_x * 1/90 * powervalue;
                float dy = anglevalue_y * 1/90 * powervalue;

                if(isGamePlaying) {
                    mUserCharacter.setX(mUserCharacter.getX() + (dx * UserSpeed));
                    mUserCharacter.setY(mUserCharacter.getY() - (dy * UserSpeed));
                }

                if(mUserCharacter.getX() > resolution_width - 100) mUserCharacter.setX(resolution_width - 100);
                if(mUserCharacter.getY() > resolution_height - 100) mUserCharacter.setY(resolution_height - 100);
                if(mUserCharacter.getX() < 0) mUserCharacter.setX(0);
                if(mUserCharacter.getY() < 0) mUserCharacter.setY(0);

            }
        }, 10);

        //벌 움직이기 위한 타이머
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < beeLevel; i++){
                            if(GameOver)    break;
                            if(isPause)     continue;

//                            try {
//                                while (isPause) {
//                                    Thread.sleep(100);
//                                }
//                            }
//                            catch (Exception e){
//                                AppConfig.printLOG("Bee move timer Exception");
//                            }

                            bees[i].moveBee(mUserCharacter.getX(), mUserCharacter.getY());
                            bees[i].setPosition();
                            /*
                            if(bees[i].getanimidx() < 25)
                                bee_images[i].setImageResource(R.drawable.bee_tmp2_2);
                            else
                                bee_images[i].setImageResource(R.drawable.bee_tmp2_3);

                             */
                            if(((int)bees[i].getX() - 75 < (int)mUserCharacter.getX() && (int)mUserCharacter.getX() < (int)bees[i].getX() + 75)
                                    && ((int)bees[i].getY() - 75 < (int)mUserCharacter.getY() && (int)mUserCharacter.getY() < (int)bees[i].getY() + 75)) {
                                if(isCollisionDetected((View)mUserCharacter, (int)mUserCharacter.getX(), (int)mUserCharacter.getY(), bees[i], (int)bees[i].getX(), (int)bees[i].getY())){
                                    //if(isCollisionDetected((View)mUserCharacter, (int)mUserCharacter.getX(), (int)mUserCharacter.getY(), bee_images[i], (int)bees[i].getX(), (int)bees[i].getY())) {
                                    //timer.cancel(); 타이머 캔슬 필요
                                    GameOver = true;
                                    isPause = true;
                                    isGamePlaying = false;
                                    AppConfig.printLOG("닿았음. 은노 아야");
//                            Log.e("닿았음", "은노 아야");
                                    joystick.setEnabled(false);
                                    //mediaPlayer.stop();
                                    //mediaPlayer = MediaPlayer.create(getContext(), R.raw.collision);
                                    //mediaPlayer.start();
                                    //showCollisionDialog();
                                    break;
                                }
                            }
                        }
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 4500, 10);
    }

    public static boolean isCollisionDetected(View view1, int x1, int y1, View view2, int x2, int y2) {

        Bitmap bitmap1 = getViewBitmap(view1);
        Bitmap bitmap2 = getViewBitmap(view2);;
        if (bitmap1 == null || bitmap2 == null) { throw new IllegalArgumentException("bitmaps cannot be null"); }

        Rect bounds1 = new Rect(x1, y1, x1 + bitmap1.getWidth(), y1 + bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2 + bitmap2.getWidth(), y2 + bitmap2.getHeight());
        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);

            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i - x1, j - y1);
                    int bitmap2Pixel = bitmap2.getPixel(i - x2, j - y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        bitmap1.recycle();
                        bitmap2.recycle();
                        return true;
                    }
                }
            }
        }
        bitmap1.recycle();
        bitmap2.recycle();
        return false;
    }
    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }
    private static Bitmap getViewBitmap(View v) {
        if (v.getMeasuredHeight() <= 0) {
            int specWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(specWidth, specWidth);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }
    private float getResolutionWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private float getResolutionHeight(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    private ConstraintLayout mainLayout;
    private void initializeBees(){
        //벌 생성
        mainLayout = findViewById(R.id.MainLinerLayout);

        for(int i = 0; i < beeLevel; i++){
            bees[i] = new Bee(this, resolution_width, resolution_height, mUserCharacter.getX(), mUserCharacter.getY());
            /*
            if(bees[i].getDirection_x() < 0){
                bees[i].setImageResource(R.drawable.bee_icon_left);
            }
            else{
                bees[i].setImageResource(R.drawable.bee_icon);
            }
             */
            bees[i].setPosition();
            mainLayout.addView(bees[i]);
        }
    }

    private void addBee(int beeCount)
    {
        bees[beeCount] = new Bee(this, resolution_width, resolution_height, mUserCharacter.getX(), mUserCharacter.getY());
        bees[beeCount].setPosition();
        mainLayout.addView(bees[beeCount]);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        pauseButton.setImageResource(R.drawable.playbutton);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        isGamePlaying = false;
    }

    @Override
    protected void onStop(){
        super.onStop();
    }


    public void onClickPause(View view){
        AppConfig.printLOG("Pause - " + isPause);
        if(!isPause){
            isPause = true;
            pauseButton.setImageResource(R.drawable.playbutton);
        }
        else{
            isPause = false;
            pauseButton.setImageResource(R.drawable.pausebutton);
        }
    }

    private class TimerRunnable implements Runnable{
        int minTime = 0;
        int secTime = 0;
        int msecTime = 0;
        int Threadtime = 0;
        int timeScore = 0;



        @Override
        public void run() {
            AppConfig.printLOG("Timer Thread run");

            countdown.start();

            try {
                Thread.sleep(3100);
                while (isGamePlaying) {
                    while (!isPause) {

                        Thread.sleep(10);
                        Threadtime = Threadtime + 1;

                        msecTime = Threadtime % 100;
                        secTime = (Threadtime / 100) % 60;
                        minTime = (Threadtime / 6000);

                        strTime = String.format("%02d:%02d:%02d", minTime, secTime, msecTime);
                        timeScore = minTime *10000 + secTime * 100 + msecTime;

                        Message msg = new Message();
                        msg.what = AppConfig.MSG_TIMER_SETTEXT;
                        msg.obj = strTime;
                        handler.sendMessage(msg);

                        if(beeLevel != 5 + (minTime * 12) + secTime / 5)
                        {
                            addBee(beeLevel);
                            beeLevel++;

                            Message msg2 = new Message();
                            msg2.what = AppConfig.MSG_BEECOUNT_SETTEXT;
                            msg2.obj = String.valueOf(beeLevel);
                            handler.sendMessage(msg2);
                        }
                    }

                    while(isPause){
                        Thread.sleep(100);
                        if(!isGamePlaying){
                            break;
                        }
                    }
                }

                if(!AppConfig.getGameisTopActivity()) {
                    return;
                }

                rank_file = new File(getFilesDir(), "rank");
                fw = null;
                fr = null;
                try{
                    fr = new FileReader(rank_file);
                    int data;
                    str_totbestScore = "";
                    while((data = fr.read()) != -1){
                        AppConfig.printLOG("rank]read data : " + data);
                        str_totbestScore += Integer.toString(data);
                    }
                    AppConfig.printLOG("rank]str_totbestScore : " + str_totbestScore);
                    if (str_totbestScore.equals("")) totbestScore = 0;
                    else totbestScore = Integer.parseInt(str_totbestScore);
                    AppConfig.printLOG("rank]totbestScore : " + totbestScore);
                    AppConfig.printLOG("rank]timeScore : " + timeScore);

                } catch (Exception e){
                    e.printStackTrace();
                }
                if(totbestScore < timeScore){
                    try{
                        fw  = new FileWriter(rank_file);
                        totbestScore = timeScore;
                        bestTime = strTime;

                        fw.write(totbestScore);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    minTime = totbestScore/10000;
                    secTime = (totbestScore%10000)/100;
                    msecTime = totbestScore%100;
                    bestTime = String.format("%02d:%02d:%02d", minTime, secTime, msecTime);
                }
                if(fr != null){
                    fr.close();
                }
                if(fw != null){
                    fw.close();
                }
                AppConfig.printLOG("rank]finish game, time score - " + strTime);
                AppConfig.printLOG("rank]finish game, best score - " + bestTime);
                BGMService.playEffectSound();

                showPopup();
            } catch (Exception e) {
                AppConfig.printLOG("TimeRunnable Exception - " + e);
            }
            //2020-02-21 은노 수정
            //TODO: finish()를 바꾸기
            //      Intent를 통해서 GameActivity에서
            //      1. HomeMenuActivity 또는
            //      2. Game 다시 시작


        }
    }


    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case AppConfig.MSG_TIMER_SETTEXT:
                    timescore.setText(msg.obj.toString());
                    break;
                case AppConfig.MSG_BEECOUNT_SETTEXT:
                    text_angle.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    public void showPopup(){
        AppConfig.printLOG("showPopup call");
        Intent intent = new Intent(this, PopupGameOverActivity.class);
        intent.putExtra("BestScore", bestTime);
        intent.putExtra("Score", strTime);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
    }
}
