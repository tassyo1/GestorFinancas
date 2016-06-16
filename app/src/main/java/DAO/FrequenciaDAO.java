package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Frequencia;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class FrequenciaDAO {
    private Banco banco;
    private Statement stmt;

    public FrequenciaDAO() throws SQLException, ClassNotFoundException{
        banco = new Banco();
        stmt = (Statement) banco.getConn().createStatement();
    }

    public ArrayList<Frequencia> getAllFrequencia() throws SQLException{
        ArrayList<Frequencia> frequencias = new ArrayList<Frequencia>();
        String query = "SELECT id, descricao FROM frequencias order by id asc;";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            frequencias.add(new Frequencia(rs.getInt("id"),rs.getString("descricao")));
        }
        banco.fecharConexao();
        return frequencias;
    }

    public String buscaFrequenciaPorId(Integer id) throws SQLException{
        String descricao ="";


        ResultSet rs = stmt.executeQuery("select descricao from frequencias where id =" + id);

        while (rs.next()){
            descricao = rs.getString("descricao");
        }
        banco.fecharConexao();
        return descricao;
    }

}
