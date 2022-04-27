package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class LogProcessoDAO {

    private static LogProcessoDAO instance = null;

    public static LogProcessoDAO getInstance() {
        if (instance == null)
            instance = new LogProcessoDAO();
        return instance;
    }

    public void insertLogProcesso(String processo, String activity){
        LogProcessoBean logProcessoBean = new LogProcessoBean();
        logProcessoBean.setProcesso(processo);
        logProcessoBean.setActivity(activity);
        logProcessoBean.setDthr(Tempo.getInstance().dthrAtualString());
        logProcessoBean.setDthrLong(Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dthrAtualString()));
        logProcessoBean.insert();
    }

    public List<LogProcessoBean> logProcessoList(){
        LogProcessoBean logProcessoBean = new LogProcessoBean();
        return logProcessoBean.orderBy("idLogProcesso", false);
    }

    public void deleteLogProcesso(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqDtrhLongDiaMenos(Tempo.getInstance().subDiaLong(3)));
        LogProcessoBean logProcessoBean = new LogProcessoBean();
        logProcessoBean.deleteGet(pesqArrayList);
    }

    private EspecificaPesquisa getPesqDtrhLongDiaMenos(Long dtrhLongDiaMenos){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("dthrLong");
        pesquisa.setValor(dtrhLongDiaMenos);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
