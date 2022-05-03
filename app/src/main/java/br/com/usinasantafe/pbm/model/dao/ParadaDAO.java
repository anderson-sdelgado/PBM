package br.com.usinasantafe.pbm.model.dao;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.ParadaBean;

public class ParadaDAO {

    public ParadaDAO() {
    }

    public ParadaBean getParadaCod(Long codParada){
        List<ParadaBean> paradaList = paradaCodList(codParada);
        ParadaBean paradaBean = paradaList.get(0);
        return paradaBean;
    }

    public ParadaBean getParadaId(Long idParada){
        List<ParadaBean> paradaList = paradaCodList(idParada);
        ParadaBean paradaBean = paradaList.get(0);
        return paradaBean;
    }

    public List<ParadaBean> paradaList(){
        ParadaBean paradaTO = new ParadaBean();
        return paradaTO.orderBy("codParada",true);
    }

    public List<ParadaBean> paradaCodList(Long codParada){
        ParadaBean paradaTO = new ParadaBean();
        return paradaTO.get("codParada", codParada);
    }

    public List<ParadaBean> paradaIdList(Long idParada){
        ParadaBean paradaTO = new ParadaBean();
        return paradaTO.get("idParada", idParada);
    }

}
