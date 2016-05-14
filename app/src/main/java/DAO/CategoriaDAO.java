package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import DAO.Banco;
import Model.Categoria;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class CategoriaDAO {
    private SQLiteDatabase db;
    private Banco banco;

    public CategoriaDAO(Context context){
        banco = new Banco(context);
    }

    public String inserir(String tipo, String nome, Integer frequencia_id, String data, Float valor){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("tipo",tipo);
        valores.put("nome",nome);
        valores.put("frequencia_id",frequencia_id);
        valores.put("data_agendada",data);
        valores.put("valor",valor);
        resultado = db.insert("categorias",null,valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

    public Categoria buscaPorNome(String nome){
        String query =
                "SELECT * FROM categorias where nome like '%"+nome+"%'";
        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c != null)
            c.moveToFirst();

        Categoria categoria_model = new Categoria();
        categoria_model.setId(c.getInt(c.getColumnIndex("id")));
        categoria_model.setNome(c.getString(c.getColumnIndex("nome")));
        categoria_model.setDataAgendada(c.getString(c.getColumnIndex("data_agendada")));
        categoria_model.setFrequencia_id(c.getInt(c.getColumnIndex("frequencia_id")));
        categoria_model.setTipo(c.getString(c.getColumnIndex("tipo")));
        categoria_model.setValor(c.getFloat(c.getColumnIndex("valor")));

        return categoria_model;
    }

}
