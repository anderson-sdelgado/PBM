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
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class OSActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private PBMContext pbmContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkOS = findViewById(R.id.buttonOkPadrao);
        Button buttonCancOS = findViewById(R.id.buttonCancPadrao);

        buttonOkOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkOS.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {\n" +
                            "                    pbmContext.getMecanicoCTR().setApontBean(new ApontMecanBean());\n" +
                            "                    pbmContext.getMecanicoCTR().getApontBean().setOsApontMecan(Long.parseLong(editTextPadrao.getText().toString()));", getLocalClassName());
                    pbmContext.getMecanicoCTR().setApontBean(new ApontMecanBean());
                    pbmContext.getMecanicoCTR().getApontBean().setOsApontMecan(Long.parseLong(editTextPadrao.getText().toString()));

                    try {

                        if(pbmContext.getMecanicoCTR().verOSApont(Long.parseLong(editTextPadrao.getText().toString()))) {

                            LogProcessoDAO.getInstance().insertLogProcesso("try {\n" +
                                    "                        if(pbmContext.getMecanicoCTR().verOSApont(Long.parseLong(editTextPadrao.getText().toString()))) {\n" +
                                    "                            Intent it = new Intent(OSActivity.this, ListaItemOSActivity.class);", getLocalClassName());
                            Intent it = new Intent(OSActivity.this, ListaItemOSActivity.class);
                            startActivity(it);
                            finish();

                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                            if (connectNetwork) {

                                LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                                        "                                progressBar = new ProgressDialog(v.getContext());\n" +
                                        "                                progressBar.setCancelable(true);\n" +
                                        "                                progressBar.setMessage(\"Pequisando a OS...\");\n" +
                                        "                                progressBar.show();\n" +
                                        "                                customHandler.postDelayed(updateTimerThread, 10000);\n" +
                                        "                                pbmContext.getMecanicoCTR().verOS(editTextPadrao.getText().toString()\n" +
                                        "                                        , OSActivity.this, DescrOSActivity.class, progressBar);", getLocalClassName());
                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Pequisando a OS...");
                                progressBar.show();

                                customHandler.postDelayed(updateTimerThread, 10000);

                                pbmContext.getMecanicoCTR().verOS(editTextPadrao.getText().toString()
                                        , OSActivity.this, DescrOSActivity.class, progressBar);

                            } else {

                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                                Intent it = new Intent(OSActivity.this, DigItemOSActivity.class);", getLocalClassName());
                                Intent it = new Intent(OSActivity.this, DigItemOSActivity.class);
                                startActivity(it);
                                finish();

                            }

                        }

                    } catch (Exception e) {

                        LogProcessoDAO.getInstance().insertLogProcesso("} catch (Exception e) {\n" +
                                "                        AlertDialog.Builder alerta = new AlertDialog.Builder(OSActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(\"O.S. INCORRETA OU INEXISTENTE! FAVOR VERIFICAR.\");", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(OSActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("O.S. INCORRETA OU INEXISTENTE! FAVOR VERIFICAR.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                        "                            @Override\n" +
                                        "                            public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
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
                if (editTextPadrao.getText().toString().length() > 0) {
                    LogProcessoDAO.getInstance().insertLogProcesso("buttonCancOS.setOnClickListener(new View.OnClickListener() {\n" +
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
                "        Intent it = new Intent(OSActivity.this, MenuFuncaoActivity.class);", getLocalClassName());
        Intent it = new Intent(OSActivity.this, MenuFuncaoActivity.class);
        startActivity(it);
        finish();
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            LogProcessoDAO.getInstance().insertLogProcesso("    private Runnable updateTimerThread = new Runnable() {\n" +
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

                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(OSActivity.this, DigItemOSActivity.class);", getLocalClassName());
                Intent it = new Intent(OSActivity.this, DigItemOSActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
