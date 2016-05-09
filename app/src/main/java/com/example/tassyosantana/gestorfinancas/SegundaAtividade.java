package com.example.tassyosantana.gestorfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SegundaAtividade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_atividade);
    }
    public void telaAnterior(View view){

        Intent Activity2 = new Intent(SegundaAtividade.this, AtividadePrincipal.class);
        startActivityForResult(Activity2, 1);
        finish();
    }
}
