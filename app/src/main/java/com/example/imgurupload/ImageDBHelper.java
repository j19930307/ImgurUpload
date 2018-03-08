package com.example.imgurupload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.imgurupload.ImageContract.*;
import com.example.imgurupload.AlbumContract.*;

import static com.example.imgurupload.ImageContract.ImageEntry.COLUMN_NAME_ALBUM_ID;


public class ImageDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "imgurupload";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String UNIQUE_KEY = " UNIQUE";
    private static final String PRIMARY_KEY = " PRIMARY_KEY";

    private static final String CREATE_TABLE_ALBUM = "CREATE TABLE " + AlbumEntry.TABLE_NAME + " (" +
            AlbumEntry.COLUMN_NAME_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
            AlbumEntry.COLUMN_NAME_DELETEHASH + TEXT_TYPE + UNIQUE_KEY + COMMA_SEP +
            AlbumEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
            "  );";

    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE  " + ImageEntry.TABLE_NAME + " (" +
            ImageEntry.COLUMN_NAME_LINK + TEXT_TYPE + COMMA_SEP +
            ImageEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            ImageEntry.COLUMN_NAME_SIZE + INTEGER_TYPE + COMMA_SEP +
            ImageEntry.COLUMN_NAME_DELETEHASH + TEXT_TYPE + COMMA_SEP +
            ImageEntry.COLUMN_NAME_ALBUM_ID + TEXT_TYPE + COMMA_SEP +
            "FOREIGN KEY(" + ImageEntry.COLUMN_NAME_ALBUM_ID + ") REFERENCES " + AlbumEntry.TABLE_NAME + "(" + AlbumEntry.COLUMN_NAME_DELETEHASH +
            ")" + " ON DELETE CASCADE);";


    private static final String DELETE_TABLE_ALBUM = "DROP TABLE IF EXISTS " + ImageEntry.TABLE_NAME;
    private static final String DELETE_TABLE_IMAGE = "DROP TABLE IF EXISTS " + AlbumEntry.TABLE_NAME;

    public ImageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        //super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_ALBUM);
        db.execSQL(DELETE_TABLE_IMAGE);
        onCreate(db);
    }

    public boolean insertImageDetail(String link, String title, int size, String deletehash, String album_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageEntry.COLUMN_NAME_LINK, link);
        contentValues.put(ImageEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(ImageEntry.COLUMN_NAME_SIZE, size);
        contentValues.put(ImageEntry.COLUMN_NAME_DELETEHASH, deletehash);
        contentValues.put(COLUMN_NAME_ALBUM_ID, album_id);
        db.insert(ImageEntry.TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getImageDetail() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(ImageEntry.TABLE_NAME, null, ImageEntry.COLUMN_NAME_ALBUM_ID + " is null", null, null, null, null, null);
        return res;
    }

    public int deleteImage(String deletehash) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ImageEntry.COLUMN_NAME_DELETEHASH + " = ?";
        return db.delete(ImageEntry.TABLE_NAME, selection, new String[]{deletehash});
    }

    public boolean insertAlbumDetail(String id, String deletehash, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlbumEntry.COLUMN_NAME_ID, id);
        contentValues.put(AlbumEntry.COLUMN_NAME_DELETEHASH, deletehash);
        contentValues.put(AlbumEntry.COLUMN_NAME_TITLE, title);
        db.insert(AlbumEntry.TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getAlbumDetail() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(AlbumEntry.TABLE_NAME,
                null, null, null, null, null, null);
        return res;
    }

    public Cursor getAlbumCover(String album_deletehash) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(ImageEntry.TABLE_NAME, new String[]{ImageEntry.COLUMN_NAME_LINK}, ImageEntry.COLUMN_NAME_ALBUM_ID + "=?", new String[]{album_deletehash}, null, null, null, null);
        return res;
    }

    public int deleteAlbum(String deletehash) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = AlbumEntry.COLUMN_NAME_DELETEHASH + " = ?";
        return db.delete(AlbumEntry.TABLE_NAME, selection, new String[]{deletehash});
    }


}