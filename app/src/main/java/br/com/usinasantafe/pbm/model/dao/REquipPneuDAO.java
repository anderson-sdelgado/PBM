package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;

public class REquipPneuDAO {

    public REquipPneuDAO() {
    }

    public List<REquipPneuBean> rEquipPneuList(Long idEquip){
        REquipPneuBean rEquipPneuBean = new REquipPneuBean();
        return rEquipPneuBean.get("idEquip", idEquip);
    }

    public REquipPneuBean getREquipPneu(Long idEquip, Long posPneu){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdEquip(idEquip));
        pesqArrayList.add(getPesqPosPneu(posPneu));

        REquipPneuBean rEquipPneuBean = new REquipPneuBean();
        List<REquipPneuBean> rEquipPneuList = rEquipPneuBean.get(pesqArrayList);
        rEquipPneuBean = rEquipPneuList.get(0);
        rEquipPneuList.clear();

        return rEquipPneuBean;

    }

    private EspecificaPesquisa getPesqIdEquip(Long idEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idEquip");
        pesquisa.setValor(idEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPosPneu(Long posPneu){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("posPneu");
        pesquisa.setValor(posPneu);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
