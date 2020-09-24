package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.ComponenteBean;

public class ComponenteDAO {

    public ComponenteDAO() {
    }

    public ComponenteBean getComponente(Long idCompItemOS){

        ComponenteBean componenteBean = new ComponenteBean();
        List componenteList = componenteBean.get("idComponente", idCompItemOS);
        if(componenteList.size() > 0){
            componenteBean = (ComponenteBean) componenteList.get(0);
        }
        else{
            componenteBean.setCodComponente("0");
            componenteBean.setDescrComponente("COMPONENTE INEXISTENTE");
        }
        componenteList.clear();

        return componenteBean;

    }

}
