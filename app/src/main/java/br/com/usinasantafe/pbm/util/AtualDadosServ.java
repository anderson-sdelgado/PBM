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
import br.com.usinasantafe.pbm.util.conHttp.GetBDGenerico;
import br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.model.pst.GenericRecordable;

public class AtualDadosServ {

	private ArrayList tabAtualArrayList;
	private static AtualDadosServ instance = null;
	private int contAtualBD = 0;
	private String classe = "";
	private ProgressDialog progressDialog;
	private int qtdeBD = 0;
	private GenericRecordable genericRecordable;
	private Context telaAtual;
	private Class telaProx;
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

				if (contAtualBD > 0) {
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
			tabAtualArrayList = new ArrayList();
	        Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

	        for (Field field : retClasse.getDeclaredFields()) {
	            String campo = field.getName();
	            Log.i("PBM", "Campo = " + campo);
	            if(campo.contains("TO")){
	            	tabAtualArrayList.add(campo);
	            }
	            
	        }
	        
	        classe = (String) tabAtualArrayList.get(contAtualBD);
			
	        String[] url = {classe};
			
		    contAtualBD++;

	        GetBDGenerico getBDGenerico = new GetBDGenerico();
	        getBDGenerico.execute(url);
	        
		} catch (Exception e) {
			Log.i("PMM", "ERRO Manip2 = " + e);
		}
        
	}

	public void atualGenericoBD(Context telaAtual, Class telaProx, ProgressDialog progressDialog, ArrayList classeArrayList){

		try {

			this.tipoReceb = 1;
			this.telaAtual = telaAtual;
			this.telaProx = telaProx;
			this.progressDialog = progressDialog;
			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				for (int i = 0; i < classeArrayList.size(); i++) {
					String classe = (String) classeArrayList.get(i);
					if(campo.equals(classe)){
						tabAtualArrayList.add(campo);
					}
				}
			}

			classe = (String) tabAtualArrayList.get(contAtualBD);
			String[] url = {classe};
			contAtualBD++;

			GetBDGenerico getBDGenerico = new GetBDGenerico();
			getBDGenerico.execute(url);

		} catch (Exception e) {
			Log.i("PMM", "ERRO = " + e);
		}

	}

	public void atualItemOSBD(ProgressDialog progressDialog){

		try {

			this.tipoReceb = 3;
			this.progressDialog = progressDialog;
			tabAtualArrayList = new ArrayList();
			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("PBM", "Campo = " + campo);
				if (campo.equals("ServicoTO") || campo.equals("ComponenteTO")) {
					tabAtualArrayList.add(campo);
				}

			}

			classe = (String) tabAtualArrayList.get(contAtualBD);

			String[] url = {classe};

			contAtualBD++;

			GetBDGenerico getBDGenerico = new GetBDGenerico();
			getBDGenerico.execute(url);

		} catch (Exception e) {
			Log.i("PMM", "ERRO Manip2 = " + e);
		}

	}

	public void atualizarBD() {

		try {

			this.tipoReceb = 2;
			tabAtualArrayList = new ArrayList();
			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				Log.i("PMM", "Campo = " + campo);
				if (campo.contains("TO")) {
					tabAtualArrayList.add(campo);
				}

			}

			classe = (String) tabAtualArrayList.get(contAtualBD);

			String[] url = {classe};

			contAtualBD++;

			GetBDGenerico getBDGenerico = new GetBDGenerico();
			getBDGenerico.execute(url);

		} catch (Exception e) {
			Log.i("PMM", "Erro Manip2 = " + e);
		}

	}
	
	public void atualizandoBD(){

		if((this.tipoReceb == 1) || (this.tipoReceb == 3)) {
		
			qtdeBD = tabAtualArrayList.size();
			
			if(contAtualBD < tabAtualArrayList.size()){
				
				this.progressDialog.setProgress((contAtualBD * 100) / qtdeBD);
		        classe = (String) tabAtualArrayList.get(contAtualBD);
				String[] url = {classe};
				contAtualBD++;

				GetBDGenerico getBDGenerico = new GetBDGenerico();
		        getBDGenerico.execute(url);
		        
			}
			else
			{
				this.progressDialog.dismiss();
				contAtualBD = 0;
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("FOI ATUALIZADO COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(tipoReceb == 3){
							Intent it = new Intent(telaAtual, telaAtual.getClass());
							telaAtual.startActivity(it);
						}
					}
				});
				
				alerta.show();
			}
		
		}
		else if(this.tipoReceb == 2){
			
			qtdeBD = tabAtualArrayList.size();
			
			if(contAtualBD < tabAtualArrayList.size()){
				
		        classe = (String) tabAtualArrayList.get(contAtualBD);
				String[] url = {classe};
				contAtualBD++;

				GetBDGenerico getBDGenerico = new GetBDGenerico();
		        getBDGenerico.execute(url);
		        
			}
			else
			{
				contAtualBD = 0;
			}
			
		}

	}

	public void encerrar(){
		
		if((this.tipoReceb == 1) || (this.tipoReceb == 3)) {
			
			this.progressDialog.dismiss();
			AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
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
	
	public void setTelaAtual(Context telaAtual){
		this.telaAtual = telaAtual;
	}
	
}
