package br.com.usinasantafe.pbm.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.PneuBean;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class PneuDAO {

    public PneuDAO() {
    }

    public PneuBean getPneu(String codPneu){
        List<PneuBean> pneuList = pneuList(codPneu);
        PneuBean pneuBean = pneuList.get(0);
        pneuList.clear();
        return pneuBean;
    }

    public boolean verPneu(String codPneu){
        List<PneuBean> pneuList = pneuList(codPneu);
        boolean ret = (pneuList.size() > 0);
        pneuList.clear();
        return ret;
    }

    public List<PneuBean> pneuList(String codPneu){
        PneuBean pneuBean = new PneuBean();
        return pneuBean.get("codPneu", codPneu);
    }

    public void verPneu(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, boolean finalManutPneu){
        VerifDadosServ.getInstance().verDadosPneu(dado, "Pneu", telaAtual, telaProx, progressDialog, finalManutPneu);
    }

    public void insertPneu(PneuBean pneuBean){
        pneuBean.insert();
    }

}
