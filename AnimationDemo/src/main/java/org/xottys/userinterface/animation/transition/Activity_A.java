package org.xottys.userinterface.animation.transition;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;


public class Activity_A extends Activity {
        private Context context;
        private Button btn;
        private ImageView iv_a;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_a);
            context = this;

            iv_a = (ImageView) findViewById(R.id.iv_a);
            //也可以在xml中配置
//        ViewCompat.setTransitionName(iv_a,"options5");
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            ActivityCompat.finishAfterTransition(Activity_A.this);
//        ActivityCompat.finishAffinity(Activity_A.this);//关闭所有的后台进程和空进程
        }

}
