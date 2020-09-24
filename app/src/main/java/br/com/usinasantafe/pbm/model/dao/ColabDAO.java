package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class ColabDAO {

    public ColabDAO() {
    }

    public boolean hasElements(){
        ColabBean colabBean = new ColabBean();
        return colabBean.hasElements();
    }

    public boolean verMatricColab(Long matricColab){
        List<ColabBean> colabList = colabMatricList(matricColab);
        boolean ret = (colabList.size() > 0);
        colabList.clear();
        return ret;
    }

    public ColabBean getMatricColab(Long matricColab){
        List<ColabBean> colabList = colabMatricList(matricColab);
        ColabBean colabBean = colabList.get(0);
        colabList.clear();
        return colabBean;
    }

    public ColabBean getIdColab(Long idColab){
        List<ColabBean> colabList = colabIdList(idColab);
        ColabBean colabBean = colabList.get(0);
        colabList.clear();
        return colabBean;
    }

    public List<ColabBean> colabMatricList(Long matricColab){
        ColabBean colabBean = new ColabBean();
        return colabBean.get("matricColab", matricColab);
    }

    public List<ColabBean> colabIdList(Long idColab){
        ColabBean colabBean = new ColabBean();
        return colabBean.get("idColab", idColab);
    }

}
