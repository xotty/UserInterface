/**
 * 本例演示了ListView的下列要点：
 * 1)用simple_list_item_2实现双行显示
 * 2)从通讯录中提取姓名、电话和电话类型直接装配到SimpleCursorAdapter中
 * 3)setViewBinder的使用，可以更加灵活的显示Cursor中的多项内容。如果设置了ViewBinder，那么这个设置的ViewBinder的
 * setViewValue(）将被调用。如果setViewValue的返回值是true，则表示绑定已经完成,将不再调用系统默认的绑定实现。
 * 如果返回值为false，视图将调用系统默认的绑定实现：
 * 1）如果View实现了Checkable（例如CheckBox），期望绑定值是一个布尔类型。
 * 2）TextView.期望绑定值是一个字符串类型，通过调用setViewText(TextView, String)绑定。
 * 3）ImageView,期望绑定值是一个资源id或者一个字符串，通过调用setViewImage(ImageView, int)
 * 或 setViewImage(ImageView, String)绑定数据。
 * 如果没有一个合适的绑定发生将会抛出IllegalStateException。
 *
 * 注：在模拟器上运行时要在setting中手动打开UIWidgetsDemo读写通讯录的权限，并在模拟器Contacts App中添加几个人的名字和电话
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author Google Inc.
 * @version 1.0
 */

package org.xottys.userinterface.AdapterViewDemo;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class AdvancedViewBinderActivity extends ListActivity {
    private static final String TAG = "AdvancedViewBinderActivity";
    //从通讯轮路中提取下列数据
    private static final String[] PHONE_PROJECTION = new String[]{
            Phone._ID,
            Phone.LABEL,
            Phone.TYPE,
            Phone.NUMBER,
            Phone.DISPLAY_NAME
    };
    //按照PHONE_PROJECTION中的顺序编号：0～4
    private static final int COLUMN_LABEL = 1;
    private static final int COLUMN_TYPE = 2;
    private static final int COLUMN_NUMBER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Get a cursor with all phones
            Cursor c = getContentResolver().query(Phone.CONTENT_URI,
                    PHONE_PROJECTION, null, null, null);

            startManagingCursor(c);

            // Map Cursor columns to views defined in simple_list_item_2.xml
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2, c,
                    new String[]{
                            Phone.DISPLAY_NAME,
                            Phone.TYPE
                    },
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);
            //Used to display a readable string for the phone type
            adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    //Let the adapter handle the binding if the column is not TYPE
                    if (columnIndex != COLUMN_TYPE) {
                        //此时显示第一行：姓名
                        return false;
                    }
                    //此时显示第二行：电话号码，需加工修改显示的内容
                    int type = cursor.getInt(COLUMN_TYPE);
                    String label = null;
                    //Custom type? Then get the custom label
                    if (type == Phone.TYPE_CUSTOM) {
                        label = cursor.getString(COLUMN_LABEL);
                    }
                    //Get the readable string
                    String text = cursor.getString(COLUMN_NUMBER) + "(" + Phone.getTypeLabel(getResources(), type, label) + ")";
                    //Set text
                    ((TextView) view).setText(text);
                    return true;
                }
            });
            setListAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "读取通讯录失败，请检查相关授权！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, new String[]{"读取通讯录失败，请检查相关授权"});
            setListAdapter(adapter);
        }

    }
}