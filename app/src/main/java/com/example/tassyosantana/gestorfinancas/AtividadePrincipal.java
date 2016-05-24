package com.example.tassyosantana.gestorfinancas;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DAO.CategoriaDAO;
import DAO.MovimentoDAO;
import Model.Categoria;
import Model.Movimento;

import com.example.tassyosantana.gestorfinancas.Servicos.ServicoBanco;


public class AtividadePrincipal extends AppCompatActivity implements OnItemClickListener{
    GridView grid;
    ArrayAdapter<String> adapter;
    ServicoBanco servicoBanco;
    boolean mBound = false;
    Categoria categoria;
    CategoriaDAO categoriaDAO;
    Movimento movimento_model;
    MovimentoDAO movimentoDAO;
    ArrayList<Categoria>arrayDeCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);

        Log.d("Atividade", "Atividade Criada");



    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, ServicoBanco.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if(!mBound){
            startService(new Intent(getBaseContext(), ServicoBanco.class));
            mBound = true;
            geraVariosMovimentos();

           // if (verificaData()){

           // }else{

                //até aqui está ok


        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServicoBanco.LocalBinder binder = (ServicoBanco.LocalBinder) service;
            servicoBanco = binder.getServerInstance();
            mBound = true;
            Toast.makeText(AtividadePrincipal.this, "Serviço Banco Ligado", Toast.LENGTH_SHORT).show();
            Log.d("Serviço", "Serviço Banco Ligado");
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Toast.makeText(AtividadePrincipal.this, "Serviço Banco Desligado", Toast.LENGTH_SHORT).show();
            Log.d("Serviço", "Serviço Banco Desligado");

        }
    };


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

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Toast.makeText(getApplicationContext(),
                ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
    }

    public boolean verificaData(){
        try {
            categoria = categoriaDAO.buscaUltimaCategoria();

            Date data = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);

            Date data_atual = cal.getTime(); //pega data atual
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date data_widget = format.parse(categoria.getDataAgendada().toString());

            if (data_widget.after(data_atual))
                return true;
            else
                return false;
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void geraVariosMovimentos(){
        categoriaDAO = new CategoriaDAO(getBaseContext());
        movimentoDAO = new MovimentoDAO(getBaseContext());
        arrayDeCategoria = categoriaDAO.buscaCategoriasSemMovimento();

        if (arrayDeCategoria.size() > 0) {
            for (int i = 0; i < arrayDeCategoria.size(); i++) {

                movimento_model = movimentoDAO.buscaUltimoMovimento();
                movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                movimentoDAO.inserir(arrayDeCategoria.get(i).getDataAgendada(),
                        movimento_model.getSaldo_atual(),
                        arrayDeCategoria.get(i).getId());

                /*servicoBanco.gerarMovimento(arrayDeCategoria.get(i).getDataAgendada(),
                        movimento_model.getSaldo_atual(),
                        arrayDeCategoria.get(i).getId());*/
            }

        }
        preencheGrid();
    }

    public void preencheGrid(){
        ArrayList <String> a = new ArrayList<String>();
        MovimentoDAO movimentoDados = new MovimentoDAO(getBaseContext());
        if (movimentoDados.buscaTodosMovimentos().size() > 0){

            for (int i = 0; i < movimentoDados.buscaTodosMovimentos().size(); i++){
                a.add(movimentoDados.buscaTodosMovimentos().get(i).getNome_categoria()+"\n"
                        +movimentoDados.buscaTodosMovimentos().get(i).getData_lancamento());

                a.add(movimentoDados.buscaTodosMovimentos().get(i).getValor().toString());
            }
            TextView saldo = (TextView)findViewById(R.id.labelSaldoAtual);
           // saldo.setText(movimentoDados.buscaTodosMovimentos().get(0).getSaldo_atual().toString());
            saldo.setText(movimentoDados.buscaUltimoMovimento().getSaldo_atual().toString());
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
        grid.setOnItemClickListener(this);
    }

}
