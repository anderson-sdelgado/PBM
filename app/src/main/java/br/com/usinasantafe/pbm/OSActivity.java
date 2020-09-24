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

import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.util.ConexaoWeb;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;

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

                if (!editTextPadrao.getText().toString().equals("")) {

                    pbmContext.getMecanicoCTR().setApontBean(new ApontBean());
                    pbmContext.getMecanicoCTR().getApontBean().setOsApont(Long.parseLong(editTextPadrao.getText().toString()));

                    try {

                        if(pbmContext.getMecanicoCTR().verOSApont(Long.parseLong(editTextPadrao.getText().toString()))) {

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

                                customHandler.postDelayed(updateTimerThread, 10000);

                                pbmContext.getMecanicoCTR().verOS(editTextPadrao.getText().toString()
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

            if(!VerifDadosServ.getInstance().isVerTerm()) {

                VerifDadosServ.getInstance().cancelVer();
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
