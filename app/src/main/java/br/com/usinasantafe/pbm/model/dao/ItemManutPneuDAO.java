package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class ItemManutPneuDAO {

    public ItemManutPneuDAO() {
    }

    public void insertItemManutPneu(ItemManutPneuBean itemManutPneuBean, Long idBolPneu){
        itemManutPneuBean.setIdBolItemManutPneu(idBolPneu);
        itemManutPneuBean.setDthrItemManutPneu(Tempo.getInstance().dthrAtualString());
        itemManutPneuBean.insert();
    }

    public void deleteItemManutPneu(ArrayList<Long> idBolPneuLongs){

        List<ItemManutPneuBean> itemManutPneuList = itemManutPneuList(idBolPneuLongs);
        for (ItemManutPneuBean itemManutPneuBean : itemManutPneuList) {
            itemManutPneuBean.delete();
        }
        itemManutPneuList.clear();

    }

    public ArrayList<Long> idItemManutPneuArrayList(List<ItemManutPneuBean> itemManutPneuList) {
        ArrayList<Long> idItemManutPneuList = new ArrayList<Long>();
        for (ItemManutPneuBean itemCalibPneuBean : itemManutPneuList) {
            idItemManutPneuList.add(itemCalibPneuBean.getIdItemManutPneu());
        }
        return idItemManutPneuList;
    }

    public List<ItemManutPneuBean> itemManutPneuList(Long idBolPneu){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBolPneu));

        ItemManutPneuBean itemManutPneuBean = new ItemManutPneuBean();
        return itemManutPneuBean.get(pesqArrayList);
    }

    public List<ItemManutPneuBean> itemManutPneuList(ArrayList<Long> idBolPneuArrayList){
        ItemManutPneuBean itemManutPneuBean = new ItemManutPneuBean();
        return itemManutPneuBean.in("idBolItemManutPneu", idBolPneuArrayList);
    }

    public String dadosItemManutPneu(ArrayList<Long> idBolArrayList){

        List<ItemManutPneuBean> itemManutPneuList = itemManutPneuList(idBolArrayList);
        JsonArray jsonArrayApont = new JsonArray();

        for (ItemManutPneuBean itemManutPneuBean : itemManutPneuList) {
            Gson gson = new Gson();
            jsonArrayApont.add(gson.toJsonTree(itemManutPneuBean, itemManutPneuBean.getClass()));
        }

        itemManutPneuList.clear();

        JsonObject jsonItemManutPneu = new JsonObject();
        jsonItemManutPneu.add("itemmanutpneu", jsonArrayApont);

        return jsonItemManutPneu.toString();

    }

    public ArrayList<String> itemManutPneuAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("ITEM MANUT PNEU");
        ItemManutPneuBean itemManutPneuBean = new ItemManutPneuBean();
        List<ItemManutPneuBean> itemManutPneuList = itemManutPneuBean.orderBy("idItemManutPneu", true);
        for (ItemManutPneuBean itemManutPneuBeanBD : itemManutPneuList) {
            dadosArrayList.add(dadosItemManutPneu(itemManutPneuBeanBD));
        }
        itemManutPneuList.clear();
        return dadosArrayList;
    }

    private String dadosItemManutPneu(ItemManutPneuBean itemManutPneuBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(itemManutPneuBean, itemManutPneuBean.getClass()).toString();
    }

    private EspecificaPesquisa getPesqIdBol(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolItemManutPneu");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
