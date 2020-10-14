package br.com.usinasantafe.pbm.util;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.control.PneuCTR;
import br.com.usinasantafe.pbm.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp;

public class EnvioDadosServ {

    private static EnvioDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private int statusEnvio; //1 - Enviando; 2 - Existe Dados para Enviar; 3 - Todos os Dados Enviados
    private boolean enviando = false;

    public EnvioDadosServ() {
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static EnvioDadosServ getInstance() {
        if (instance == null) {
            instance = new EnvioDadosServ();
        }
        return instance;
    }

    //////////////////////// ENVIAR DADOS ////////////////////////////////////////////

    public void enviarBolAberto() {

        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        String dados = mecanicoCTR.dadosEnvioBolSemEnvio();

        Log.i("PMM", "ABERTO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolAberto()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void enviarBolFechado() {

        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        String dados = mecanicoCTR.dadosEnvioBolFechado();

        Log.i("PMM", "FECHADO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolFechado()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

    }

    public void enviarPneu() {

        PneuCTR pneuCTR = new PneuCTR();
        String dadosEnvioPneu = pneuCTR.dadosEnvioBolPneuFechado();

        Log.i("PMM", "BOLETIM PNEU = " + dadosEnvioPneu);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsInsertBolPneu()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dadosEnvioPneu);

        PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
        conHttpPostGenerico.setParametrosPost(parametrosPost);
        conHttpPostGenerico.execute(url);

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
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        PneuCTR pneuCTR = new PneuCTR();
        if (mecanicoCTR.verBoletimFechado()) {
            enviarBolFechado();
        } else {
            if (mecanicoCTR.verApontSemEnvio()) {
                enviarBolAberto();
            }
            else{
                if(pneuCTR.verBoletimPneuFechado()){
                    enviarPneu();
                }
            }
        }
    }

    public boolean verifDadosEnvio() {
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        PneuCTR pneuCTR = new PneuCTR();
        if ((!mecanicoCTR.verBoletimFechado())
                && (!mecanicoCTR.verApontSemEnvio())
                && (!pneuCTR.verBoletimPneuFechado())) {
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
