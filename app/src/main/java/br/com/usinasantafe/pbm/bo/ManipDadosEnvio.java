package br.com.usinasantafe.pbm.bo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pbm.conWEB.ConHttpPostCadGenerico;
import br.com.usinasantafe.pbm.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.variaveis.ApontamentoTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class ManipDadosEnvio {

    private static ManipDadosEnvio instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private int statusEnvio; //1 - Enviando; 2 - Existe Dados para Enviar; 3 - Todos os Dados Enviados
    private boolean enviando = false;

    public ManipDadosEnvio() {
        // TODO Auto-generated constructor stub
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static ManipDadosEnvio getInstance() {
        if (instance == null) {
            instance = new ManipDadosEnvio();
        }
        return instance;
    }

    //////////////////////// SALVAR DADOS ////////////////////////////////////////////

    public void salvaBoletimAberto(BoletimTO boletimTO) {

        boletimTO.setDthrInicialBoletim(Tempo.getInstance().datahora());
        boletimTO.setIdExtBoletim(0L);
        boletimTO.setStatusBoletim(1L);
        boletimTO.insert();

    }

    public void salvaBoletimFechado() {

        BoletimTO boletimTO = new BoletimTO();
        List listBoletim = boletimTO.get("statusBoletim", 1L);
        boletimTO = (BoletimTO) listBoletim.get(0);
        listBoletim.clear();

        boletimTO.setDthrFinalBoletim(Tempo.getInstance().datahora());
        boletimTO.setStatusBoletim(2L);
        boletimTO.update();

    }

    public void salvaApontamento(ApontamentoTO apontamentoTO) {

        String datahora = Tempo.getInstance().datahora();
        apontamentoTO.setDthrInicialAponta(datahora);

        BoletimTO boletimMMTO = new BoletimTO();
        List lBol = boletimMMTO.get("statusBoletim", 1L);
        boletimMMTO = (BoletimTO) lBol.get(0);
        lBol.clear();

        apontamentoTO.setIdBolAponta(boletimMMTO.getIdBoletim());
        apontamentoTO.setIdExtBolAponta(boletimMMTO.getIdExtBoletim());
        apontamentoTO.insert();

    }

    //////////////////////// ENVIAR DADOS ////////////////////////////////////////////

    public void enviarBolAberto() {

        BoletimTO boletimTO = new BoletimTO();
        List listBoletim = boletinsAbertoSemEnvio();

        JsonArray jsonArrayBoletim = new JsonArray();
        JsonArray jsonArrayAponta = new JsonArray();

        for (int i = 0; i < listBoletim.size(); i++) {

            boletimTO = (BoletimTO) listBoletim.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayBoletim.add(gsonCabec.toJsonTree(boletimTO, boletimTO.getClass()));

            ApontamentoTO apontamentoTO = new ApontamentoTO();
            List apontaList = apontamentoTO.get("idBolAponta", boletimTO.getIdBoletim());

            for (int j = 0; j < apontaList.size(); j++) {

                apontamentoTO = (ApontamentoTO) apontaList.get(j);
                if (apontamentoTO.getStatusAponta() != 2L) {
                    Gson gsonItem = new Gson();
                    jsonArrayAponta.add(gsonItem.toJsonTree(apontamentoTO, apontamentoTO.getClass()));

                }

            }

        }

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("boletim", jsonArrayBoletim);

        JsonObject jsonAponta = new JsonObject();
        jsonAponta.add("aponta", jsonArrayAponta);

        String dados = jsonBoletim.toString() + "_" + jsonAponta.toString();

        Log.i("PMM", "ABERTO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolAberto()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        ConHttpPostCadGenerico conHttpPostGenerico = new ConHttpPostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void enviarBolFechado() {

        BoletimTO boletimTO = new BoletimTO();
        List listBoletim = boletinsFechado();

        JsonArray jsonArrayBoletim = new JsonArray();
        JsonArray jsonArrayAponta = new JsonArray();

        for (int i = 0; i < listBoletim.size(); i++) {

            boletimTO = (BoletimTO) listBoletim.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayBoletim.add(gsonCabec.toJsonTree(boletimTO, boletimTO.getClass()));

            ApontamentoTO apontamentoTO = new ApontamentoTO();
            List apontaList = apontamentoTO.get("idBolAponta", boletimTO.getIdBoletim());

            for (int j = 0; j < apontaList.size(); j++) {

                apontamentoTO = (ApontamentoTO) apontaList.get(j);
                Gson gsonItem = new Gson();
                jsonArrayAponta.add(gsonItem.toJsonTree(apontamentoTO, apontamentoTO.getClass()));

            }

        }

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("boletim", jsonArrayBoletim);

        JsonObject jsonAponta = new JsonObject();
        jsonAponta.add("aponta", jsonArrayAponta);

        String dados = jsonBoletim.toString() + "_" + jsonAponta.toString();

        Log.i("PMM", "FECHADO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolFechado()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        ConHttpPostCadGenerico conHttpPostGenerico = new ConHttpPostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void envioApontamento() {

        JsonArray jsonArrayAponta = new JsonArray();

        ApontamentoTO apontamentoTO = new ApontamentoTO();
        List apontaList = apontamentoTO.all();

        for (int i = 0; i < apontaList.size(); i++) {
            apontamentoTO = (ApontamentoTO) apontaList.get(i);
            Gson gson = new Gson();
            jsonArrayAponta.add(gson.toJsonTree(apontamentoTO, apontamentoTO.getClass()));
        }

        JsonObject jsonAponta = new JsonObject();
        jsonAponta.add("aponta", jsonArrayAponta);

        String dados = jsonAponta.toString();

        Log.i("PMM", "APONTAMENTO = " + dados);

        String[] url = {urlsConexaoHttp.getsInsertApont()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        ConHttpPostCadGenerico conHttpPostCadGenerico = new ConHttpPostCadGenerico();
        conHttpPostCadGenerico.setParametrosPost(parametrosPost);
        conHttpPostCadGenerico.execute(url);

    }

    /////////////////////////////// DELETAR DADOS ///////////////////////////////////////////////

    public void atualDelBoletimMM(String retorno) {

        try {

            int pos1 = retorno.indexOf("=") + 1;
            int pos2 = retorno.indexOf("_") + 1;
            String id = retorno.substring(pos1, (pos2 - 1));

            BoletimTO boletimTO = new BoletimTO();
            List listBoletim = boletimTO.get("statusBoletim", 1L);
            boletimTO = (BoletimTO) listBoletim.get(0);
            boletimTO.setIdExtBoletim(Long.parseLong(id.trim()));
            boletimTO.update();

            ApontamentoTO apontamentoTO = new ApontamentoTO();
            List apontaList = apontamentoTO.get("idBolAponta", boletimTO.getIdBoletim());

            for (int j = 0; j < apontaList.size(); j++) {

                apontamentoTO = (ApontamentoTO) apontaList.get(j);

                if(apontamentoTO.getStatusAponta() == 1L){
                    apontamentoTO.setStatusAponta(2L);
                    apontamentoTO.update();
                }
                else if(apontamentoTO.getStatusAponta() == 3L){
                    apontamentoTO.delete();
                }

            }

        } catch (Exception e) {
            Tempo.getInstance().setEnvioDado(true);
        }

    }

    public void delBolFechado() {

        BoletimTO boletimMMTO = new BoletimTO();
        List listBoletim = boletimMMTO.get("statusBoletim", 2L);
        ArrayList<Long> rLista = new ArrayList<Long>();

        for (int i = 0; i < listBoletim.size(); i++) {
            boletimMMTO = (BoletimTO) listBoletim.get(i);
            rLista.add(boletimMMTO.getIdBoletim());
        }

        ApontamentoTO apontamentoTO = new ApontamentoTO();
        List apontaList = apontamentoTO.in("idBolAponta", rLista);

        for (int j = 0; j < apontaList.size(); j++) {
            apontamentoTO = (ApontamentoTO) apontaList.get(j);
            apontamentoTO.delete();
        }

        for (int i = 0; i < listBoletim.size(); i++) {
            boletimMMTO = (BoletimTO) listBoletim.get(i);
            boletimMMTO.delete();
        }

    }

    public void delApontamento() {

        ApontamentoTO apontamentoTO = new ApontamentoTO();
        List apontaList = apontamentoTO.all();

        for (int j = 0; j < apontaList.size(); j++) {
            apontamentoTO = (ApontamentoTO) apontaList.get(j);
            apontamentoTO.delete();
        }

    }


    //////////////////////////TRAZER DADOS////////////////////////////

    public List boletinsAbertoSemEnvio() {

        BoletimTO boletimTO = new BoletimTO();
        ArrayList listaPesq = new ArrayList();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusBoletim");
        pesquisa.setValor(1L);
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("idExtBoletim");
        pesquisa2.setValor(0L);
        listaPesq.add(pesquisa2);

        return boletimTO.get(listaPesq);

    }

    public List boletinsFechado() {
        BoletimTO boletimMMTO = new BoletimTO();
        return boletimMMTO.get("statusBoletim", 2L);
    }

    public List apontamento() {
        ApontamentoTO apontamentoTO = new ApontamentoTO();
        return apontamentoTO.dif("statusAponta", 2L);
    }


    //////////////////////VERIFICAÇÃO DE DADOS///////////////////////////

    public Boolean verifBolAbertoSemEnvio() {
        return boletinsAbertoSemEnvio().size() > 0;
    }

    public Boolean verifBolFechado() {
        return boletinsFechado().size() > 0;
    }

    public Boolean verifAponta() {
        return apontamento().size() > 0;
    }

    /////////////////////////MECANISMO DE ENVIO//////////////////////////////////

    public void envioDados(Context context) {
        enviando = true;
        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if (conexaoWeb.verificaConexao(context)) {
            envioDadosPrinc();
        }
        else{
            enviando = false;
        }

    }

    public void envioDadosPrinc() {
        if (verifBolFechado()) {
            enviarBolFechado();
        } else {
            if (verifBolAbertoSemEnvio()) {
                enviarBolAberto();
            } else {
                if (verifAponta()) {
                    envioApontamento();
                }
            }
        }
    }

    public boolean verifDadosEnvio() {
        if ((!verifBolFechado())
                && (!verifBolAbertoSemEnvio())
                && (!verifAponta())) {
            enviando = false;
            return false;
        } else {
            return true;
        }
    }

    public int getStatusEnvio() {
        if (enviando) {
            statusEnvio = 1;
        } else {
            if (!verifDadosEnvio()) {
                statusEnvio = 3;
            } else {
                statusEnvio = 2;
            }
        }
        return statusEnvio;
    }

    public void setEnviando(boolean enviando) {
        this.enviando = enviando;
    }

    public void setStatusEnvio(int statusEnvio) {
        this.statusEnvio = statusEnvio;
    }


}
