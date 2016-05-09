package com.example.tassyosantana.gestorfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import DAO.FrequenciaDAO;
import Model.Frequencia;

public class SegundaAtividade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_atividade);

        Spinner spinner = (Spinner) findViewById(R.id.listaFrequencia);

        FrequenciaDAO frequenciaDados = new FrequenciaDAO(getBaseContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,frequenciaDados.getAllFrequencia()); // array de frequencias

        spinner.setAdapter(adapter);

    }
    public void telaAnterior(View view){

        Intent Activity2 = new Intent(SegundaAtividade.this, AtividadePrincipal.class);
        startActivityForResult(Activity2, 1);
        finish();
    }
}
