package com.example.edgar.chatapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private ChatDbHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new ChatDbHelper(ourcontext,ChatDbHelper.DATABASE_NAME,null,ChatDbHelper.DATABASE_VERSION);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public Cursor leerDatos() {

        Cursor c = database.rawQuery(" SELECT mensajes.contenido as Mensajes, mensajes.id as ID "
                +" FROM mensajes ",null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int actualizarDatos(long ID, String contenido) {
        ContentValues cvActualizar = new ContentValues();
        cvActualizar.put(MessageContract.MessageEntry.CONTENIDO, contenido);
        int i = database.update(MessageContract.MessageEntry.TABLE_NAME, cvActualizar,
                MessageContract.MessageEntry.ID + " = " + ID, null);
        return i;
    }

    public void deleteData(long ID) {
        database.delete(MessageContract.MessageEntry.TABLE_NAME, MessageContract.MessageEntry.ID + "="
                + ID, null);
    }
}
