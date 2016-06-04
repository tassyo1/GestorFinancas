package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public String buscaFrequenciaPorId(Integer id){
        String descricao ="";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("select descricao from frequencias where id ="+id, null);

        if(c.moveToFirst()){
            do{
                descricao = c.getString(c.getColumnIndex("descricao"));
            }while (c.moveToNext());
        }
        return descricao;
    }

}
