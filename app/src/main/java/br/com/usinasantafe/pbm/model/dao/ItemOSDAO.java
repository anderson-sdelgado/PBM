package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.ItemOSBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.OSBean;

public class ItemOSDAO {

    public ItemOSDAO() {
    }

    public List<ItemOSBean> itemOSList(Long idOS){
        ItemOSBean itemOSTO = new ItemOSBean();
        return itemOSTO.getAndOrderBy("idOS", idOS,"seqItemOS",true);
    }

    public void deleteAllItemOS(){
        OSBean osBean = new OSBean();
        osBean.deleteAll();
    }

    public void insertItemOS(ItemOSBean itemOSBean){
        itemOSBean.insert();
    }

}
