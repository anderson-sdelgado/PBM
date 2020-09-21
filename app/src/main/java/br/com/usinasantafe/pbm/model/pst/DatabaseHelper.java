package br.com.usinasantafe.pbm.model.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String FORCA_DB_NAME = "pbm_db";
	public static final int FORCA_BD_VERSION = 1;

	private static DatabaseHelper instance;

	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {

		super(context, FORCA_DB_NAME, null, FORCA_BD_VERSION);;
		instance = this;
		
	}

	@Override
	public void close() {

		super.close();
		instance = null;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {

		try{
//
//			TableUtils.createTable(cs, ColabTO.class);
//			TableUtils.createTable(cs, ParadaTO.class);
//			TableUtils.createTable(cs, ComponenteTO.class);
//			TableUtils.createTable(cs, ServicoTO.class);
//			TableUtils.createTable(cs, OSTO.class);
//			TableUtils.createTable(cs, ItemOSTO.class);
//			TableUtils.createTable(cs, EscalaTrabTO.class);
//			TableUtils.createTable(cs, EquipTO.class);
//			TableUtils.createTable(cs, ParametroTO.class);
//			TableUtils.createTable(cs, PneuTO.class);
//			TableUtils.createTable(cs, REquipPneuTO.class);
//
//			TableUtils.createTable(cs, ConfiguracaoTO.class);
//			TableUtils.createTable(cs, VerApontaFuncTO.class);
//			TableUtils.createTable(cs, BoletimTO.class);
//			TableUtils.createTable(cs, ApontTO.class);
//			TableUtils.createTable(cs, BoletimPneuTO.class);
//			TableUtils.createTable(cs, ItemMedPneuTO.class);
//			TableUtils.createTable(cs, ItemManutPneuTO.class);

		}
		catch(Exception e){
			Log.e(DatabaseHelper.class.getName(),
					"Erro criando banco de dados...",
					e);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,
			ConnectionSource cs,
			int oldVersion,
			int newVersion) {
		
		try {
			
			if(oldVersion == 1 && newVersion == 2){
//				TableUtils.createTable(cs, ConfiguracaoTO.class);
				oldVersion = 2;
			}
			
			
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}
		
	}

}
