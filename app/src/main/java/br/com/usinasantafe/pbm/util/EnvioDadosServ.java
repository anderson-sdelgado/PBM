package br.com.usinasantafe.pbm.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.control.PneuCTR;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.view.ActivityGeneric;

public class EnvioDadosServ {

    private static EnvioDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    public static int status; //1 - Existe Dados para Enviar; 2 - Enviado; 3 - Todos os Dados Foram Enviados;

    public EnvioDadosServ() {
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static EnvioDadosServ getInstance() {
        if (instance == null) {
            instance = new EnvioDadosServ();
        }
        return instance;
    }

    /////////////////////////////////// ENVIAR DADOS //////////////////////////////////////////////

    public void enviarBoletimMecan(String activity) {

        MecanicoCTR mecanicoCTR = new MecanicoCTR();

        LogProcessoDAO.getInstance().insertLogProcesso("mecanicoCTR.dadosEnvioBolSemEnvio()", activity);
        envio(urlsConexaoHttp.getsInsertBoletimMecan(), mecanicoCTR.dadosEnvioBolSemEnvio(), activity);

    }

    public void enviarBoletimPneu(String activity) {

        PneuCTR pneuCTR = new PneuCTR();

        LogProcessoDAO.getInstance().insertLogProcesso("pneuCTR.dadosEnvioBolPneuFechado()", activity);
        envio(urlsConexaoHttp.getsInsertBoletimPneu(), pneuCTR.dadosEnvioBolPneuFechado(), activity);

    }

    public void envio(String url, String dados, String activity){

        String[] strings = {url, activity};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        Log.i("PMM", "URL = " + url + " - Dados de Envio = " + dados);
        LogProcessoDAO.getInstance().insertLogProcesso("postCadGenerico.execute('" + url + "'); - Dados de Envio = " + dados, activity);
        PostCadGenerico postCadGenerico = new PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(strings);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////// VERIFICAR DADOS //////////////////////////////////////

    public boolean verApontSemEnvio(){
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        return mecanicoCTR.verApontSemEnvio();
    }

    public boolean verBoletimPneuFechado(){
        PneuCTR pneuCTR = new PneuCTR();
        return pneuCTR.verBoletimPneuFechado();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////// MECANISMO DE ENVIO///////////////////////////////////

    public void envioDados(String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("public void envioDados(String activity) {\n" +
                "        status = 1;", activity);
        status = 1;
        if(ActivityGeneric.connectNetwork) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(ActivityGeneric.connectNetwork) {\n" +
                    "            status = 2;", activity);
            status = 2;
            LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
            if (verApontSemEnvio()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (verApontSemEnvio()) {\n" +
                        "                    enviarBolAberto(activity);", activity);
                enviarBoletimMecan(activity);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
                if(verBoletimPneuFechado()){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(verBoletimPneuFechado()){\n" +
                            "                        enviarPneu(activity);", activity);
                    enviarBoletimPneu(activity);
                }
            }
        }
        else{
            status = 3;
        }
    }

    public boolean verifDadosEnvio() {
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        PneuCTR pneuCTR = new PneuCTR();
        if ((!mecanicoCTR.verBoletimFechado())
                && (!mecanicoCTR.verApontSemEnvio())
                && (!pneuCTR.verBoletimPneuFechado())) {
            return false;
        } else {
            return true;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////RECEBER DADOS////////////////////////////////////////////

    public void recDados(String result, String activity){
        LogProcessoDAO.getInstance().insertLogProcesso("public void recDados(String " + result + ", String activity){", activity);
        if(result.trim().startsWith("BOLETIMMECAN")){
            LogProcessoDAO.getInstance().insertLogProcesso("if(result.trim().startsWith(\"BOLFECHADOMEC\")){\n" +
                    "            MecanicoCTR mecanicoCTR = new MecanicoCTR();\n" +
                    "            mecanicoCTR.updateBolFechado(result);", activity);
            MecanicoCTR mecanicoCTR = new MecanicoCTR();
            mecanicoCTR.updateBoletim(result, activity);
        }
        else if(result.trim().startsWith("BOLETIMPNEU")){
            LogProcessoDAO.getInstance().insertLogProcesso("else if(result.trim().startsWith(\"BOLPNEU\")){\n" +
                    "            PneuCTR pneuCTR = new PneuCTR();\n" +
                    "            pneuCTR.updateBolEnviado(result);", activity);
            PneuCTR pneuCTR = new PneuCTR();
            pneuCTR.updateBolEnviado(result, activity);
        }
        else{
            LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                    "            status = 1;\n" +
                    "            LogErroDAO.getInstance().insertLogErro(result);", activity);
            status = 1;
            LogErroDAO.getInstance().insertLogErro(result);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

}
