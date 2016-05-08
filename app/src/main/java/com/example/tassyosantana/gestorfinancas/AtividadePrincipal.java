package com.example.tassyosantana.gestorfinancas;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;

public class AtividadePrincipal extends AppCompatActivity {
    GridView grid;
    ArrayAdapter<String> adapter;

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
        a.add("-500000,00");
        a.add("30/04/2015");
        a.add("20000,00");
        a.add("TV à cabo");
        a.add("-150,00");
        a.add("05/05/2016");
        a.add("100000000,00");
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
                params.height = getPixelsFromDPs(AtividadePrincipal.this,25);
                params.width = getPixelsFromDPs(AtividadePrincipal.this, 150);
                tv.setLayoutParams(params);

                return tv;
            }
        };

        grid.setAdapter(adapter);


        grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Method for converting DP value to pixels
    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
}
