package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

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

    public ArrayList<Frequencia> getAllFrequencia(){
        ArrayList<Frequencia> frequencias = new ArrayList<Frequencia>();
        String query = "SELECT id, descricao FROM frequencias order by id asc;";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do {
                frequencias.add( new Frequencia(c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("descricao"))) );
            }while(c.moveToNext());
        }
        return frequencias;
    }

}
