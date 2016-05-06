package Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import DAO.Banco;
/**
 * Created by tassyosantana on 06/05/16.
 */
public class MovimentoController {
    private SQLiteDatabase db;
    private Banco banco;

    public MovimentoController(Context context){
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

}
