package DAO;


import android.webkit.WebHistoryItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by tassyosantana on 16/06/16.
 */
public class WebServiceConnection {
    private Boolean temParametro;

    public void setTemParametro(Boolean temParametro) {
        this.temParametro = temParametro;
    }

    public Object requestWebService(ArrayList<String> soapParams, HashMap param) {
        try {
            SoapObject request = new SoapObject(soapParams.get(2), soapParams.get(1)); // 1METHOD_NAME  2NAMESPACE

            if (this.temParametro){
                Set set = param.entrySet();
                Iterator i = set.iterator();
                while (i.hasNext()){
                    Map.Entry me = (Map.Entry)i.next();
                    request.addProperty(me.getKey().toString(),me.getValue());
                }
            }
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setAddAdornments(false);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(soapParams.get(3)); //  3URL
            transport.call(soapParams.get(0), soapEnvelope);// 0SOAP_ACTION

            return soapEnvelope.getResponse();
        } catch (Exception soapFault) {
            soapFault.printStackTrace();
            return null;
        }

    }

    public boolean isVector(Object response){
        try {
            Vector<SoapObject> a = (Vector<SoapObject>) response;
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
