package com.example.carlos.aplicacionnatureyou;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class AltaProductos extends AppCompatActivity {
    private EditText nombre, descripcion, precio;
    private Spinner categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobar_productos);
        nombre = (EditText) findViewById(R.id.txtNombre);
        descripcion = (EditText) findViewById(R.id.txtDescripcion);
        precio = (EditText) findViewById(R.id.txtPrecio);
        categoria = (Spinner) findViewById(R.id.cbCategorias);
    }

    public void limpia(View v){
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
    public void alta(View v){
        String nombreS, descripcionS, categoriaS;
        double precioS;
        int id;

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
            id = fila.getInt(0);
        else //primer producto
            id = 1;
        db.close();//cerrar por seguridad

        //Content Value para meterlos a la BD
        ContentValues registro = new ContentValues();
        registro.put("idProducto",id);
        registro.put("descripcion",descripcionS);
        registro.put("nombre",nombreS);
        registro.put("precio",precioS);
        registro.put("categoria",categoriaS);
        if(db.insert("productos",null,registro) > 0)
            Toast.makeText(this,"se cargaron los datos del producto con el id: "
                    + id,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"no se cargaron los datos del producto",Toast.LENGTH_LONG).show();
        limpia(v);

    }

}