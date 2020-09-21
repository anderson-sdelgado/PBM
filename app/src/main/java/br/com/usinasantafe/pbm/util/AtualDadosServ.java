package br.com.usinasantafe.pbm.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.usinasantafe.pbm.MenuInicialActivity;
import br.com.usinasantafe.pbm.util.conWEB.ConHttpGetBDGenerico;
import br.com.usinasantafe.pbm.util.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.model.pst.GenericRecordable;

public class AtualDadosServ {

	private ArrayList tabelaAtualizar;
	private static AtualDadosServ instance = null;
	private int contAtualizaBD = 0;
	private String classe = "";
	private ProgressDialog progressDialog;
	private int qtdeBD = 0;
	private GenericRecordable genericRecordable;
	private Context context;
	private int tipoReceb;
	private UrlsConexaoHttp urlsConexaoHttp;
	private String versao;
	private MenuInicialActivity menuInicialActivity;
	
	public AtualDadosServ() {
		genericRecordable = new GenericRecordable();
	}
	
    public static AtualDadosServ getInstance() {
        if (instance == null)
        instance = new AtualDadosServ();
        return instance;
    }
	
	@SuppressWarnings("unchecked")
	public void manipularDadosHttp(String tipo, String result){

		if(!result.equals("")){

			try {

				Log.i("PMM", "TIPO -> " + tipo);
				Log.i("PMM", "RESULT -> " + result);

				JSONObject jObj = new JSONObject(result);
				JSONArray jsonArray = jObj.getJSONArray("dados");
				Class classe = Class.forName(manipLocalClasse(tipo));
				genericRecordable.deleteAll(classe);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject objeto = jsonArray.getJSONObject(i);
					Gson gson = new Gson();
					genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);

				}

				Log.i("PMM", " SALVOU DADOS ");

				if (contAtualizaBD > 0) {
					atualizandoBD();
				}

			} catch (Exception e) {
				Log.i("PMM", "Erro Manip = " + e);
			}

		}
		else{
			encerrar();
		}

	}
	

	public void atualizarBD(ProgressDialog progressDialog){
		
		try {
			
			this.tipoReceb = 1;
			this.progressDialog = progressDialog;
			tabelaAtualizar = new ArrayList();
	        Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

	        for (Field field : retClasse.getDeclaredFields()) {
	            String campo = field.getName();
	            Log.i("PBM", "Campo = " + campo);
	            if(campo.contains("TO")){
	            	tabelaAtualizar.add(campo);
	            }
	            
	        }
	        
	        classe = (String) tabelaAtualizar.get(contAtualizaBD);
			
	        String[] url = {classe};
			
		    contAtualizaBD++;

	        ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
	        conHttpGetBDGenerico.execute(url);
	        
		} catch (Exception e) {
			Log.i("PMM", "ERRO Manip2 = " + e);
		}
        
	}

	public void atualItemOSBD(ProgressDialog progressDialog){

		try {

			this.tipoReceb = 3;
			this.progressDialog = progressDialog;
			tabelaAtualizar = new ArrayList();
			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("PBM", "Campo = " + campo);
				if (campo.equals("ServicoTO") || campo.equals("ComponenteTO")) {
					tabelaAtualizar.add(campo);
				}

			}

			classe = (String) tabelaAtualizar.get(contAtualizaBD);

			String[] url = {classe};

			contAtualizaBD++;

			ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
			conHttpGetBDGenerico.execute(url);

		} catch (Exception e) {
			Log.i("PMM", "ERRO Manip2 = " + e);
		}

	}

	public void atualizarBD() {

		try {

			this.tipoReceb = 2;
			tabelaAtualizar = new ArrayList();
			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("PMM", "Campo = " + campo);
				if (campo.contains("TO")) {
					tabelaAtualizar.add(campo);
				}

			}

			classe = (String) tabelaAtualizar.get(contAtualizaBD);

			String[] url = {classe};

			contAtualizaBD++;

			ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
			conHttpGetBDGenerico.execute(url);

		} catch (Exception e) {
			Log.i("PMM", "Erro Manip2 = " + e);
		}

	}
	
	public void atualizandoBD(){

		if((this.tipoReceb == 1) || (this.tipoReceb == 3)) {
		
			qtdeBD = tabelaAtualizar.size();
			
			if(contAtualizaBD < tabelaAtualizar.size()){
				
				this.progressDialog.setProgress((contAtualizaBD * 100) / qtdeBD);
		        classe = (String) tabelaAtualizar.get(contAtualizaBD);
				String[] url = {classe};
				contAtualizaBD++;

				ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
		        conHttpGetBDGenerico.execute(url);
		        
			}
			else
			{
				this.progressDialog.dismiss();
				contAtualizaBD = 0;
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.context);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("FOI ATUALIZADO COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(tipoReceb == 3){
							Intent it = new Intent(context, context.getClass());
							context.startActivity(it);
						}
					}
				});
				
				alerta.show();
			}
		
		}
		else if(this.tipoReceb == 2){
			
			qtdeBD = tabelaAtualizar.size();
			
			if(contAtualizaBD < tabelaAtualizar.size()){
				
		        classe = (String) tabelaAtualizar.get(contAtualizaBD);
				String[] url = {classe};
				contAtualizaBD++;

				ConHttpGetBDGenerico conHttpGetBDGenerico = new ConHttpGetBDGenerico();
		        conHttpGetBDGenerico.execute(url);
		        
			}
			else
			{
				contAtualizaBD = 0;
			}
			
		}

	}

	public void encerrar(){
		
		if((this.tipoReceb == 1) || (this.tipoReceb == 3)) {
			
			this.progressDialog.dismiss();
			AlertDialog.Builder alerta = new AlertDialog.Builder(this.context);
			alerta.setTitle("ATENCAO");
			alerta.setMessage("FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
			alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
	
				}
			});
			
			alerta.show();
			
		}
	}


	public String manipLocalClasse(String classe){
	    if(classe.contains("TO")){
	    	classe = urlsConexaoHttp.localPSTEstatica + classe;
	    }
		return classe;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
}
