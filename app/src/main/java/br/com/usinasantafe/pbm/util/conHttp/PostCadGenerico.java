package br.com.usinasantafe.pbm.util.conHttp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.control.PneuCTR;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;

public class PostCadGenerico extends AsyncTask<String, Void, String> {


	private static PostCadGenerico instance = null;
	private Map<String, Object> parametrosPost = null;

	public PostCadGenerico() {
	}

    public static PostCadGenerico getInstance() {
        if (instance == null)
        instance = new PostCadGenerico();
        return instance;
    }


	@Override
	protected String doInBackground(String... arg) {
		
		BufferedReader bufferedReader = null;
		String resultado = null;
		
		String url = arg[0];
		
		try {

			String parametros = getQueryString(parametrosPost);
			URL urlCon = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlCon.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();

			OutputStream out = connection.getOutputStream();
			byte[] bytes = parametros.getBytes("UTF8");
			out.write(bytes);
			out.flush();
			out.close();

			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();

			connection.disconnect();
			
		} catch (Exception e) {
			Log.i("PMM", "Erro = " + e);
			EnvioDadosServ.getInstance().setEnviando(false);
			Tempo.getInstance().setEnvioDado(true);
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception er) {
					Log.i("PMM", "Erro = " + er);
				}
				
			}
		}
		finally{
			
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception e) {
					Log.i("PMM", "Erro = " + e);
				}
				
			}
			
		}
		return resultado;
		
	}

	protected void onPostExecute(String result) {

		try {
			EnvioDadosServ.getInstance().setEnviando(false);
			Log.i("ECM", "VALOR RECEBIDO --> " + result);
			MecanicoCTR mecanicoCTR = new MecanicoCTR();
			PneuCTR pneuCTR = new PneuCTR();
			if(result.trim().startsWith("BOLFECHADOMEC")){
				mecanicoCTR.delBolFechado(result);
			}
			else if(result.trim().startsWith("BOLABERTOMEC")){
				mecanicoCTR.atualBolAberto(result);
			}
			else if(result.trim().startsWith("APONTMEC")){
				mecanicoCTR.atualApont(result);
			}
			else if(result.trim().startsWith("BOLPNEU")){
				pneuCTR.delBolPneu(result);
			}
		} catch (Exception e) {
			EnvioDadosServ.getInstance().setEnviando(true);
		}
		
    }

	public void setParametrosPost(Map<String, Object> parametrosPost) {
		this.parametrosPost = parametrosPost;
	}

	private String getQueryString(Map<String, Object> params) throws Exception {
		if (params == null || params.size() == 0) {
			return null;
		}
		String urlParams = null;
		Iterator<String> e = (Iterator<String>) params.keySet().iterator();
		while (e.hasNext()) {
			String chave = (String) e.next();
			Object objValor = params.get(chave);
			String valor = objValor.toString();
			urlParams = urlParams == null ? "" : urlParams + "&";
			urlParams += chave + "=" + valor;
		}
		return urlParams;
	}

}
