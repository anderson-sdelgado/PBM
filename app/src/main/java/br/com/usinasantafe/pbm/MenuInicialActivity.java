package br.com.usinasantafe.pbm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.to.variaveis.VerApontaFuncTO;

public class MenuInicialActivity extends ActivityGeneric {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        if(!checkPermission(Manifest.permission.CAMERA)){
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        VerApontaFuncTO verApontaFuncTO = new VerApontaFuncTO();
        verApontaFuncTO.setIdFuncApont(1L);
        verApontaFuncTO.setDthrApont("12/11/2018 07:30");
        verApontaFuncTO.deleteAll();
        verApontaFuncTO.insert();

        listarMenuInicial();

    }

    private void listarMenuInicial() {

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listaMenuInicial);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {
//                    Intent it = new Intent(MenuInicialActivity.this, FuncionarioLeitorActivity.class);
//                    startActivity(it);
//                    finish();

                    VerApontaFuncTO verApontaFuncTO = new VerApontaFuncTO();
                    List verApontaFuncList = verApontaFuncTO.all();
                    verApontaFuncTO = (VerApontaFuncTO) verApontaFuncList.get(0);

                    Log.i("PBM", "" + Tempo.getInstance().verifDataApont(verApontaFuncTO.getDthrApont()));
                    Log.i("PBM", "" + Tempo.getInstance().datahora());

                } else if (text.equals("CONFIGURAÇÃO")) {
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();
                } else if (text.equals("SAIR")) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

        });

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void onBackPressed()  {
    }

}
