package DAO;
import android.util.Log;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import Model.Movimento;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class MovimentoDAO  {
    private ArrayList<String> soapParams;
    private HashMap params;

    public String inserir(String data_lancamento, Float saldo, Integer categoria_id) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/inserir");
        soapParams.add(1, "inserir");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/MovimentoWebService?WSDL");
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("data", data_lancamento);
            params.put("saldo", saldo);
            params.put("categoria_id", categoria_id);

            SoapPrimitive resposta = (SoapPrimitive) wsc.requestWebService(soapParams, params);
            return resposta.toString();
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    //usado para preencher o grid
    public ArrayList<Movimento> buscaTodosMovimentos(){
        ArrayList<Movimento> array = new ArrayList<>();

        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/buscaTodosMovimentos");
        soapParams.add(1, "buscaTodosMovimentos");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/MovimentoWebService?WSDL");

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            Movimento movimento_model;

            if (!(wsc.requestWebService(soapParams, null) == null)) {
                if (wsc.isVector(wsc.requestWebService(soapParams, null))) {
                    Vector<SoapObject> resposta = (Vector<SoapObject>) wsc.requestWebService(soapParams, null);
                    for (SoapObject obj : resposta) {
                        movimento_model = new Movimento();
                        movimento_model.setData_lancamento(obj.getProperty("data_lancamento").toString());
                        movimento_model.setSaldo_atual(Float.parseFloat(obj.getProperty("saldo_atual").toString()));
                        movimento_model.setNome_categoria(obj.getProperty("nome").toString());
                        movimento_model.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                        movimento_model.setTipo(obj.getProperty("tipo").toString());
                        array.add(movimento_model);
                    }
                } else {
                    SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, null);
                    movimento_model = new Movimento();
                    movimento_model.setData_lancamento(obj.getProperty("data_lancamento").toString());
                    movimento_model.setSaldo_atual(Float.parseFloat(obj.getProperty("saldo_atual").toString()));
                    movimento_model.setNome_categoria(obj.getProperty("nome").toString());
                    movimento_model.setValor(Float.parseFloat(obj.getProperty("valor").toString()));
                    movimento_model.setTipo(obj.getProperty("tipo").toString());
                    array.add(movimento_model);
                }
            }
            return array;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    public Movimento buscaUltimoMovimento() {
        soapParams = new ArrayList<>();
        soapParams.add(0,"http://ws/buscaUltimoMovimento");
        soapParams.add(1,"buscaUltimoMovimento");
        soapParams.add(2,"http://ws/");
        soapParams.add(3,"http://192.168.1.100:8080/WSConnectionMySQL/MovimentoWebService?WSDL");

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, null);
            Movimento movimento_model = new Movimento();

            movimento_model.setId(Integer.parseInt(obj.getProperty("id").toString()));
            movimento_model.setData_lancamento(obj.getProperty("data_lancamento").toString());
            movimento_model.setSaldo_atual(Float.parseFloat(obj.getProperty("saldo_atual").toString()));
            movimento_model.setCategoria_id(Integer.parseInt(obj.getProperty("categoria_id").toString()));

            return movimento_model;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
            return null;
        }
    }

    //traz movimentos com categoria passada por parâmetro  que foram lançados hj
    public Movimento buscaMovimentosPorCategoriaHoje(int categoria_id) {
        soapParams = new ArrayList<>();
        soapParams.add(0, "http://ws/buscaMovimentosPorCategoriaHoje");
        soapParams.add(1, "buscaMovimentosPorCategoriaHoje");
        soapParams.add(2, "http://ws/");
        soapParams.add(3, "http://192.168.1.100:8080/WSConnectionMySQL/MovimentoWebService?WSDL");
        params = new HashMap();

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("categoria_id", categoria_id);
            SoapObject obj = (SoapObject) wsc.requestWebService(soapParams, params);
            Movimento movimento_model = new Movimento();

            movimento_model.setCategoria_id(Integer.parseInt(obj.getProperty("categoria_id").toString()));

            return movimento_model;
        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
        }
        return null;
    }
}
