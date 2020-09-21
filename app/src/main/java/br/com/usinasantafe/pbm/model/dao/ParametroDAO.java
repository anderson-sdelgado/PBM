package br.com.usinasantafe.pbm.model.dao;

import br.com.usinasantafe.pbm.model.bean.estaticas.ParametroBean;

public class ParametroDAO {

    public ParametroDAO() {
    }

    public void insert(Long minutos){

        ParametroBean parametroBean = new ParametroBean();
        parametroBean.setMinParametro(minutos);
        parametroBean.deleteAll();
        parametroBean.insert();

    }

}
