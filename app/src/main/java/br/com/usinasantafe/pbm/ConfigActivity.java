package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pbm.util.ConexaoWeb;
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

        Button btOkConfig = (Button) findViewById(R.id.buttonSalvarConfig);
        Button btCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarBD);
        editTextEquipConfig = (EditText) findViewById(R.id.editTextEquipConfig);
        editTextSenhaConfig = (EditText) findViewById(R.id.editTextSenhaConfig);

        if (pbmContext.getConfigCTR().hasElements()) {
            editTextEquipConfig.setText(String.valueOf(pbmContext.getConfigCTR().getEquip().getNumEquip()));
            editTextSenhaConfig.setText(pbmContext.getConfigCTR().getConfig().getSenhaConfig());
        }


        btOkConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextEquipConfig.getText().toString().equals("") &&
                        !editTextSenhaConfig.getText().toString().equals("")) {

                    Long nroEquip = Long.parseLong(editTextEquipConfig.getText().toString());

                    if (pbmContext.getConfigCTR().verNroEquip(nroEquip)) {

                        pbmContext.getConfigCTR().insertConfig(pbmContext.getConfigCTR().getEquip(nroEquip).getIdEquip(), editTextSenhaConfig.getText().toString());

                        Intent it = new Intent(ConfigActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("EQUIPAMENTO INEXISTENTE! POR FAVOR, VERIFIQUE A NUMERAÇÃO DO MESMO.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();
                    }

                }

            }
        });

        btCancConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ConfigActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }
        });

        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ConfigActivity.this)) {
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();
                }
            }
        });

    }

    public void onBackPressed() {
    }

}
