package br.com.usinasantafe.pbm.control;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.PneuBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.TipoManutBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimMecanBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.dao.ApontMecanDAO;
import br.com.usinasantafe.pbm.model.dao.BoletimMecanDAO;
import br.com.usinasantafe.pbm.model.dao.BoletimPneuDAO;
import br.com.usinasantafe.pbm.model.dao.EquipDAO;
import br.com.usinasantafe.pbm.model.dao.ItemCalibPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemManutPneuDAO;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;
import br.com.usinasantafe.pbm.model.dao.PneuDAO;
import br.com.usinasantafe.pbm.model.dao.REquipPneuDAO;
import br.com.usinasantafe.pbm.model.dao.TipoManutDAO;
import br.com.usinasantafe.pbm.util.AtualDadosServ;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class PneuCTR {

    private ItemManutPneuBean itemManutPneuBean;
    private ItemCalibPneuBean itemCalibPneuBean;

    public PneuCTR() {
    }

    ////////////////////////////////////// VER DADOS /////////////////////////////////////////////

    public boolean verPneu(String codPneu){
        PneuDAO pneuDAO = new PneuDAO();
        return pneuDAO.verPneu(codPneu);
    }

    public boolean verPneuItemCalib(String nroPneu){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        return itemCalibPneuDAO.verPneuItemCalib(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu(), nroPneu);
    }

    public boolean verifFinalItemPneuBoletimCalibAberto(){
        EquipDAO equipDAO = new EquipDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        return (equipDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getIdEquipBolPneu()).size() == itemCalibPneuDAO.itemCalibPneuIdBolList(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu()).size());
    }

    public boolean verBoletimPneuFechado(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        return boletimPneuDAO.verBoletimPneuFechado();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////// INSERIR, ATUALIZAR E EXCLUIR DADOS /////////////////////////////////

    public void salvarItemCalibPneu(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        itemCalibPneuDAO.insertItemCalibPneu(itemCalibPneuBean, boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
    }

    public void salvarItemManutPneu(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        itemManutPneuDAO.insertItemManutPneu(itemManutPneuBean, boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
    }

    public void salvarBoletim(Long idEquip){
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        boletimPneuDAO.salvarBoletimPneu(idEquip, mecanicoCTR.getColabApont().getMatricColab());
    }

    public void deletePneuAberto(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        itemCalibPneuDAO.deleteItemMedPneuIdBol(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        itemManutPneuDAO.deleteItemManutPneuIdBol(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
        boletimPneuDAO.deleteBoletimPneuAberto();
    }

    public void fecharBoletimPneu(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        boletimPneuDAO.fecharBoletimPneu();
    }

    public void deleteBoletimAberto() {

        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();

        List<BoletimPneuBean> boletimList = boletimPneuDAO.boletimPneuAbertoList();

        for(BoletimPneuBean boletimPneuBean : boletimList){

            ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
            ArrayList<Long> idItemCalibPneuArrayList = itemCalibPneuDAO.idItemCalibPneuArrayList(itemCalibPneuDAO.itemCalibPneuIdBolList(boletimPneuBean.getIdBolPneu()));
            itemCalibPneuDAO.deleteItemCalibPneu(idItemCalibPneuArrayList);

            ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
            ArrayList<Long> idItemManutPneuArrayList = itemManutPneuDAO.idItemManutPneuArrayList(itemManutPneuDAO.itemManutPneuList(boletimPneuBean.getIdBolPneu()));
            itemManutPneuDAO.deleteItemManutPneu(idItemManutPneuArrayList);

            boletimPneuDAO.deleteBoletimMecan(boletimPneuBean.getIdBolPneu());

        }

    }

    public void deleteBoletimEnviado(){

        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ArrayList<BoletimPneuBean> boletimPneuArrayList = boletimPneuDAO.boletimExcluirArrayList();

        for (BoletimPneuBean boletimPneuBean : boletimPneuArrayList) {

            ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
            ArrayList<Long> idItemCalibPneuArrayList = itemCalibPneuDAO.idItemCalibPneuArrayList(itemCalibPneuDAO.itemCalibPneuIdBolList(boletimPneuBean.getIdBolPneu()));
            itemCalibPneuDAO.deleteItemCalibPneu(idItemCalibPneuArrayList);

            ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
            ArrayList<Long> idItemManutPneuArrayList = itemManutPneuDAO.idItemManutPneuArrayList(itemManutPneuDAO.itemManutPneuList(boletimPneuBean.getIdBolPneu()));
            itemManutPneuDAO.deleteItemManutPneu(idItemManutPneuArrayList);

            boletimPneuDAO.deleteBoletimMecan(boletimPneuBean.getIdBolPneu());

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// GET DADOS /////////////////////////////////////////////

    public ArrayList<REquipPneuBean> rEquipPneuCalibList(){

        ArrayList<REquipPneuBean> rEquipPneuArrayList = new ArrayList();
        EquipDAO equipDAO = new EquipDAO();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();

        List<REquipPneuBean> rEquipPneuList = equipDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getIdEquipBolPneu());
        for (REquipPneuBean rEquipPneuBean : rEquipPneuList){
            if(!itemCalibPneuDAO.verItemMedPneuIdBolIdPosConf(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu()
                    , rEquipPneuBean.getIdPosConfPneu())){
                rEquipPneuBean.setStatusPneu(1L);
                rEquipPneuArrayList.add(rEquipPneuBean);
            }
        }

        for (REquipPneuBean rEquipPneuBean : rEquipPneuList){
            if(itemCalibPneuDAO.verItemMedPneuIdBolIdPosConf(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu()
                    , rEquipPneuBean.getIdPosConfPneu())){
                rEquipPneuBean.setStatusPneu(2L);
                rEquipPneuArrayList.add(rEquipPneuBean);
            }
        }

        return rEquipPneuArrayList;
    }

    public ArrayList<REquipPneuBean> rEquipPneuManutList(){

        ArrayList<REquipPneuBean> rEquipPneuArrayList = new ArrayList();
        EquipDAO equipDAO = new EquipDAO();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();

        List<REquipPneuBean> rEquipPneuList = equipDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getIdEquipBolPneu());
        for (REquipPneuBean rEquipPneuBean : rEquipPneuList){
            if(!itemManutPneuDAO.verItemManutPneuIdBolIdPosConf(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu()
                    , rEquipPneuBean.getIdPosConfPneu())){
                rEquipPneuBean.setStatusPneu(1L);
                rEquipPneuArrayList.add(rEquipPneuBean);
            }
        }

        for (REquipPneuBean rEquipPneuBean : rEquipPneuList){
            if(itemManutPneuDAO.verItemManutPneuIdBolIdPosConf(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu()
                    , rEquipPneuBean.getIdPosConfPneu())){
                rEquipPneuBean.setStatusPneu(2L);
                rEquipPneuArrayList.add(rEquipPneuBean);
            }
        }

        return rEquipPneuArrayList;

    }

    public ItemManutPneuBean getItemManutPneuBean() {
        return itemManutPneuBean;
    }

    public ItemCalibPneuBean getItemCalibPneuBean() {
        return itemCalibPneuBean;
    }

    public REquipPneuBean getREquipPneu(Long posPneu){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        REquipPneuDAO rEquipPneuDAO = new REquipPneuDAO();
        return rEquipPneuDAO.getREquipPneu(boletimPneuDAO.getBoletimPneuAberto().getIdEquipBolPneu(), posPneu);
    }

    public List<TipoManutBean> tipoManutList(){
        TipoManutDAO tipoManutDAO = new TipoManutDAO();
        return tipoManutDAO.tipoManutList();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// SET DADOS /////////////////////////////////////////////

    public void setItemManutPneuBean(ItemManutPneuBean itemManutPneuBean) {
        this.itemManutPneuBean = itemManutPneuBean;
    }

    public void setItemCalibPneuBean(ItemCalibPneuBean itemCalibPneuBean) {
        this.itemCalibPneuBean = itemCalibPneuBean;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////// VERIFICAÇÃO E ATUALIZAÇÃO DE DADOS POR SERVIDOR /////////////////////

    public void verPneu(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, boolean finalManutPneu){
        PneuDAO pneuDAO = new PneuDAO();
        pneuDAO.verPneu(dado, telaAtual, telaProx, progressDialog, finalManutPneu);
    }

    public void atualDadosTipoManut(Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ArrayList itemOSArrayList = new ArrayList();
        itemOSArrayList.add("TipoManutBean");
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, itemOSArrayList);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////// RECEBER DADOS SERVIDOR //////////////////////////////////////

    public void recDadosPneu(String result, boolean finalManutPneu){

        try {

            if (!result.contains("exceeded")) {

                JSONObject jObj = new JSONObject(result);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    PneuDAO pneuDAO = new PneuDAO();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        pneuDAO.insertPneu(gson.fromJson(objeto.toString(), PneuBean.class));

                    }

                    if(finalManutPneu){
                        salvarItemManutPneu();
                    }

                    VerifDadosServ.getInstance().pulaTelaComTerm();

                }
                else {
                    VerifDadosServ.getInstance().msgComTerm("PNEU INEXISTENTE NA BASE DE DADOS! FAVOR VERIFICA A NUMERAÇÃO.");
                }

            }
            else {
                VerifDadosServ.getInstance().msgComTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().msgComTerm("FALHA DE PESQUISA DE PNEU! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

    public void updateBolEnviado(String result) {

        try {

            String[] retorno = result.split("_");

            JSONObject jObj = new JSONObject(retorno[1]);
            JSONArray jsonArray = jObj.getJSONArray("boletimpneu");

            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Gson gson = new Gson();

                    BoletimPneuBean boletimPneuBean = gson.fromJson(objeto.toString(), BoletimPneuBean.class);

                    BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
                    boletimPneuDAO.updateEnviadoBoletimPneu(boletimPneuBean.getIdBolPneu());

                }

            }

        } catch (Exception e) {
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////// SALVAR DADOS SERVIDOR //////////////////////////////////////

    public String dadosEnvioBolPneuFechado() {

        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        String dadosBolPneu = boletimPneuDAO.dadosBolPneuFechado();

        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        String dadosItemCalibPneu = itemCalibPneuDAO.dadosItemCalibPneu(boletimPneuDAO.idBolPneuFechado());

        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        String dadosItemManutPneu = itemManutPneuDAO.dadosItemManutPneu(boletimPneuDAO.idBolPneuFechado());

        return dadosBolPneu + "_" + dadosItemCalibPneu + "_" + dadosItemManutPneu;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

}
