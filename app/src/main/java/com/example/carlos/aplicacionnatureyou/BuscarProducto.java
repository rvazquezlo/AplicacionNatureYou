package com.example.carlos.aplicacionnatureyou;

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

public class BuscarProducto extends AppCompatActivity {
    TextView nom, id, price, descript;
    Spinner cate, cbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);
        id = (TextView)findViewById(R.id.tvId);
        descript = (TextView)findViewById(R.id.tvDescr);
        nom = (TextView)findViewById(R.id.tvNom);
        price = (TextView)findViewById(R.id.txvPrecio);
        cate = (Spinner) findViewById(R.id.cbCategoria);
        cbId = (Spinner)findViewById(R.id.cbIdP);
        llenaCategoria(cate);//Llenar combo de categorias
    }

    protected void llenaCategoria(Spinner categoria){
        ArrayList<String> listaCategoria;
        ArrayAdapter<String> adaptador;

        try{
            listaCategoria =  new ArrayList<String>();//lista para guardar todas las categorias
            listaCategoria.add("Accesorios");
            listaCategoria.add("Articulos para el hogar");
            listaCategoria.add("Articulos para mascotas");
            listaCategoria.add("Juguetes");
            listaCategoria.add("Ropa");
            listaCategoria.add("Utencilios de cocina");
            listaCategoria.add("Útiles");

            //Pasar los datos de la lista al Spinner adaptándolos
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listaCategoria);
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoria.setAdapter(adaptador);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void limpia(View view){
        id.setText("");
        descript.setText("");
        nom.setText("");
        price.setText("");
        cate.setSelection(0);
        cbId.setSelection(0);
    }

    protected void volver(View v){
        try{
            Intent in= new Intent(this,Inicio.class);
            startActivity(in);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void aceptar(View v){
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;
        ArrayList<String> listaId;
        ArrayAdapter adaptador;

        try{
            admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            db = admin.getWritableDatabase();

            //pedir los id de los productos de una categoria
            fila = db.rawQuery("select idProducto from productos where categoria=" +
                    (int)cate.getSelectedItem(),null);

            listaId = new ArrayList<String>();
            while(fila.moveToFirst())//si tiene datos
                listaId.add(fila.getString(0));//agregar los ids a la listaId
            db.close();//cerrar por seguridad

            //Si no hay datos, mandar mensaje
            if(listaId.isEmpty())
                Toast.makeText(this,"no hay productos en la categoria seleccionada ",
                        Toast.LENGTH_LONG);
            else{
                //Pasar los datos de la lista al Spinner adaptándolos
                adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        listaId);
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cbId.setAdapter(adaptador);//Ponerlos en cbId

                limpia(v);//Se limpian los datos para una nueva busqueda
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void buscar(View v) {
        AdminSQLiteOpenHelper admin;
        SQLiteDatabase db;
        Cursor fila;

        try{
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            db = admin.getWritableDatabase();
            fila = db.rawQuery("select descripcion, nombre, precio, id from productos where idProducto="
                    + (int) cbId.getSelectedItem(), null);
            if (fila.moveToFirst()) {//Pedir datos
                descript.setText(fila.getString(0));
                nom.setText(fila.getString(1));
                price.setText(fila.getString(2));
                id.setText(fila.getString(3));
            }
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
