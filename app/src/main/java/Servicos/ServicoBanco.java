package Servicos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void fazAlgo(){
        Toast.makeText(this,"Estou fazendo algo",Toast.LENGTH_SHORT).show();
    }
    /*private void geraMovimento(){

    }*/

    @Override
    public void run(){
        fazAlgo();
        stopSelf();
    }
}
