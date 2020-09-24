package br.com.usinasantafe.pbm.model.dao;


import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.EscalaTrabBean;

public class EscalaTrabDAO {

    public EscalaTrabDAO() {
    }

    public EscalaTrabBean getEscalaTrab(Long idEscalaTrab){
        EscalaTrabBean escalaTrabBean = new EscalaTrabBean();
        List<EscalaTrabBean> escalaTrabList = escalaTrabBean.get("idEscalaTrab", idEscalaTrab);
        escalaTrabBean = escalaTrabList.get(0);
        escalaTrabList.clear();
        return escalaTrabBean;
    }

}
