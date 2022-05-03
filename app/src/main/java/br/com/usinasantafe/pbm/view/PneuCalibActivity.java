package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
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
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPneuCalib.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {\n" +
                            "                    pbmContext.getPneuCTR().getItemCalibPneuBean().setNroPneuItemCalibPneu(editTextPadrao.getText().toString());", getLocalClassName());
                    pbmContext.getPneuCTR().getItemCalibPneuBean().setNroPneuItemCalibPneu(editTextPadrao.getText().toString());

                    if (pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())) {", getLocalClassName());

                        if(pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString())){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString())){", getLocalClassName());
                            if (connectNetwork) {

                                LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                                        "                            progressBar = new ProgressDialog(PneuCalibActivity.this);\n" +
                                        "                            progressBar.setCancelable(true);\n" +
                                        "                            progressBar.setMessage(\"Atualizando Pneu...\");\n" +
                                        "                            progressBar.show();\n" +
                                        "                            customHandler.postDelayed(updateTimerThread, 10000);\n" +
                                        "                            pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()\n" +
                                        "                                    , PneuCalibActivity.this, PressaoEncPneuActivity.class, progressBar, false);", getLocalClassName());
                                progressBar = new ProgressDialog(PneuCalibActivity.this);
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Atualizando Pneu...");
                                progressBar.show();

                                customHandler.postDelayed(updateTimerThread, 10000);

                                pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()
                                        , PneuCalibActivity.this, PressaoEncPneuActivity.class, progressBar, false);

                            } else {

                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                            Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);", getLocalClassName());
                                Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                                startActivity(it);
                                finish();

                            }

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);", getLocalClassName());
                            Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                            startActivity(it);
                            finish();

                        }

                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                            AlertDialog.Builder alerta = new AlertDialog.Builder(PneuCalibActivity.this);\n" +
                                "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                            alerta.setMessage(\"PNEU REPETIDO! FAVOR CALIBRAR OUTRO PNEU.\");", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(PneuCalibActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("PNEU REPETIDO! FAVOR CALIBRAR OUTRO PNEU.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                        "                                @Override\n" +
                                        "                                public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                            }
                        });
                        alerta.show();

                    }

                }

            }

        });

        buttonCancPneuCalib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPadrao.getText().toString().length() > 0) {
                    LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPneuCalib.setOnClickListener(new View.OnClickListener() {\n" +
                            "            @Override\n" +
                            "            public void onClick(View v) {\n" +
                            "                if (editTextPadrao.getText().toString().length() > 0) {\n" +
                            "                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));", getLocalClassName());
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {\n" +
                "        Intent it = new Intent(PneuCalibActivity.this, ListaPosPneuActivity.class);", getLocalClassName());
        Intent it = new Intent(PneuCalibActivity.this, ListaPosPneuActivity.class);
        startActivity(it);
        finish();
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            LogProcessoDAO.getInstance().insertLogProcesso("private Runnable updateTimerThread = new Runnable() {\n" +
                    "        public void run() {", getLocalClassName());
            if(!VerifDadosServ.getInstance().isVerTerm()) {

                LogProcessoDAO.getInstance().insertLogProcesso("if(!VerifDadosServ.getInstance().isVerTerm()) {\n" +
                        "                VerifDadosServ.getInstance().cancel();", getLocalClassName());
                VerifDadosServ.getInstance().cancel();
                if (progressBar.isShowing()) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (progressBar.isShowing()) {\n" +
                            "                    progressBar.dismiss();", getLocalClassName());
                    progressBar.dismiss();
                }

                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);", getLocalClassName());
                Intent it = new Intent(PneuCalibActivity.this, PressaoEncPneuActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
