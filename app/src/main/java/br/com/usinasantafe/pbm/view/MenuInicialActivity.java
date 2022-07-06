package br.com.usinasantafe.pbm.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;

public class MenuInicialActivity extends ActivityGeneric {

    private ListView menuInicialListView;
    private ProgressDialog progressBar;
    private PBMContext pbmContext;
    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        pbmContext = (PBMContext) getApplication();

        textViewProcesso = findViewById(R.id.textViewProcesso);

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        LogProcessoDAO.getInstance().insertLogProcesso("customHandler.postDelayed(updateTimerThread, 0);", getLocalClassName());
        customHandler.postDelayed(updateTimerThread, 0);

        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"APONTAMENTO\");\n" +
                "        itens.add(\"CONFIGURAÇÃO\");\n" +
                "        itens.add(\"SAIR\");\n" +
                "        itens.add(\"LOG\");", getLocalClassName());

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("SAIR");
        itens.add("LOG");

        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        menuInicialListView = (ListView) findViewById(R.id.listaMenuInicial);\n" +
                "        menuInicialListView.setAdapter(adapterList);", getLocalClassName());

        AdapterList adapterList = new AdapterList(this, itens);
        menuInicialListView = (ListView) findViewById(R.id.listaMenuInicial);
        menuInicialListView.setAdapter(adapterList);
        menuInicialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("        menuInicialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                        "                String text = textView.getText().toString();", getLocalClassName());
                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"APONTAMENTO\")) {", getLocalClassName());
                    if(pbmContext.getConfigCTR().hasElemConfig() && pbmContext.getMecanicoCTR().hasElementsColab()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getConfigCTR().hasElemConfig() && pbmContext.getMecanicoCTR().hasElementsColab()) {\n" +
                                "                        pbmContext.getMecanicoCTR().forcarFechamentoBoletim();\n" +
                                "                        Intent it = new Intent(MenuInicialActivity.this, LeitorFuncActivity.class);", getLocalClassName());
                        pbmContext.getMecanicoCTR().forcarFechamentoBoletim();
                        Intent it = new Intent(MenuInicialActivity.this, LeitorFuncActivity.class);
                        startActivity(it);
                        finish();
                    }
                } else if (text.equals("CONFIGURAÇÃO")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CONFIGURAÇÃO\")) {\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                    pbmContext.setVerTela(5);
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();
                } else if (text.equals("SAIR")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"SAIR\")) {\n" +
                            "                    Intent intent = new Intent(Intent.ACTION_MAIN);\n" +
                            "                    intent.addCategory(Intent.CATEGORY_HOME);\n" +
                            "                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);\n" +
                            "                    startActivity(intent);", getLocalClassName());
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (text.equals("LOG")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("else if (text.equals(\"LOG\")) {", getLocalClassName());
                    if(pbmContext.getConfigCTR().hasElemConfig()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pmmContext.getConfigCTR().hasElemConfig()) {\n" +
                                "                        pmmContext.getConfigCTR().setPosicaoTela(12L);\n" +
                                "                        Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                        pbmContext.setVerTela(6);
                        Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                        startActivity(it);
                        finish();
                    }
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

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(pbmContext.getConfigCTR().hasElemConfig()) {
                if (EnvioDadosServ.status == 1) {
                    textViewProcesso.setTextColor(Color.RED);
                    textViewProcesso.setText("Existem Dados para serem Enviados");
                } else if (EnvioDadosServ.status == 2) {
                    textViewProcesso.setTextColor(Color.YELLOW);
                    textViewProcesso.setText("Enviando Dados...");
                } else if (EnvioDadosServ.status == 3) {
                    textViewProcesso.setTextColor(Color.GREEN);
                    textViewProcesso.setText("Todos os Dados já foram Enviados");
                }
            }
            else{
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Aparelho sem Equipamento");
            }
            customHandler.postDelayed(this, 10000);
        }
    };

}
