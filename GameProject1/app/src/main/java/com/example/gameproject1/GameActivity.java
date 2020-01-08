package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zerokol.views.joystickView.JoystickView;

public class GameActivity extends AppCompatActivity{
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
//        angleTextView = (TextView) findViewById(R.id.angleTextView);
//        powerTextView = (TextView) findViewById(R.id.powerTextView);
//        directionTextView = (TextView) findViewById(R.id.directionTextView);
        //Referencing also other views
        joystick = (JoystickView) findViewById(R.id.joystickView);
        gameView = (View) findViewById(R.id.view);
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
                        break;
                    case JoystickView.FRONT_RIGHT:
                        //directionTextView.setText(R.string.front_right_lab);
                        break;
                    case JoystickView.RIGHT:
                        //directionTextView.setText(R.string.right_lab);
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        //directionTextView.setText(R.string.right_bottom_lab);
                        break;
                    case JoystickView.BOTTOM:
                        //directionTextView.setText(R.string.bottom_lab);
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        //directionTextView.setText(R.string.bottom_left_lab);
                        break;
                    case JoystickView.LEFT:
                        //directionTextView.setText(R.string.left_lab);
                        break;
                    case JoystickView.LEFT_FRONT:
                        //directionTextView.setText(R.string.left_front_lab);
                        break;
                    default:
                        //directionTextView.setText(R.string.center_lab);
                }
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);
    }


}
