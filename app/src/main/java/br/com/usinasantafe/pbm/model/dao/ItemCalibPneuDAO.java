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
        if(verItemMedPneuIdBolIdPosConf(idBolPneu, itemCalibPneuBean.getIdPosItemCalibPneu())) {
            ItemCalibPneuBean itemCalibPneuBeanBD = getItemMedPneuIdBolIdPosConf(idBolPneu, itemCalibPneuBean.getIdPosItemCalibPneu());
            itemCalibPneuBeanBD.setNroPneuItemCalibPneu(itemCalibPneuBean.getNroPneuItemCalibPneu());
            itemCalibPneuBeanBD.setPressaoEncItemCalibPneu(itemCalibPneuBean.getPressaoEncItemCalibPneu());
            itemCalibPneuBeanBD.setPressaoColItemCalibPneu(itemCalibPneuBean.getPressaoColItemCalibPneu());
            itemCalibPneuBeanBD.setDthrItemCalibPneu(Tempo.getInstance().dthrAtualString());
            itemCalibPneuBeanBD.update();
        } else {
            itemCalibPneuBean.setDthrItemCalibPneu(Tempo.getInstance().dthrAtualString());
            itemCalibPneuBean.setIdBolItemCalibPneu(idBolPneu);
            itemCalibPneuBean.insert();
        }
    }

    public ItemCalibPneuBean getItemMedPneuIdBolIdPosConf(Long idBol, Long idPosConf){
        List<ItemCalibPneuBean> itemMedPneuIdBolPosList = itemMedPneuIdBolIdPosConfList(idBol, idPosConf);
        ItemCalibPneuBean itemCalibPneuBean = itemMedPneuIdBolPosList.get(0);
        itemMedPneuIdBolPosList.clear();
        return itemCalibPneuBean;
    }

    public void deleteItemMedPneuIdBol(Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqItemMedPneuIdBol(idBol));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        List<ItemCalibPneuBean> itemMedPneuList = itemCalibPneuBean.get(pesqArrayList);

        for(ItemCalibPneuBean itemCalibPneuBeanBD : itemMedPneuList){
            itemCalibPneuBeanBD.delete();
        }

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

    public List<ItemCalibPneuBean> itemCalibPneuIdBolList(Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqItemMedPneuIdBol(idBol));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.getAndOrderBy(pesqArrayList, "idItemCalibPneu", true);

    }

    public List<ItemCalibPneuBean> itemCalibPneuList(ArrayList<Long> idBolPneuArrayList){
        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.in("idBolItemCalibPneu", idBolPneuArrayList);
    }

    public boolean verItemMedPneuIdBolIdPosConf(Long idBol, Long idPosConf){
        List<ItemCalibPneuBean> itemMedPneuIdBolPosList = itemMedPneuIdBolIdPosConfList(idBol, idPosConf);
        boolean ret = (itemMedPneuIdBolPosList.size() > 0);
        itemMedPneuIdBolPosList.clear();
        return ret;
    }

    public boolean verPneuItemCalib(Long idBol, String nroPneu){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqItemMedPneuIdBol(idBol));
        pesqArrayList.add(getPesqItemMedPneuNroPneu(nroPneu));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        List<ItemCalibPneuBean> itemCalibPneuList = itemCalibPneuBean.get(pesqArrayList);
        boolean ret = (itemCalibPneuList.size() == 0);
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

    public List<ItemCalibPneuBean> itemMedPneuIdBolIdPosConfList(Long idBol, Long idPosConf){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqItemMedPneuIdBol(idBol));
        pesqArrayList.add(getPesqItemMedPneuPos(idPosConf));

        ItemCalibPneuBean itemCalibPneuBean = new ItemCalibPneuBean();
        return itemCalibPneuBean.getAndOrderBy(pesqArrayList, "idItemCalibPneu", true);

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


    private EspecificaPesquisa getPesqItemMedPneuNroPneu(String nroPneu){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("nroPneuItemCalibPneu");
        pesquisa.setValor(nroPneu);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqItemMedPneuIdBol(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolItemCalibPneu");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqItemMedPneuPos(Long idPosConf){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idPosItemCalibPneu");
        pesquisa.setValor(idPosConf);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
