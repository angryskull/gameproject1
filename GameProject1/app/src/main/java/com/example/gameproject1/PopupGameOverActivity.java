package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

//AndroidMainfest.xml에서 android:theme="@android:style/Theme.DeviceDefault.Light.Dialog" 로 해주었기 때문에
//PopupGameOverActivity에서 extends AppCompatActivity로 해주었을 경우 Theme.AppCompat을 사용하라는 에러가 발생한다.
//따라서 extends Activity로 바꾸어주었다. 그랬더니 잘 되네
public class PopupGameOverActivity extends Activity {
    File Life_file;
    FileWriter fw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_game_over);

        Intent intent = getIntent();
        String bestScore = intent.getStringExtra("BestScore");
        String score = intent.getStringExtra("Score");

        AppConfig.printLOG("bestScore is " + bestScore);
        AppConfig.printLOG("Score is " + score);

        TextView text_bestScore = (TextView)findViewById(R.id.text_best_score);
        TextView text_score = (TextView)findViewById(R.id.text_score);

        text_bestScore.setText(bestScore);
        text_score.setText(score);

        text_bestScore.bringToFront();
        text_score.bringToFront();

        Button btn_home = (Button)findViewById(R.id.button_home);
        Button btn_replay = (Button)findViewById(R.id.button_replay);
        
        //home menu로 돌아가기
        btn_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //게임 다시 재생
        btn_replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(AppConfig.getLifevalue() == 0)
                {
                    AppConfig.printLOG("Life is 0");
                    Intent intent = new Intent(PopupGameOverActivity.this, PopupActivity.class);
                    intent.putExtra("msg", "You don't have life.\nWatch the AD!");
                    startActivity(intent);
                    finish();
                    return;
                }

                AppConfig.printLOG("play game!");
                AppConfig.setLifevalue(AppConfig.getLifevalue() - 1);
                Life_file = new File(getFilesDir(), "life");
                try{
                    fw  = new FileWriter(Life_file);
                    fw.write(AppConfig.getLifevalue());
                    fw.close();
                } catch (Exception e){
                    e.printStackTrace();
                }


                Intent intent = new Intent(PopupGameOverActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥 layer 클릭시 안닫히게
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){

    }
}
