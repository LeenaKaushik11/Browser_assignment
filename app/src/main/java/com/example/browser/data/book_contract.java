package com.example.browser.data;

import android.provider.BaseColumns;

public final class book_contract {
    private book_contract(){}
    public static final class book_entry implements BaseColumns {
        public final static String TABLE_NAME="Bookmarks";
        public final static String _ID=BaseColumns._ID;
        public final static String COLUMN_TITLE="title";
        public final static String COLUMN_Link="url";


    }
}
