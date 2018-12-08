package com.example.carlos.aplicacionnatureyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {
    private RadioButton alta, baja, modifica, navegar;

    /**
     * En este método se recuperan los RadioButtons necesarios del Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        alta = (RadioButton)findViewById(R.id.rbAlta);
        baja = (RadioButton)findViewById(R.id.rbBaja);
        modifica = (RadioButton)findViewById(R.id.rbModificar);
        navegar = (RadioButton)findViewById(R.id.rbNoticia);
    }

    /**
     * Este método manda a la Activity seleccionada en el RadioButton
     * @param v
     */
    protected void ir(View v){
        try{
           //ver qué botón está seleccionado para determinar a donde redirigir
           if(alta.isChecked()){//mandar a AltaProductos
               Intent myIntent = new Intent(v.getContext(), AltaProductos.class);
               startActivityForResult(myIntent, 0);
           }
           else if(baja.isChecked()){//mandar a Baja
               Intent myIntent = new Intent(this, Baja.class);
               startActivity(myIntent);
           }
           else if(navegar.isChecked()){//mandar a Navegar
               Intent myIntent = new Intent(v.getContext(), Navega.class);
               startActivityForResult(myIntent, 0);
           }
           else{//Mandar a Modificar
               Intent myIntent = new Intent(this, Modificar.class);
               startActivity(myIntent);
           }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error en Inicio: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
