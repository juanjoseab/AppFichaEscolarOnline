package com.consumodedatos;
import android.util.Log;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public DbHelper(Context ctx){
		super(ctx,"fichaescolar",null,1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		//Log.i("LOG", "CREADA LA BD");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva){
		//db.execSQL("DROP TABLE IF EXIST establecimientos");
		//this.onCreate(db);
	}
	
	public void dropTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXIST establecimiento");
		db.execSQL("DROP TABLE IF EXIST matricula");
		db.execSQL("DROP TABLE IF EXIST config");
	}
	
	public void createTableEstablecimiento(){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "CREATE TABLE IF NOT EXIST" +
				"establecimiento (" +
				"NombreSectorActual," +
				"NombrePlan," +
				"NombreAreaActual," +
				"NombreTipo," +
				"Telefono," +
				"NombreCiclo," +
				"CorreoElectronico," +
				"NombreJornada," +
				"NombreRegion," +
				"NombreDepartamento," +
				"Longitud," +
				"SituacionActual," +
				"CodigoUDI," +
				"NombreNivel," +
				"NombreMunicipio," +
				"CORREO_ELECTRONICO_SUP," +
				"Latitud," +
				"DIRECTOR," +
				"NombreCategoria," +
				"NombreModalidad," +
				"TEL_SEDE," +
				"CantidadDocentes," +
				"Direccion," +
				"NombreEstablecimiento," +
				"NombreSupervisor," +
				"DIR_SEDE"+
				");";
		db.execSQL(sql);
	}
	
	public void createTableMatricula(){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "CREATE TABLE IF NOT EXIST matricula (" +
				"RetiradosHombres," +
				"PromovidosHombres," +
				"TasaRetencionMujeres," +
				"InscritosFinalMujeres," +
				"FracasoHombres," +
				"FracasoMujeres," +
				"RetiradosMujeres," +
				"InscritosInicialHombres," +
				"NoPromovidosHombres," +
				"TasaRepitenciaHombres," +
				"TasaDesercionMujeres," +
				"ANIO," +
				"InscritosFinalHombres," +
				"PromovidosMujeres," +
				"TasaDesercionHombres," +
				"GRADO," +
				"RepitentesMujeres," +
				"TasaRepitenciaMujeres," +
				"CodigoUDI," +
				"InscritosInicialMujeres," +
				"TasaRetencionHombres," +
				"RepitentesHombres," +
				"CantidadEstudiante," +
				"NoPromovidosMujeres" +
				");";
		db.execSQL(sql);
	}
	
	public void createTableConfig(){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "CREATE TABLE IF NOT EXIST config (param, val)";
	}
	
	public void executeSQL(String sql){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}
	
	public void insertToEstablecimiento(String sqlInserts){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO establecimiento (" +
				"NombreSectorActual,NombrePlan,NombreAreaActual,NombreTipo,Telefono,NombreCiclo,CorreoElectronico," +
				"NombreJornada,NombreRegion,NombreDepartamento,Longitud,SituacionActual,CodigoUDI,NombreNivel,NombreMunicipio,CORREO_ELECTRONICO_SUP," +
				"Latitud,DIRECTOR,NombreCategoria,NombreModalidad,TEL_SEDE,CantidadDocentes,Direccion,NombreEstablecimiento,NombreSupervisor,DIR_SEDE"+
				") VALUES ";
		sql += sqlInserts;
		db.execSQL(sql);		
	}
	public void insertToConfig(){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO config (param,val) VALUES ('db_install','1')";
		db.execSQL(sql);
	}
	
	public void insertToMatricula(String sqlInserts){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO matricula (RetiradosHombres,PromovidosHombres,TasaRetencionMujeres,InscritosFinalMujeres,FracasoHombres," +
				"FracasoMujeres,RetiradosMujeres,InscritosInicialHombres,NoPromovidosHombres,TasaRepitenciaHombres,TasaDesercionMujeres," +
				"ANIO,InscritosFinalHombres,PromovidosMujeres,TasaDesercionHombres,GRADO,RepitentesMujeres,TasaRepitenciaMujeres,CodigoUDI," +
				"InscritosInicialMujeres,TasaRetencionHombres,RepitentesHombres,CantidadEstudiante,NoPromovidosMujeres) VALUES ";
		sql += sqlInserts;
		db.execSQL(sql);		
	}
	
	public void openDb(){
		this.getWritableDatabase();
	}
	
	public void closeDb(){
		this.close();
	}
}
