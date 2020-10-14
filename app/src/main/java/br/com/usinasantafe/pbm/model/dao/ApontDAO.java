package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class ApontDAO {

    public ApontDAO() {
    }

    public void salvarApont(ApontBean apontBean, String horarioEnt, BoletimBean boletimBean){

        List<ApontBean> apontList = apontList(boletimBean.getIdBoletim());

        if(apontBean.getParadaApont() > 0) {

            if(apontList.size() > 0) {
                ApontBean apontBeanBD = apontList.get(0);
                apontBean.setDthrInicialApont(apontBeanBD.getDthrFinalApont());
            }
            else{
                apontBean.setDthrInicialApont(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + horarioEnt));
            }

            apontBean.setDthrFinalApont(Tempo.getInstance().dataHora());

        }
        else {

            if(apontList.size() > 0) {

                ApontBean apontBeanBD = apontList.get(0);
                apontBeanBD.setDthrFinalApont(Tempo.getInstance().dataHora());
                apontBeanBD.setStatusApont(0L);
                apontBeanBD.update();

                apontBean.setDthrInicialApont(Tempo.getInstance().dataHora());

            }
            else{

                apontBean.setDthrInicialApont(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + horarioEnt));

            }

            apontBean.setDthrFinalApont("");

        }

        apontList.clear();
        apontBean.setIdBolApont(boletimBean.getIdBoletim());
        apontBean.setIdExtBolApont(boletimBean.getIdExtBoletim());
        apontBean.setStatusApont(0L);
        apontBean.insert();

    }

    public void updApont(ApontBean apontBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdApont(apontBean.getIdApont()));

        List<ApontBean> apontList = apontBean.get(pesqArrayList);

        ApontBean apontBeanBD = apontList.get(0);
        apontBeanBD.setStatusApont(1L);
        apontBeanBD.update();

        apontList.clear();

    }

    public void updApontIdExtBoletim(Long idBol, Long idExtBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));

        ApontBean apontBean = new ApontBean();
        List<ApontBean> apontList = apontBean.get(pesqArrayList);

        for(ApontBean apontBeanBD : apontList){
            apontBeanBD.setIdExtBolApont(idExtBol);
            apontBeanBD.update();
        }

        apontList.clear();

    }

    public void updApontEnviado(Long idApont, Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdApont(idApont));
        pesqArrayList.add(getPesqIdBol(idBol));

        ApontBean apontBean = new ApontBean();
        List<ApontBean> apontList = apontBean.get(pesqArrayList);

        for(ApontBean apontBeanBD : apontList){
            apontBeanBD.setStatusApont(1L);
            apontBeanBD.update();
        }

        apontList.clear();

    }

    public void delApont(Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));

        ApontBean apontBean = new ApontBean();
        List<ApontBean> apontList = apontBean.get(pesqArrayList);

        for(ApontBean apontBeanBD : apontList){
            apontBeanBD.delete();
        }

        apontList.clear();

    }

    public boolean verApontSemEnvio(){
        List<ApontBean> apontList = apontSemEnvioList();
        boolean ret = (apontList.size() > 0);
        apontList.clear();
        return ret;
    }

    public List<ApontBean> apontList(Long idBol){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));
        ApontBean apontBean = new ApontBean();
        return apontBean.getAndOrderBy(pesqArrayList, "idApont", false);
    }

    public List<ApontBean> apontList(ArrayList<Long> idBolArrayList){
        ApontBean apontBean = new ApontBean();
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqSemEnvio());
        return apontBean.inAndGet("idBolApont", idBolArrayList, pesqArrayList);
    }

    public List<ApontBean> apontSemEnvioList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqSemEnvio());
        ApontBean apontBean = new ApontBean();
        return apontBean.get(pesqArrayList);
    }

    public ArrayList<Long> idBolAbertoList(){

        List<ApontBean> apontSemEnvioList = apontSemEnvioList();
        ArrayList<Long> idBolAbertoSemEnvioList = new ArrayList<>();
        for (ApontBean apontBean : apontSemEnvioList) {
            idBolAbertoSemEnvioList.add(apontBean.getIdBolApont());
        }
        apontSemEnvioList.clear();
        return idBolAbertoSemEnvioList;

    }

    public void finalizarApont(ApontBean apontBean){
        apontBean.setDthrFinalApont(Tempo.getInstance().dataHora());
        apontBean.setRealizApont(1L);
        apontBean.setStatusApont(0L);
        apontBean.update();
    }

    public void interroperApont(ApontBean apontBean){
        apontBean.setDthrFinalApont(Tempo.getInstance().dataHora());
        apontBean.setStatusApont(0L);
        apontBean.update();
    }

    public boolean verOSApont(Long idBol, Long nroOS){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));
        pesqArrayList.add(getPesqOSApont(nroOS));

        ApontBean apontBean = new ApontBean();
        List<ApontBean> apontList = apontBean.get(pesqArrayList);
        boolean ret = (apontList.size() > 0);
        apontList.clear();

        return ret;

    }

    public void fecharApont(ApontBean apontBean){
        if(apontBean.getParadaApont() == 0L){
            apontBean.setDthrFinalApont(Tempo.getInstance().dataHora());
            apontBean.setStatusApont(0L);
            apontBean.update();
        }
    }

    public void fecharApont(ApontBean apontBean, String dthrFinal){
        if(apontBean.getParadaApont() == 0L){
            apontBean.setDthrFinalApont(dthrFinal);
            apontBean.setStatusApont(0L);
            apontBean.update();
        }
    }

    public String dadosEnvioApont(ArrayList<Long> idBolArrayList){

        List<ApontBean> apontList = apontList(idBolArrayList);
        JsonArray jsonArrayApont = new JsonArray();

        for (ApontBean apontBean : apontList) {

            Gson gson = new Gson();
            jsonArrayApont.add(gson.toJsonTree(apontBean, apontBean.getClass()));

        }

        apontList.clear();

        JsonObject jsonApont = new JsonObject();
        jsonApont.add("aponta", jsonArrayApont);

        return jsonApont.toString();

    }

    private EspecificaPesquisa getPesqIdApont(Long idApont){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idApont");
        pesquisa.setValor(idApont);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdBol(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolApont");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOSApont(Long nroOS){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("osApont");
        pesquisa.setValor(nroOS);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqSemEnvio(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApont");
        pesquisa.setValor(0L);
        pesquisa.setTipo(1);
        return pesquisa;
    }


}
