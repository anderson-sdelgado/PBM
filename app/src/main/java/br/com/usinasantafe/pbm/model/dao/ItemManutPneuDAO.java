package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.util.Tempo;

public class ItemManutPneuDAO {

    public ItemManutPneuDAO() {
    }

    public void deleteItemManutPneu(ArrayList<Long> idBolPneuLongs){

        List<ItemManutPneuBean> itemManutPneuList = itemManutPneuList(idBolPneuLongs);
        for (ItemManutPneuBean itemManutPneuBean : itemManutPneuList) {
            itemManutPneuBean.delete();
        }
        itemManutPneuList.clear();

    }

    public void deleteItemManutPneu(Long idBolPneuList){

        List<ItemManutPneuBean> itemManutPneuList = itemManutPneuList(idBolPneuList);
        for (ItemManutPneuBean itemManutPneuBean : itemManutPneuList) {
            itemManutPneuBean.delete();
        }
        itemManutPneuList.clear();

    }

    public void insertItemManutPneu(ItemManutPneuBean itemManutPneuBean, Long idBolPneu){
        itemManutPneuBean.setIdBolItemManutPneu(idBolPneu);
        itemManutPneuBean.setDthrItemManutPneu(Tempo.getInstance().dthr());
        itemManutPneuBean.insert();
    }

    public List<ItemManutPneuBean> itemManutPneuList(Long idBolPneu){
        ItemManutPneuBean itemManutPneuBean = new ItemManutPneuBean();
        return itemManutPneuBean.get("idBolItemManutPneu", idBolPneu);
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

}
