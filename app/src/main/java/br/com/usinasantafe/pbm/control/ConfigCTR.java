package br.com.usinasantafe.pbm.control;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.LogErroBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pbm.model.dao.ApontMecanDAO;
import br.com.usinasantafe.pbm.model.dao.AtualAplicDAO;
import br.com.usinasantafe.pbm.model.dao.BoletimMecanDAO;
import br.com.usinasantafe.pbm.model.dao.BoletimPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ConfigDAO;
import br.com.usinasantafe.pbm.model.dao.EquipDAO;
import br.com.usinasantafe.pbm.model.dao.ItemCalibPneuDAO;
import br.com.usinasantafe.pbm.model.dao.ItemManutPneuDAO;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.AtualDadosServ;
import br.com.usinasantafe.pbm.util.Json;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.view.TelaInicialActivity;

public class ConfigCTR {

    public ConfigCTR() {
    }

    public boolean hasElemConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.hasElements();
    }

    public boolean verConfig(String senha){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.verConfig(senha);
    }

    public ConfigBean getConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getConfig();
    }

    public EquipBean getEquip(){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getIdEquip(getConfig().getIdEquipConfig());
    }

    public EquipBean getEquip(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getNroEquip(nroEquip);
    }

    public boolean verNroEquip(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.verNroEquip(nroEquip);
    }

    public void salvarConfigInicial(String senha, Long idEquip){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.insert(idEquip, senha);
    }

    public Long getStatusRetVerif(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getStatusRetVerif();
    }

    public void verEquipConfig(String senha, String versao, String nroEquip, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        EquipDAO equipDAO = new EquipDAO();
        LogProcessoDAO.getInstance().insertLogProcesso("equipDAO.verEquip(equipDAO.dadosVerEquip(Long.parseLong(nroEquip), versao), telaAtual, telaProx, progressDialog, activity, tipo);", activity);
        equipDAO.verEquip(senha, equipDAO.dadosVerEquip(Long.parseLong(nroEquip), versao), telaAtual, telaProx, progressDialog, activity);
    }

    public void verAtualAplic(String versaoAplic, TelaInicialActivity telaInicialActivity, String activity) {
        AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
        LogProcessoDAO.getInstance().insertLogProcesso("VerifDadosServ.getInstance().verifAtualAplic(atualAplicDAO.dadosVerAtualAplicBean(equipBean.getNroEquip(), equipBean.getIdCheckList(), versaoAplic)\n" +
                "                , telaInicialActivity, progressDialog);", activity);
        VerifDadosServ.getInstance().verifAtualAplic(atualAplicDAO.dadosVerAtualAplicBean(getConfig().getIdEquipConfig(), versaoAplic)
                , telaInicialActivity, activity);
    }

    public ArrayList<String> logBaseDadoList(){
        ArrayList<String> dadosArrayList = new ArrayList<>();
        BoletimMecanDAO boletimMecanDAO = new BoletimMecanDAO();
        ApontMecanDAO apontMecanDAO = new ApontMecanDAO();
        BoletimPneuDAO boletimPneuDAO = new BoletimPneuDAO();
        ItemCalibPneuDAO itemCalibPneuDAO = new ItemCalibPneuDAO();
        ItemManutPneuDAO itemManutPneuDAO = new ItemManutPneuDAO();
        dadosArrayList = boletimMecanDAO.boletimMecanAllArrayList(dadosArrayList);
        dadosArrayList = apontMecanDAO.apontMecanAllArrayList(dadosArrayList);
        dadosArrayList = boletimPneuDAO.boletimPneuAllArrayList(dadosArrayList);
        dadosArrayList = itemCalibPneuDAO.itemCalibPneuAllArrayList(dadosArrayList);
        dadosArrayList = itemManutPneuDAO.itemManutPneuAllArrayList(dadosArrayList);
        return dadosArrayList;
    }

    public List<LogErroBean> logErroList(){
        LogErroDAO logErroDAO = new LogErroDAO();
        return logErroDAO.logErroBeanList();
    }

    public List<LogProcessoBean> logProcessoList(){
        LogProcessoDAO logProcessoDAO = new LogProcessoDAO();
        return logProcessoDAO.logProcessoList();
    }

    public void deleteLogs(){
        LogProcessoDAO logProcessoDAO = new LogProcessoDAO();
        LogErroDAO logErroDAO = new LogErroDAO();
        logProcessoDAO.deleteLogProcesso();
        logErroDAO.deleteLogErro();
    }


    public void receberVerifEquip(String senha, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String result){

        try {

            if (!result.contains("exceeded")) {

                String[] retorno = result.split("_");

                Json json = new Json();
                JSONArray jsonArray = json.jsonArray(retorno[0]);

                if (jsonArray.length() > 0) {

                    EquipDAO equipDAO = new EquipDAO();
                    EquipBean equipBean = equipDAO.recDadosEquip(jsonArray);

                    salvarConfigInicial(senha, equipBean.getIdEquip());
                    progressDialog.dismiss();
                    progressDialog = new ProgressDialog(telaAtual);
                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("ATUALIZANDO ...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setProgress(0);
                    progressDialog.setMax(100);
                    progressDialog.show();

                    AtualDadosServ.getInstance().atualizarBD(telaAtual, telaProx, progressDialog);

                } else {
                    VerifDadosServ.getInstance().msgComTerm("EQUIPAMENTO INEXISTENTE NA BASE DE DADOS! FAVOR VERIFICA A NUMERAÇÃO.");
                }

            } else {
                VerifDadosServ.getInstance().msgComTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");
            }

        } catch (Exception e) {
            LogErroDAO.getInstance().insertLogErro(e);
            VerifDadosServ.getInstance().msgComTerm("FALHA DE PESQUISA DE EQUIPAMENTO! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

}
