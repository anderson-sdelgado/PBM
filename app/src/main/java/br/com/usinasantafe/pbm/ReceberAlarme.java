package br.com.usinasantafe.pbm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.DatabaseHelper;

public class ReceberAlarme extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if(DatabaseHelper.getInstance() == null){
			new DatabaseHelper(context);
		}

		Log.i("PMM", "DATA HORA = " + Tempo.getInstance().dataHora());
		if(EnvioDadosServ.getInstance().verifDadosEnvio()){
			Log.i("PMM", "ENVIANDO");
			EnvioDadosServ.getInstance().envioDados(context);
		}

		teste();

	}

	public void teste() {

		BoletimBean boletimBean = new BoletimBean();
		List boletimList = boletimBean.all();

		Log.i("PMM", "AKI");

		for (int i = 0; i < boletimList.size(); i++) {

			boletimBean = (BoletimBean) boletimList.get(i);
			Log.i("PBM", "BOLETIM");
			Log.i("PBM", "idBoletim = " + boletimBean.getIdBoletim());
			Log.i("PBM", "idExtBoletim = " + boletimBean.getIdExtBoletim());
			Log.i("PBM", "idFuncBoletim = " + boletimBean.getIdFuncBoletim());
			Log.i("PBM", "EquipBoletim = " + boletimBean.getEquipBoletim());
			Log.i("PBM", "dthrInicialBoletim = " + boletimBean.getDthrInicialBoletim());
			Log.i("PBM", "dthrFinalBoletim = " + boletimBean.getDthrFinalBoletim());
			Log.i("PBM", "statusBoletim = " + boletimBean.getStatusBoletim());

		}

		ApontBean apontTO = new ApontBean();
		List apontList = apontTO.all();

		for (int i = 0; i < apontList.size(); i++) {

			apontTO = (ApontBean) apontList.get(i);
			Log.i("PBM", "APONTAMENTO");
			Log.i("PBM", "idApont = " + apontTO.getIdApont());
			Log.i("PBM", "idBolApont = " + apontTO.getIdBolApont());
			Log.i("PBM", "idExtBolApont = " + apontTO.getIdExtBolApont());
			Log.i("PBM", "osApont = " + apontTO.getOsApont());
			Log.i("PBM", "itemOSApont = " + apontTO.getItemOSApont());
			Log.i("PBM", "paradaApont = " + apontTO.getParadaApont());
			Log.i("PBM", "dthrInicialAponta = " + apontTO.getDthrInicialApont());
			Log.i("PBM", "dthrFinalAponta = " + apontTO.getDthrFinalApont());
			Log.i("PBM", "realizAponta = " + apontTO.getRealizApont());
			Log.i("PBM", "statusAponta = " + apontTO.getStatusApont());

		}

	}

}