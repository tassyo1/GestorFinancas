package DAO;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Movimento;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class MovimentoDAO  {
    private ArrayList<String> soapParams;
    private HashMap params;

    public String inserir(String data_lancamento, Float saldo, Integer categoria_id) {


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



        return movimentos;
    }

    public Movimento buscaUltimoMovimento() {
        Movimento movimento_model = new Movimento();
        String query =
                "SELECT * FROM movimentos ORDER BY id DESC LIMIT 1";



        return movimento_model;
    }

    //traz movimentos com categoria passada por parâmetro  que foram lançados hj
    public Movimento buscaMovimentosPorCategoriaHoje(int categoria_id) {
        Movimento movimento = new Movimento();
        String query = "select distinct categoria_id from movimentos where categoria_id ="+categoria_id+
                        " and DATE (substr(data_lancamento, 7, 4) || '-' || " +
                        "substr(data_lancamento, 4, 2) || '-' || substr(data_lancamento, 1, 2)) " +
                        " <=  date('now');  ";



        return movimento;
    }

}
