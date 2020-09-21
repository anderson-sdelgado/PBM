package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;

public class BoletimPneuDAO {

    public BoletimPneuDAO() {
    }

    public List<BoletimPneuBean> boletimPneuAbertList(){
        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get("statusBolPneu", 1L);
    }

    public ArrayList<Long> idBolPneuList(List<BoletimPneuBean> boletimPneuList){
        ArrayList<Long> idBolPneuList = new ArrayList<Long>();
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            idBolPneuList.add(boletimPneuBean.getIdBolPneu());
        }
        return idBolPneuList;
    }

    public void delete(List<BoletimPneuBean> boletimPneuList){
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            boletimPneuBean.delete();
        }
    }

}
