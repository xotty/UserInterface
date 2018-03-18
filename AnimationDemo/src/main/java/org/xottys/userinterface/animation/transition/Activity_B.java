package org.xottys.userinterface.animation.transition;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import org.xottys.userinterface.animation.R;
/**
 * Created by ${cqc} on 2016/12/2.
 */

public class Activity_B extends Activity {

    private Button btn;
    private ImageView iv;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置contentFeature,可使用切换动画
        // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  或者
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
        getWindow().setEnterTransition(explode);

        setContentView(R.layout.activity_b);
    }

}
