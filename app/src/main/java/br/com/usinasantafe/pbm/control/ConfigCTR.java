package br.com.usinasantafe.pbm.control;

import br.com.usinasantafe.pbm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pbm.model.dao.ConfigDAO;
import br.com.usinasantafe.pbm.model.dao.EquipDAO;

public class ConfigCTR {

    public ConfigCTR() {
    }

    public boolean hasElements(){
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

}
