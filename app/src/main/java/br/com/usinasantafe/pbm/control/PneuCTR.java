package br.com.usinasantafe.pbm.control;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.usinasantafe.pbm.model.bean.estaticas.PneuBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.dao.BoletimPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemManutPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemCalibPneuDAO;
import br.com.usinasantafe.pbm.model.dao.PneuDAO;
import br.com.usinasantafe.pbm.model.dao.REquipPneuDAO;
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

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// SET DADOS /////////////////////////////////////////////

    public void salvarItemCalibPneu(){
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        itemCalibPneuDAO.insertItemCalibPneu(itemCalibPneuBean, boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// SET DADOS /////////////////////////////////////////////

    public void salvarBoletim(Long idEquip){
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        boletimPneuDAO.salvarBoletimPneu(idEquip, mecanicoCTR.getColabApont().getMatricColab());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// LIMPAR DADOS //////////////////////////////////////////////

    public void clear(){

        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        List<BoletimPneuBean> boletimPneuList = boletimPneuDAO.boletimPneuAbertoList();

        ArrayList<Long> idBolPneuLongs = boletimPneuDAO.idBoletimPneuList(boletimPneuList);

        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        itemManutPneuDAO.delete(idBolPneuLongs);

        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        itemCalibPneuDAO.deleteItemCalibPneu(idBolPneuLongs);

        boletimPneuDAO.deleteBoletimPneu(boletimPneuList);

        idBolPneuLongs.clear();
        boletimPneuList.clear();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////// SET DADOS /////////////////////////////////////////////

    public ArrayList<String> rEquipPneuCalibList(){
        ArrayList<String> rEquipPneuCalibList = new ArrayList<>();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        REquipPneuDAO rEquipPneuDAO = new REquipPneuDAO();
        if(boletimPneuDAO.verBoletimPneuAberto()){
            List<ItemCalibPneuBean> itemMedPneuList = itemCalibPneuDAO.itemCalibPneuList(boletimPneuDAO.getBoletimPneuAberto().getIdBolPneu());
            List<REquipPneuBean> rEquipPneuList = rEquipPneuDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getEquipBolPneu());
            for(REquipPneuBean rEquipPneuBean : rEquipPneuList){
                boolean ver = true;
                for(ItemCalibPneuBean itemCalibPneuBean : itemMedPneuList) {
                    if(Objects.equals(rEquipPneuBean.getIdPosConfPneu(), itemCalibPneuBean.getPosItemCalibPneu())){
                        ver = false;
                    }
                }
                if(ver) {
                    rEquipPneuCalibList.add(rEquipPneuBean.getPosPneu());
                }
            }
        }
        else{
            List<REquipPneuBean> rEquipPneuList = rEquipPneuDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getEquipBolPneu());
            for(REquipPneuBean rEquipPneuBean : rEquipPneuList){
                rEquipPneuCalibList.add(rEquipPneuBean.getPosPneu());
            }
            rEquipPneuList.clear();
        }
        return rEquipPneuCalibList;
    }

    public ArrayList<String> rEquipPneuManutList(){
        ArrayList<String> rEquipPneuManutList = new ArrayList<>();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        REquipPneuDAO rEquipPneuDAO = new REquipPneuDAO();
        List<REquipPneuBean> rEquipPneuList = rEquipPneuDAO.rEquipPneuList(boletimPneuDAO.getBoletimPneuAberto().getEquipBolPneu());
        for(REquipPneuBean rEquipPneuBean : rEquipPneuList){
            rEquipPneuManutList.add(rEquipPneuBean.getPosPneu());
        }
        rEquipPneuList.clear();
        return rEquipPneuManutList;
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
        return rEquipPneuDAO.getREquipPneu(boletimPneuDAO.getBoletimPneuAberto().getEquipBolPneu(), posPneu);
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

    public void verPneu(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        PneuDAO pneuDAO = new PneuDAO();
        pneuDAO.verPneu(dado, telaAtual, telaProx, progressDialog);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////// RECEBER DADOS SERVIDOR //////////////////////////////////////

    public void recDadosPneu(String result){

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

    //////////////////////////////////////////////////////////////////////////////////////////////

}
