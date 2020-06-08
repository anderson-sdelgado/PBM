package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosVerif;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ItemOSTO;
import br.com.usinasantafe.pbm.to.estaticas.OSTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class OSActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private PBMContext pbmContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkOS = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancOS = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getApontTO().setOsApont(Long.parseLong(editTextPadrao.getText().toString()));

                    try {

                        ArrayList boletimPesqList = new ArrayList();
                        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                        pesquisa.setCampo("atualBoletim");
                        pesquisa.setValor(1L);
                        boletimPesqList.add(pesquisa);

                        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                        pesquisa2.setCampo("statusBoletim");
                        pesquisa2.setValor(1L);
                        boletimPesqList.add(pesquisa2);

                        BoletimTO boletimTO = new BoletimTO();
                        List boletimList = boletimTO.get(boletimPesqList);
                        boletimTO = (BoletimTO) boletimList.get(0);

                        ArrayList apontPesqList = new ArrayList();
                        EspecificaPesquisa pesquisa3 = new EspecificaPesquisa();
                        pesquisa3.setCampo("idBolApont");
                        pesquisa3.setValor(boletimTO.getIdBoletim());
                        apontPesqList.add(pesquisa3);

                        EspecificaPesquisa pesquisa4 = new EspecificaPesquisa();
                        pesquisa4.setCampo("osApont");
                        pesquisa4.setValor(pbmContext.getApontTO().getOsApont());
                        apontPesqList.add(pesquisa4);

                        ApontTO apontTO = new ApontTO();
                        List apontList = apontTO.get(apontPesqList);

                        OSTO osto = new OSTO();
                        List osList = osto.get("nroOS", pbmContext.getApontTO().getOsApont());

                        if((apontList.size() > 0) && (osList.size() > 0)) {

                            Intent it = new Intent(OSActivity.this, ItemOSListaActivity.class);
                            startActivity(it);
                            finish();

                        }
                        else{

                            ConexaoWeb conexaoWeb = new ConexaoWeb();
                            if (conexaoWeb.verificaConexao(OSActivity.this)) {

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Pequisando a OS...");
                                progressBar.show();

                                osto.deleteAll();
                                ItemOSTO itemOSTO = new ItemOSTO();
                                itemOSTO.deleteAll();

                                customHandler.postDelayed(updateTimerThread, 10000);

                                ManipDadosVerif.getInstance().verDados(editTextPadrao.getText().toString(), "OS"
                                        , OSActivity.this, DescrOSActivity.class, progressBar);

                            } else {

                                Intent it = new Intent(OSActivity.this, ItemOSDigActivity.class);
                                startActivity(it);
                                finish();

                            }


                        }


                    } catch (Exception e) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(OSActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("O.S. INCORRETA OU INEXISTENTE! FAVOR VERIFICAR.");
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

        buttonCancOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        Intent it = new Intent(OSActivity.this, MenuFuncaoActivity.class);
        startActivity(it);
        finish();
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(!ManipDadosVerif.getInstance().isVerTerm()) {

                ManipDadosVerif.getInstance().cancelVer();
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }

                Intent it = new Intent(OSActivity.this, ItemOSDigActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
