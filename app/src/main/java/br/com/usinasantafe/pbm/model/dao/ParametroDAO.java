package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.ParametroBean;

public class ParametroDAO {

    public ParametroDAO() {
    }

    public void insert(ParametroBean parametroBean){
        parametroBean.deleteAll();
        parametroBean.insert();
    }

    public ParametroBean getParametro(){
        ParametroBean parametroBean = new ParametroBean();
        List<ParametroBean> parametroList = parametroBean.all();
        parametroBean = parametroList.get(0);
        parametroList.clear();
        return parametroBean;
    }

}
