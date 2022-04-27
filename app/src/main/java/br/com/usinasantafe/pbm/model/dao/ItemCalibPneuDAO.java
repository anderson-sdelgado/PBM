package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        itemCalibPneuBean.setDthrItemCalibPneu(Tempo.getInstance().dthrAtualString());
        itemCalibPneuBean.insert();
    }

    public void deleteItemCalibPneu(ArrayList<Long> idBolPneuList){

        List<ItemCalibPneuBean> itemCalibPneuList = itemCalibPneuList(idBolPneuList);
        for (ItemCalibPneuBean itemCalibPneuBean : itemCalibPneuList) {
            itemCalibPneuBean.delete();
        }
        itemCalibPneuList.clear();

    }

    public ArrayList<Long> idItemCalibPneuArrayList(List<ItemCalibPneuBean> itemCalibPneuList) {
        ArrayList<Long> idItemCalibPneuList = new ArrayList<Long>();
        for (ItemCalibPneuBean itemCalibPneuBean : itemCalibPneuList) {
            idItemCalibPneuList.add(itemCalibPneuBean.getIdItemCalibPneu());
        }
        return idItemCalibPneuList;
    }

    public List<ItemCalibPneuBean> itemCalibPneuList(Long idBolPneu){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBolPneu));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.get(pesqArrayList);
    }

    public List<ItemCalibPneuBean> itemCalibPneuList(ArrayList<Long> idBolPneuArrayList){
        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.in("idBolItemCalibPneu", idBolPneuArrayList);
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

    public String dadosItemCalibPneu(ArrayList<Long> idBolArrayList){

        List<ItemCalibPneuBean> itemCalibPneuList = itemCalibPneuList(idBolArrayList);
        JsonArray jsonArrayApont = new JsonArray();

        for (ItemCalibPneuBean itemCalibPneuBean : itemCalibPneuList) {
            Gson gson = new Gson();
            jsonArrayApont.add(gson.toJsonTree(itemCalibPneuBean, itemCalibPneuBean.getClass()));
        }

        itemCalibPneuList.clear();

        JsonObject jsonItemCalibPneu = new JsonObject();
        jsonItemCalibPneu.add("itemcalibpneu", jsonArrayApont);

        return jsonItemCalibPneu.toString();

    }

    public ArrayList<String> itemCalibPneuAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("ITEM CALIB PNEU");
        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        List<ItemCalibPneuBean> itemCalibPneuList = itemCalibPneuBean.orderBy("idItemCalibPneu", true);
        for (ItemCalibPneuBean itemCalibPneuBeanBD : itemCalibPneuList) {
            dadosArrayList.add(dadosItemCalibPneu(itemCalibPneuBeanBD));
        }
        itemCalibPneuList.clear();
        return dadosArrayList;
    }

    private String dadosItemCalibPneu(ItemCalibPneuBean itemCalibPneuBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(itemCalibPneuBean, itemCalibPneuBean.getClass()).toString();
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
