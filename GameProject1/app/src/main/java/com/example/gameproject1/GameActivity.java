package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerokol.views.joystickView.JoystickView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameActivity extends AppCompatActivity{
    private TextView mText;
    private int timervalue = 3;
    private int UserSpeed = 20;
    private ImageView mUserCharacter;
    private TextView angleTextView;
    private TextView powerTextView;
    private TextView directionTextView;
    // Importing also other views
    private JoystickView joystick;
    private View gameView;

    private int Width, Height, cx, cy;// View의 전체 폭과 중심점
    private float x1, y1, x2, y2; //Viewport 좌표
    private Bitmap me; //우주선 이미지
    private int w, h; //우주선의 폭과 높이

    private int sw, sh; // 우주선의 폭과 높이

    private static int Vec[][] = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    //벌 추가
    Bee bees[] = new Bee[10];
    ImageView bee_images[] = new ImageView[10];

    //float scale = getResources().getDisplayMetrics().density;
    float scale = 2;
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


        new CountDownTimer(4 * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) { // 총 시간과 주기
                mText.setText(String.valueOf(timervalue--));
                if(timervalue == -1) {
                    mText.setVisibility(View.INVISIBLE);
                    mUserCharacter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                //벌 생성
                initializeBees();
            }
        }.start();  // 타이머 시작
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
                }
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);


        //벌 움직이기 위한 타이머
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run(){
                for(int i = 0; i < bees.length; i++){
                    bees[i].moveBee(mUserCharacter.getX(), mUserCharacter.getY());
                    bee_images[i].setX(bees[i].getX());
                    bee_images[i].setY(bees[i].getY());
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 4500, 100);

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

    private void initializeBees(){
        //벌 생성
        float resolution_width = getResolutionWidth();
        float resolution_height = getResolutionHeight();
        for(int i = 0; i < bees.length; i++){
            bees[i] = new Bee(resolution_width, resolution_height, mUserCharacter.getX(), mUserCharacter.getY());
        }

        ConstraintLayout mainLayout = findViewById(R.id.MainLinerLayout);

        for(int i = 0; i < bee_images.length; i++){
            bee_images[i] = new ImageView(this);
            //이미지 지정
            bee_images[i].setImageResource(R.drawable.bee_tmp2);

            //크기 지정
            int dpWidthInPx  = (int) (30 * scale);
            int dpHeightInPx = (int) (30 * scale);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            bee_images[i].setLayoutParams(layoutParams);

            //좌표 지정
            bee_images[i].setX(bees[i].getX());
            bee_images[i].setY(bees[i].getY());

            mainLayout.addView(bee_images[i]);
        }
    }
}
