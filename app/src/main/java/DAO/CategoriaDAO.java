package DAO;


import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Model.Categoria;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class CategoriaDAO {
    private ArrayList<String> soapParams;
    private HashMap params;

    public String inserir(String tipo, String nome, Integer frequencia_id, String data, Float valor) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/inserir");
        soapParams.add(1, "inserir");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("tipo", tipo);
            params.put("nome", nome);
            params.put("frequencia_id", frequencia_id);
            params.put("data", data);
            params.put("valor", valor);

            SoapPrimitive resposta = (SoapPrimitive) wsc.requestWebService(soapParams, params);
            return resposta.toString();
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public String atualizar(Integer id, String tipo, String nome, Integer frequencia_id, String data, Float valor) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/atualizar");
        soapParams.add(1, "atualizar");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("id", id);
            params.put("tipo", tipo);
            params.put("nome", nome);
            params.put("frequencia_id", frequencia_id);
            params.put("data", data);
            params.put("valor", valor);

            SoapPrimitive resposta = (SoapPrimitive) wsc.requestWebService(soapParams, params);
            return resposta.toString();
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public String deletar(Integer id) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/deletar");
        soapParams.add(1, "deletar");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("id", id);

            SoapPrimitive resposta = (SoapPrimitive) wsc.requestWebService(soapParams, params);
            return resposta.toString();
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public boolean temMovimento(Integer id)  {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/temMovimento");
        soapParams.add(1, "temMovimento");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("id", id);

            return (Boolean) wsc.requestWebService(soapParams, params);
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Categoria> listaTodasCategorias()  {
        ArrayList<Categoria> array = new ArrayList<>();

        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/listaTodasCategorias");
        soapParams.add(1, "listaTodasCategorias");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            Vector<SoapObject> resposta =(Vector<SoapObject>) wsc.requestWebService(soapParams,null);

            for ( SoapObject obj : resposta){
                Categoria categoria = new Categoria();
                categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                categoria.setNome(obj.getProperty("nome").toString());
                categoria.setDataAgendada(obj.getProperty("data_agendada").toString());
                categoria.setTipo(obj.getProperty("tipo").toString());
                categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                array.add(categoria);
            }
            return array;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
        }

        return array;
    }

    public Categoria buscaPorNome(String nome) throws SQLException {
        String query = "SELECT * FROM categorias where nome like '" + nome + "'";


        Categoria categoria_model = new Categoria();


        return categoria_model;
    }

    // categoria com nome já existente
    public Boolean validaNomeCategoria(String nome, Integer id) throws SQLException {
        Boolean valida;
        Categoria categoria = this.buscaPorNome(nome);

        if (categoria.getNome() == null)
            categoria.setNome("");

        //alteração
        if (id != 0) {
            if (categoria.getNome().equals(""))
                valida = true;
            else if ((categoria.getNome().trim().equals(nome)) && (categoria.getId().toString().equals(id.toString())))
                valida = true;
            else
                valida = false;
        } else {
            if (categoria.getNome().trim().equals(nome))
                valida = false;
            else
                valida = true;
        }
        return valida;
    }


    public ArrayList<Categoria> buscaCategoriasEventuais() {

        String query = "select categorias.* from categorias left join movimentos on categorias.id = categoria_id where categoria_id is null " +
                "and DATE (substr(data_agendada, 7, 4) || '-' || substr(data_agendada, 4, 2) || '-' || substr(data_agendada, 1, 2)) " +
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
