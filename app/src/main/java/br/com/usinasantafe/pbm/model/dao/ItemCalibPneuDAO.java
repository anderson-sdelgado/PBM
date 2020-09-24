package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class ItemCalibPneuDAO {

    public ItemCalibPneuDAO() {
    }

    public void insertItemCalibPneu(ItemCalibPneuBean itemCalibPneuBean, Long idBolPneu){
        itemCalibPneuBean.setIdBolItemCalibPneu(idBolPneu);
        itemCalibPneuBean.setDthrItemCalibPneu(Tempo.getInstance().dataHora());
        itemCalibPneuBean.insert();
    }

    public void deleteItemCalibPneu(ArrayList<Long> idBolPneuList){

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        List itemMedPneuList = itemCalibPneuBean.in("idBolItemCalibPneu", idBolPneuList);

        for (int i = 0; i < itemMedPneuList.size(); i++) {
            itemCalibPneuBean = (ItemCalibPneuBean) itemMedPneuList.get(i);
            itemCalibPneuBean.delete();
        }

    }

    public List<ItemCalibPneuBean> itemCalibPneuList(Long idBolPneu){
        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.get("idBolItemCalibPneu", idBolPneu);
    }

    public boolean verPneuItemCalib(Long idBol, String nroPneu){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));
        pesqArrayList.add(getPesqNroPneu(nroPneu));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        List<ItemCalibPneuBean> itemCalibPneuList = itemCalibPneuBean.get(pesqArrayList);
        boolean ret = (itemCalibPneuList.size() > 0);
        itemCalibPneuList.clear();

        return ret;

    }

    private EspecificaPesquisa getPesqIdBol(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolItemCalibPneu");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqNroPneu(String nroPneu){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("nroPneuItemCalibPneu");
        pesquisa.setValor(nroPneu);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
