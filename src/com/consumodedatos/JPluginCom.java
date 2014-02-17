package com.consumodedatos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class JPluginCom extends CordovaPlugin {
	
	private enum Metodo {
		getEstablecimientos, getEstablecimientoInfo, getNumEstablecimientos, getMatricula, getMatriculaNum, getMatriculaHistorica, getMatriculaHistoricaDetalle;
	}
	Context mContext;
	
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {        
        //this.getNumEstablecimientos(callbackContext);
		Metodo met = Metodo.valueOf(action);
		String init, numrows, codigoUdI = "";
		switch(met) {		    
		    case getEstablecimientos:
		    	init = args.getString(0); 
		    	numrows = args.getString(1);
		    	String str = this.getEstablecimientos(init, numrows);
		    	callbackContext.success(str);
		    	 
		    	/*
		    		            
	            this.getEstablecimientos(init, numrows, callbackContext);
			    break;*/
		    case getNumEstablecimientos:		    	
		    	//init = args.getString(0); 
		    	
	            this.getNumEstablecimientos(callbackContext);
			    break;
		    case getEstablecimientoInfo:		    	
		    	codigoUdI = args.getString(0); 
		    	str =  this.getEstablecimientoInfo(codigoUdI);
		    	callbackContext.success(str);
			    break;
		    case getMatriculaHistorica:		    	
		    	codigoUdI = args.getString(0);
		    	str =  this.getMatriculaHistorica(codigoUdI);
		    	callbackContext.success(str);
			    break;
		    case getMatriculaHistoricaDetalle:
		    	Log.i("Proceso","llego al case MATRICULA DETALLE");
		    	codigoUdI = args.getString(0);
		    	str =  this.getMatriculaHistoricaDetalle(codigoUdI);
		    	Log.d("DATA",str);
		    	callbackContext.success(str);
		    	break;
		    	
		}		
        return false;
    }
 	
	private String getEstablecimientos(String init, String numrows){
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date today = Calendar.getInstance().getTime(); 
			Log.i("INIT TIME",df.format(today));
			ConsumoFichaEscolar consumo = new ConsumoFichaEscolar();
			String response = consumo.getEstablecimientos(Integer.parseInt(init), Integer.parseInt(numrows));
			String sql = "";
				try {					
					JSONArray jsonArray = new JSONArray(response);
					//Log.d("DEBUG", "JSON OBJECT CREATION");
					sql = "";
					for(int j = 0; j < jsonArray.length(); j++){
						if(sql.length() > 0){
							sql  += "||";
						}
						JSONObject row = jsonArray.getJSONObject(j);
						sql  += this.getSQLFromEstablecimientoRow(row);
					}
					//Log.d("SQLiteSentence", sql);
				} catch (Throwable t) {
				    Log.e("My App", "Error Parseando el JSON");
				}
				
			today = Calendar.getInstance().getTime();
			Log.i("END TIME",df.format(today));			
			return(sql);
			/*
				*/			
			//callbackContext.success("PARSEANDO EL JSON");		
	}
	
	
	private String getEstablecimientoInfo(String codigoUDI){
		ConsumoFichaEscolar consumo = new ConsumoFichaEscolar();
		
		//callbackContext.success(res);	
		String res = (String) consumo.getEstablecimientoInfo(codigoUDI);
		
		return res;
		/*
			*/			
		//callbackContext.success("PARSEANDO EL JSON");		
	}
	
	private String getMatriculaHistorica(String codigoUDI){		
		String res = "";
		ConsumoFichaEscolar consumo = new ConsumoFichaEscolar();
		res = (String) consumo.getMatriculaHistorica(codigoUDI);		
		return res;
	}
	
	private String getMatriculaHistoricaDetalle(String codigoUDI){		
		String res = "";
		ConsumoFichaEscolar consumo = new ConsumoFichaEscolar();
		res = (String) consumo.getMatriculaHistoricaDetalle(codigoUDI);		
		return res;
	}
	
	private void getNumEstablecimientos(CallbackContext callbackContext){		
		Log.i("Success","llegamos al metodo");		
		ConsumoFichaEscolar consumo = new ConsumoFichaEscolar();
		String res = (String) consumo.getNumEstablecimientos();
		callbackContext.success(res);	
	}
	
	private String getSQLFromEstablecimientoRow(JSONObject row) throws JSONException{
		String sql = "(";	
		if(row.getString("NombreSectorActual").length() > 0)
		{
		 sql += "'" + row.getString("NombreSectorActual").replaceAll("'", "-") + "',";
		}else{
		sql += "'SIN DATO',";
		}
			
		if(row.getString("NombrePlan").length() >0 )
		{
		sql += "'" + row.getString("NombrePlan").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreAreaActual").length()>0)
		 {
		  sql += "'" + row.getString("NombreAreaActual").replaceAll("'", "-")+ "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		if(row.getString("NombreTipo").length()> 0)
		{
			sql += "'" + row.getString("NombreTipo").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("Telefono").length() > 0)
		 {
		 sql += "'" + row.getString("Telefono")+ "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		
		if(row.getString("NombreCiclo").length() > 0)
		 {
		 sql += "'" + row.getString("NombreCiclo")+ "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		
		if(row.getString("CorreoElectronico").length() > 0)
		{
		 sql += "'" + row.getString("CorreoElectronico").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreJornada").length()>0)
		{
		 sql += "'" + row.getString("NombreJornada").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreRegion").length()>0)
		{
		 sql += "'" + row.getString("NombreRegion").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreDepartamento").replaceAll("'", "-").length()>0 )
		{
		sql += "'" + row.getString("NombreDepartamento")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("Longitud").length() >0)
		{
		 sql += "'" + row.getString("Longitud")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("SituacionActual").length()>0 )
		{
		sql += "'" + row.getString("SituacionActual")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("CodigoUDI").length()>0)
		{
		sql += "'" + row.getString("CodigoUDI")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreNivel").length()>0)
		{
		sql += "'" + row.getString("NombreNivel").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreMunicipio").length()>0 )
		{
		sql += "'" + row.getString("NombreMunicipio").replaceAll("'", "-")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("CORREO_ELECTRONICO_SUP").length() > 0)
		 {
		 sql += "'" + row.getString("CORREO_ELECTRONICO_SUP").replaceAll("'", "-")+ "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		
		if(row.getString("Latitud").length()>0)
		{
		sql += "'" + row.getString("Latitud")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("DIRECTOR").length()>0)
		{
		sql += "'" + row.getString("DIRECTOR").replaceAll("'", "-") + "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreCategoria").length() > 0)
		 {
		 sql += "'" + row.getString("NombreCategoria").replaceAll("'", "-") + "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		
		if(row.getString("NombreModalidad").length()>0)
		{
		 sql += "'" + row.getString("NombreModalidad").replaceAll("'", "-") + "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("TEL_SEDE").length()>0)
		{
		 sql += "'" + row.getString("TEL_SEDE").replaceAll("'", "-") + "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("CantidadDocentes").length()>0)
		{
		 sql += "'" + row.getString("CantidadDocentes")+ "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("Direccion").length()>0)
		{
		 sql += "'" + row.getString("Direccion") + "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreEstablecimiento").length()>0)
		{
		 sql += "'" + row.getString("NombreEstablecimiento").replaceAll("'", "-") + "',";
		}else{
		sql += "'SIN DATO',";
		}
		
		if(row.getString("NombreSupervisor").length() >0 )
		 {
		 sql += "'" + row.getString("NombreSupervisor").replaceAll("'", "-") + "',";
		 }else{
		 sql += "'SIN DATO',";
		 }
		
		if(row.getString("DIR_SEDE").length()>0)
		 {
		 sql += "'" + row.getString("DIR_SEDE")+ "')";
		 }else{
		 sql += "'SIN DATO')";
		 }
		//sql += "'FOOL SIN DATO')";
				
		return sql;
	}

	
	
	
}
