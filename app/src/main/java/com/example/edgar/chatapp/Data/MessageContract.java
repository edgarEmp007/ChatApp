package com.example.edgar.chatapp.Data;

import android.provider.BaseColumns;

public class MessageContract {

    public static abstract class MessageEntry implements BaseColumns {

        public static final String TABLE_NAME="mensajes";
        public static final String ID="id";
        public static final String CONTENIDO="contenido";

    }
}
