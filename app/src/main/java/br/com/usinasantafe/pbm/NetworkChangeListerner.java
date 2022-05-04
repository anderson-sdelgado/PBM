package br.com.usinasantafe.pbm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.view.ActivityGeneric;

public class NetworkChangeListerner extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(ConnectNetwork.isConnected(context)){
			ActivityGeneric.connectNetwork = true;
			LogProcessoDAO.getInstance().insertLogProcesso("if(ConnectNetwork.isConnected(context)){\n" +
					"            ActivityGeneric.connectNetwork = true;\n" +
					"Tempo.getInstance().zerarDifTempo()", context.getClass().getName());
//			Tempo.getInstance().zerarDifTempo();
			if(VerifDadosServ.status == 1){
				LogProcessoDAO.getInstance().insertLogProcesso("if(VerifDadosServ.status == 1){\n" +
						"VerifDadosServ.getInstance().reenvioVerif(context.getClass().getName());", context.getClass().getName());
				VerifDadosServ.getInstance().reenvioVerif(context.getClass().getName());
			}
			if (EnvioDadosServ.status == 1) {
				LogProcessoDAO.getInstance().insertLogProcesso("if (EnvioDadosServ.status == 1) {\n" +
						"EnvioDadosServ.getInstance().envioDados(context.getClass().getName());", context.getClass().getName());
				EnvioDadosServ.getInstance().envioDados(context.getClass().getName());
			}
		}
		else{
			LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
					"            ActivityGeneric.connectNetwork = false;", context.getClass().getName());
			ActivityGeneric.connectNetwork = false;
		}
	}

	public void teste() {

		BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
		List boletimList = boletimMecanBean.all();

		Log.i("PMM", "AKI");

		for (int i = 0; i < boletimList.size(); i++) {

			boletimMecanBean = (BoletimMecanBean) boletimList.get(i);
			Log.i("PBM", "BOLETIM");
			Log.i("PBM", "idBoletim = " + boletimMecanBean.getIdBolMecan());
			Log.i("PBM", "idExtBoletim = " + boletimMecanBean.getIdExtBolMecan());
			Log.i("PBM", "idFuncBoletim = " + boletimMecanBean.getIdFuncBolMecan());
			Log.i("PBM", "EquipBoletim = " + boletimMecanBean.getIdEquipBolMecan());
			Log.i("PBM", "dthrInicialBoletim = " + boletimMecanBean.getDthrInicialBolMecan());
			Log.i("PBM", "dthrFinalBoletim = " + boletimMecanBean.getDthrFinalBolMecan());
			Log.i("PBM", "statusBoletim = " + boletimMecanBean.getStatusBolMecan());

		}

		ApontMecanBean apontTO = new ApontMecanBean();
		List apontList = apontTO.all();

		for (int i = 0; i < apontList.size(); i++) {

			apontTO = (ApontMecanBean) apontList.get(i);
			Log.i("PBM", "APONTAMENTO");
			Log.i("PBM", "idApont = " + apontTO.getIdApontMecan());
			Log.i("PBM", "idBolApont = " + apontTO.getIdBolApontMecan());
			Log.i("PBM", "osApont = " + apontTO.getNroOSApontMecan());
			Log.i("PBM", "itemOSApont = " + apontTO.getItemOSApontMecan());
			Log.i("PBM", "paradaApont = " + apontTO.getParadaApontMecan());
			Log.i("PBM", "dthrInicialAponta = " + apontTO.getDthrInicialApontMecan());
			Log.i("PBM", "dthrFinalAponta = " + apontTO.getDthrFinalApontMecan());
			Log.i("PBM", "realizAponta = " + apontTO.getRealizApontMecan());
			Log.i("PBM", "statusAponta = " + apontTO.getStatusApontMecan());

		}

	}

}