package br.com.usinasantafe.pbm.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;
import br.com.usinasantafe.pbm.model.dao.BoletimPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemManutPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemMedPneuDAO;

public class PneuCTR {

    public PneuCTR() {
    }

    public void clear(){

        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        List<BoletimPneuBean> boletimPneuList = boletimPneuDAO.boletimPneuAbertList();

        ArrayList<Long> idBolPneuLongs = boletimPneuDAO.idBolPneuList(boletimPneuList);

        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        itemManutPneuDAO.delete(idBolPneuLongs);

        ItemMedPneuDAO itemMedPneuDAO = new ItemMedPneuDAO();
        itemMedPneuDAO.delete(idBolPneuLongs);

        boletimPneuDAO.delete(boletimPneuList);

        idBolPneuLongs.clear();
        boletimPneuList.clear();

    }

}
