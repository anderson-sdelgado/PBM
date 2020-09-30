package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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

    public void atualIdExtBol(BoletimBean boletimBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(boletimBean.getIdBoletim()));

        BoletimBean boletimBeanBD = new BoletimBean();
        List<BoletimBean> boletimList = boletimBeanBD.get(pesqArrayList);

        boletimBeanBD = boletimList.get(0);
        boletimBeanBD.setIdExtBoletim(boletimBean.getIdExtBoletim());
        boletimBeanBD.update();

    }

    public void delBoletim(BoletimBean boletimBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(boletimBean.getIdBoletim()));

        BoletimBean boletimBeanBD = new BoletimBean();
        List<BoletimBean> boletimList = boletimBeanBD.get(pesqArrayList);

        boletimBeanBD = boletimList.get(0);
        boletimBeanBD.delete();

    }

    public void fecharBoletim(BoletimBean boletimBean){
        boletimBean.setDthrFinalBoletim(Tempo.getInstance().dataHora());
        boletimBean.setStatusBoletim(2L);
        boletimBean.update();
    }

    public boolean verBoletimFechado(){
        List<BoletimBean> boletimList = boletimFechadoList();
        boolean ret = (boletimList.size() > 0);
        boletimList.clear();
        return ret;
    }

    public boolean verBoletimSemEnvio(){
        List<BoletimBean> boletimList = boletimSemEnvioList();
        boolean ret = (boletimList.size() > 0);
        boletimList.clear();
        return ret;
    }

    public List<BoletimBean> boletimSemEnvioList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusAberto());
        pesqArrayList.add(getPesqStatusSemEnvio());

        BoletimBean boletimBean = new BoletimBean();
        return boletimBean.get(pesqArrayList);

    }

    public List<BoletimBean> boletimFechadoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusFechado());

        BoletimBean boletimBean = new BoletimBean();
        return boletimBean.get(pesqArrayList);

    }

    public List<BoletimBean> boletimEnviadoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusEnviado());

        BoletimBean boletimBean = new BoletimBean();
        return boletimBean.get(pesqArrayList);

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

    public ArrayList<Long> idBolFechadoList(){

        List<BoletimBean> bolFechadoList = boletimFechadoList();
        ArrayList<Long> idBolFechadoList = new ArrayList<>();
        for (BoletimBean boletimBean : bolFechadoList) {
            idBolFechadoList.add(boletimBean.getIdBoletim());
        }
        bolFechadoList.clear();
        return idBolFechadoList;

    }

    public ArrayList<Long> idBolAbertoSemEnvioList(){

        List<BoletimBean> bolAbertoSemEnvioList = boletimSemEnvioList();
        ArrayList<Long> idBolAbertoSemEnvioList = new ArrayList<>();
        for (BoletimBean boletimBean : bolAbertoSemEnvioList) {
            idBolAbertoSemEnvioList.add(boletimBean.getIdBoletim());
        }
        bolAbertoSemEnvioList.clear();
        return idBolAbertoSemEnvioList;

    }

    public String dadosBolFechado(){

        List<BoletimBean> bolFechadoList = boletimFechadoList();
        JsonArray jsonArrayBolFechado = new JsonArray();

        for (BoletimBean boletimBean : bolFechadoList) {

            Gson gson = new Gson();
            jsonArrayBolFechado.add(gson.toJsonTree(boletimBean, boletimBean.getClass()));

        }

        bolFechadoList.clear();

        JsonObject jsonBolFechado = new JsonObject();
        jsonBolFechado.add("boletim", jsonArrayBolFechado);

        return jsonBolFechado.toString();

    }

    public String dadosBolAbertoSemEnvio(){

        List<BoletimBean> bolFechadoList = boletimSemEnvioList();
        JsonArray jsonArrayBolFechado = new JsonArray();

        for (BoletimBean boletimBean : bolFechadoList) {

            Gson gson = new Gson();
            jsonArrayBolFechado.add(gson.toJsonTree(boletimBean, boletimBean.getClass()));

        }

        bolFechadoList.clear();

        JsonObject jsonBolFechado = new JsonObject();
        jsonBolFechado.add("boletim", jsonArrayBolFechado);

        return jsonBolFechado.toString();

    }

    private EspecificaPesquisa getPesqIdBoletim(Long idBoletim){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBoletim");
        pesquisa.setValor(idBoletim);
        pesquisa.setTipo(1);
        return pesquisa;
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

    private EspecificaPesquisa getPesqStatusFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBoletim");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusSemEnvio(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idExtBoletim");
        pesquisa.setValor(0L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idExtBoletim");
        pesquisa.setValor(0L);
        pesquisa.setTipo(2);
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
