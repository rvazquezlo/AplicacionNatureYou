package com.example.carlos.aplicacionnatureyou;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Baja extends AppCompatActivity {
    private TextView nom;
    private Spinner id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja);
        id = (Spinner) findViewById(R.id.cbID);
        nom = (TextView) findViewById(R.id.tvNombre);
        update(id);//llenar Spinner
    }

    /**
     * Método para llenar el Spinner
     * @param sp
     */
    protected void update(Spinner sp){
        ArrayList<String> listaId;
        ArrayAdapter<String> adaptador;
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;

        try{
            listaId =  new ArrayList<String>();//lista para guardar todos los id

            //Recuperar todos los idProducto de la base de datos
            admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            db = admin.getWritableDatabase();
            fila = db.rawQuery("select idProducto from productos",null);
            while(fila.moveToFirst())//si tiene datos
                listaId.add(fila.getString(0));//agregar los idProducto a la listaId
            if(listaId.isEmpty())
                Toast.makeText(this,"no hay productos dados de alta ",Toast.LENGTH_LONG);

            //Pasar los datos de la lista al Spinner adaptándolos
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listaId);
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            id.setAdapter(adaptador);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void limpia (View v){
        id.setSelection(0);
        nom.setText("");
    }

    /**
     * Método que da de baja al producto seleccionado por el usuario
     * @param v
     */
    protected void baja (View v){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;

        try{
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            db = admin.getWritableDatabase();

            //dar de baja al producto con la clave seleccionada del Spinner
            if(db.delete("productos","idProducto=" + (int)id.getSelectedItem(),
                    null) == 1)//si se dio de baja
                Toast.makeText(this,"producto eliminado",Toast.LENGTH_LONG).show();
            else//no se dio de baja
                Toast.makeText(this,"producto no eliminado",Toast.LENGTH_LONG).show();
            db.close();//cerrar acceso
            limpia(v);//limpiar casillas
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que muestra el nombre del producto correspondiente al idProducto seleccionado por el
     * usuario
     * @param v
     */
    protected void verifica(View v){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;

        try{
            admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            db = admin.getWritableDatabase();

            //pedir el nombre del producto dado el idProducto
            fila = db.rawQuery("select nombre from productos where idProducto=" +
                    (int)id.getSelectedItem(),null);

            //debe regresar un dato porque el idProducto viene directo de la base de datos
            nom.setText(fila.getString(0));//poner el nombre en la pantalla
            db.close();//cerrar por seguridad
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void volver(View v){
        try{
            Intent myIntent = new Intent(v.getContext(), Inicio.class);
            startActivityForResult(myIntent, 0);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
