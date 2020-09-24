package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class BoletimDAO {

    public BoletimDAO() {
    }

    public void atualBoletimSApont(){
        BoletimBean boletimBean = new BoletimBean();
        List boletimList = boletimBean.all();
        for (int i = 0; i < boletimList.size(); i++) {
            boletimBean = (BoletimBean) boletimList.get(i);
            boletimBean.setApontBoletim(0L);
            boletimBean.update();
        }
        boletimList.clear();
    }

    public void atualSalvarBoletim(Long idEquip, Long idColab, String horarioEntr){

        List<BoletimBean> boletimList = boletimList(idColab);

        if(boletimList.size() == 0){
            BoletimBean boletimBean = new BoletimBean();
            boletimBean.setDthrInicialBoletim(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraComTZ() + " " + horarioEntr));
            boletimBean.setEquipBoletim(idEquip);
            boletimBean.setIdFuncBoletim(idColab);
            boletimBean.setIdExtBoletim(0L);
            boletimBean.setStatusBoletim(1L);
            boletimBean.setApontBoletim(1L);
            boletimBean.insert();
        }
        else{
            BoletimBean boletimBean = boletimList.get(0);
            boletimBean.setApontBoletim(1L);
            boletimBean.update();
        }

    }

    public void fecharBoletim(BoletimBean boletimBean){
        boletimBean.setDthrFinalBoletim(Tempo.getInstance().dataHora());
        boletimBean.setStatusBoletim(2L);
        boletimBean.update();
    }

    public List<BoletimBean> boletimList(Long idFunc){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdFunc(idFunc));
        pesqArrayList.add(getPesqStatusAberto());

        BoletimBean boletimBean = new BoletimBean();
        return boletimBean.get(pesqArrayList);

    }

    public BoletimBean getBoletimApont(){
        List<BoletimBean> boletimList = boletimApontList();
        BoletimBean boletimBean = boletimList.get(0);
        boletimList.clear();
        return boletimBean;
    }

    public List<BoletimBean> boletimApontList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqApont());

        BoletimBean boletimBean = new BoletimBean();
        return boletimBean.get(pesqArrayList);

    }

    private EspecificaPesquisa getPesqIdFunc(Long idFunc){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idFuncBoletim");
        pesquisa.setValor(idFunc);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBoletim");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqApont(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("apontBoletim");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
