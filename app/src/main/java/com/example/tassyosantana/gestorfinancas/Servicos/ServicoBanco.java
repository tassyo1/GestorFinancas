package com.example.tassyosantana.gestorfinancas.Servicos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import DAO.MovimentoDAO;


/**
 * Created by tassyosantana on 10/05/16.
 */
public class ServicoBanco extends Service implements Runnable {
    private MovimentoDAO movimentoDAO = new MovimentoDAO(getBaseContext());

    @Override
    public void onCreate() {
        Thread thread = new Thread(this);
        thread.start();
        Log.d("Serviço", "Serviço criado");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Serviço", "Serviço destruido");
        super.onDestroy();
    }

    private void fazAlgo(){
        Log.d("Serviço","Estou fazendo algo");
    }
    /*private void geraMovimento(){

    }*/

    @Override
    public void run(){
        fazAlgo();
        stopSelf();
    }
}
