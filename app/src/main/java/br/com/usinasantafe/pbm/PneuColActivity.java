package br.com.usinasantafe.pbm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosVerif;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.estaticas.PneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemManutPneuTO;

public class PneuColActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ProgressDialog progressBar;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneu_col);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkPneuCol = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancPneuCol = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkPneuCol.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getItemManutPneuTO().setNroPneuColItemManutPneu(editTextPadrao.getText().toString());

                    PneuTO pneuTO = new PneuTO();
                    List pneuList = pneuTO.get("codPneu", editTextPadrao.getText().toString());

                    if(pneuList.size() == 0){

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(PneuColActivity.this)) {

                            progressBar = new ProgressDialog(PneuColActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Pneu...");
                            progressBar.show();

                            customHandler.postDelayed(updateTimerThread, 10000);

                            ManipDadosVerif.getInstance().verDadosPneuFinal(editTextPadrao.getText().toString(), "Pneu"
                                    , PneuColActivity.this, MenuInicialActivity.class, progressBar);

                        }
                        else{

                            salvarBoletimPneu();
                            Intent it = new Intent(PneuColActivity.this, MenuInicialActivity.class);
                            startActivity(it);

                        }

                    }
                    else {

                        salvarBoletimPneu();
                        Intent it = new Intent(PneuColActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                    }

                }

            }

        });

        buttonCancPneuCol.setOnClickListener(new View.OnClickListener() {

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
        Intent it = new Intent(PneuColActivity.this, PneuRetActivity.class);
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

                salvarBoletimPneu();

                Intent it = new Intent(PneuColActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

    public void salvarBoletimPneu(){

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
        boletimList.clear();
        boletimPesqList.clear();

        ColabTO colabTO = new ColabTO();
        List colabList = colabTO.get("idColab", boletimTO.getIdFuncBoletim());
        colabTO = (ColabTO) colabList.get(0);
        colabList.clear();

        BoletimPneuTO boletimPneuTO = pbmContext.getBoletimPneuTO();
        boletimPneuTO.setDthrBolPneu(Tempo.getInstance().datahora());
        boletimPneuTO.setFuncBolPneu(colabTO.getMatricColab());
        boletimPneuTO.setStatusBolPneu(1L);
        boletimPneuTO.insert();

        List boletimPneuList = boletimPneuTO.get("statusBolPneu", 1L);
        boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(0);

        ItemManutPneuTO itemManutPneuTO = pbmContext.getItemManutPneuTO();
        itemManutPneuTO.setIdBolItemManutPneu(boletimPneuTO.getIdBolPneu());
        itemManutPneuTO.setDthrItemManutPneu(Tempo.getInstance().datahora());
        itemManutPneuTO.insert();

        boletimPneuTO.setStatusBolPneu(2L);
        boletimPneuTO.update();

        Intent it = new Intent(PneuColActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();

    }

}
