package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import Model.Movimento;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class MovimentoDAO  {

    private Banco banco;
    private Statement stmt;
    private PreparedStatement pstmt;


    public MovimentoDAO()throws SQLException, ClassNotFoundException{
        banco = new Banco();
        stmt = banco.getConn().createStatement();

    }
    public String inserir(String data_lancamento, Float saldo, Integer categoria_id) throws  SQLException{
        pstmt = banco.getConn().prepareStatement("insert into movimentos (data_lancamento, saldo_atual," +
                " categoria_id ) values(?,?,?)");

        long resultado;

        pstmt.setString(1, data_lancamento);
        pstmt.setString(2, saldo.toString());
        pstmt.setString(3, categoria_id.toString());

        resultado = pstmt.executeUpdate();
        banco.fecharConexao();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

    //usado para preencher o grid
    public ArrayList<Movimento> buscaTodosMovimentos() throws SQLException{
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        String query =
                "SELECT categorias.nome, categorias.valor, data_lancamento, saldo_atual, categorias.tipo FROM movimentos "
                +"INNER JOIN categorias ON categorias.id = movimentos.categoria_id ORDER BY " +
                        "date (substr(data_lancamento, 7, 4) || '-' ||"+
                "substr(data_lancamento, 4, 2) || '-' || substr(data_lancamento, 1, 2)) desc";

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {

            Movimento movimento_model = new Movimento();
            movimento_model.setData_lancamento(rs.getString("data_lancamento"));
            movimento_model.setSaldo_atual(rs.getFloat("saldo_atual"));
            movimento_model.setNome_categoria(rs.getString("nome"));
            movimento_model.setValor(rs.getFloat("valor"));
            movimento_model.setTipo(rs.getString("tipo"));

            movimentos.add(movimento_model);
        }
        banco.fecharConexao();
        return movimentos;
    }

    public Movimento buscaUltimoMovimento() throws SQLException{
        Movimento movimento_model = new Movimento();
        String query =
                "SELECT * FROM movimentos ORDER BY id DESC LIMIT 1";

        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {
            movimento_model.setId(rs.getInt("id"));
            movimento_model.setData_lancamento(rs.getString("data_lancamento"));
            movimento_model.setSaldo_atual(rs.getFloat("saldo_atual"));
            movimento_model.setCategoria_id(rs.getInt("categoria_id"));
        }

        banco.fecharConexao();

        return movimento_model;
    }

    //traz movimentos com categoria passada por parâmetro  que foram lançados hj
    public Movimento buscaMovimentosPorCategoriaHoje(int categoria_id) throws SQLException{
        Movimento movimento = new Movimento();
        String query = "select distinct categoria_id from movimentos where categoria_id ="+categoria_id+
                        " and DATE (substr(data_lancamento, 7, 4) || '-' || " +
                        "substr(data_lancamento, 4, 2) || '-' || substr(data_lancamento, 1, 2)) " +
                        " <=  date('now');  ";

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()){
            movimento.setCategoria_id(rs.getInt("categoria_id"));
        }

        banco.fecharConexao();

        return movimento;
    }

}
