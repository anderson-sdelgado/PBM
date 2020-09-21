package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;

public class ItemManutPneuDAO {

    public ItemManutPneuDAO() {
    }

    public void delete(ArrayList<Long> idBolPneuLongs){

        ItemManutPneuBean itemManutPneuBean = new ItemManutPneuBean();
        List itemManutPneuList = itemManutPneuBean.in("idBolItemManutPneu", idBolPneuLongs);

        for (int i = 0; i < itemManutPneuList.size(); i++) {
            itemManutPneuBean = (ItemManutPneuBean) itemManutPneuList.get(i);
            itemManutPneuBean.delete();
        }

    }


}
