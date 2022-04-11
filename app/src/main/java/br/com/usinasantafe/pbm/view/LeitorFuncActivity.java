package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class LeitorFuncActivity extends ActivityGeneric {

    public static final int REQUEST_CODE = 0;
    private PBMContext pbmContext;
    private TextView txtRetFunc;
    private ProgressDialog progressBar;
    private ColabBean colabBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_leitor);

        pbmContext = (PBMContext) getApplication();

        txtRetFunc = (TextView) findViewById(R.id.txtRetFunc);
        Button buttonOkFunc = (Button) findViewById(R.id.buttonOkFunc);
        Button buttonCancFunc = (Button) findViewById(R.id.buttonCancFunc);
        Button buttonDigFunc = (Button) findViewById(R.id.buttonDigFunc);
        Button buttonAtualPadrao = (Button) findViewById(R.id.buttonAtualPadrao);

        colabBean = new ColabBean();
        colabBean.setIdColab(0L);
        colabBean.setMatricColab(0L);
        colabBean.setIdEscalaTrabColab(0L);
        colabBean.setNomeColab("");

        txtRetFunc.setText("Por Favor, realize a leitura do Cartão do Colaborador Mecânico.");

        buttonOkFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (colabBean.getIdColab() > 0) {

                    pbmContext.getMecanicoCTR().atualBoletimSApont();
                    pbmContext.getMecanicoCTR().atualSalvarBoletim(colabBean);

                    Intent it = new Intent(LeitorFuncActivity.this, MenuFuncaoActivity.class);
                    startActivity(it);
                    finish();
                }

            }
        });

        buttonCancFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(LeitorFuncActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonDigFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(LeitorFuncActivity.this, DigFuncActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (connectNetwork) {

                            progressBar = new ProgressDialog(LeitorFuncActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Colaborador...");
                            progressBar.show();

                            pbmContext.getMecanicoCTR().atualDadosColab(LeitorFuncActivity.this
                                    , LeitorFuncActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alerta.show();

                        }


                    }
                });

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }

        });

    }

    public void callZXing(View view) {
        Intent it = new Intent(LeitorFuncActivity.this, br.com.usinasantafe.pbm.zxing.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            String matricula = data.getStringExtra("SCAN_RESULT");
            if (matricula.length() == 8) {
                matricula = matricula.substring(0, 7);
                if (pbmContext.getMecanicoCTR().verMatricColab(Long.parseLong(matricula))) {
                    colabBean = pbmContext.getMecanicoCTR().getColab(Long.parseLong(matricula));
                    txtRetFunc.setText(matricula + "\n" + colabBean.getNomeColab());
                } else {
                    txtRetFunc.setText("Funcionário Inexistente");
                }
            }
        }

    }

    public void onBackPressed() {
        Intent it = new Intent(LeitorFuncActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();
    }

}
