package com.example.carlos.aplicacionnatureyou;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    protected void llenarId(Spinner id){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;
        ArrayList<String> listaId;
        ArrayAdapter adaptador;

        try{
            admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            db = admin.getWritableDatabase();

            //pedir los id de los productos
            fila = db.rawQuery("select idProducto from productos",null);

            listaId = new ArrayList<String>();
            if(fila.moveToFirst())
                while(fila.moveToNext())//si tiene datos
                    listaId.add(fila.getString(0));//agregar los ids a la listaId
            else//Si no hay datos, mandar mensaje
                Toast.makeText(this,"no hay productos dados de alta",
                        Toast.LENGTH_LONG);
            db.close();//cerrar por seguridad


            //Pasar los datos de la lista al Spinner adaptándolos
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listaId);
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbId.setAdapter(adaptador);//Ponerlos en cbId

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void acepte(View v){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;

        try{
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            db = admin.getWritableDatabase();
            fila = db.rawQuery("select descripcion, nombre, precio from productos where idProducto="
                    + Integer.parseInt(cbId.getSelectedItem().toString()), null);
            if (fila.moveToFirst()) {//Pedir datos
                descripcion.setText(fila.getString(0));
                nombre.setText(fila.getString(1));
                precio.setText(fila.getString(2));
            }
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void modificar(View v){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;

        try{
            admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);
            db = admin.getWritableDatabase();

            //Agregar nuevos valores
            ContentValues registro = new ContentValues();
            registro.put("nombre",nombre.getText().toString());
            registro.put("descripcion",descripcion.getText().toString());
            registro.put("precio", Double.parseDouble(precio.getText().toString()));

            //hacer el update
            if(db.update("productos",registro,"idProducto=" +
                    Integer.parseInt(cbId.getSelectedItem().toString()),null) == 1)
                Toast.makeText(this,"se modificaron los datos del producto",
                        Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"no se modificaron los datos",Toast.LENGTH_LONG).show();
            db.close();//cerrar por seguridad
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
