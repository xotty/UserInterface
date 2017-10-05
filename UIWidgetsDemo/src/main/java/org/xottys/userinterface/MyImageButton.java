package org.xottys.userinterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class MyImageButton extends android.support.v7.widget.AppCompatImageButton{
    private String text = null;      //要显示的文字
    private int color;               //文字的颜色
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void setText(String text){
        this.text = text;      //设置文字
    }
    public void setColor(int color){
        this.color = color;    //设置文字颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置文字位置、大小、颜色
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(3);
        paint.setTextSize(40);
        //绘制文字
        canvas.drawText(text,120,80, paint);
    }
}
