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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AltaProductos extends AppCompatActivity {
    private EditText nombre, descripcion, precio;
    private Spinner categoria;

    /**
     * En este método se recuperan los EditText y el Spinner necesarios del Activity. También se
     * llena el Spinner.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobar_productos);
        nombre = (EditText) findViewById(R.id.txtNombre);
        descripcion = (EditText) findViewById(R.id.txtDescripcion);
        precio = (EditText) findViewById(R.id.txtPrecio);
        categoria = (Spinner) findViewById(R.id.cbCategorias);
        llenaCategoria(categoria);//llenar Spinner
    }

    /**
     * Este método llena el Spinner de las categorias.
     * @param categoria
     */
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
            categoria.setAdapter(adaptador);//asignar al Spinner
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este método pone Strings vacíos y pone al Spinner en el índice 0. Esto se hace después de
     * haber dado de alta un producto
     * @param v
     */
    protected void limpia(View v){
        nombre.setText("");
        descripcion.setText("");
        precio.setText("");
        categoria.setSelection(0);
    }


    /**
     * Método para dar de alta productos. Se avisa al usuario si fue o no posible agregar el producto
     * por medio de un Toast
     * @param v
     */
    protected void alta(View v){
        String nombreS, descripcionS, categoriaS;
        double precioS;
        int id;

        try{
            AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();

            //recuperar en variables que voy a meter en la BD de la caja de texto
            nombreS = nombre.getText().toString();
            descripcionS = descripcion.getText().toString();
            precioS = Double.parseDouble(precio.getText().toString());
            categoriaS = categoria.getSelectedItem().toString();

            //Asignar un id al producto
            Cursor fila= db.rawQuery("select max(idProducto)from productos",null);//pedir la ultima id
            if(fila.moveToFirst()) //ya se registraron productos
                id = fila.getInt(0) + 1 ;
            else //primer producto
                id = 1;

            //Meter a Bd
            db.execSQL("INSERT INTO productos (idProducto, descripcion, nombre, precio, categoria) " +
                    "VALUES("+ id +", '" + descripcionS+ "', '"+nombreS+"', "+ precioS+", '"+categoriaS+"')");
            Toast.makeText(this, "Producto dado de alta", Toast.LENGTH_LONG);
            limpia(v);//Vuelve a vaciar las casillas
            db.close();//cerrar por seguridad
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
