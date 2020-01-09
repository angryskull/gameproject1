package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.zerokol.views.joystickView.JoystickView;

public class GameActivity extends AppCompatActivity{
    private TextView mText;
    private int timervalue = 3;
    private TextView mUserCharacter;
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

    public void ReadSprite(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mText=(TextView)findViewById(R.id.TextView1);
        mUserCharacter=(TextView)findViewById(R.id.UserCharacter);

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
                    mUserCharacter.setY(mUserCharacter.getY() - 10);
                    break;
                case JoystickView.FRONT_RIGHT:
                    //directionTextView.setText(R.string.front_right_lab);
                    mUserCharacter.setX(mUserCharacter.getX() - 8);
                    mUserCharacter.setY(mUserCharacter.getY() - 8);
                    break;
                case JoystickView.RIGHT:
                    //directionTextView.setText(R.string.right_lab);
                    mUserCharacter.setX(mUserCharacter.getX() - 10);
                    mUserCharacter.setY(mUserCharacter.getY());
                    break;
                case JoystickView.RIGHT_BOTTOM:
                    //directionTextView.setText(R.string.right_bottom_lab);
                    mUserCharacter.setX(mUserCharacter.getX() - 8);
                    mUserCharacter.setY(mUserCharacter.getY() + 8);
                    break;
                case JoystickView.BOTTOM:
                    //directionTextView.setText(R.string.bottom_lab);
                    mUserCharacter.setX(mUserCharacter.getX());
                    mUserCharacter.setY(mUserCharacter.getY() + 10);
                    break;
                case JoystickView.BOTTOM_LEFT:
                    //directionTextView.setText(R.string.bottom_left_lab);
                    mUserCharacter.setX(mUserCharacter.getX() + 8);
                    mUserCharacter.setY(mUserCharacter.getY() + 8);
                    break;
                case JoystickView.LEFT:
                    //directionTextView.setText(R.string.left_lab);
                    mUserCharacter.setX(mUserCharacter.getX() + 10);
                    mUserCharacter.setY(mUserCharacter.getY());
                    break;
                case JoystickView.LEFT_FRONT:
                    //directionTextView.setText(R.string.left_front_lab);
                    mUserCharacter.setX(mUserCharacter.getX() + 8);
                    mUserCharacter.setY(mUserCharacter.getY() - 8);
                    break;
                default:
                    //directionTextView.setText(R.string.center_lab);
                }
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);
    }


}
