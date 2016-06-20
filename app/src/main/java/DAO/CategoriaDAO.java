package DAO;


import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
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
            Categoria categoria;

            if (wsc.isVector(wsc.requestWebService(soapParams, null))) {
                Vector<SoapObject> resposta = (Vector<SoapObject>) wsc.requestWebService(soapParams,params);

                for (SoapObject obj : resposta) {
                    categoria = new Categoria();
                    categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                    categoria.setNome(obj.getProperty("nome").toString());
                    categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                    categoria.setTipo(obj.getProperty("tipo").toString());
                    categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                    array.add(categoria);
                }
            }else{
                SoapObject obj = (SoapObject) wsc.requestWebService(soapParams,null);
                categoria = new Categoria();
                categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                categoria.setNome(obj.getProperty("nome").toString());
                categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                categoria.setTipo(obj.getProperty("tipo").toString());
                categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                array.add(categoria);
            }

            return array;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public Categoria buscaPorNome(String nome) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/buscaPorNome");
        soapParams.add(1, "buscaPorNome");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");
        params = new HashMap();

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("nome", nome);
            SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, params);
            Categoria categoria_model = new Categoria();

            categoria_model.setId(Integer.parseInt(obj.getProperty("id").toString()));
            categoria_model.setNome(obj.getProperty("nome").toString());
            categoria_model.setDataAgendada(obj.getProperty("dataAgendada").toString());
            categoria_model.setFrequencia_id(Integer.parseInt(obj.getProperty("frequencia_id").toString()));
            categoria_model.setTipo(obj.getProperty("tipo").toString());
            categoria_model.setValor(Float.parseFloat(obj.getProperty("valor").toString()));

            return categoria_model;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    // categoria com nome já existente
    public Boolean validaNomeCategoria(String nome, Integer id) {
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
        ArrayList<Categoria> array = new ArrayList<>();

        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/buscaCategoriasEventuais");
        soapParams.add(1, "buscaCategoriasEventuais");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            Categoria categoria;
            if (wsc.isVector(wsc.requestWebService(soapParams, null))) {
                Vector<SoapObject> resposta = (Vector<SoapObject>) wsc.requestWebService(soapParams, null);
                for (SoapObject obj : resposta) {
                    categoria = new Categoria();
                    categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                    categoria.setNome(obj.getProperty("nome").toString());
                    categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                    categoria.setTipo(obj.getProperty("tipo").toString());
                    categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                    array.add(categoria);
                }
            } else {
                SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, null);
                categoria = new Categoria();
                categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                categoria.setNome(obj.getProperty("nome").toString());
                categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                categoria.setTipo(obj.getProperty("tipo").toString());
                categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                array.add(categoria);
            }
            return array;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<Categoria> buscaCategoriasFrequentes() {
        ArrayList<Categoria> array = new ArrayList<>();

        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/buscaCategoriasFrequentes");
        soapParams.add(1, "buscaCategoriasFrequentes");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/CategoriaWebService?WSDL");

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            Categoria categoria;

            if (wsc.isVector(wsc.requestWebService(soapParams, null))) {
                Vector<SoapObject> resposta =(Vector<SoapObject>)  wsc.requestWebService(soapParams, null);
                for (SoapObject obj : resposta) {
                    categoria = new Categoria();
                    categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                    categoria.setNome(obj.getProperty("nome").toString());
                    categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                    categoria.setTipo(obj.getProperty("tipo").toString());
                    categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                    array.add(categoria);
                }
            }else{
                SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, null);
                categoria = new Categoria();
                categoria.setId(Integer.parseInt(obj.getProperty("id").toString()));
                categoria.setNome(obj.getProperty("nome").toString());
                categoria.setDataAgendada(obj.getProperty("dataAgendada").toString());
                categoria.setTipo(obj.getProperty("tipo").toString());
                categoria.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                array.add(categoria);
            }
            return array;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }
}
