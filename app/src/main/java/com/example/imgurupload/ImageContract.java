package com.example.imgurupload;

import android.provider.BaseColumns;

public final class ImageContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ImageContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class ImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "image";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_DELETEHASH = "deletehash";
        public static final String COLUMN_NAME_ALBUM_ID = "album_id";
    }
}
