package com.example.tassyosantana.gestorfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;

public class AtividadePrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);

        //clique do imagebutton para outra tela
        ImageButton adicionaCategoria = (ImageButton) findViewById(R.id.adicionaCategoria);
        adicionaCategoria.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Activity2 = new Intent(AtividadePrincipal.this, SegundaAtividade.class);
                startActivityForResult(Activity2, 1);
                finish();
            }
        });

        ArrayList <String> a = new ArrayList<String>();
        a.add("lanche");
        a.add("-5,00");
        a.add("30/04/2015");
        a.add("2000,00");
        a.add("Berlim");
        a.add("Praga");
        a.add("Moscou");
        a.add("Amsterdan");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, a);

        GridView grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(adapter);


        grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
