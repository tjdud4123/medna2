package com.example.myapplication4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class AgreeSign extends View {
    public AgreeSign(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        //canvas.drawRect(10,10,100,100,paint);
        canvas.drawLine(100,100,100,100,paint);
    }

    int oldX, oldY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int)event.getX();
        int Y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)  // 뷰를 누를때
        {
            oldX = X;
            oldY = Y;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE) // 뷰를 누른 후 움직일때
        {
            if(oldX != -1)
            {
                //mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
                oldX = X;
                oldY = Y;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)  // 뷰에서 손가락을 뗄 때
        {
            if(oldX != -1)
            {
                //mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
            }
            oldX = -1;
            oldY = -1;
        }
        //return super.onTouchEvent(event);
        invalidate();
        return true;
    }
}
