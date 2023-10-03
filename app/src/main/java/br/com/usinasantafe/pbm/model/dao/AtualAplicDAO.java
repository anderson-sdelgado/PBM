package br.com.usinasantafe.pbm.model.dao;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.MessageDigest;

import br.com.usinasantafe.pbm.BuildConfig;
import br.com.usinasantafe.pbm.model.bean.AtualAplicBean;

public class AtualAplicDAO {

    public AtualAplicDAO() {
    }

    public String dadosVerAtualAplicBean(Long idEquip, String versaoAplic){

        AtualAplicBean atualAplicBean = new AtualAplicBean();
        atualAplicBean.setVersao(versaoAplic);
        atualAplicBean.setIdEquip(idEquip);

        return getToken(atualAplicBean);

    }

    public String getAtualBDToken(){
        AtualAplicBean atualAplicBean = new AtualAplicBean();
        return getToken(atualAplicBean);
    }

    private String getToken(AtualAplicBean atualAplicBean){

        atualAplicBean.setToken(token());
        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        return json.toString();

    }

    public String token(){

        String token = "";

        try {

            ConfigDAO configDAO = new ConfigDAO();
            token = "PBM-VERSAO_" + BuildConfig.VERSION_NAME + "-" + configDAO.getConfig().getIdEquipConfig();
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(token.getBytes(),0, token.length());
            BigInteger bigInteger = new BigInteger(1, m.digest());
            String str = bigInteger.toString(16).toUpperCase();
            token = Strings.padStart(str, 32, '0');

        } catch (Exception e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }

        return token;

    }

}
