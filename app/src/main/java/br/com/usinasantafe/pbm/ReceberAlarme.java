package br.com.usinasantafe.pbm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
	}

//	public void teste() {
//
//		BoletimTO boletimTO = new BoletimTO();
//		List boletimList = boletimTO.all();
//
//		Log.i("PMM", "AKI");
//
//		for (int i = 0; i < boletimList.size(); i++) {
//
//			boletimTO = (BoletimTO) boletimList.get(i);
//			Log.i("PMM", "BOLETIM");
//			Log.i("PMM", "idBoletim = " + boletimTO.getIdBoletim());
//			Log.i("PMM", "idExtBoletim = " + boletimTO.getIdExtBoletim());
//			Log.i("PMM", "idFuncBoletim = " + boletimTO.getIdFuncBoletim());
//			Log.i("PMM", "EquipBoletim = " + boletimTO.getEquipBoletim());
//			Log.i("PMM", "dthrInicialBoletim = " + boletimTO.getDthrInicialBoletim());
//			Log.i("PMM", "dthrFinalBoletim = " + boletimTO.getDthrFinalBoletim());
//			Log.i("PMM", "statusBoletim = " + boletimTO.getStatusBoletim());
//			Log.i("PMM", "atualBoletim = " + boletimTO.getAtualBoletim());
//
//		}
//
//		ApontTO apontTO = new ApontTO();
//		List apontList = apontTO.all();
//
//		for (int i = 0; i < apontList.size(); i++) {
//
//			apontTO = (ApontTO) apontList.get(i);
//			Log.i("PMM", "APONTAMENTO");
//			Log.i("PMM", "idApont = " + apontTO.getIdApont());
//			Log.i("PMM", "idBolApont = " + apontTO.getIdBolApont());
//			Log.i("PMM", "idExtBolApont = " + apontTO.getIdExtBolApont());
//			Log.i("PMM", "osApont = " + apontTO.getOsApont());
//			Log.i("PMM", "itemOSApont = " + apontTO.getItemOSApont());
//			Log.i("PMM", "paradaApont = " + apontTO.getParadaApont());
//			Log.i("PMM", "dthrInicialAponta = " + apontTO.getDthrInicialApont());
//			Log.i("PMM", "dthrFinalAponta = " + apontTO.getDthrFinalApont());
//			Log.i("PMM", "realizAponta = " + apontTO.getRealizApont());
//			Log.i("PMM", "statusAponta = " + apontTO.getStatusApont());
//
//		}
//
//		OSTO osTO = new OSTO();
//		List osList = osTO.all();
//
//		for (int i = 0; i < osList.size(); i++) {
//
//			osTO = (OSTO) osList.get(i);
//			Log.i("PMM", "OS");
//			Log.i("PMM", "idOS = " + osTO.getIdOS());
//			Log.i("PMM", "nroOS = " + osTO.getNroOS());
//			Log.i("PMM", "equipOS = " + osTO.getEquipOS());
//			Log.i("PMM", "descrEquipOS = " + osTO.getDescrEquipOS());
//
//		}
//
//	}

}