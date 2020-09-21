package br.com.usinasantafe.pbm.control;

import br.com.usinasantafe.pbm.model.dao.ColabDAO;
import br.com.usinasantafe.pbm.model.dao.ParametroDAO;

public class MecanicoCTR {

    public MecanicoCTR() {
    }

    ///////////////////////////////// VERIFICAR DADOS ////////////////////////////////////////////

    public boolean hasElementsColab(){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.hasElements();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////// SALVAR DADOS /////////////////////////////////////////////

    public void insertParametro(Long minutos){
        ParametroDAO parametroDAO = new ParametroDAO();
        parametroDAO.insert(minutos);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

}
