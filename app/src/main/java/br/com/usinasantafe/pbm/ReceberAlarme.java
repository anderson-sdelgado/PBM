package br.com.usinasantafe.pbm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.usinasantafe.pbm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.DatabaseHelper;

public class ReceberAlarme extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if(DatabaseHelper.getInstance() == null){
			new DatabaseHelper(context);
		}

		Log.i("PMM", "DATA HORA = " + Tempo.getInstance().datahora());
		if(ManipDadosEnvio.getInstance().verifDadosEnvio()){
			Log.i("PMM", "ENVIANDO");
			ManipDadosEnvio.getInstance().envioDados(context);
		}
	}

}