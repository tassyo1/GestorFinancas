package com.example.tassyosantana.gestorfinancas;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;

import DAO.MovimentoDAO;
import Servicos.ServicoBanco;


public class AtividadePrincipal extends AppCompatActivity {
    GridView grid;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);

        //iniciando o Serviço do Banco
        startService(new Intent(this, ServicoBanco.class));


        ArrayList <String> a = new ArrayList<String>();
        MovimentoDAO movimentoDados = new MovimentoDAO(getBaseContext());
        if (movimentoDados.getAllMovimentos().size() > 0){

            for (int i = 0; i < movimentoDados.getAllMovimentos().size(); i++){
                a.add(movimentoDados.getAllMovimentos().get(i).getNome_categoria()+"\n"
                +movimentoDados.getAllMovimentos().get(i).getData_lancamento());

                a.add(movimentoDados.getAllMovimentos().get(i).getValor().toString());
            }
        TextView saldo = (TextView)findViewById(R.id.labelSaldoAtual);
        saldo.setText(movimentoDados.getAllMovimentos().get(0).getSaldo_atual().toString());
        }
        grid = (GridView) findViewById(R.id.gridView);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, a){
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                tv.setGravity(Gravity.LEFT);


                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT
                );

                tv.setLayoutParams(lp);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
                tv.setBackgroundColor(Color.WHITE);
                params.height = getPixelsFromDPs(AtividadePrincipal.this,50);
                params.width = getPixelsFromDPs(AtividadePrincipal.this, 175);
                tv.setLayoutParams(params);

                return tv;
            }
        };
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    // DP para pixels
    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

    public void proximaTela(View view){
        Intent Activity2 = new Intent(AtividadePrincipal.this, SegundaAtividade.class);
        startActivityForResult(Activity2, 1);
        finish();
    }

}
