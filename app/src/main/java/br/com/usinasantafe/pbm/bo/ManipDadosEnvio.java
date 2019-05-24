package br.com.usinasantafe.pbm.bo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pbm.conWEB.ConHttpPostCadGenerico;
import br.com.usinasantafe.pbm.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemManutPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemMedPneuTO;

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

            ApontTO apontTO = new ApontTO();
            List apontaList = apontTO.get("idBolApont", boletimTO.getIdBoletim());

            for (int j = 0; j < apontaList.size(); j++) {

                apontTO = (ApontTO) apontaList.get(j);
                if (apontTO.getStatusApont() == 0L) {
                    Gson gsonItem = new Gson();
                    jsonArrayAponta.add(gsonItem.toJsonTree(apontTO, apontTO.getClass()));

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

            ApontTO apontTO = new ApontTO();
            List apontaList = apontTO.get("idBolApont", boletimTO.getIdBoletim());

            for (int j = 0; j < apontaList.size(); j++) {

                apontTO = (ApontTO) apontaList.get(j);
                if (apontTO.getStatusApont() == 0L) {
                    Gson gsonItem = new Gson();
                    jsonArrayAponta.add(gsonItem.toJsonTree(apontTO, apontTO.getClass()));
                }

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

        ApontTO apontTO = new ApontTO();
        List apontaList = dadosApontamento();

        for (int i = 0; i < apontaList.size(); i++) {
            apontTO = (ApontTO) apontaList.get(i);
            Gson gson = new Gson();
            jsonArrayAponta.add(gson.toJsonTree(apontTO, apontTO.getClass()));
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

    public void enviarBolPneu() {

        BoletimPneuTO boletimPneuTO = new BoletimPneuTO();
        List boletimPneuList = boletinsPneu();

        JsonArray jsonArrayBolPneu = new JsonArray();
        JsonArray jsonArrayItemMedPneu = new JsonArray();
        JsonArray jsonArrayItemManutPneu = new JsonArray();

        for (int i = 0; i < boletimPneuList.size(); i++) {

            boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayBolPneu.add(gsonCabec.toJsonTree(boletimPneuTO, boletimPneuTO.getClass()));

            ItemMedPneuTO itemMedPneuTO = new ItemMedPneuTO();
            List itemMedPneuList = itemMedPneuTO.get("idBolItemMedPneu", boletimPneuTO.getIdBolPneu());

            for (int j = 0; j < itemMedPneuList.size(); j++) {
                itemMedPneuTO = (ItemMedPneuTO) itemMedPneuList.get(j);
                Gson gsonItem = new Gson();
                jsonArrayItemMedPneu.add(gsonItem.toJsonTree(itemMedPneuTO, itemMedPneuTO.getClass()));
            }

            ItemManutPneuTO itemManutPneuTO = new ItemManutPneuTO();
            List itemManutPneuList = itemManutPneuTO.get("idBolItemManutPneu", boletimPneuTO.getIdBolPneu());

            for (int m = 0; m < itemManutPneuList.size(); m++) {
                itemManutPneuTO = (ItemManutPneuTO) itemManutPneuList.get(m);
                Gson gsonItem = new Gson();
                jsonArrayItemManutPneu.add(gsonItem.toJsonTree(itemManutPneuTO, itemManutPneuTO.getClass()));
            }

        }

        JsonObject jsonBolPneu = new JsonObject();
        jsonBolPneu.add("bolpneu", jsonArrayBolPneu);

        JsonObject jsonItemMedPneu = new JsonObject();
        jsonItemMedPneu.add("itemmedpneu", jsonArrayItemMedPneu);

        JsonObject jsonItemManutPneu = new JsonObject();
        jsonItemManutPneu.add("itemmanutpneu", jsonArrayItemManutPneu);

        String dados = jsonBolPneu.toString() + "_" + jsonItemMedPneu.toString() + "|" + jsonItemManutPneu.toString();

        Log.i("PMM", "BOLETIM PNEU = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolPneu()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        ConHttpPostCadGenerico conHttpPostGenerico = new ConHttpPostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    /////////////////////////////// DELETAR DADOS ///////////////////////////////////////////////

    public void atualDadosBolAberto(String retorno) {

        try {

            int pos1 = retorno.indexOf("#") + 1;
            int pos2 = retorno.indexOf("_") + 1;
            String dados = retorno.substring(pos1, (pos2 - 1));

            JSONObject jObj = new JSONObject(dados);
            JSONArray jsonArray = jObj.getJSONArray("dados");

            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objJsonBoletim = jsonArray.getJSONObject(i);

                    BoletimTO boletimTO = new BoletimTO();
                    List boletimList = boletimTO.get("idBoletim", objJsonBoletim.getLong("idBoletim"));
                    boletimTO = (BoletimTO) boletimList.get(0);
                    boletimTO.setIdExtBoletim(objJsonBoletim.getLong("idExtBoletim"));
                    boletimTO.update();

                    ApontTO apontTO = new ApontTO();
                    List apontamentoList = apontTO.get("idBolApont", objJsonBoletim.getLong("idBoletim"));

                    for (int j = 0; j < jsonArray.length(); j++) {
                        apontTO = (ApontTO) apontamentoList.get(j);
                        apontTO.setIdExtBolApont(objJsonBoletim.getLong("idExtBoletim"));
                        apontTO.setStatusApont(1L);
                        apontTO.update();
                    }

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

        ApontTO apontTO = new ApontTO();
        List apontaList = apontTO.in("idBolApont", rLista);

        for (int i = 0; i < apontaList.size(); i++) {
            apontTO = (ApontTO) apontaList.get(i);
            apontTO.delete();
        }

        for (int i = 0; i < listBoletim.size(); i++) {
            boletimMMTO = (BoletimTO) listBoletim.get(i);
            boletimMMTO.delete();
        }

    }

    public void atualApont() {

        ApontTO apontTO = new ApontTO();
        List apontaList = apontTO.all();

        for (int i = 0; i < apontaList.size(); i++) {
            apontTO = (ApontTO) apontaList.get(i);
            apontTO.setStatusApont(1L);
            apontTO.update();
        }

    }

    public void delBolPneu() {

        BoletimPneuTO boletimPneuTO = new BoletimPneuTO();
        List boletimPneuList = boletimPneuTO.get("statusBolPneu", 2L);

        ArrayList<Long> rLista = new ArrayList<Long>();

        for (int i = 0; i < boletimPneuList.size(); i++) {
            boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(i);
            rLista.add(boletimPneuTO.getIdBolPneu());
        }

        ItemMedPneuTO itemMedPneuTO = new ItemMedPneuTO();
        List itemMedPneuList = itemMedPneuTO.in("idBolItemMedPneu", rLista);

        for (int i = 0; i < itemMedPneuList.size(); i++) {
            itemMedPneuTO = (ItemMedPneuTO) itemMedPneuList.get(i);
            itemMedPneuTO.delete();
        }

        ItemManutPneuTO itemManutPneuTO = new ItemManutPneuTO();
        List itemManutPneuList = itemManutPneuTO.in("idBolItemManutPneu", rLista);

        for (int i = 0; i < itemManutPneuList.size(); i++) {
            itemManutPneuTO = (ItemManutPneuTO) itemManutPneuList.get(i);
            itemManutPneuTO.delete();
        }

        for (int i = 0; i < boletimPneuList.size(); i++) {
            boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(i);
            boletimPneuTO.delete();
        }

    }


    //////////////////////////TRAZER DADOS////////////////////////////

    public List boletinsPneu() {
        BoletimPneuTO boletimPneuTO = new BoletimPneuTO();
        return boletimPneuTO.get("statusBolPneu", 2L);
    }

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

    public List dadosApontamento() {
        ApontTO apontTO = new ApontTO();
        return apontTO.get("statusApont", 0L);
    }


    //////////////////////VERIFICAÇÃO DE DADOS///////////////////////////

    public Boolean verifBolPneu() {
        return boletinsPneu().size() > 0;
    }

    public Boolean verifBolAbertoSemEnvio() {
        return boletinsAbertoSemEnvio().size() > 0;
    }

    public Boolean verifBolFechado() {
        return boletinsFechado().size() > 0;
    }

    public Boolean verifAponta() {

        List apontList = dadosApontamento();
        for (int i = 0; i < apontList.size(); i++) {
            ApontTO apontTO = (ApontTO) apontList.get(i);
            if(apontTO.getIdExtBolApont() == 0){
                BoletimTO boletimTO = new BoletimTO();
                List boletimList = boletimTO.get("idBoletim", apontTO.getIdBolApont());
                boletimTO = (BoletimTO) boletimList.get(0);
                boletimList.clear();
                apontTO.setIdExtBolApont(boletimTO.getIdExtBoletim());
                apontTO.update();
            }
        }

        return dadosApontamento().size() > 0;
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
                else{
                    if(verifBolPneu()){
                        enviarBolPneu();
                    }
                }
            }
        }
    }

    public boolean verifDadosEnvio() {
        if ((!verifBolFechado())
                && (!verifBolAbertoSemEnvio())
                && (!verifAponta())
                && (!verifBolPneu())) {
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

}
