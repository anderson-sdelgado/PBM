package br.com.usinasantafe.pbm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosVerif;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.to.estaticas.PneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
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

                            ManipDadosVerif.getInstance().verDadosPneu(editTextPadrao.getText().toString(), "Pneu"
                                    , PneuColActivity.this, MenuFuncaoActivity.class, progressBar, 2);

                        }
                        else{

                            salvarBoletimPneu();
                            Intent it = new Intent(PneuColActivity.this, MenuFuncaoActivity.class);
                            startActivity(it);

                        }

                    }
                    else {

                        salvarBoletimPneu();
                        Intent it = new Intent(PneuColActivity.this, MenuFuncaoActivity.class);
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

                Intent it = new Intent(PneuColActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

    public void salvarBoletimPneu(){

        BoletimPneuTO boletimPneuTO = pbmContext.getBoletimPneuTO();
        boletimPneuTO.setDthrBolPneu(Tempo.getInstance().datahora());
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

    }

}
