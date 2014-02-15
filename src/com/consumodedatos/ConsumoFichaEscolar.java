package com.consumodedatos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class ConsumoFichaEscolar {
	// Metodo que queremos ejecutar en el servicio web
    // Namespace definido en el servicio web
    private static final String namespace = "http://fichaescolar.hepp.edu/";
    // namespace + metodo

    private static final String getEstablecimientos = "getEstablecimientos";
    private static final String getNumEstablecimientos = "getNumEstablecimientos";
    private static final String getMatricula = "getMatricula";
    private static final String getNumMatricula = "getNumMatricula";
    private static final String getEstablecimientoInfo = "getEstablecimientoInfo";
    
    // Fichero de definicion del servcio web
    private static final String url = "http://107.21.223.125:9090/AppFicha/WSApp?wsdl";
    //tag para interpretacion de xml 
    public static final String TAG = "Resultado";
	
	
	public String getNumEstablecimientos(){
    	try {
    		//
            SoapObject request = new SoapObject(namespace, getNumEstablecimientos);
            //request.addProperty("Param", "valor"); // Paso parametros al WS
           
                    
            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = false;
            sobre.setOutputSoapObject(request);
            //Log.i("proceso", "sobre preparado");
         
            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);
         
            // Llamada
            transporte.call(getNumEstablecimientos, sobre);
            
            // Resultado 
            String resultado = sobre.getResponse().toString();
            Log.i("datos numEst", resultado);
            return resultado;
         
        } catch (Exception e) {
        	String err = (e.getMessage()==null)?"SD Card failed":e.getMessage();
        	Log.e("ERROR", err );
            //return e.getMessage();
            return "ERROR : " + e.getMessage().toString();
        }
    }
	
	public String getEstablecimientos(int init, int numRows){
    	try {
    		
    		SoapObject request = new SoapObject(namespace, getEstablecimientos);
            request.addProperty("init", init);
            request.addProperty("rowsNum", numRows);
            
            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = false;
            sobre.setOutputSoapObject(request);
            
            Log.d("TRACE", "sobre preparado");
            String resultado = "";
         try {
			
	
            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);           
            // Llamada
            transporte.call(getEstablecimientos, sobre);            
            //String resultado = "";
            resultado = sobre.getResponse().toString();
            //Log.i("sobre", sobre.getResponse().toString());
            
     	} catch (Exception e) {
     		Log.e("ERROR 1 ", e.getMessage().toString());
     		Log.e("ERROR 2 ", e.getStackTrace().toString());     		
		}
          /*  try{
            	
            	//sobre.getResponse();
            	Log.i("DEBUG OK", resultado);
            }catch(Exception e){
            	Log.e("DEBUG ERROR", e.getMessage());
            }*/
            
            //Log.i("TRACE","After last try catch");
            return resultado;
            
            
         
        } catch (Exception e) {
        	String err = (e.getMessage()==null)?"SD Card failed":e.getMessage();
        	Log.e("ERROR : DeBUG ", err );
            //return e.getMessage();
            return "ERROR : " + e.getMessage().toString() ;
        }
    }
	
	public String getEstablecimientoInfo(String codigoUDI){
    	try {
    		
    		SoapObject request = new SoapObject(namespace, getEstablecimientoInfo);
            request.addProperty("codigoUDI", codigoUDI);
            
            
            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = false;
            sobre.setOutputSoapObject(request);
            
            Log.d("TRACE", "sobre preparado");
            String resultado = "";
         try {
				
            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);           
            // Llamada
            transporte.call(getEstablecimientos, sobre);            
            //String resultado = "";
            resultado = sobre.getResponse().toString();
            //Log.i("sobre", sobre.getResponse().toString());
            
     	} catch (Exception e) {
     		Log.e("ERROR 1 ", e.getMessage().toString());
     		Log.e("ERROR 2 ", e.getStackTrace().toString());     		
		}
          /*  try{
            	
            	//sobre.getResponse();
            	Log.i("DEBUG OK", resultado);
            }catch(Exception e){
            	Log.e("DEBUG ERROR", e.getMessage());
            }*/
            
            //Log.i("TRACE","After last try catch");
            return resultado;
            
            
         
        } catch (Exception e) {
        	String err = (e.getMessage()==null)?"SD Card failed":e.getMessage();
        	Log.e("ERROR : DeBUG ", err );
            //return e.getMessage();
            return "ERROR : " + e.getMessage().toString() ;
        }
    }

}
