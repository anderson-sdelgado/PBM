package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class BoletimMecanDAO {

    public BoletimMecanDAO() {
    }

    public void atualBoletimSApont(){
        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        List boletimList = boletimMecanBean.all();
        for (int i = 0; i < boletimList.size(); i++) {
            boletimMecanBean = (BoletimMecanBean) boletimList.get(i);
            boletimMecanBean.setStatusApontBolMecan(0L);
            boletimMecanBean.update();
        }
        boletimList.clear();
    }

    public void atualSalvarBoletim(Long idEquip, Long idColab, String horarioEntr){

        List<BoletimMecanBean> boletimList = boletimAbertoListIdFunc(idColab);

        if(boletimList.size() == 0){
            BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
            String dthrInicial = Tempo.getInstance().dtAtualString() + " " + horarioEntr;
            boletimMecanBean.setDthrInicialBolMecan(dthrInicial);
            boletimMecanBean.setDthrInicialLongBolMecan(Tempo.getInstance().dthrStringToLong(dthrInicial));
            boletimMecanBean.setIdEquipBolMecan(idEquip);
            boletimMecanBean.setIdFuncBolMecan(idColab);
            boletimMecanBean.setIdExtBolMecan(0L);
            boletimMecanBean.setStatusBolMecan(1L);
            boletimMecanBean.setStatusApontBolMecan(1L);
            boletimMecanBean.insert();
        }
        else{
            BoletimMecanBean boletimMecanBean = boletimList.get(0);
            boletimMecanBean.setStatusApontBolMecan(1L);
            boletimMecanBean.update();
        }

    }

    public void atualIdExtBol(BoletimMecanBean boletimMecanBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(boletimMecanBean.getIdBolMecan()));

        BoletimMecanBean boletimMecanBeanBD = new BoletimMecanBean();
        List<BoletimMecanBean> boletimList = boletimMecanBeanBD.get(pesqArrayList);

        boletimMecanBeanBD = boletimList.get(0);
        boletimMecanBeanBD.setIdExtBolMecan(boletimMecanBean.getIdExtBolMecan());
        boletimMecanBeanBD.update();

    }

    public void updateEnviadoBoletim(BoletimMecanBean boletimMecanBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(boletimMecanBean.getIdBolMecan()));

        BoletimMecanBean boletimMecanBeanBD = new BoletimMecanBean();
        List<BoletimMecanBean> boletimList = boletimMecanBeanBD.get(pesqArrayList);

        boletimMecanBeanBD = boletimList.get(0);
        boletimMecanBeanBD.setStatusBolMecan(3L);
        boletimMecanBeanBD.update();

    }

    public void fecharBoletim(BoletimMecanBean boletimMecanBean){
        Long dthrLongFinal = Tempo.getInstance().dthrAtualLong();
        boletimMecanBean.setDthrFinalLongBolMecan(dthrLongFinal);
        boletimMecanBean.setDthrFinalBolMecan(Tempo.getInstance().dthrLongToString(dthrLongFinal));
        boletimMecanBean.setStatusBolMecan(2L);
        boletimMecanBean.setStatusFechBolMecan(1L);
        boletimMecanBean.update();
    }

    public void forcarFechamentoBoletim(BoletimMecanBean boletimMecanBean, Long dthrLongFinal){
        boletimMecanBean.setDthrFinalLongBolMecan(dthrLongFinal);
        boletimMecanBean.setDthrFinalBolMecan(Tempo.getInstance().dthrLongToString(dthrLongFinal));
        boletimMecanBean.setStatusBolMecan(2L);
        boletimMecanBean.setStatusFechBolMecan(0L);
        boletimMecanBean.update();
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

    public ArrayList<BoletimMecanBean> boletimExcluirArrayList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusEnviado());

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        List<BoletimMecanBean> boletimMecanList =  boletimMecanBean.get(pesqArrayList);

        ArrayList<BoletimMecanBean> boletimMecanArrayList = new ArrayList<>();
        for (BoletimMecanBean boletimMecanBeanBD : boletimMecanList) {
            if(boletimMecanBeanBD.getDthrFinalLongBolMecan() < Tempo.getInstance().subDiaLong(3)) {
                boletimMecanArrayList.add(boletimMecanBeanBD);
            }
        }
        boletimMecanList.clear();
        return boletimMecanArrayList;

    }

    public boolean verBoletimFechado(){
        List<BoletimMecanBean> boletimList = boletimFechadoList();
        boolean ret = (boletimList.size() > 0);
        boletimList.clear();
        return ret;
    }

    public BoletimMecanBean getBoletimApontando(){
        List<BoletimMecanBean> boletimList = boletimApontandoList();
        BoletimMecanBean boletimMecanBean = boletimList.get(0);
        boletimList.clear();
        return boletimMecanBean;
    }

    public BoletimMecanBean getBoletimListIdBol(Long idBol){
        List<BoletimMecanBean> boletimList = boletimListIdBol(idBol);
        BoletimMecanBean boletimMecanBean = boletimList.get(0);
        boletimList.clear();
        return boletimMecanBean;
    }

    public List<BoletimMecanBean> boletimList(ArrayList<Long> idBolList){
        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.in("idBolMecan", idBolList);
    }

    public List<BoletimMecanBean> boletimListIdBol(Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBoletim(idBol));

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.get(pesqArrayList);

    }

    public List<BoletimMecanBean> boletimAbertoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusAberto());

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.get(pesqArrayList);

    }

    public List<BoletimMecanBean> boletimFechadoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusFechado());

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.get(pesqArrayList);

    }

    public List<BoletimMecanBean> boletimAbertoListIdFunc(Long idFunc){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdFunc(idFunc));
        pesqArrayList.add(getPesqStatusAberto());

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.get(pesqArrayList);

    }

    public List<BoletimMecanBean> boletimApontandoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqBolApontando());

        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        return boletimMecanBean.get(pesqArrayList);

    }

    public ArrayList<Long> idBolFechadoList(){

        List<BoletimMecanBean> bolFechadoList = boletimFechadoList();
        ArrayList<Long> idBolFechadoList = new ArrayList<>();
        for (BoletimMecanBean boletimMecanBean : bolFechadoList) {
            idBolFechadoList.add(boletimMecanBean.getIdBolMecan());
        }
        bolFechadoList.clear();
        return idBolFechadoList;

    }

    public String dadosBolFechado(){

        List<BoletimMecanBean> bolFechadoList = boletimFechadoList();
        JsonArray jsonArrayBolFechado = new JsonArray();

        for (BoletimMecanBean boletimMecanBean : bolFechadoList) {

            Gson gson = new Gson();
            jsonArrayBolFechado.add(gson.toJsonTree(boletimMecanBean, boletimMecanBean.getClass()));

        }

        bolFechadoList.clear();

        JsonObject jsonBolFechado = new JsonObject();
        jsonBolFechado.add("boletim", jsonArrayBolFechado);

        return jsonBolFechado.toString();

    }

    public String dadosBoletimApontSemEnvio(ArrayList<Long> idBolAbertoList){

        List<BoletimMecanBean> boletimMecanList = boletimList(idBolAbertoList);
        JsonArray jsonArrayBoletimMecan = new JsonArray();

        for (BoletimMecanBean boletimMecanBean : boletimMecanList) {

            Gson gson = new Gson();
            jsonArrayBoletimMecan.add(gson.toJsonTree(boletimMecanBean, boletimMecanBean.getClass()));

        }

        boletimMecanList.clear();

        JsonObject jsonBoletimMecan = new JsonObject();
        jsonBoletimMecan.add("boletim", jsonArrayBoletimMecan);

        return jsonBoletimMecan.toString();

    }

    public ArrayList<String> boletimMecanAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("BOLETIM MECANICO");
        BoletimMecanBean boletimMecanBean = new BoletimMecanBean();
        List<BoletimMecanBean> boletimMecanList = boletimMecanBean.orderBy("idBolMecan", true);
        for (BoletimMecanBean boletimMecanBeanBD : boletimMecanList) {
            dadosArrayList.add(dadosBoletimMecanFert(boletimMecanBeanBD));
        }
        boletimMecanList.clear();
        return dadosArrayList;
    }

    private String dadosBoletimMecanFert(BoletimMecanBean boletimMecanBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(boletimMecanBean, boletimMecanBean.getClass()).toString();
    }

    private EspecificaPesquisa getPesqIdBoletim(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolMecan");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdFunc(Long idFunc){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idFuncBolMecan");
        pesquisa.setValor(idFunc);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolMecan");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolMecan");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBolMecan");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqBolApontando(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontBolMecan");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
