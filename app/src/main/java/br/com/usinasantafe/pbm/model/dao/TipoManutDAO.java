package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.TipoManutBean;

public class TipoManutDAO {

    public List<TipoManutBean> tipoManutList(){
        TipoManutBean tipoManutBean = new TipoManutBean();
        return tipoManutBean.orderBy("descrTipoManut",true);
    }

}
