package br.com.usinasantafe.pbm.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.bean.AtualAplicBean;
import br.com.usinasantafe.pbm.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class EquipDAO {

    public EquipDAO() {
    }

    public EquipBean getIdEquip(Long idEquip){
        List<EquipBean> equipList = equipListId(idEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    public boolean verNroEquip(Long nroEquip){
        List<EquipBean> equipList = equipListNro(nroEquip);
        boolean ret = (equipList.size() > 0);
        equipList.clear();
        return ret;
    }

    public EquipBean getNroEquip(Long nroEquip){
        List<EquipBean> equipList = equipListNro(nroEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    public List<EquipBean> equipListId(Long idEquip){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqId(idEquip));

        EquipBean equipBean = new EquipBean();
        return  equipBean.get(pesqArrayList);
    }

    public List<EquipBean> equipListNro(Long nroEquip){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqNro(nroEquip));

        EquipBean equipBean = new EquipBean();
        return  equipBean.get(pesqArrayList);
    }

    public String dadosVerEquip(Long nroEquip, String versaoAplic){

        AtualAplicBean atualAplicBean = new AtualAplicBean();
        atualAplicBean.setVersao(versaoAplic);
        atualAplicBean.setNroEquip(nroEquip);

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        return json.toString();
    }

    public void verEquip(String senha, String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        LogProcessoDAO.getInstance().insertLogProcesso("VerifDadosServ.getInstance().verifDados(dado, \"Equip\", telaAtual, telaProx, progressDialog, activity);", activity);
        VerifDadosServ.getInstance().verifDados(senha, dado, telaAtual, telaProx, progressDialog, activity);
    }

    public EquipBean recDadosEquip(JSONArray jsonArray) throws JSONException {

        EquipBean equipBean = new EquipBean();
        equipBean.deleteAll();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject objeto = jsonArray.getJSONObject(i);
            Gson gson = new Gson();
            equipBean = gson.fromJson(objeto.toString(), EquipBean.class);
            equipBean.insert();

        }

        return equipBean;

    }

    private EspecificaPesquisa getPesqId(Long idEquip){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("idEquip");
        especificaPesquisa.setValor(idEquip);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

    private EspecificaPesquisa getPesqNro(Long nroEquip){
        EspecificaPesquisa especificaPesquisa = new EspecificaPesquisa();
        especificaPesquisa.setCampo("nroEquip");
        especificaPesquisa.setValor(nroEquip);
        especificaPesquisa.setTipo(1);
        return especificaPesquisa;
    }

}
