package br.com.usinasantafe.pbm.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class ApontMecanDAO {

    public ApontMecanDAO() {
    }

    public void salvarApont(ApontMecanBean apontMecanBean, String horarioEnt, BoletimMecanBean boletimMecanBean){

        List<ApontMecanBean> apontList = apontIdBolList(boletimMecanBean.getIdBolMecan());

        if(apontMecanBean.getParadaApontMecan() > 0) {

            if(apontList.size() > 0) {
                ApontMecanBean apontMecanBeanBD = apontList.get(0);
                apontMecanBean.setDthrInicialLongApontMecan(apontMecanBeanBD.getDthrFinalLongApontMecan());
                apontMecanBean.setDthrInicialApontMecan(apontMecanBeanBD.getDthrFinalApontMecan());
            }
            else{
                Long dthrLong = Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dtAtualString() + " " + horarioEnt);
                apontMecanBean.setDthrInicialLongApontMecan(dthrLong);
                apontMecanBean.setDthrInicialApontMecan(Tempo.getInstance().dthrLongToString(dthrLong));
            }

            apontMecanBean.setDthrFinalApontMecan(Tempo.getInstance().dthrAtualString());
            apontMecanBean.setStatusApontMecan(3L);

        }
        else {

            if(apontList.size() > 0) {

                ApontMecanBean apontMecanBeanBD = apontList.get(0);
                Long dthrLong = Tempo.getInstance().dthrAtualLong();
                apontMecanBeanBD.setDthrFinalLongApontMecan(dthrLong);
                apontMecanBeanBD.setDthrFinalApontMecan(Tempo.getInstance().dthrLongToString(dthrLong));
                apontMecanBeanBD.setStatusApontMecan(0L);
                apontMecanBeanBD.update();

                apontMecanBean.setDthrInicialLongApontMecan(dthrLong);
                apontMecanBean.setDthrInicialApontMecan(Tempo.getInstance().dthrLongToString(dthrLong));

            }
            else{

                Long dthrLong = Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dtAtualString() + " " + horarioEnt);
                apontMecanBean.setDthrInicialLongApontMecan(dthrLong);
                apontMecanBean.setDthrInicialApontMecan(Tempo.getInstance().dthrLongToString(dthrLong));

            }

            apontMecanBean.setDthrFinalApontMecan("");
            apontMecanBean.setStatusApontMecan(1L);

        }

        apontList.clear();
        apontMecanBean.setIdBolApontMecan(boletimMecanBean.getIdBolMecan());
        apontMecanBean.insert();

    }

    public void updApontEnviado(Long idApont, Long idBol){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdApontMecan(idApont));
        pesqArrayList.add(getPesqIdBolMecan(idBol));

        ApontMecanBean apontMecanBean = new ApontMecanBean();
        List<ApontMecanBean> apontList = apontMecanBean.get(pesqArrayList);

        for(ApontMecanBean apontMecanBeanBD : apontList){
            if(apontMecanBeanBD.getStatusApontMecan() == 1L){
                apontMecanBeanBD.setStatusApontMecan(2L);
            }
            else if(apontMecanBeanBD.getStatusApontMecan() == 3L){
                apontMecanBeanBD.setStatusApontMecan(4L);
            }
            apontMecanBeanBD.update();
        }

        apontList.clear();

    }

    public void deleteApontMecan(ArrayList<Long> idApontMecanArrayList) {

        List<ApontMecanBean> apontMecanList = apontMecanList(idApontMecanArrayList);

        for (ApontMecanBean apontMecanBean : apontMecanList) {
            apontMecanBean.delete();
        }

        apontMecanList.clear();
        idApontMecanArrayList.clear();

    }

    public boolean verApontSemEnvio(){
        List<ApontMecanBean> apontList = apontSemEnvioList();
        boolean ret = (apontList.size() > 0);
        apontList.clear();
        return ret;
    }

    public boolean verApont(Long idBol){
        List<ApontMecanBean> apontList = apontIdBolList(idBol);
        boolean ret = (apontList.size() > 0);
        apontList.clear();
        return ret;
    }

    public ApontMecanBean getUltApontIdBol(Long idBol){
        List<ApontMecanBean> apontMecanList = apontIdBolList(idBol);
        ApontMecanBean apontMecanBean = apontMecanList.get(0);
        apontMecanList.clear();
        return apontMecanBean;
    }

    public List<ApontMecanBean> apontIdBolList(Long idBol){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBolMecan(idBol));
        ApontMecanBean apontMecanBean = new ApontMecanBean();
        return apontMecanBean.getAndOrderBy(pesqArrayList, "idApontMecan", false);
    }

    public List<ApontMecanBean> apontIdBolList(ArrayList<Long> idBolArrayList){
        ApontMecanBean apontMecanBean = new ApontMecanBean();
        return apontMecanBean.in("idBolApontMecan", idBolArrayList);
    }

    public List<ApontMecanBean> apontSemEnvioList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusAbertoNEnviadoMecan());
        pesqArrayList.add(getPesqStatusAbertoEnviadoMecan());
        ApontMecanBean apontMecanBean = new ApontMecanBean();
        return apontMecanBean.getWithOr(pesqArrayList);
    }

    public List<ApontMecanBean> apontMecanList(ArrayList<Long> idApontMecanArrayList){
        ApontMecanBean apontMecanBean = new ApontMecanBean();
        return apontMecanBean.in("idApontMecan", idApontMecanArrayList);
    }

    public ArrayList<Long> idApontMecanArrayList(List<ApontMecanBean> apontMecanList) {
        ArrayList<Long> idApontMecanList = new ArrayList<Long>();
        for (ApontMecanBean apontMecanBean : apontMecanList) {
            idApontMecanList.add(apontMecanBean.getIdApontMecan());
        }
        return idApontMecanList;
    }

    public ArrayList<Long> idBolAbertoList(){

        List<ApontMecanBean> apontSemEnvioList = apontSemEnvioList();
        ArrayList<Long> idBolAbertoSemEnvioList = new ArrayList<>();
        for (ApontMecanBean apontMecanBean : apontSemEnvioList) {
            idBolAbertoSemEnvioList.add(apontMecanBean.getIdBolApontMecan());
        }
        apontSemEnvioList.clear();
        return idBolAbertoSemEnvioList;

    }

    public void finalizarApont(ApontMecanBean apontMecanBean){
        apontMecanBean.setDthrFinalApontMecan(Tempo.getInstance().dthrAtualString());
        apontMecanBean.setRealizApontMecan(1L);
        apontMecanBean.setStatusApontMecan(3L);
        apontMecanBean.update();
    }

    public void interroperApont(ApontMecanBean apontMecanBean){
        apontMecanBean.setDthrFinalApontMecan(Tempo.getInstance().dthrAtualString());
        apontMecanBean.setStatusApontMecan(3L);
        apontMecanBean.update();
    }

    public boolean verOSApont(Long idBol, Long nroOS){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBolMecan(idBol));
        pesqArrayList.add(getPesqOSApontMecan(nroOS));

        ApontMecanBean apontMecanBean = new ApontMecanBean();
        List<ApontMecanBean> apontList = apontMecanBean.get(pesqArrayList);
        boolean ret = (apontList.size() > 0);
        apontList.clear();

        return ret;

    }

    public void fecharApont(ApontMecanBean apontMecanBean){
        if(apontMecanBean.getParadaApontMecan() == 0L){
            apontMecanBean.setDthrFinalApontMecan(Tempo.getInstance().dthrAtualString());
            apontMecanBean.setStatusApontMecan(3L);
            apontMecanBean.update();
        }
    }

    public void fecharApont(ApontMecanBean apontMecanBean, String dthrFinal){
        if(apontMecanBean.getParadaApontMecan() == 0L){
            apontMecanBean.setDthrFinalApontMecan(dthrFinal);
            apontMecanBean.setStatusApontMecan(3L);
            apontMecanBean.update();
        }
    }

    public String dadosEnvioApont(ArrayList<Long> idBolArrayList){

        List<ApontMecanBean> apontList = apontIdBolList(idBolArrayList);
        JsonArray jsonArrayApont = new JsonArray();

        for (ApontMecanBean apontMecanBean : apontList) {

            Gson gson = new Gson();
            jsonArrayApont.add(gson.toJsonTree(apontMecanBean, apontMecanBean.getClass()));

        }

        apontList.clear();

        JsonObject jsonApont = new JsonObject();
        jsonApont.add("aponta", jsonArrayApont);

        return jsonApont.toString();

    }

    public ArrayList<String> apontMecanAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("APONTAMENTO MECANICO");
        ApontMecanBean apontMecanBean = new ApontMecanBean();
        List<ApontMecanBean> apontMecanList = apontMecanBean.orderBy("idApontMecan", true);
        for (ApontMecanBean apontMecanBeanBD : apontMecanList) {
            dadosArrayList.add(dadosApontMecan(apontMecanBeanBD));
        }
        apontMecanList.clear();
        return dadosArrayList;
    }

    private String dadosApontMecan(ApontMecanBean apontMecanBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(apontMecanBean, apontMecanBean.getClass()).toString();
    }

    private EspecificaPesquisa getPesqIdApontMecan(Long idApont){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idApontMecan");
        pesquisa.setValor(idApont);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdBolMecan(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolApontMecan");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOSApontMecan(Long nroOS){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("nroOSApontMecan");
        pesquisa.setValor(nroOS);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusAbertoNEnviadoMecan(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontMecan");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusAbertoEnviadoMecan(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontMecan");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
