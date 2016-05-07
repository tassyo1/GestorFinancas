package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import DAO.Banco;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class FrequenciaDAO {
    private SQLiteDatabase db;
    private Banco banco;


    public FrequenciaDAO(Context context){
        banco = new Banco(context);
    }


}
