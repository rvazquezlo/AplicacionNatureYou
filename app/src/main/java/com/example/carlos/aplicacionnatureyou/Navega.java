package com.example.carlos.aplicacionnatureyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Navega extends AppCompatActivity {

    /**
     * En este método se abre el url en una WebView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navega);
        WebView wb = new WebView(this);
        setContentView(wb);
        wb.loadUrl("https://www.ecoticias.com/eco-america");
    }
}
