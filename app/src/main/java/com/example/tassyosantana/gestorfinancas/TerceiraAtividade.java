package com.example.tassyosantana.gestorfinancas;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLException;
import java.util.ArrayList;

import DAO.CategoriaDAO;
import DAO.FrequenciaDAO;
import Model.Categoria;

public class TerceiraAtividade extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terceira_atividade2);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        preencherLista();
    }

    public void telaAnterior(View view){
        Intent Activity = new Intent(TerceiraAtividade.this, AtividadePrincipal.class);
        startActivityForResult(Activity, 1);
        finish();
    }

    public void preencherLista() {
        ArrayList<String> ar = new ArrayList<String>();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        if (categoriaDAO.listaTodasCategorias().size() > 0){
            for (int i =0 ; i< categoriaDAO.listaTodasCategorias().size();i++){
                ar.add(categoriaDAO.listaTodasCategorias().get(i).getNome());
                ar.add("Editar");
                ar.add("Excluir");
            }
        }

        GridView gv = (GridView) findViewById(R.id.gridViewCategoria);

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,R.layout.lista_categorias,ar);
        gv.setAdapter(ad);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            if (((TextView) v).getText().equals("Editar")) {
                GridView gv = (GridView) findViewById(R.id.gridViewCategoria);
                TextView tv = (TextView) gv.getAdapter().getView(position - 1, null, gv);
                EditarClick(tv.getText().toString().trim());
            } else if (((TextView) v).getText().equals("Excluir")) {
                GridView gv = (GridView) findViewById(R.id.gridViewCategoria);
                TextView tv = (TextView) gv.getAdapter().getView(position - 2, null, gv);
                ExcluirClick(tv.getText().toString().trim());
            } else {
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                Categoria categoria = categoriaDAO.buscaPorNome(((TextView) v).getText().toString().trim());

                String tipo = "";
                if (categoria.getTipo().equals("D"))
                    tipo = "despesa";
                else
                    tipo = "receita";

                FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
                String descricao = frequenciaDAO.buscaFrequenciaPorId(categoria.getId());

                //Ver as informações da categoria ao clicar na célula
                Toast.makeText(getApplicationContext(), "categoria: " + categoria.getNome() + "\n" +
                        "valor: R$" + categoria.getValor() + "\n" +
                        "data: " + categoria.getDataAgendada() + "\n" +
                        "tipo: " + tipo + "\n" +
                        "frequência: " + descricao, Toast.LENGTH_LONG).show();

            }
    }

    public void ExcluirClick(String descricao_categoria) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria categoria = categoriaDAO.buscaPorNome(descricao_categoria.trim());

        if (categoriaDAO.temMovimento(categoria.getId()))
            Toast.makeText(getApplicationContext(), "Não foi possível excluir! \nCategoria já possui movimento", Toast.LENGTH_SHORT).show();
        else {
            preencherLista();
            Toast.makeText(getApplicationContext(), categoriaDAO.deletar(categoria.getId()), Toast.LENGTH_SHORT).show() ;

        }
    }

    public void EditarClick(String descricao_categoria) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria categoria = categoriaDAO.buscaPorNome(descricao_categoria.trim());
        ArrayList <String> prop = new ArrayList<String>();

        prop.add(0,categoria.getId().toString());
        prop.add(1,categoria.getNome());
        prop.add(2, categoria.getValor().toString());
        prop.add(3,categoria.getDataAgendada());
        prop.add(4,categoria.getTipo());
        prop.add(5,categoria.getFrequencia_id().toString());

        Intent intent = new Intent(this, SegundaAtividade.class);
        Bundle b = new Bundle();
        b.putStringArrayList("categoria",prop);
        intent.putExtras(b);
        startActivity( intent);
        finish();
    }
}
