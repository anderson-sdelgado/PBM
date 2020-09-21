package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemMedPneuBean;

public class ItemMedPneuDAO {

    public ItemMedPneuDAO() {
    }

    public void delete(ArrayList<Long> idBolPneuLongs){

        ItemMedPneuBean itemMedPneuBean = new ItemMedPneuBean();
        List itemMedPneuList = itemMedPneuBean.in("idBolItemMedPneu", idBolPneuLongs);

        for (int i = 0; i < itemMedPneuList.size(); i++) {
            itemMedPneuBean = (ItemMedPneuBean) itemMedPneuList.get(i);
            itemMedPneuBean.delete();
        }

    }

}
