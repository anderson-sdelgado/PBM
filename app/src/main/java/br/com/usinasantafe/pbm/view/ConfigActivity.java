package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pbm.BuildConfig;
import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.util.AtualDadosServ;

public class ConfigActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextEquipConfig;
    private EditText editTextSenhaConfig;
    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        pbmContext = (PBMContext) getApplication();

        Button buttonSalvarConfig = findViewById(R.id.buttonSalvarConfig);
        Button buttonCancConfig = findViewById(R.id.buttonCancConfig);
        Button buttonAtualizarBD = findViewById(R.id.buttonAtualizarBD);
        editTextEquipConfig = findViewById(R.id.editTextEquipConfig);
        editTextSenhaConfig = findViewById(R.id.editTextSenhaConfig);

        if (pbmContext.getConfigCTR().hasElemConfig()) {
            LogProcessoDAO.getInstance().insertLogProcesso("        if (pbmContext.getConfigCTR().hasElemConfig()) {\n" +
                    "            editTextEquipConfig.setText(String.valueOf(pbmContext.getConfigCTR().getEquip().getNroEquip()));\n" +
                    "            editTextSenhaConfig.setText(pbmContext.getConfigCTR().getConfig().getSenhaConfig());", getLocalClassName());
            editTextEquipConfig.setText(String.valueOf(pbmContext.getConfigCTR().getEquip().getNroEquip()));
            editTextSenhaConfig.setText(pbmContext.getConfigCTR().getConfig().getSenhaConfig());
        }

        buttonSalvarConfig.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonSalvarConfig.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());

            if (!editTextEquipConfig.getText().toString().equals("") &&
                    !editTextSenhaConfig.getText().toString().equals("")) {

                LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextEquipConfig.getText().toString().equals(\"\") &&\n" +
                        "                        !editTextSenhaConfig.getText().toString().equals(\"\")) {\n" +
                        "                    Long nroEquip = Long.parseLong(editTextEquipConfig.getText().toString());", getLocalClassName());

                LogProcessoDAO.getInstance().insertLogProcesso("        buttonSalvarConfig.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextEquipConfig.getText().toString().equals("") &&
                        !editTextSenhaConfig.getText().toString().equals("")){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextEquipConfig.getText().toString().equals(\"\") &&\n" +
                            "                        !editTextSenhaConfig.getText().toString().equals(\"\")){\n" +
                            "progressBar = new ProgressDialog(v.getContext());\n" +
                            "                    progressBar.setCancelable(true);\n" +
                            "                    progressBar.setMessage(\"Pequisando o Equipamento...\");\n" +
                            "                    progressBar.show();", getLocalClassName());
                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Pequisando o Equipamento...");
                    progressBar.show();

                    LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().salvarConfig(" + editTextSenhaConfig.getText().toString() + ");\n" +
                            "                    pmmContext.getConfigCTR().verEquipConfig(" + editTextEquipConfig.getText().toString() + ", ConfigActivity.this ,TelaInicialActivity.class, progressBar);", getLocalClassName());
                    pbmContext.getConfigCTR().verEquipConfig(editTextSenhaConfig.getText().toString(), BuildConfig.VERSION_NAME, editTextEquipConfig.getText().toString(), ConfigActivity.this , TelaInicialActivity.class, progressBar, getLocalClassName());

                }

            }

        });

        buttonCancConfig.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancConfig.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(ConfigActivity.this, TelaInicialActivity.class);", getLocalClassName());
            Intent it = new Intent(ConfigActivity.this, TelaInicialActivity.class);
            startActivity(it);
            finish();

        });

        buttonAtualizarBD.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("        buttonAtualizarBD.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n", getLocalClassName());
            if(pbmContext.getConfigCTR().hasElemConfig()) {

                LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getConfigCTR().hasElemConfig()) {", getLocalClassName());
                if (connectNetwork) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                    progressBar = new ProgressDialog(v.getContext());\n" +
                            "                    progressBar.setCancelable(true);\n" +
                            "                    progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                            "                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                            "                    progressBar.setProgress(0);\n" +
                            "                    progressBar.setMax(100);\n" +
                            "                    progressBar.show();\n" +
                            "                    AtualDadosServ.getInstance().setTelaAtual(ConfigActivity.this);\n" +
                            "                    AtualDadosServ.getInstance().atualizarBD(progressBar);", getLocalClassName());
                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();
                    AtualDadosServ.getInstance().setTelaAtual(ConfigActivity.this);
                    AtualDadosServ.getInstance().atualizarBD(progressBar);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                        @Override\n" +
                                    "                        public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                        }
                    });

                    alerta.show();
                }

            } else {

                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);\n" +
                        "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                        "                    alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                        "                        @Override\n" +
                        "                        public void onClick(DialogInterface dialog, int which) {\n" +
                        "                    });\n" +
                        "                    alerta.show();", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("POR FAVOR, INSIRA O EQUIPAMENTO E SENHA ANTES DE ATUALIZAR OS DADDOS.");
                alerta.setPositiveButton("OK", (dialog, which) -> {
                });
                alerta.show();

            }
        });

    }

    public void onBackPressed() {
    }

}
