/**本例演示了ImageView及其子类的基本用法
 * 1)ImageView
 * 2)ImageButton
 * 3)ZoomButton/ZoomControls
 * 4)QuickContactBadge
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;
import android.widget.ZoomButton;
import android.widget.ZoomControls;


public class ImageViewActivity extends Activity {
    // 定义一个访问图片的数组
    int[] images = new int[]{
            R.drawable.lijiang,
            R.drawable.qiao,
            R.drawable.shuangta,
            R.drawable.shui,
            R.drawable.xiangbi,
    };
    // 定义默认显示的图片
    int currentImg = 2;
    // 定义图片的初始透明度
    private int alpha = 255;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        final Button plus = (Button) findViewById(R.id.plus);
        final Button minus = (Button) findViewById(R.id.minus);
        //ImageView1是整体主图，ImageView2是ImageView1的局部细节图
        final ImageView image1 = (ImageView) findViewById(R.id.image1);
        final ImageView image2 = (ImageView) findViewById(R.id.image2);
        //三张限定大小的ImageView
        final ImageView image3 = (ImageView) findViewById(R.id.image3);
        final ImageView image4 = (ImageView) findViewById(R.id.image4);
        final ImageView image5 = (ImageView) findViewById(R.id.image5);
        final Button next = (Button) findViewById(R.id.next);

        //定义查看下一张图片的监听器
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 控制ImageView显示下一张图片
                int imageNo = currentImg++;
                image1.setImageResource(images[imageNo % images.length]);
                image3.setImageResource(images[imageNo % images.length]);
                image4.setImageResource(images[imageNo % images.length]);
                image5.setImageResource(images[imageNo % images.length]);
            }
        });

        // 定义改变图片透明度的方法
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == plus) {
                    alpha += 20;
                }
                else if (v == minus) {
                    alpha -= 20;
                }
                else if (alpha >= 255) {
                    alpha = 255;
                }
                else if (alpha <= 0) {
                    alpha = 0;
                }
                // 改变图片的透明度
                image1.setImageAlpha(alpha);
            }
        };
        // 为两个按钮添加监听器
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

        //将ImageView1的局部截取出来，放到ImageView2上去
        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) image1
                        .getDrawable();
                // 获取第一个图片显示框中的位图
                Bitmap bitmap = bitmapDrawable.getBitmap();
                // bitmap图片实际大小与第一个ImageView的缩放比例
                double scale = bitmap.getWidth() / 320.0;
                // 获取需要显示的图片的开始点
                int x = (int) (event.getX() * scale);
                int y = (int) (event.getY() * scale);

                if (x + 120 > bitmap.getWidth()) {
                    x = bitmap.getWidth() - 120;
                }
                if (y + 120 > bitmap.getHeight()) {
                    y = bitmap.getHeight() - 120;
                }
                // 显示图片的指定区域
                image2.setImageBitmap(Bitmap.createBitmap(bitmap
                        , x, y, 120, 120));
                image2.setImageAlpha(alpha);
                return false;
            }
        });

        //图片按钮，完全没有文字，其它特性和和普通按钮一样
        //从左到右依次为：标准、增加点击样式、系统图片和自定义带文字的图片按钮
        final MyImageButton imgButton = (MyImageButton) findViewById(R.id.imageButton2);
        imgButton.setText("按钮文字");
        imgButton.setColor(Color.BLUE);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("点击了：'自定义带文本图形按钮'");
            }
        });

        //ZoomButton只是一种特定图形的ImageButton，其主要是实现了长按时会按照设定的时间频率重复点击事件
        ZoomButton btn1 = (ZoomButton) findViewById(R.id.btn_zoom_down);
        ZoomButton btn2 = (ZoomButton) findViewById(R.id.btn_zoom_up);
        //设定时间频率
        btn1.setZoomSpeed(200);
        btn2.setZoomSpeed(200);
        //ZoomButton的缩放功能需要自己在onClick中实现，此处实现缩小
        btn1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                int x = imgButton.getWidth();
                int y = imgButton.getHeight();
                ViewGroup.LayoutParams params = imgButton.getLayoutParams();
                params.height = y - 10;
                params.width = x - 10;
                imgButton.setLayoutParams(params);
              }
        });

        //ZoomButton的缩放功能需要自己在onClick中实现，此处实现放大
        btn2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int x = imgButton.getWidth();
                  int y = imgButton.getHeight();
                  ViewGroup.LayoutParams params = imgButton.getLayoutParams();
                  params.height = y + 10;
                  params.width = x + 10;
                  imgButton.setLayoutParams(params);
              }
        });

        //同时有放大缩小功能的ZoomButton
        ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomControls);

        //注册放大监听器
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int x = imgButton.getWidth();
                int y = imgButton.getHeight();
                ViewGroup.LayoutParams params = imgButton.getLayoutParams();
                params.height = y + 10;
                params.width = x + 10;
                imgButton.setLayoutParams(params);
            }
        });

        //注册缩小监听器
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int x = imgButton.getWidth();
                int y = imgButton.getHeight();
                ViewGroup.LayoutParams params = imgButton.getLayoutParams();
                params.height = y - 10;
                params.width = x - 10;
                imgButton.setLayoutParams(params);
            }
        });
        //设置长按时的缩放速度
        zoomControls.setZoomSpeed(200);

        // QuickContactBadge是一种可以直接进入联系人通讯录的图片按钮
        QuickContactBadge badge = (QuickContactBadge) findViewById(R.id.badge);
        // 将QuickContactBadge组件与特定电话号码对应的联系人建立关联，点击时进入该关联的联系人，
        // 若无此联系人，系统会提示是否添加该联系人
        badge.assignContactFromPhone("010-12345678", false);
        /*另外两种关联联系人的方法如下：
        assignContactFromEmail(String emailAddress, boolean lazyLookup):
        assignContactUri(Uri contactUri)*/
    }

    private void showMessage(final String msg) {
        Toast.makeText(ImageViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
