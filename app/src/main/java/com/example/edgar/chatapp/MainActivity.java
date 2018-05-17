package com.example.edgar.chatapp;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.edgar.chatapp.Data.ChatDbHelper;
import com.example.edgar.chatapp.Data.MessageContract;
import com.example.edgar.chatapp.Data.SQLControlador;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.edgar.chatapp.Data.ChatDbHelper.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab,fab2,fab3;
    private EditText etEntradaMensaje;
    private SQLControlador dbConnection;
    TextView tvID,tvContenido;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);
        etEntradaMensaje=(EditText)findViewById(R.id.etEntradaMensaje);
        editar(fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessagetoDataBase(etEntradaMensaje.getText().toString());
                etEntradaMensaje.setText("");
                desplegarMensaje();
            }
        });
        eliminar(fab3);
        ListView list_of_message=(ListView)findViewById(R.id.list_of_message);


    }

    public void sendMessagetoDataBase(String mensaje)
    {
        ChatDbHelper chatDbHelper=new ChatDbHelper(getBaseContext(), DATABASE_NAME,null,ChatDbHelper.DATABASE_VERSION);
        SQLiteDatabase db=chatDbHelper.getWritableDatabase();

        ContentValues valor =new ContentValues();

        valor.put("contenido",mensaje);

        db.insert(MessageContract.MessageEntry.TABLE_NAME,null,valor);
        db.close();


    }

    public void desplegarMensaje()
    {
        obtenerMensajes();
        ListView list_of_Message=(ListView)findViewById(R.id.list_of_message);
        String[] Mensajes= new String[obtenerMensajes().size()];

        for(int i=0;i<obtenerMensajes().size();i++)
        {
            Mensajes[i]=obtenerMensajes().get(i);
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,Mensajes);
        list_of_Message.setAdapter(arrayAdapter);


    }

    public ArrayList<String> obtenerMensajes()
    {
        ArrayList<String> arrayListMensajes=new ArrayList<String>();
        ChatDbHelper chatDbHelper=new ChatDbHelper(getBaseContext(), DATABASE_NAME,null,ChatDbHelper.DATABASE_VERSION);
        SQLiteDatabase db=chatDbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(" SELECT mensajes.contenido as Mensajes "
                +" FROM mensajes ",null);
        if(cursor.moveToFirst())
        {
           do {

               arrayListMensajes.add(cursor.getString(0));

           }while(cursor.moveToNext());
        }

        return arrayListMensajes;
    }

    public void editar(FloatingActionButton fab)
    {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               desplegarMensaje();

            }
        });

    }

    public void eliminar(FloatingActionButton fab3)
    {
        fab3.setOnClickListener(new View.OnClickListener() {

            ChatDbHelper chatDbHelper=new ChatDbHelper(getBaseContext(), DATABASE_NAME,null,ChatDbHelper.DATABASE_VERSION);
            SQLiteDatabase db=chatDbHelper.getWritableDatabase();
            @Override
            public void onClick(View v) {
                db.delete(MessageContract.MessageEntry.TABLE_NAME,null,null);
            }
        });
    }


}
