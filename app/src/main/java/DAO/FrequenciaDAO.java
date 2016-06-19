package DAO;

import android.util.Log;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Model.Frequencia;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class FrequenciaDAO {
    private ArrayList<String> soapParams;
    private HashMap params;

    public ArrayList<Frequencia> getAllFrequencia() {
        ArrayList<Frequencia> lista = new ArrayList<Frequencia>();

        soapParams = new ArrayList<String>();
        soapParams.add(0,"http://ws/getAllFrequencia"); //SOAP_ACTION
        soapParams.add(1,"getAllFrequencia"); //METHOD_NAME
        soapParams.add(2,"http://ws/"); //NAMESPACE
        soapParams.add(3,"http://192.168.1.100:8080/WSConnectionMySQL/FrequenciaWebService?WSDL"); //URL

        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(false);
            Vector<SoapObject> resposta =(Vector<SoapObject>) wsc.requestWebService(soapParams,null);

            for ( SoapObject obj: resposta) {
                Frequencia frequencia = new Frequencia();
                frequencia.setId(Integer.parseInt(obj.getProperty("id").toString()));
                frequencia.setDescricao(obj.getProperty("descricao").toString());
                lista.add(frequencia);
            }
            return lista;

        } catch (Exception ex) {
            ex.getStackTrace();
            Log.e("Response ", "Error: " + ex.getMessage());
        }
        return lista;
    }

    public String buscaFrequenciaPorId(Integer id) {
        soapParams = new ArrayList<String>();
        soapParams.add(0,"http://ws/buscaFrequenciaPorId"); //SOAP_ACTION
        soapParams.add(1,"buscaFrequenciaPorId"); //METHOD_NAME
        soapParams.add(2,"http://ws/"); //NAMESPACE
        soapParams.add(3,"http://192.168.1.100:8080/WSConnectionMySQL/FrequenciaWebService?WSDL"); //URL
        params = new HashMap();
        try {
            WebServiceConnection wsc = new WebServiceConnection();
            wsc.setTemParametro(true);
            params.put("id",id);

            SoapPrimitive resposta =(SoapPrimitive) wsc.requestWebService(soapParams,params);

            return resposta.toString();
        } catch (Exception ex) {

            Log.e("Response ", "Error: " + ex.getMessage());
        }
        return "";
    }

}
