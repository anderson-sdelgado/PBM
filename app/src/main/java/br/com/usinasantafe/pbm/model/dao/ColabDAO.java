package br.com.usinasantafe.pbm.model.dao;

import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class ColabDAO {

    public ColabDAO() {
    }

    public boolean hasElements(){
        ColabBean colabBean = new ColabBean();
        return colabBean.hasElements();
    }

}
