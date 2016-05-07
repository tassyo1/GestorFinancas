package com.example.tassyosantana.gestorfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AtividadePrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);


        ImageButton adicionaCategoria = (ImageButton) findViewById(R.id.adicionaCategoria);
        adicionaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Activity2 = new Intent(AtividadePrincipal.this, SegundaAtividade.class);
                startActivityForResult(Activity2, 1);
                finish();
            }
        });
    }
}
