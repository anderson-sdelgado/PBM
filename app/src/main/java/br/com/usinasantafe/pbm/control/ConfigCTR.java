package br.com.usinasantafe.pbm.control;

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
        return equipDAO.getIdEquip(getConfig().getEquipConfig());
    }

    public EquipBean getEquip(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getNroEquip(nroEquip);
    }

    public boolean verNroEquip(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.verNroEquip(nroEquip);
    }

    public void insertConfig(Long idEquip, String senha){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.insert(idEquip, senha);
    }

    public void setDifDthrConfig(Long difDthr){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setDifDthrConfig(difDthr);
    }

    public Long getStatusRetVerif(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getStatusRetVerif();
    }

    public void deleteLogs(){
        LogProcessoDAO logProcessoDAO = new LogProcessoDAO();
        LogErroDAO logErroDAO = new LogErroDAO();
        logProcessoDAO.deleteLogProcesso();
        logErroDAO.deleteLogErro();
    }

    public void verAtualAplic(String versaoAplic, TelaInicialActivity telaInicialActivity, String activity) {
        AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
        LogProcessoDAO.getInstance().insertLogProcesso("VerifDadosServ.getInstance().verifAtualAplic(atualAplicDAO.dadosVerAtualAplicBean(equipBean.getNroEquip(), equipBean.getIdCheckList(), versaoAplic)\n" +
                "                , telaInicialActivity, progressDialog);", activity);
        VerifDadosServ.getInstance().verifAtualAplic(atualAplicDAO.dadosVerAtualAplicBean(getConfig().getEquipConfig(), versaoAplic)
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

}
