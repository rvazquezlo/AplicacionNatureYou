package com.example.carlos.aplicacionnatureyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {
    private RadioButton alta, baja, buscar, modifica, navegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        alta = (RadioButton)findViewById(R.id.rbAlta);
        baja = (RadioButton)findViewById(R.id.rbBaja);
        buscar = (RadioButton)findViewById(R.id.rbBuscar);
        modifica = (RadioButton)findViewById(R.id.rbModificar);
        navegar = (RadioButton)findViewById(R.id.rbNoticia);
    }

    protected void ir(View v){
        try{
           //ver qué botón está seleccionado para determinar a donde redirigir
           if(alta.isChecked()){
               Intent myIntent = new Intent(v.getContext(), AltaProductos.class);
               startActivityForResult(myIntent, 0);
           }
           else if(baja.isChecked()){
               Intent myIntent = new Intent(v.getContext(), Baja.class);
               startActivityForResult(myIntent, 0);
           }
           else if(buscar.isChecked()){
               Intent myIntent = new Intent(v.getContext(), BuscarProducto.class);
               startActivityForResult(myIntent, 0);
           }
           else if(navegar.isChecked()){
               Intent myIntent = new Intent(v.getContext(), Navega.class);
               startActivityForResult(myIntent, 0);
           }
           else{
               Intent myIntent = new Intent(v.getContext(), Modificar.class);
               startActivityForResult(myIntent, 0);
           }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
