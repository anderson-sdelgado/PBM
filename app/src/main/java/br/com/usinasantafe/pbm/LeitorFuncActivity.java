package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.util.ConexaoWeb;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class LeitorFuncActivity extends ActivityGeneric {

    public static final int REQUEST_CODE = 0;
    private PBMContext pbmContext;
    private TextView txtRetFunc;
    private String matricula;
    private Boolean verFunc;
    private ProgressDialog progressBar;
    private ColabBean colabBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_leitor);

        pbmContext = (PBMContext) getApplication();

        txtRetFunc = (TextView) findViewById(R.id.txtRetFunc);
        Button buttonOkFunc = (Button) findViewById(R.id.buttonOkFunc);
        Button buttonCancFunc = (Button) findViewById(R.id.buttonCancFunc);
        Button buttonDigFunc = (Button) findViewById(R.id.buttonDigFunc);
        Button buttonAtualPadrao = (Button) findViewById(R.id.buttonAtualPadrao);
        verFunc = false;

        txtRetFunc.setText("Por Favor, realize a leitura do Cartão do Colaborador Mecânico.");

        buttonOkFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (verFunc) {

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
                    if (boletimList.size() == 0) {

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

                    boletimPesqList.clear();
                    boletimList.clear();
                    Intent it = new Intent(LeitorFuncActivity.this, MenuFuncaoActivity.class);
                    startActivity(it);
                    finish();
                }

            }
        });

        buttonCancFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(LeitorFuncActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonDigFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(LeitorFuncActivity.this, DigFuncActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(LeitorFuncActivity.this)) {

                            progressBar = new ProgressDialog(LeitorFuncActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Colaborador...");
                            progressBar.show();

                            VerifDadosServ.getInstance().verDados("", "Colab"
                                    , LeitorFuncActivity.this, LeitorFuncActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);
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

    }

    public void callZXing(View view) {
        Intent it = new Intent(LeitorFuncActivity.this, br.com.usinasantafe.pbm.zxing.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            matricula = data.getStringExtra("SCAN_RESULT");
            if (matricula.length() == 8) {
                matricula = matricula.substring(0, 7);
                colabBean = new ColabBean();
                List listColab = colabBean.get("matricColab", Long.parseLong(matricula));
                if (listColab.size() > 0) {
                    colabBean = (ColabBean) listColab.get(0);
                    verFunc = true;
                    txtRetFunc.setText(matricula + "\n" + colabBean.getNomeColab());
                } else {
                    verFunc = false;
                    txtRetFunc.setText("Funcionário Inexistente");
                }
            }
        }

    }

    public void onBackPressed() {
        Intent it = new Intent(LeitorFuncActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();
    }

}
