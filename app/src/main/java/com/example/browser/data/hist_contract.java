package com.example.browser.data;

import android.provider.BaseColumns;

public final class hist_contract {
        private hist_contract(){}
        public static final class hist_entry implements BaseColumns {
            public final static String TABLE_NAME="History";
            public final static String _ID=BaseColumns._ID;
            public final static String COLUMN_TITLE="title";
            public final static String COLUMN_Link="url";


        }
    }


