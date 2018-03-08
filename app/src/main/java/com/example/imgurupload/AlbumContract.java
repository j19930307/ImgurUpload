package com.example.imgurupload;

import android.provider.BaseColumns;


public final class AlbumContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AlbumContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class AlbumEntry implements BaseColumns {
        public static final String TABLE_NAME = "album";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DELETEHASH = "deletehash";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}