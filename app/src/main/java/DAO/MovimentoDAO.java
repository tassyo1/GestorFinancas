package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.Date;

import DAO.Banco;
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

    public ArrayList<Movimento> getAllMovimentos(){
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        String query =
                "SELECT categorias.nome, categorias.valor, data_lancamento, saldo_atual FROM movimentos "
                +"INNER JOIN categorias ON categorias.id = movimentos.categoria_id ORDER BY movimentos.data_lancamento desc";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()){
            do {
                Movimento movimento_model = new Movimento();
                movimento_model.setData_lancamento(c.getString(c.getColumnIndex("data_lancamento")));
                movimento_model.setSaldo_atual(c.getFloat(c.getColumnIndex("saldo_atual")));
                movimento_model.setNome_categoria(c.getString(c.getColumnIndex("nome")));
                movimento_model.setValor(c.getFloat(c.getColumnIndex("valor")));

                movimentos.add(movimento_model);
            }while(c.moveToNext());
        }
        return movimentos;
    }

}
