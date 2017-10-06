/**
 * 本例演示了SimpleCursorTreeAdapter的用法：
 * 1)setGroupCursor:设置父项数据，setChildrenCursor设置子项数据
 * 2)在onCrease和getChildrenCursor中分别启动AsyncQueryHandler查询父项和子项数据
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

import android.app.ExpandableListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.CursorTreeAdapter;
import android.widget.SimpleCursorTreeAdapter;

/**
 * Demonstrates expandable lists backed by Cursors
 */
public class SimpleCursorTreeActivity extends ExpandableListActivity {

    private static final String[] CONTACTS_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME
    };
    private static final int GROUP_ID_COLUMN_INDEX = 0;

    private static final String[] PHONE_NUMBER_PROJECTION = new String[] {
            Phone._ID,
            Phone.NUMBER
    };

    private static final int TOKEN_GROUP = 0;
    private static final int TOKEN_CHILD = 1;

    private static final class QueryHandler extends AsyncQueryHandler {
        private CursorTreeAdapter mAdapter;

        public QueryHandler(Context context, CursorTreeAdapter adapter) {
            super(context.getContentResolver());
            this.mAdapter = adapter;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            switch (token) {
            //用查询结果设置父项数据
            case TOKEN_GROUP:
                mAdapter.setGroupCursor(cursor);
                break;
            //用查询结果设置子项数据
            case TOKEN_CHILD:
                int groupPosition = (Integer) cookie;
                mAdapter.setChildrenCursor(groupPosition, cursor);
                break;
            }
        }
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

        // Note that the constructor does not take a Cursor. This is done to avoid querying the 
        // database on the main thread.
        public MyExpandableListAdapter(Context context, int groupLayout,
                                       int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                                       int[] childrenTo) {

            super(context, null, groupLayout, groupFrom, groupTo, childLayout, childrenFrom,
                    childrenTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // Given the group, we return a cursor for all the children within that group 
            // Return a cursor that points to this contact's phone numbers
            Uri.Builder builder = Contacts.CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, groupCursor.getLong(GROUP_ID_COLUMN_INDEX));
            builder.appendEncodedPath(Contacts.Data.CONTENT_DIRECTORY);
            Uri phoneNumbersUri = builder.build();

            //用异步查询结果在onQueryComplete中设置子项数据
            mQueryHandler.startQuery(TOKEN_CHILD, groupCursor.getPosition(), phoneNumbersUri, 
                    PHONE_NUMBER_PROJECTION, Phone.MIMETYPE + "=?",
                    new String[] { Phone.CONTENT_ITEM_TYPE }, null);
            return null;
        }
    }

    private QueryHandler mQueryHandler;
    private CursorTreeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { Contacts.DISPLAY_NAME }, // Name for group layouts
                new int[] { android.R.id.text1 },
                new String[] { Phone.NUMBER }, // Number for child layouts
                new int[] { android.R.id.text1 });

        setListAdapter(mAdapter);

        mQueryHandler = new QueryHandler(this, mAdapter);

        // Query for people，用异步查询结果在onQueryComplete中设置父项数据
        mQueryHandler.startQuery(TOKEN_GROUP, null, Contacts.CONTENT_URI, CONTACTS_PROJECTION,
                Contacts.HAS_PHONE_NUMBER + "=1", null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Null out the group cursor. This will cause the group cursor and all of the child cursors
        // to be closed.
        mAdapter.changeCursor(null);
        mAdapter = null;
    }
}
