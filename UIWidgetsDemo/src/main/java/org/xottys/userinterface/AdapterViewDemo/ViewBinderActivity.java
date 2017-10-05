/**
 * 本例演示了ListView的下列要点：
 * 1)用simple_list_item_2实现双行显示
 * 2)从通讯录中提取姓名、电话和电话类型直接装配到SimpleCursorAdapter中
 * 3)setViewBinder的使用，可以更加灵活的显示Cursor中的多项内容
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 * @author Google Inc.
 * @version 1.0
 */

package org.xottys.userinterface.AdapterViewDemo;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ViewBinderActivity extends ListActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       // Get a cursor with all phones
       Cursor c = getContentResolver().query(Phone.CONTENT_URI,
               PHONE_PROJECTION, null, null, null);
       startManagingCursor(c);

       // Map Cursor columns to views defined in simple_list_item_2.xml
       SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
               android.R.layout.simple_list_item_2, c,
                       new String[] {
                           Phone.DISPLAY_NAME,
                           Phone.TYPE
                       },
                       new int[] { android.R.id.text1, android.R.id.text2 },0);
       //Used to display a readable string for the phone type
       adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
           @Override
           public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
               //Let the adapter handle the binding if the column is not TYPE
               if (columnIndex != COLUMN_TYPE) {
                   return false;
               }
               int type = cursor.getInt(COLUMN_TYPE);
               String label = null;
               //Custom type? Then get the custom label
               if (type == Phone.TYPE_CUSTOM) {
                   label = cursor.getString(COLUMN_LABEL);
               }
               //Get the readable string
               String text = cursor.getString(COLUMN_NUMBER)+"("+(String) Phone.getTypeLabel(getResources(), type, label)+")";
               //Set text
               ((TextView) view).setText(text);
               return true;
           }
       });
       setListAdapter(adapter);
   }

   //从通讯轮路中提取下列数据
   private static final String[] PHONE_PROJECTION = new String[] {
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
}