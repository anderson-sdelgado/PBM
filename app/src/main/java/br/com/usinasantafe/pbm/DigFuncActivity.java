package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.util.ConexaoWeb;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class DigFuncActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_dig);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkFunc = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancFunc = (Button) findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = (Button) findViewById(R.id.buttonAtualPadrao);

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(  DigFuncActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(DigFuncActivity.this)) {

                            progressBar = new ProgressDialog(DigFuncActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Colaborador...");
                            progressBar.show();

                            VerifDadosServ.getInstance().verDados("", "Colab"
                                    , DigFuncActivity.this, DigFuncActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder( DigFuncActivity.this);
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

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }

        });

        buttonOkFunc.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {

                if (!editTextPadrao.getText().toString().equals("")) {

                    ColabBean colabBean = new ColabBean();
                    List colabList = colabBean.get("matricColab", Long.parseLong(editTextPadrao.getText().toString()));

                    if (colabList.size() > 0) {

                        colabBean = (ColabBean) colabList.get(0);

                        BoletimTO bolTO = new BoletimTO();
                        List bolList = bolTO.all();
                        for (int i = 0; i < bolList.size(); i++) {
                            bolTO = (BoletimTO) bolList.get(i);
                            bolTO.setAtualBoletim(0L);
                            bolTO.update();
                        }
                        bolList.clear();

                        ArrayList boletimPesqList = new ArrayList();
                        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                        pesquisa.setCampo("idFuncBoletim");
                        pesquisa.setValor(colabBean.getIdColab());
                        boletimPesqList.add(pesquisa);

                        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                        pesquisa2.setCampo("statusBoletim");
                        pesquisa2.setValor(1L);
                        boletimPesqList.add(pesquisa2);

                        BoletimTO boletimTO = new BoletimTO();
                        List boletimList = boletimTO.get(boletimPesqList);
                        if(boletimList.size() == 0){

                            ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
                            List configuracaoList = configuracaoTO.all();
                            configuracaoTO = (ConfiguracaoTO) configuracaoList.get(0);

                            EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                            List escalaTrabList = escalaTrabTO.get("idEscalaTrab", colabBean.getIdEscalaTrabColab());
                            escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                            boletimTO.setDthrInicialBoletim(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + escalaTrabTO.getHorarioEntEscalaTrab()));
                            boletimTO.setEquipBoletim(configuracaoTO.getEquipConfig());
                            boletimTO.setIdFuncBoletim(colabBean.getIdColab());
                            boletimTO.setIdExtBoletim(0L);
                            boletimTO.setStatusBoletim(1L);
                            boletimTO.setAtualBoletim(1L);
                            boletimTO.insert();

                        }
                        else{

                            boletimTO = (BoletimTO) boletimList.get(0);
                            boletimTO.setAtualBoletim(1L);
                            boletimTO.update();

                        }

                        boletimList.clear();
                        boletimPesqList.clear();
                        colabList.clear();
                        Intent it = new Intent(DigFuncActivity.this, MenuFuncaoActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(DigFuncActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("NUMERAÇÃO DO FUNCIONÁRIO INEXISTENTE! FAVOR VERIFICA A MESMA.");
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

        buttonCancFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed()  {
        Intent it = new Intent(DigFuncActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();
    }

}
