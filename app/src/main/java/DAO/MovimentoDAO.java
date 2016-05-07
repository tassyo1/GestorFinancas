package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.Date;

import DAO.Banco;
/**
 * Created by tassyosantana on 06/05/16.
 */
public class MovimentoDAO  {
    private SQLiteDatabase db;
    private Banco banco;

    public MovimentoDAO(Context context){
        banco = new Banco(context);
    }

    public String inserir(String data_lancamento, Float saldo, Integer categoria_id){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("data_lacamento",data_lancamento);
        valores.put("saldo_atual",saldo);
        valores.put("categoria_id",categoria_id);
        resultado = db.insert("movimentos",null,valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

    public ArrayList<String> getAllMovimentos(){
        ArrayList<String> movimentos = new ArrayList<String>();
        String query = "SELECT * FROM movimentos order by data desc;";
        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()){
            do {

            }while(c.moveToNext());
        }
        return movimentos;
    }

}
