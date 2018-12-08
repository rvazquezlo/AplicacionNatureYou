package com.example.carlos.aplicacionnatureyou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Modificar extends AppCompatActivity {
    private Spinner cbId;
    private EditText nombre, precio, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        nombre = (EditText)findViewById(R.id.etNombre);
        precio = (EditText) findViewById(R.id.etPrecio);
        descripcion = (EditText) findViewById(R.id.etDescripcion);
        cbId = (Spinner) findViewById(R.id.cbID);
        llenarId(cbId);
    }

    public void llenarId(Spinner id){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;
        ArrayList<String> listaId;
        ArrayAdapter adaptador;

        admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        db = admin.getWritableDatabase();

        //pedir los id de los productos
        fila = db.rawQuery("select idProducto from productos",null);

        listaId = new ArrayList<String>();
        while(fila.moveToFirst())//si tiene datos
            listaId.add(fila.getString(0));//agregar los ids a la listaId
        db.close();//cerrar por seguridad

        //Si no hay datos, mandar mensaje
        if(listaId.isEmpty())
            Toast.makeText(this,"no hay productos dados de alta",
                    Toast.LENGTH_LONG);
        else {
            //Pasar los datos de la lista al Spinner adaptándolos
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listaId);
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbId.setAdapter(adaptador);//Ponerlos en cbId
        }
    }
}
