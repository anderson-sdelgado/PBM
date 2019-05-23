package br.com.usinasantafe.pbm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class PneuRetActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ProgressDialog progressBar;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneu_ret);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkPneuRet = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancPneuRet = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkPneuRet.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getItemManutPneuTO().setNroPneuRetItemManutPneu(editTextPadrao.getText().toString());

                    PneuTO pneuTO = new PneuTO();
                    List pneuList = pneuTO.get("codPneu", editTextPadrao.getText().toString());

                    if(pneuList.size() == 0){

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(PneuRetActivity.this)) {

                            progressBar = new ProgressDialog(PneuRetActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Pneu...");
                            progressBar.show();

                            customHandler.postDelayed(updateTimerThread, 10000);

                            ManipDadosVerif.getInstance().verDadosPneu(editTextPadrao.getText().toString(), "Pneu"
                                    , PneuRetActivity.this, PneuColActivity.class, progressBar, 1);

                        }
                        else{

                            Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                            startActivity(it);

                        }

                    }
                    else {

                        Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                        startActivity(it);
                    }

                }

            }

        });

        buttonCancPneuRet.setOnClickListener(new View.OnClickListener() {

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
        Intent it = new Intent(PneuRetActivity.this, ListaPosPneuActivity.class);
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

                Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                startActivity(it);
                finish();

            }

        }
    };



}
