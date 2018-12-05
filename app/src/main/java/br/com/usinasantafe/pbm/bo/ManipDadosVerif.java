package br.com.usinasantafe.pbm.bo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pbm.MenuInicialActivity;
import br.com.usinasantafe.pbm.conWEB.ConHttpPostVerGenerico;
import br.com.usinasantafe.pbm.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pbm.pst.GenericRecordable;
import br.com.usinasantafe.pbm.to.variaveis.AtualizaTO;

/**
 * Created by anderson on 16/11/2015.
 */
public class ManipDadosVerif {

    private static ManipDadosVerif instance = null;
    private GenericRecordable genericRecordable;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
    private String dado;
    private String tipo;
    private AtualizaTO atualizaTO;
    private MenuInicialActivity menuInicialActivity;
    private ConHttpPostVerGenerico conHttpPostVerGenerico;
    private boolean verTerm;
    private String senha;

    public ManipDadosVerif() {
        //genericRecordable = new GenericRecordable();
    }

    public static ManipDadosVerif getInstance() {
        if (instance == null)
            instance = new ManipDadosVerif();
        return instance;
    }

    public void manipularDadosHttp(String result) {

        if (!result.equals("")) {
            retornoVerifNormal(result);
        }

    }

    public String manipLocalClasse(String classe) {
        if (classe.contains("TO")) {
            classe = urlsConexaoHttp.localPSTEstatica + classe;
        }
        return classe;
    }

    public void verAtualizacao(AtualizaTO atualizaTO, MenuInicialActivity menuInicialActivity, ProgressDialog progressDialog) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.atualizaTO = atualizaTO;
        this.progressDialog = progressDialog;
        this.tipo = "Atualiza";
        this.menuInicialActivity = menuInicialActivity;

        envioAtualizacao();

    }

    public void verDados(String dado, String tipo, Context telaAtual, Class telaProx, ProgressDialog progressDialog) {
        verTerm = false;
        urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.dado = dado;
        this.tipo = tipo;

        envioDados();

    }

    public void verDados(String dado, String tipo, Context telaAtual, Class telaProx) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.dado = dado;
        this.tipo = tipo;

        envioDados();

    }

    public void envioAtualizacao() {

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualizaTO, atualizaTO.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PMM", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        conHttpPostVerGenerico = new ConHttpPostVerGenerico();
        conHttpPostVerGenerico.setParametrosPost(parametrosPost);
        conHttpPostVerGenerico.execute(url);

    }

    public void envioDados() {

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", String.valueOf(dado));

        Log.i("PMM", "VERIFICA = " + String.valueOf(dado));

        conHttpPostVerGenerico = new ConHttpPostVerGenerico();
        conHttpPostVerGenerico.setParametrosPost(parametrosPost);
        conHttpPostVerGenerico.execute(url);

    }


    public void retornoVerifNormal(String result) {

        try {

            if (this.tipo.equals("Atualiza")) {

                String verAtualizacao = result.trim();

                if (verAtualizacao.equals("S")) {
                    AtualizarAplicativo atualizarAplicativo = new AtualizarAplicativo();
                    atualizarAplicativo.setContext(this.menuInicialActivity);
                    atualizarAplicativo.execute();
                } else {
                    this.menuInicialActivity.startTimer();
                }

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("PMM", "Erro Manip atualizar = " + e);
        }

    }

    public void cancelVer() {
        if (conHttpPostVerGenerico.getStatus() == AsyncTask.Status.RUNNING) {
            conHttpPostVerGenerico.cancel(true);
        }
    }

    public boolean isVerTerm() {
        return verTerm;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
