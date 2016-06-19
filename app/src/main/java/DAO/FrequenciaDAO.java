package DAO;


import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.util.ArrayList;
import java.util.Vector;

import Model.Frequencia;

/**
 * Created by tassyosantana on 06/05/16.
 */
public class FrequenciaDAO {



    public ArrayList<Frequencia> getAllFrequencia() {
        ArrayList<Frequencia> lista = new ArrayList<Frequencia>();
        String SOAP_ACTION = "http://ws/getAllFrequencia";
        String METHOD_NAME = "getAllFrequencia";
        String NAMESPACE = "http://ws/";
        String URL = "http://192.168.1.100:8080/WSConnectionMySQL/FrequenciaWebService?WSDL";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setAddAdornments(false);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(request);


            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            Vector<SoapObject> resposta =(Vector<SoapObject>) soapEnvelope.getResponse();

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
        String SOAP_ACTION = "\"http://bd/buscaFrequenciaPorId\"";
        String METHOD_NAME = "buscaFrequenciaPorId";
        String NAMESPACE = "http://bd/";
        String URL = "http://192.168.1.100:8080/WSConnectionMySQL/FrequenciaWebService?WSDL";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setAddAdornments(false);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(Request);


            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
          //  descricao = soapEnvelope.getResponse().toString();

        } catch (Exception ex) {

            Log.e("Response ", "Error: " + ex.getMessage());
        }
        return "";
    }

}
