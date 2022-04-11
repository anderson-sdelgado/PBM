package br.com.usinasantafe.pbm.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

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

                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getPneuCTR().getItemManutPneuBean().setNroPneuColItemManutPneu(editTextPadrao.getText().toString());

                    if(pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())){

                        if (connectNetwork) {

                            progressBar = new ProgressDialog(PneuColActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Pneu...");
                            progressBar.show();

                            customHandler.postDelayed(updateTimerThread, 10000);

                            pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()
                                    , PneuColActivity.this, MenuInicialActivity.class, progressBar, true);

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

            if(!VerifDadosServ.getInstance().isVerTerm()) {

                VerifDadosServ.getInstance().cancelVer();
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

        pbmContext.getPneuCTR().salvarItemManutPneu();
        pbmContext.getPneuCTR().fecharBoletim();

        Intent it = new Intent(PneuColActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();

    }

}
