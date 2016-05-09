package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import DAO.Banco;
import Model.Frequencia;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class FrequenciaDAO {
    private SQLiteDatabase db;
    private Banco banco;


    public FrequenciaDAO(Context context){
        banco = new Banco(context);
    }

    public ArrayList<String> getAllFrequencia(){
        ArrayList<String> frequencias = new ArrayList<String>();
        String query = "SELECT descricao FROM frequencias order by id asc;";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do {
                Frequencia frequencia = new Frequencia();
                frequencia.setDescricao(c.getString(c.getColumnIndex("descricao")));

                frequencias.add(frequencia.getDescricao());
            }while(c.moveToNext());
        }
        return frequencias;
    }

}
