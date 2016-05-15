package com.example.tassyosantana.gestorfinancas.Servicos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import DAO.CategoriaDAO;
import DAO.MovimentoDAO;
import Model.Categoria;


/**
 * Created by tassyosantana on 10/05/16.
 */
public class ServicoBanco extends Service  {
    private MovimentoDAO movimentoDAO = new MovimentoDAO(getBaseContext());
    private CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());

    public class LocalBinder extends Binder{
        public ServicoBanco getServerInstance() {
            return ServicoBanco.this;
        }
    }
    IBinder mBinder = new LocalBinder();

    public ServicoBanco(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.d("Serviço", "Serviço criado");
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        Log.d("Serviço", "Serviço destruido");
        //super.onDestroy();
    }

    public void gerarMovimento(String data_lancamento, Float saldo, Integer categoria_id){

        movimentoDAO.inserir(data_lancamento, saldo, categoria_id);
    }

    public Categoria trazCategoria(String nome){
        return categoriaDAO.buscaPorNome(nome);
    }

}
