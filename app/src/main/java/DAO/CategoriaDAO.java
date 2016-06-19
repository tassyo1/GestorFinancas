package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Categoria;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class CategoriaDAO {

    private Banco banco;
    private PreparedStatement pstmt;
    private Statement stmt;

    public CategoriaDAO() throws SQLException, ClassNotFoundException{
        banco = new Banco();

    }

    public String inserir(String tipo, String nome, Integer frequencia_id, String data, Float valor)
    throws ClassNotFoundException, SQLException{

        return "Registro inserido com sucesso";
    }

    public String atualizar(Integer id, String tipo, String nome, Integer frequencia_id, String data, Float valor )
    throws ClassNotFoundException, SQLException{



        return "Registro atualizado com sucesso";
    }

    public String deletar(Integer id) throws SQLException{
        long resultado;


        return "Registro deletado com sucesso";
    }

    public boolean temMovimento(Integer id) throws SQLException{
        boolean tem;
        String query = "select categorias.* from categorias inner join movimentos on categorias.id = categoria_id where " +
                "categorias.id = "+id +"  limit 1 ";

        String descricao ="";



        if (descricao.equals(""))
            tem = false;
        else
            tem = true;

        return tem;
    }

    public ArrayList<Categoria> listaTodasCategorias() throws SQLException, ClassNotFoundException{

        String query = "select * from categorias order by nome";

        ResultSet rs = stmt.executeQuery(query);

        ArrayList<Categoria> array = new ArrayList<Categoria>();


        return array;
    }

    public Categoria buscaPorNome(String nome) throws SQLException{
        String query = "SELECT * FROM categorias where nome like '"+nome+"'";

        ResultSet rs = stmt.executeQuery(query);

        Categoria categoria_model = new Categoria();


        return categoria_model;
    }
    // categoria com nome já existente
    public Boolean validaNomeCategoria(String nome, Integer id) throws SQLException{
        Boolean valida;
        Categoria categoria = this.buscaPorNome(nome);

        if (categoria.getNome() == null)
            categoria.setNome("");

        //alteração
        if (id !=0) {
            if (categoria.getNome().equals(""))
                valida = true;
            else if ((categoria.getNome().trim().equals(nome)) && (categoria.getId().toString().equals(id.toString())))
                valida = true;
            else
                valida = false;
        }else {
            if (categoria.getNome().trim().equals(nome) )
                valida = false;
            else
                valida = true;
        }
        return valida;
    }


    public ArrayList<Categoria> buscaCategoriasEventuais() {

        String query = "select categorias.* from categorias left join movimentos on categorias.id = categoria_id where categoria_id is null "+
        "and DATE (substr(data_agendada, 7, 4) || '-' || substr(data_agendada, 4, 2) || '-' || substr(data_agendada, 1, 2)) "+
                "<= date('now') and frequencia_id = 1;";


        ArrayList<Categoria> array = new ArrayList<Categoria>();



        return array;
    }

    public ArrayList<Categoria> buscaCategoriasFrequentes() {
        String query = "select categorias.* from categorias where frequencia_id <> 1 " +
                "and DATE (substr(data_agendada, 7, 4) || '-' || substr(data_agendada, 4, 2) || '-' || substr(data_agendada, 1, 2)) " +
                "<= date('now');";


        ArrayList<Categoria> array = new ArrayList<Categoria>();


        return array;
    }
}
