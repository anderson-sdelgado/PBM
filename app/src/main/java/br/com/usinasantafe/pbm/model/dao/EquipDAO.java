package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;

public class EquipDAO {

    public EquipDAO() {
    }

    public EquipBean getIdEquip(Long idEquip){
        List<EquipBean> equipList = equipListId(idEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    public boolean verNroEquip(Long nroEquip){
        List<EquipBean> equipList = equipListNro(nroEquip);
        boolean ret = (equipList.size() > 0);
        equipList.clear();
        return ret;
    }

    public EquipBean getNroEquip(Long nroEquip){
        List<EquipBean> equipList = equipListNro(nroEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    public List<EquipBean> equipListId(Long idEquip){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqId(idEquip));

        EquipBean equipBean = new EquipBean();
        return  equipBean.get(pesqArrayList);
    }

    public List<EquipBean> equipListNro(Long nroEquip){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqNro(nroEquip));

        EquipBean equipBean = new EquipBean();
        return  equipBean.get(pesqArrayList);
    }

    public List<REquipPneuBean> rEquipPneuList(Long idEquip){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqId(idEquip));

        REquipPneuBean rEquipPneuBean = new REquipPneuBean();
        return rEquipPneuBean.getAndOrderBy(pesqArrayList,"posPneu", true);
    }

    private EspecificaPesquisa getPesqId(Long idEquip){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("idEquip");
        especificaPesquisa.setValor(idEquip);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqNro(Long nroEquip){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("nroEquip");
        especificaPesquisa.setValor(nroEquip);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

}
