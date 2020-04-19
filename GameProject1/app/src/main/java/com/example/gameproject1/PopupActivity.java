package com.example.gameproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_okpopup);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");

        AppConfig.printLOG("Msg is " + msg);
        TextView text_msg = (TextView)findViewById(R.id.text_msg);
        text_msg.setText(msg);
        text_msg.bringToFront();

        Button btn_ok = (Button)findViewById(R.id.button_ok);

        //home menu로 돌아가기
        btn_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
