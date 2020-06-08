package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosReceb;
import br.com.usinasantafe.pbm.bo.ManipDadosVerif;
import br.com.usinasantafe.pbm.to.estaticas.EquipTO;
import br.com.usinasantafe.pbm.to.variaveis.ConfiguracaoTO;

public class ConfiguracaoActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextEquipConfig;
    private EditText editTextSenhaConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        Button btOkConfig = (Button) findViewById(R.id.buttonSalvarConfig);
        Button btCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarBD);
        editTextEquipConfig = (EditText) findViewById(R.id.editTextEquipConfig);
        editTextSenhaConfig = (EditText) findViewById(R.id.editTextSenhaConfig);

        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();

        if (configuracaoTO.hasElements()) {

            List configList = configuracaoTO.all();
            configuracaoTO = (ConfiguracaoTO) configList.get(0);

            EquipTO equipTO = new EquipTO();
            List equipList = equipTO.get("idEquip", configuracaoTO.getEquipConfig());
            equipTO = (EquipTO) equipList.get(0);
            equipList.clear();

            editTextEquipConfig.setText(String.valueOf(equipTO.getNumEquip()));
            editTextSenhaConfig.setText(configuracaoTO.getSenhaConfig());
            configList.clear();
        }


        btOkConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!editTextEquipConfig.getText().toString().equals("") &&
                        !editTextSenhaConfig.getText().toString().equals("")) {

                    EquipTO equipTO = new EquipTO();
                    List equipList = equipTO.get("numEquip", Long.parseLong(editTextEquipConfig.getText().toString()));

                    if (equipList.size() > 0) {

                        equipTO = (EquipTO) equipList.get(0);

                        ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                        configuracaoTO.deleteAll();
                        configuracaoTO.setEquipConfig(equipTO.getIdEquip());
                        configuracaoTO.setSenhaConfig(editTextSenhaConfig.getText().toString());
                        configuracaoTO.insert();
                        configuracaoTO.commit();

                        Intent it = new Intent(ConfiguracaoActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracaoActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("EQUIPAMENTO INEXISTENTE! POR FAVOR, VERIFIQUE A NUMERAÇÃO DO MESMO.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();
                    }

                    equipList.clear();

                }

            }
        });

        btCancConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ConfiguracaoActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }
        });

        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ConfiguracaoActivity.this)) {
                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();
                    ManipDadosReceb.getInstance().setContext(ConfiguracaoActivity.this);
                    ManipDadosReceb.getInstance().atualizarBD(progressBar);
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracaoActivity.this);
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
