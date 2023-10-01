package com.example.a15151.db.creation;

import android.provider.BaseColumns;


    public class ResultEntry implements BaseColumns {
        private ResultEntry(){
        }
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_LAST_SCORE = "last_score";
        public static final String COLUMN_BEST_SCORE = "best_score";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_LEVEL + " INTEGER," +
                        COLUMN_LAST_SCORE + " INTEGER," +
                        COLUMN_BEST_SCORE + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


