/**
 * Demonstrates creating a Screen that uses custom views. This example uses
 * {@link org.xottys.userinterface.CustomLabelView}
 *
 */

package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;


public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view);
    }
}
