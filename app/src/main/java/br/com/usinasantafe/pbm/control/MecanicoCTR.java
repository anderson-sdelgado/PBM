package br.com.usinasantafe.pbm.control;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.AtualAplicBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ComponenteBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.EscalaTrabBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ItemOSBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ParadaBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ParametroBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.ServicoBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.dao.ApontMecanDAO;
import br.com.usinasantafe.pbm.model.dao.BoletimMecanDAO;
import br.com.usinasantafe.pbm.model.dao.ColabDAO;
import br.com.usinasantafe.pbm.model.dao.ComponenteDAO;
import br.com.usinasantafe.pbm.model.dao.EquipDAO;
import br.com.usinasantafe.pbm.model.dao.EscalaTrabDAO;
import br.com.usinasantafe.pbm.model.dao.ItemOSDAO;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.model.dao.OSDAO;
import br.com.usinasantafe.pbm.model.dao.ParadaDAO;
import br.com.usinasantafe.pbm.model.dao.ParametroDAO;
import br.com.usinasantafe.pbm.model.dao.ServicoDAO;
import br.com.usinasantafe.pbm.util.AtualDadosServ;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class MecanicoCTR {

    private ApontMecanBean apontMecanBean;

    public MecanicoCTR() {
    }

    ///////////////////////////////// VERIFICAR DADOS ////////////////////////////////////////////

    public boolean hasElementsColab(){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.hasElements();
    }

    public boolean verMatricColab(Long matricColab){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.verMatricColab(matricColab);
    }

    public boolean verApontBolApontando(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        List<ApontMecanBean> apontList = apontMecanDAO.apontIdBolList(boletimMecanDAO.getBoletimApontando().getIdBolMecan());
        boolean ret = (apontList.size() > 0);
        apontList.clear();
        return ret;
    }

    public boolean verOSApont(Long nroOS){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        OSDAO osDAO = new OSDAO();
        return (apontMecanDAO.verOSApont(boletimMecanDAO.getBoletimApontando().getIdBolMecan(), nroOS) && osDAO.verOS(nroOS));
    }

    public boolean verBoletimFechado(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        return boletimMecanDAO.verBoletimFechado();
    }

    public boolean verApontSemEnvio(){
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        return apontMecanDAO.verApontSemEnvio();
    }

    public boolean verifDataHoraTurno(){
        return Tempo.getInstance().verifHora(getEscalaTrab(getColabApont().getIdEscalaTrabColab()).getHorarioEntEscalaTrab());
    }

    public boolean verifDataHoraFinalUltApont(){
        return Tempo.getInstance().verifDataHoraParada(getUltApontBolApontando().getDthrFinalApontMecan(), getParametro().getMinutosParada());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////// SALVAR/ATUALIZAR/EXCLUIR DADOS ///////////////////////////////////

    public void insertParametro(String parametros) throws JSONException {

        JSONObject jObj = new JSONObject(parametros);
        JSONArray jsonArray = jObj.getJSONArray("parametro");

        if (jsonArray.length() > 0) {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objeto = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                AtualAplicBean atualAplicBean = gson.fromJson(objeto.toString(), AtualAplicBean.class);
                ParametroDAO parametroDAO = new ParametroDAO();
                parametroDAO.insert(atualAplicBean);
            }

        }

    }

    public void atualSalvarBoletim(ColabBean colabBean, String activity){

        ConfigCTR configCTR = new ConfigCTR();

        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        boletimMecanDAO.atualSalvarBoletim(configCTR.getConfig().getIdEquipConfig(), colabBean.getIdColab(), getEscalaTrab(colabBean.getIdEscalaTrabColab()).getHorarioEntEscalaTrab());

        LogProcessoDAO.getInstance().insertLogProcesso("EnvioDadosServ.getInstance().envioDados(activity);", activity);
        EnvioDadosServ.getInstance().envioDados(activity);

    }

    public void atualBoletimSApont(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        boletimMecanDAO.atualBoletimSApont();
    }

    public void salvarApont(Long itemOSApontMecan, Long paradaApontMecan, Long realizApontMecan){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        apontMecanBean.setItemOSApontMecan(itemOSApontMecan);
        apontMecanBean.setParadaApontMecan(paradaApontMecan);
        apontMecanBean.setRealizApontMecan(realizApontMecan);
        apontMecanDAO.salvarApont(apontMecanBean, getEscalaTrab(getColabApont().getIdEscalaTrabColab()).getHorarioEntEscalaTrab(), boletimMecanDAO.getBoletimApontando());
    }

    public void fecharBoletim(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        boletimMecanDAO.fecharBoletim(boletimMecanDAO.getBoletimApontando());
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        apontMecanDAO.fecharApont(getUltApontBolApontando());
    }

    public void forcarFechamentoBoletim(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        ColabDAO colabDAO = new ColabDAO();
        List<BoletimMecanBean> boletimMecanAbertoList = boletimMecanDAO.boletimAbertoList();
        for(BoletimMecanBean boletimMecanBean : boletimMecanAbertoList){
            ApontMecanBean apontMecanBean = apontMecanDAO.getUltApontIdBol(boletimMecanBean.getIdBolMecan());
            Long dthrLongUltApont = (apontMecanBean.getParadaApontMecan() == 0L) ? apontMecanBean.getDthrInicialLongApontMecan() : apontMecanBean.getDthrFinalLongApontMecan();
            if(Tempo.getInstance().verifDataHoraForcaFechBol(dthrLongUltApont, getParametro().getHoraFechBoletim())){
                Long dthrLongFinal = Tempo.getInstance().dthrLongFinalizarBol(boletimMecanBean.getDthrInicialLongBolMecan()
                        , boletimMecanBean.getDthrInicialBolMecan().substring(0, 10) + " " + getEscalaTrab(colabDAO.getIdColab(boletimMecanBean.getIdFuncBolMecan()).getIdEscalaTrabColab()).getHorarioSaiEscalaTrab());
                boletimMecanDAO.forcarFechamentoBoletim(boletimMecanBean, dthrLongFinal);
                apontMecanDAO.fecharApont(apontMecanBean, dthrLongFinal);
            }
        }
    }

    public void finalizarApont(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        apontMecanDAO.finalizarApont(apontMecanDAO.apontIdBolList(boletimMecanDAO.getBoletimApontando().getIdBolMecan()).get(0));
    }

    public void interroperApont(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        apontMecanDAO.interroperApont(apontMecanDAO.apontIdBolList(boletimMecanDAO.getBoletimApontando().getIdBolMecan()).get(0));
    }

    public void deleteBolMecanSemApont() {

        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();

        List<BoletimMecanBean> boletimList = boletimMecanDAO.boletimAbertoList();

        for(BoletimMecanBean boletimMecanBean : boletimList){
            if(!apontMecanDAO.verApont(boletimMecanBean.getIdBolMecan())){
                boletimMecanDAO.deleteBoletimMecan(boletimMecanBean.getIdBolMecan());
            }
        }

    }

    public void deleteBolMecanEnviado(){

        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ArrayList<BoletimMecanBean> boletimMMFertArrayList = boletimMecanDAO.boletimExcluirArrayList();;

        for (BoletimMecanBean boletimMecanBean : boletimMMFertArrayList) {

            ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
            ArrayList<Long> idApontArrayList = apontMecanDAO.idApontMecanArrayList(apontMecanDAO.apontIdBolList(boletimMecanBean.getIdBolMecan()));
            apontMecanDAO.deleteApontMecan(idApontArrayList);

            boletimMecanDAO.deleteBoletimMecan(boletimMecanBean.getIdBolMecan());

        }

        boletimMMFertArrayList.clear();

    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// GET DADOS /////////////////////////////////////////////

    public EscalaTrabBean getEscalaTrab(Long idEscalaTrab){
        EscalaTrabDAO escalaTrabDAO = new EscalaTrabDAO();
        return escalaTrabDAO.getEscalaTrab(idEscalaTrab);
    }

    public ColabBean getColab(Long matricColab){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.getMatricColab(matricColab);
    }

    public ColabBean getColabApont(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.getIdColab(boletimMecanDAO.getBoletimApontando().getIdFuncBolMecan());
    }

    public ApontMecanBean getUltApontBolApontando(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        List<ApontMecanBean> apontList = apontMecanDAO.apontIdBolList(boletimMecanDAO.getBoletimApontando().getIdBolMecan());
        ApontMecanBean apontMecanBean = apontList.get(0);
        apontList.clear();
        return apontMecanBean;
    }

    public ApontMecanBean getApontBean() {
        return apontMecanBean;
    }

    public OSBean getOS(){
        OSDAO osDAO = new OSDAO();
        return osDAO.getOS(apontMecanBean.getNroOSApontMecan());
    }

    public EquipBean getEquipOS(){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getIdEquip(getOS().getIdEquip());
    }

    public ServicoBean getServico(Long idServItemOS){
        ServicoDAO servicoDAO = new ServicoDAO();
        return servicoDAO.getServico(idServItemOS);
    }

    public ComponenteBean getComponente(Long idCompItemOS){
        ComponenteDAO componenteDAO = new ComponenteDAO();
        return componenteDAO.getComponente(idCompItemOS);
    }

    public List<ApontMecanBean> apontBolApontandoList(){
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        return apontMecanDAO.apontIdBolList(boletimMecanDAO.getBoletimApontando().getIdBolMecan());
    }

    public List<ItemOSBean> itemOSList(){
        ItemOSDAO itemOSDAO = new ItemOSDAO();
        return itemOSDAO.itemOSList(getOS().getIdOS());
    }

    public List<ParadaBean> paradaList(){
        ParadaDAO paradaDAO = new ParadaDAO();
        return paradaDAO.paradaList();
    }

    public ParadaBean getParadaCod(Long codParada){
        ParadaDAO paradaDAO = new ParadaDAO();
        return paradaDAO.getParadaCod(codParada);
    }

    public ParadaBean getParadaId(Long idParada){
        ParadaDAO paradaDAO = new ParadaDAO();
        return paradaDAO.getParadaId(idParada);
    }

    public ParametroBean getParametro(){
        ParametroDAO parametroDAO = new ParametroDAO();
        return parametroDAO.getParametro();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// SET DADOS /////////////////////////////////////////////

    public void setApontBean(ApontMecanBean apontMecanBean) {
        this.apontMecanBean = apontMecanBean;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////// VERIFICAÇÃO E ATUALIZAÇÃO DE DADOS POR SERVIDOR /////////////////////

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        OSDAO osDAO = new OSDAO();
        osDAO.verOS(dado, telaAtual, telaProx, progressDialog);
    }

    public void atualDadosColab(Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ArrayList colabArrayList = new ArrayList();
        colabArrayList.add("ColabBean");
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, colabArrayList);
    }

    public void atualDadosItemOS(Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ArrayList itemOSArrayList = new ArrayList();
        itemOSArrayList.add("ComponenteBean");
        itemOSArrayList.add("ServicoBean");
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, itemOSArrayList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////// RECEBER DADOS SERVIDOR ///////////////////////////////////////

    public void recDadosOS(String result){

        try {

            if (!result.contains("exceeded")) {

                String[] retorno = result.split("_");

                JSONObject jObj = new JSONObject(retorno[0]);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    OSDAO osDAO = new OSDAO();
                    osDAO.deleteAllOS();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osDAO.insertOS(gson.fromJson(objeto.toString(), OSBean.class));
                    }

                    jObj = new JSONObject(retorno[1]);
                    jsonArray = jObj.getJSONArray("dados");

                    ItemOSDAO itemOSDAO = new ItemOSDAO();
                    itemOSDAO.deleteAllItemOS();

                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject objeto = jsonArray.getJSONObject(j);
                        Gson gson = new Gson();
                        itemOSDAO.insertItemOS(gson.fromJson(objeto.toString(), ItemOSBean.class));

                    }

                    VerifDadosServ.getInstance().pulaTelaComTerm();

                } else {
                    VerifDadosServ.getInstance().msgComTerm("NÃO EXISTE O.S. PARA ESSE COLABORADOR! POR FAVOR, ENTRE EM CONTATO COM A AREA QUE CRIA O.S. PARA APONTAMENTO.");
                }

            } else {
                VerifDadosServ.getInstance().msgComTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().msgComTerm("FALHA DE PESQUISA DE OS! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

    public void updateBoletim(String result, String activity) {

        try {

            String[] retorno = result.split("_");

            JSONObject boletimJsonObject = new JSONObject(retorno[1]);
            JSONArray boletimJsonArray = boletimJsonObject.getJSONArray("boletim");

            JSONObject apontJsonObject = new JSONObject(retorno[2]);
            JSONArray apontJsonArray = apontJsonObject.getJSONArray("apont");

            if (boletimJsonArray.length() > 0) {

                for (int i = 0; i < apontJsonArray.length(); i++) {

                    JSONObject objeto = apontJsonArray.getJSONObject(i);
                    Gson gson = new Gson();

                    ApontMecanBean apontMecanBean = gson.fromJson(objeto.toString(), ApontMecanBean.class);

                    ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
                    apontMecanDAO.updApontEnviado(apontMecanBean.getIdApontMecan(), apontMecanBean.getIdBolApontMecan());

                }

                for (int i = 0; i < boletimJsonArray.length(); i++) {

                    JSONObject objeto = boletimJsonArray.getJSONObject(i);
                    Gson gson = new Gson();

                    BoletimMecanBean boletimMecanBean = gson.fromJson(objeto.toString(), BoletimMecanBean.class);

                    BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
                    if(boletimMecanDAO.getBoletimListIdBol(boletimMecanBean.getIdBolMecan()).getStatusBolMecan() == 1L){
                        boletimMecanDAO.atualIdExtBol(boletimMecanBean);
                    }
                    else{
                        boletimMecanDAO.updateEnviadoBoletim(boletimMecanBean);
                    }
                }

            }

            EnvioDadosServ.getInstance().envioDados(activity);

        } catch (Exception e) {
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////// ENVIO DADOS SERVIDOR ///////////////////////////////////////

    public String dadosEnvioBolFechado() {

        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        String dadosBoletim = boletimMecanDAO.dadosBolFechado();

        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        String dadosApont = apontMecanDAO.dadosEnvioApont(boletimMecanDAO.idBolFechadoList());

        return dadosBoletim + "_" + dadosApont;

    }

    public String dadosEnvioBolSemEnvio() {

        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();

        String dadosBoletim = boletimMecanDAO.dadosBoletimApontSemEnvio(apontMecanDAO.idBolApontSemEnvioList());
        String dadosApont = apontMecanDAO.dadosEnvioApont(apontMecanDAO.idBolApontSemEnvioList());

        return dadosBoletim + "_" + dadosApont;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}
