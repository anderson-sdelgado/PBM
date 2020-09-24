package br.com.usinasantafe.pbm.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.Tempo;

public class ApontDAO {

    public ApontDAO() {
    }

    public void salvarApontTrab(ApontBean apontBean, String horarioEnt, BoletimBean boletimBean){

        List<ApontBean> apontList = apontList(boletimBean.getIdBoletim());

        if(apontList.size() > 0) {

            ApontBean apontBD = (ApontBean) apontList.get(0);
            if(apontBean.getParadaApont() == 0){
                apontBD.setDthrFinalApont(Tempo.getInstance().dataHora());
                apontBD.setStatusApont(0L);
                apontBD.update();
                apontBean.setDthrInicialApont(Tempo.getInstance().dataHora());
            }
            else{
                apontBean.setDthrInicialApont(apontBD.getDthrFinalApont());
            }

        }
        else{

            apontBean.setDthrInicialApont(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + horarioEnt));

        }

        apontBean.setDthrFinalApont("");
        apontBean.setIdBolApont(boletimBean.getIdBoletim());
        apontBean.setIdExtBolApont(boletimBean.getIdExtBoletim());
        apontBean.setStatusApont(0L);
        apontBean.insert();

    }

    public List<ApontBean> apontList(Long idBol){
        ApontBean apontBean = new ApontBean();
        return apontBean.getAndOrderBy("idBolApont", idBol, "idApont", false);
    }

    public boolean verOSApont(Long idBol, Long nroOS){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdBol(idBol));
        pesqArrayList.add(getPesqOSApont(nroOS));

        ApontBean apontBean = new ApontBean();
        List<ApontBean> apontList = apontBean.get(pesqArrayList);
        boolean ret = (apontList.size() > 0);
        apontList.clear();

        return ret;

    }


    public void fecharApont(ApontBean apontBean){
        if(apontBean.getParadaApont() == 0L){
            apontBean.setDthrFinalApont(Tempo.getInstance().dataHora());
            apontBean.setStatusApont(0L);
            apontBean.update();
        }
    }

    private EspecificaPesquisa getPesqIdBol(Long idBol){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBolApont");
        pesquisa.setValor(idBol);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOSApont(Long nroOS){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("osApont");
        pesquisa.setValor(nroOS);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
