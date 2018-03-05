package org.xottys.userinterface.animation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class PathView extends View {

    private int screenWidth;

    private int screenHeight;

    private Path path;

    private Paint paint;

    public PathView(Context context) {
        super(context);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        path = new Path();
        path.moveTo(10, 10);
       // path.quadTo(screenWidth - 100, 400, 200, screenHeight - 600);
        path.quadTo(screenWidth - 100, 400, screenWidth - 1200, screenHeight - 700);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        path = new Path();
        path.moveTo(10, 10);
        path.quadTo(screenWidth - 300, 200,  100, screenHeight - 600);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);


        /*try {
            SVG svg = SVG.getFromResource(context, R.raw.android);
            svgCanvas.scale(100, 100);
            svg.renderToCanvas(svgCanvas);
        } catch (SVGParseException e) {
            e.printStackTrace();
        }*/
    }

    /*private Canvas svgCanvas = new Canvas() {
        private final Matrix mMatrix = new Matrix();

        @Override
        public int getWidth() {
            return 720;
        }

        @Override
        public int getHeight() {
            return 1000;
        }

        @Override
        public void drawPath(Path p, Paint paint) {
            Path dst = new Path();
            getMatrix(mMatrix);
            p.transform(mMatrix, dst);
            path = dst;
        }
    };*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
