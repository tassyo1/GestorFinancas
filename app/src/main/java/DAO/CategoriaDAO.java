package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Categoria;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class CategoriaDAO {

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
        resultado = db.insert("categorias", null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";

        return "Registro inserido com sucesso";
    }

    public String atualizar(Integer id, String tipo, String nome, Integer frequencia_id, String data, Float valor ){
        ContentValues valores;
        long resultado;
        db =banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("tipo",tipo);
        valores.put("nome",nome);
        valores.put("frequencia_id",frequencia_id);
        valores.put("data_agendada",data);
        valores.put("valor",valor);
        resultado = db.update("categorias", valores, "id =" + id, null);
        db.close();

        if (resultado == -1)
            return "Erro ao atualizar registro";

        return "Registro atualizado com sucesso";
    }

    public String deletar(Integer id){
        long resultado;
        db =banco.getWritableDatabase();
        resultado = db.delete("categorias", "id=" + id, null);
        db.close();

        if (resultado == -1)
            return "Erro ao deletar registro";

        return "Registro deletado com sucesso";
    }

    public boolean temMovimento(Integer id){
        boolean tem;
        String query = "select categorias.* from categorias inner join movimentos on categorias.id = categoria_id where " +
                "categorias.id = "+id +"  limit 1 ";

        String descricao ="";

        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst())
            descricao = c.getString(c.getColumnIndex("nome"));

        db.close();

        if (descricao.equals(""))
            tem = false;
        else
            tem = true;

        return tem;
    }

    public ArrayList<Categoria> listaTodasCategorias() {

        String query = "select * from categorias order by nome";

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

    public Categoria buscaPorNome(String nome){
        String query =
                "SELECT * FROM categorias where nome like '"+nome+"'";
        db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        Categoria categoria_model = new Categoria();
        if (c.moveToFirst()) {

            do {
                categoria_model.setId(c.getInt(c.getColumnIndex("id")));
                categoria_model.setNome(c.getString(c.getColumnIndex("nome")));
                categoria_model.setDataAgendada(c.getString(c.getColumnIndex("data_agendada")));
                categoria_model.setFrequencia_id(c.getInt(c.getColumnIndex("frequencia_id")));
                categoria_model.setTipo(c.getString(c.getColumnIndex("tipo")));
                categoria_model.setValor(c.getFloat(c.getColumnIndex("valor")));
            }
            while (c.moveToNext()) ;
        }
        db.close();

        return categoria_model;
    }
    // categoria com nome já existente
    public Boolean validaNomeCategoria(String nome, Integer id){
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
                "and DATE (substr(data_agendada, 7, 4) || '-' || substr(data_agendada, 4, 2) || '-' || substr(data_agendada, 1, 2)) " +
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
}
