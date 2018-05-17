package com.example.edgar.chatapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="chat.db";

    public ChatDbHelper(Context context, String chat, Object o, int i)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ MessageContract.MessageEntry.TABLE_NAME+"("
        + MessageContract.MessageEntry.ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + MessageContract.MessageEntry.CONTENIDO+" TEXT NOT NULL, "
        + " UNIQUE (" + MessageContract.MessageEntry.ID+" ))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MessageContract.MessageEntry.TABLE_NAME);
        onCreate(db);
    }
}
