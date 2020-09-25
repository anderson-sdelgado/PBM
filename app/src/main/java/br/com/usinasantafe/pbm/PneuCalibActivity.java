package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pbm.util.ConexaoWeb;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class PneuCalibActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ProgressDialog progressBar;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneu_calib);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkPneuCalib = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancPneuCalib = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkPneuCalib.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {

                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getPneuCTR().getItemCalibPneuBean().setNroPneuItemCalibPneu(editTextPadrao.getText().toString());

                    if(pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())){

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(PneuCalibActivity.this)) {

                            progressBar = new ProgressDialog(PneuCalibActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Pneu...");
                            progressBar.show();

                            customHandler.postDelayed(updateTimerThread, 10000);

                            pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()
                                    , PneuCalibActivity.this, PressaoEncPneuActivity.class, progressBar, false);

                        }
                        else{

                            Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                            startActivity(it);
                            finish();

                        }

                    }
                    else{

                        if(!pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString())){
                            Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                            startActivity(it);
                            finish();
                        }
                        else{

                            AlertDialog.Builder alerta = new AlertDialog.Builder(PneuCalibActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("PNEU REPETIDO! FAVOR CALIBRAR OUTRO PNEU.");

                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alerta.show();

                        }

                    }

                }

            }

        });

        buttonCancPneuCalib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        Intent it = new Intent(PneuCalibActivity.this, ListaPosPneuActivity.class);
        startActivity(it);
        finish();
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(!VerifDadosServ.getInstance().isVerTerm()) {

                VerifDadosServ.getInstance().cancelVer();
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }

                Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
