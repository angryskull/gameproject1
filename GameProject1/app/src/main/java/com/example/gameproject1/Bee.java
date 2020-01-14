package com.example.gameproject1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Bee {
    private int speed;
    private float direction_x;
    private float direction_y;
    private float width;
    private float height;
    private float coord_x;
    private float coord_y;

   public Bee(float width, float height, float honey_x, float honey_y){
       /*
       Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
       Point size = new Point();
       display.getSize(size);
       width = size.x;
       height = size.y;
       */
       this.width = width - 50;
       this.height = height - 50;
       resetBee(honey_x, honey_y);
   }

    public void resetBee(float honey_x, float honey_y){
        speed = 20;

        int startPoint = (int) (Math.random() * 4);

        coord_x = (float) (Math.random() * width);
        coord_y = (float) (Math.random() * height);
        switch (startPoint){
            case 0:
                coord_x = 0;
                break;
            case 1:
                coord_x = width;
                break;
            case 2:
                coord_y = 0;
                break;
            case 3:
                coord_y = height;
                break;
        }

        //float honey_x = findViewById(R.id.UserCharacter).getX();
        //float honey_y = findViewById(R.id.UserCharacter).getY();
        //direction_x = (honey_x - coord_x > 0 ? 1 : -1);
        //direction_y = direction_x * ((honey_y - coord_y) / (honey_x - coord_x));
        float dx = honey_x - coord_x;
        float dy = honey_y - coord_y;
        direction_x = dx * (float)(1.0 / (Math.abs(dx) + Math.abs(dy)));
        direction_y = dy * (float)(1.0 / (Math.abs(dx) + Math.abs(dy)));
    }

    public void moveBee(float honey_x, float honey_y){
        coord_x += speed * direction_x;
        coord_y += speed * direction_y;
        if(coord_x > width + 50 || coord_x < 0 - 50)
            resetBee(honey_x, honey_y);
        if (coord_y > height + 50 || coord_y < 0 - 50)
            resetBee(honey_x, honey_y);
    }

    public float getX(){
       return coord_x;
    }

    public float getY(){
       return coord_y;
    }
}
