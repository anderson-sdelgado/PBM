package br.com.usinasantafe.pbm.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

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

                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPneuRet.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {\n" +
                            "                    pbmContext.getPneuCTR().getItemManutPneuBean().setNroPneuRetItemManutPneu(editTextPadrao.getText().toString());", getLocalClassName());
                    pbmContext.getPneuCTR().getItemManutPneuBean().setNroPneuRetItemManutPneu(editTextPadrao.getText().toString());
                    if(pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())){

                        LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getPneuCTR().verPneuItemCalib(editTextPadrao.getText().toString())){", getLocalClassName());
                        if (connectNetwork) {

                            LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                                    "                            progressBar = new ProgressDialog(PneuRetActivity.this);\n" +
                                    "                            progressBar.setCancelable(true);\n" +
                                    "                            progressBar.setMessage(\"Atualizando Pneu...\");\n" +
                                    "                            progressBar.show();\n" +
                                    "                            customHandler.postDelayed(updateTimerThread, 10000);\n" +
                                    "                            pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()\n" +
                                    "                                    , PneuRetActivity.this, PneuColActivity.class, progressBar, false);", getLocalClassName());
                            progressBar = new ProgressDialog(PneuRetActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Pneu...");
                            progressBar.show();

                            customHandler.postDelayed(updateTimerThread, 10000);

                            pbmContext.getPneuCTR().verPneu(editTextPadrao.getText().toString()
                                    , PneuRetActivity.this, PneuColActivity.class, progressBar, false);

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);", getLocalClassName());
                            Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                            startActivity(it);
                            finish();

                        }

                    } else {

                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);", getLocalClassName());
                        Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                        startActivity(it);
                        finish();
                    }

                }

            }

        });

        buttonCancPneuRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPneuRet.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (editTextPadrao.getText().toString().length() > 0) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (editTextPadrao.getText().toString().length() > 0) {\n" +
                            "                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));", getLocalClassName());
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {\n" +
                "        Intent it = new Intent(PneuRetActivity.this, ListaPosPneuActivity.class);", getLocalClassName());
        Intent it = new Intent(PneuRetActivity.this, ListaPosPneuActivity.class);
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

                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);", getLocalClassName());
                Intent it = new Intent(PneuRetActivity.this, PneuColActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
