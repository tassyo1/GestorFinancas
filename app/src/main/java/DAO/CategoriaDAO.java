package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

    public Categoria buscaUltimaCategoria(){
        String query ="SELECT * FROM categorias ORDER BY id DESC LIMIT 1";

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

    //busca as categorias sem movimentos e com a data igual ou inferior a hoje
    public ArrayList<Categoria> buscaCategoriasAnteriores() {

        String query = "select categorias.* from categorias left join movimentos on categorias.id = categoria_id where categoria_id is null "+
        "and DATE (substr(data_agendada, 7, 4) || '-' || substr(data_agendada, 4, 2) || '-' || substr(data_agendada, 1, 2)) "+
                "<= date('now');";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        ArrayList<Categoria> array = new ArrayList<Categoria>();

        if (c.moveToFirst()){
            do {
                Categoria categoria = new Categoria();
                categoria.setId(c.getInt(c.getColumnIndex("id")));
                categoria.setNome(c.getString(c.getColumnIndex("nome")));
                categoria.setDataAgendada(c.getString(c.getColumnIndex("data_agendada")));
                categoria.setFrequencia_id(c.getInt(c.getColumnIndex("frequencia_id")));
                categoria.setTipo(c.getString(c.getColumnIndex("tipo")));
                categoria.setValor(c.getFloat(c.getColumnIndex("valor")));

                array.add(categoria);
            }while(c.moveToNext());
        }
        db.close();

        return array;
    }

    public ArrayList<Categoria> buscaCategoriasFrequentes(){
        String query = "select categorias.* from categorias where frequencia_id <> 1 "+
                "and substr(data_agendada, 1, 2)" +
                "= substr(date('now'),9,2);";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        ArrayList<Categoria> array = new ArrayList<Categoria>();

        if (c.moveToFirst()){
            do {
                Categoria categoria = new Categoria();
                categoria.setId(c.getInt(c.getColumnIndex("id")));
                categoria.setNome(c.getString(c.getColumnIndex("nome")));
                categoria.setDataAgendada(c.getString(c.getColumnIndex("data_agendada")));
                categoria.setFrequencia_id(c.getInt(c.getColumnIndex("frequencia_id")));
                categoria.setTipo(c.getString(c.getColumnIndex("tipo")));
                categoria.setValor(c.getFloat(c.getColumnIndex("valor")));

                array.add(categoria);
            }while(c.moveToNext());
        }
        db.close();

        return array;
    }
}
