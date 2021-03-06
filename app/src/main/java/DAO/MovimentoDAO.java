package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import Model.Movimento;

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
        valores.put("data_lancamento",data_lancamento);
        valores.put("saldo_atual",saldo);
        valores.put("categoria_id",categoria_id);
        resultado = db.insert("movimentos",null,valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

    //usado para preencher o grid
    public ArrayList<Movimento> buscaTodosMovimentos(){
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        String query =
                "SELECT categorias.nome, categorias.valor, data_lancamento, saldo_atual, categorias.tipo FROM movimentos "
                +"INNER JOIN categorias ON categorias.id = movimentos.categoria_id ORDER BY " +
                        "date (substr(data_lancamento, 7, 4) || '-' ||"+
                "substr(data_lancamento, 4, 2) || '-' || substr(data_lancamento, 1, 2)) desc";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()){
            do {
                Movimento movimento_model = new Movimento();
                movimento_model.setData_lancamento(c.getString(c.getColumnIndex("data_lancamento")));
                movimento_model.setSaldo_atual(c.getFloat(c.getColumnIndex("saldo_atual")));
                movimento_model.setNome_categoria(c.getString(c.getColumnIndex("nome")));
                movimento_model.setValor(c.getFloat(c.getColumnIndex("valor")));
                movimento_model.setTipo(c.getString(c.getColumnIndex("tipo")));

                movimentos.add(movimento_model);
            }while(c.moveToNext());
        }
        db.close();
        return movimentos;
    }

    public Movimento buscaUltimoMovimento(){
        Movimento movimento_model = new Movimento();
        String query =
                "SELECT * FROM movimentos ORDER BY id DESC LIMIT 1";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();

            movimento_model.setId(c.getInt(c.getColumnIndex("id")));
            movimento_model.setData_lancamento(c.getString(c.getColumnIndex("data_lancamento")));
            movimento_model.setSaldo_atual(c.getFloat(c.getColumnIndex("saldo_atual")));
            movimento_model.setCategoria_id(c.getInt(c.getColumnIndex("categoria_id")));
        }

        db.close();

        return movimento_model;
    }

    //traz movimentos com categoria passada por parâmetro  que foram lançados hj
    public Movimento buscaMovimentosPorCategoriaHoje(int categoria_id){
        Movimento movimento = new Movimento();
        String query = "select distinct categoria_id from movimentos where categoria_id ="+categoria_id+
                        " and DATE (substr(data_lancamento, 7, 4) || '-' || " +
                        "substr(data_lancamento, 4, 2) || '-' || substr(data_lancamento, 1, 2)) " +
                        " <=  date('now');  ";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();

          /*  movimento.setId(c.getInt(c.getColumnIndex("id")));
            movimento.setData_lancamento(c.getString(c.getColumnIndex("data_lancamento")));
            movimento.setSaldo_atual(c.getFloat(c.getColumnIndex("saldo_atual"))); */
            movimento.setCategoria_id(c.getInt(c.getColumnIndex("categoria_id")));
        }
        db.close();

        return movimento;
    }

}
