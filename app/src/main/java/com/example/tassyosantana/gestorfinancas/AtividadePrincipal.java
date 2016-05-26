package com.example.tassyosantana.gestorfinancas;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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


public class AtividadePrincipal extends AppCompatActivity implements OnItemClickListener{
    GridView grid;
    ArrayAdapter<String> adapter;
    boolean mBound = false;
    Categoria categoria;
    Movimento movimento_model;
    ArrayList<Categoria>arrayDeCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);

        Log.d("Atividade", "Atividade Criada");
    }

    @Override
    protected void onStart() {
        super.onStart();

        geraVariosMovimentos();
        geraMovimentosFrequentes();
        preencheGrid();

    }

    @Override
    protected void onStop() {
        super.onStop();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Toast.makeText(getApplicationContext(),
                ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
    }

    public boolean verificaData(){
        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
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

    public ArrayList<Categoria> removeCategoriaQueGerouMovimento(ArrayList<Categoria> array){
        MovimentoDAO movimentoDAO = new MovimentoDAO(getBaseContext());
        Movimento movimento_geral;
        for (int i = 0; i < array.size(); i++){
            movimento_geral = movimentoDAO.buscaMovimentosPorCategoriaHoje(array.get(i).getId());
            if (array.get(i).getId() == movimento_geral.getCategoria_id()){
                array.remove(i);
            }
        }

        return array;
    }
    public void geraMovimentosFrequentes(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        MovimentoDAO movimentoDAO = new MovimentoDAO(getBaseContext());
        arrayDeCategoria = removeCategoriaQueGerouMovimento(categoriaDAO.buscaCategoriasFrequentes());

        Date agora = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (arrayDeCategoria.size() > 0) {
            for (int i = 0; i < arrayDeCategoria.size(); i++) {
                Integer mes = Integer.parseInt(arrayDeCategoria.get(i).getDataAgendada().substring(3,5));
                // se for dezembro o próximo será janeiro
                mes =(mes + 1 == 13)?1:mes;

                //mensal
                if(arrayDeCategoria.get(i).getFrequencia_id().toString().equals("2") &&
                        ( mes + 1 == Integer.parseInt(dateFormat.format(agora).toString().substring(3,5)))){

                    movimento_model = movimentoDAO.buscaUltimoMovimento();
                    movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                    movimentoDAO.inserir(dateFormat.format(agora).toString(),
                            movimento_model.getSaldo_atual(),
                            arrayDeCategoria.get(i).getId());


                  //bimestral
                }else if(arrayDeCategoria.get(i).getFrequencia_id().toString().equals("3") &&
                        (mes + 2 == Integer.parseInt(dateFormat.format(agora).toString().substring(3,5))) ){
                    movimento_model = movimentoDAO.buscaUltimoMovimento();
                    movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                    movimentoDAO.inserir(dateFormat.format(agora).toString(),
                            movimento_model.getSaldo_atual(),
                            arrayDeCategoria.get(i).getId());

                  //trimestral
                }else if(arrayDeCategoria.get(i).getFrequencia_id().toString().equals("4") &&
                        (mes + 3 == Integer.parseInt(dateFormat.format(agora).toString().substring(3,5))) ){
                    movimento_model = movimentoDAO.buscaUltimoMovimento();
                    movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                    movimentoDAO.inserir(dateFormat.format(agora).toString(),
                            movimento_model.getSaldo_atual(),
                            arrayDeCategoria.get(i).getId());

                  //semestral
                }else if (arrayDeCategoria.get(i).getFrequencia_id().toString().equals("5") &&
                        (mes + 6 == Integer.parseInt(dateFormat.format(agora).toString().substring(3,5))) ){
                    movimento_model = movimentoDAO.buscaUltimoMovimento();
                    movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                    movimentoDAO.inserir(dateFormat.format(agora).toString(),
                            movimento_model.getSaldo_atual(),
                            arrayDeCategoria.get(i).getId());


                    //anual
                }else if (arrayDeCategoria.get(i).getFrequencia_id().toString().equals("6") &&
                        (mes == Integer.parseInt(dateFormat.format(agora).toString().substring(3,5))) ){
                    movimento_model = movimentoDAO.buscaUltimoMovimento();
                    movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                    movimentoDAO.inserir(dateFormat.format(agora).toString(),
                            movimento_model.getSaldo_atual(),
                            arrayDeCategoria.get(i).getId());
                }
            }
        }

    }
    public void geraVariosMovimentos(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        MovimentoDAO movimentoDAO = new MovimentoDAO(getBaseContext());
        arrayDeCategoria = categoriaDAO.buscaCategoriasAnteriores();

        if (arrayDeCategoria.size() > 0) {
            for (int i = 0; i < arrayDeCategoria.size(); i++) {

                //para pegar o saldo mais recente
                movimento_model = movimentoDAO.buscaUltimoMovimento();
                movimento_model.atualizaSaldo(arrayDeCategoria.get(i).getValor(), arrayDeCategoria.get(i).getTipo());

                movimentoDAO.inserir(arrayDeCategoria.get(i).getDataAgendada(),
                        movimento_model.getSaldo_atual(),
                        arrayDeCategoria.get(i).getId());

            }

        }

    }

    public void preencheGrid(){
        ArrayList <String> a = new ArrayList<String>();
        MovimentoDAO movimentoDados = new MovimentoDAO(getBaseContext());
        if (movimentoDados.buscaTodosMovimentos().size() > 0) {

            for (int i = 0; i < movimentoDados.buscaTodosMovimentos().size(); i++) {
                a.add(movimentoDados.buscaTodosMovimentos().get(i).getNome_categoria() + "\n"
                        + movimentoDados.buscaTodosMovimentos().get(i).getData_lancamento());

                //preenchendo valor no adapter do grid
                if (movimentoDados.buscaTodosMovimentos().get(i).getTipo().equals("D")) {
                    a.add("-"+movimentoDados.buscaTodosMovimentos().get(i).getValor().toString());
                }else {
                    a.add("+"+movimentoDados.buscaTodosMovimentos().get(i).getValor().toString());
                }
            }
            TextView saldo = (TextView) findViewById(R.id.labelSaldoAtual);
            saldo.setText(movimentoDados.buscaUltimoMovimento().getSaldo_atual().toString());
            // saldo.setText(movimentoDados.buscaTodosMovimentos().get(0).getSaldo_atual().toString());

        }
        grid = (GridView) findViewById(R.id.gridView);


        //parte do layout do grid
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
