package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;
import br.com.usinasantafe.pbm.util.Tempo;

public class BoletimPneuDAO {

    public BoletimPneuDAO() {
    }

    public void salvarBoletimPneu(Long idEquip, Long matricColab){

        if(!verBoletimPneuAberto()){
            BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
            boletimPneuBean.setIdApontBolPneu(0L);
            boletimPneuBean.setEquipBolPneu(idEquip);
            boletimPneuBean.setFuncBolPneu(matricColab);
            boletimPneuBean.setDthrBolPneu(Tempo.getInstance().dataHora());
            boletimPneuBean.setStatusBolPneu(1L);
            boletimPneuBean.insert();
        }

    }

    public void fecharBoletimPneu(){
        BoletimPneuBean boletimPneuBean = getBoletimPneuAberto();
        boletimPneuBean.setStatusBolPneu(2L);
        boletimPneuBean.update();
    }

    public boolean verBoletimPneuAberto(){
        List<BoletimPneuBean> boletimPneuList = boletimPneuAbertoList();
        boolean ret = (boletimPneuList.size() > 0);
        boletimPneuList.clear();
        return ret;
    }

    public boolean verBoletimPneuFechado(){
        List<BoletimPneuBean> boletimPneuList = boletimPneuFechadoList();
        boolean ret = (boletimPneuList.size() > 0);
        boletimPneuList.clear();
        return ret;
    }

    public BoletimPneuBean getBoletimPneuAberto(){
        List<BoletimPneuBean> boletimPneuList = boletimPneuAbertoList();
        BoletimPneuBean boletimPneuBean = boletimPneuList.get(0);
        boletimPneuList.clear();
        return boletimPneuBean;
    }

    public List<BoletimPneuBean> boletimPneuIdList(Long idBolPneu){
        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get("idBolPneu", idBolPneu);
    }

    public List<BoletimPneuBean> boletimPneuAbertoList(){
        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get("statusBolPneu", 1L);
    }

    public List<BoletimPneuBean> boletimPneuFechadoList(){
        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get("statusBolPneu", 2L);
    }

    public ArrayList<Long> idBoletimPneuList(List<BoletimPneuBean> boletimPneuList){
        ArrayList<Long> idBolPneuList = new ArrayList<Long>();
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            idBolPneuList.add(boletimPneuBean.getIdBolPneu());
        }
        return idBolPneuList;
    }

    public void deleteBoletimPneu(Long idbolPneu){
        List<BoletimPneuBean> boletimPneuList = boletimPneuIdList(idbolPneu);
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            boletimPneuBean.delete();
        }
        boletimPneuList.clear();
    }

    public void deleteBoletimPneu(List<BoletimPneuBean> boletimPneuList){
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            boletimPneuBean.delete();
        }
    }

    public ArrayList<Long> idBolPneuFechado(){

        List<BoletimPneuBean> bolPneuFechadoList = boletimPneuFechadoList();
        ArrayList<Long> idBolPneuFechadoList = new ArrayList<>();
        for (BoletimPneuBean boletimPneuBean : bolPneuFechadoList) {
            idBolPneuFechadoList.add(boletimPneuBean.getIdBolPneu());
        }
        bolPneuFechadoList.clear();
        return idBolPneuFechadoList;

    }

    public String dadosBolPneuFechado(){

        List<BoletimPneuBean> bolPneuFechadoList = boletimPneuFechadoList();
        JsonArray jsonArrayBolPneuFechado = new JsonArray();

        for (BoletimPneuBean boletimPneuBean : bolPneuFechadoList) {

            Gson gson = new Gson();
            jsonArrayBolPneuFechado.add(gson.toJsonTree(boletimPneuBean, boletimPneuBean.getClass()));

        }

        bolPneuFechadoList.clear();

        JsonObject jsonBolFechado = new JsonObject();
        jsonBolFechado.add("bolpneu", jsonArrayBolPneuFechado);

        return jsonBolFechado.toString();

    }

}
