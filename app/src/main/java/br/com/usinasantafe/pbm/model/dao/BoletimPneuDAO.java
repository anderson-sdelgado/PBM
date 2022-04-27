package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class BoletimPneuDAO {

    public BoletimPneuDAO() {
    }

    public void salvarBoletimPneu(Long idEquip, Long matricColab){

        if(!verBoletimPneuAberto()){
            BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
            boletimPneuBean.setIdEquipBolPneu(idEquip);
            boletimPneuBean.setIdFuncBolPneu(matricColab);
            String dthr = Tempo.getInstance().dthrAtualString();
            boletimPneuBean.setDthrBolPneu(dthr);
            boletimPneuBean.setDthrLongBolPneu(Tempo.getInstance().dthrStringToLong(dthr));
            boletimPneuBean.setStatusBolPneu(1L);
            boletimPneuBean.insert();
        }

    }

    public void fecharBoletimPneu(){
        BoletimPneuBean boletimPneuBean = getBoletimPneuAberto();
        boletimPneuBean.setStatusBolPneu(2L);
        boletimPneuBean.update();
    }

    public void deleteBoletimMecan(Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(idBol));

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        List<BoletimMecanBean> boletimMecanList = boletimMecanBean.get(pesqArrayList);
        boletimMecanBean = boletimMecanList.get(0);
        boletimMecanBean.delete();
        boletimMecanList.clear();

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

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusAberto());

        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get(pesqArrayList);
    }

    public List<BoletimPneuBean> boletimPneuFechadoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusFechado());

        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        return boletimPneuBean.get(pesqArrayList);
    }

    public ArrayList<Long> idBoletimPneuList(List<BoletimPneuBean> boletimPneuList){
        ArrayList<Long> idBolPneuList = new ArrayList<Long>();
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            idBolPneuList.add(boletimPneuBean.getIdBolPneu());
        }
        return idBolPneuList;
    }

    public void updateEnviadoBoletimPneu(Long idbolPneu){
        List<BoletimPneuBean> boletimPneuList = boletimPneuIdList(idbolPneu);
        for (BoletimPneuBean boletimPneuBean : boletimPneuList) {
            boletimPneuBean.setStatusBolPneu(3L);
            boletimPneuBean.update();
        }
        boletimPneuList.clear();
    }

    public ArrayList<BoletimPneuBean> boletimExcluirArrayList() {

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusEnviado());

        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        List<BoletimPneuBean> boletimPneuList =  boletimPneuBean.get(pesqArrayList);

        ArrayList<BoletimPneuBean> boletimPneuArrayList = new ArrayList<>();
        for (BoletimPneuBean boletimPneuBeanBD : boletimPneuList) {
            if(boletimPneuBeanBD.getDthrLongBolPneu() < Tempo.getInstance().subDiaLong(3)) {
                boletimPneuArrayList.add(boletimPneuBeanBD);
            }
        }
        boletimPneuList.clear();
        return boletimPneuArrayList;

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

    public ArrayList<String> boletimPneuAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("BOLETIM PNEU");
        BoletimPneuBean boletimPneuBean = new BoletimPneuBean();
        List<BoletimPneuBean> boletimPneuList = boletimPneuBean.orderBy("idBolPneu", true);
        for (BoletimPneuBean boletimPneuBeanBD : boletimPneuList) {
            dadosArrayList.add(dadosBoletimPneu(boletimPneuBeanBD));
        }
        boletimPneuList.clear();
        return dadosArrayList;
    }

    private String dadosBoletimPneu(BoletimPneuBean boletimPneuBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(boletimPneuBean, boletimPneuBean.getClass()).toString();
    }

    private EspecificaPesquisa getPesqStatusAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolPneu");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolPneu");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolPneu");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdBoletim(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolPneu");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
