package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import DAO.Banco;
/**
 * Created by tassyosantana on 06/05/16.
 */
public class CategoriaDAO {
    private SQLiteDatabase db;
    private Banco banco;

    public CategoriaDAO(Context context){
        banco = new Banco(context);
    }

    public String inserir(String tipo, String nome, Integer frequencia_id,
                          Date data, Float valor){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("tipo",tipo);
        valores.put("nome",nome);
        valores.put("frequencia_id",frequencia_id);
        valores.put("data_agendada",data.toString());
        resultado = db.insert("categorias",null,valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

}
