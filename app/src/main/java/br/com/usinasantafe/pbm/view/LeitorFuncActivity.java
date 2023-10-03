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
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;
import br.com.usinasantafe.pbm.zxing.CaptureActivity;

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

        txtRetFunc = findViewById(R.id.txtRetFunc);
        Button buttonOkFunc = findViewById(R.id.buttonOkFunc);
        Button buttonCancFunc = findViewById(R.id.buttonCancFunc);
        Button buttonDigFunc = findViewById(R.id.buttonDigFunc);
        Button buttonAtualPadrao = findViewById(R.id.buttonAtualPadrao);

        LogProcessoDAO.getInstance().insertLogProcesso("colabBean = new ColabBean();\n" +
                "        colabBean.setIdColab(0L);\n" +
                "        colabBean.setMatricColab(0L);\n" +
                "        colabBean.setIdEscalaTrabColab(0L);\n" +
                "        colabBean.setNomeColab(\"\");\n" +
                "        txtRetFunc.setText(\"Por Favor, realize a leitura do Cartão do Colaborador Mecânico.\");", getLocalClassName());

        colabBean = new ColabBean();
        colabBean.setIdColab(0L);
        colabBean.setMatricColab(0L);
        colabBean.setIdEscalaTrabColab(0L);
        colabBean.setNomeColab("");

        txtRetFunc.setText("Por Favor, realize a leitura do Cartão do Colaborador Mecânico.");

        buttonOkFunc.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkFunc.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if (colabBean.getIdColab() > 0) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (colabBean.getIdColab() > 0) {\n" +
                        "                    pbmContext.getMecanicoCTR().atualBoletimSApont();\n" +
                        "                    pbmContext.getMecanicoCTR().atualSalvarBoletim(colabBean, getLocalClassName());\n" +
                        "                    Intent it = new Intent(LeitorFuncActivity.this, MenuFuncaoActivity.class);", getLocalClassName());
                pbmContext.getMecanicoCTR().atualBoletimSApont();
                pbmContext.getMecanicoCTR().atualSalvarBoletim(colabBean, getLocalClassName());
                Intent it = new Intent(LeitorFuncActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonCancFunc.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancFunc.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(LeitorFuncActivity.this, TelaInicialActivity.class);", getLocalClassName());
            Intent it = new Intent(LeitorFuncActivity.this, TelaInicialActivity.class);
            startActivity(it);
            finish();
        });

        buttonDigFunc.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonDigFunc.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(LeitorFuncActivity.this, DigFuncActivity.class);", getLocalClassName());
            Intent it = new Intent(LeitorFuncActivity.this, DigFuncActivity.class);
            startActivity(it);
            finish();
        });

        buttonAtualPadrao.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);\n" +
                    "                alerta.setTitle(\"ATENÇÃO\");\n" +
                    "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
            AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);
            alerta.setTitle("ATENÇÃO");
            alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
            alerta.setNegativeButton("SIM", (dialog, which) -> {

                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                        "                    @Override\n" +
                        "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                            progressBar = new ProgressDialog(LeitorFuncActivity.this);\n" +
                            "                            progressBar.setCancelable(true);\n" +
                            "                            progressBar.setMessage(\"Atualizando Colaborador...\");\n" +
                            "                            progressBar.show();\n" +
                            "                            pbmContext.getMecanicoCTR().atualDadosColab(LeitorFuncActivity.this\n" +
                            "                                    , LeitorFuncActivity.class, progressBar);", getLocalClassName());
                    progressBar = new ProgressDialog(LeitorFuncActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Atualizando Colaborador...");
                    progressBar.show();

                    pbmContext.getMecanicoCTR().atualDadosColab(LeitorFuncActivity.this
                            , LeitorFuncActivity.class, progressBar);

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                            AlertDialog.Builder alerta = new AlertDialog.Builder(LeitorFuncActivity.this);\n" +
                            "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                            alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", getLocalClassName());
                    AlertDialog.Builder alerta1 = new AlertDialog.Builder(LeitorFuncActivity.this);
                    alerta1.setTitle("ATENÇÃO");
                    alerta1.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta1.setPositiveButton("OK", (dialog1, which1) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                                @Override\n" +
                            "                                public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
                    alerta1.show();

                }


            });

            alerta.setPositiveButton("NÃO", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                    "                    @Override\n" +
                    "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
            alerta.show();

        });

    }

    public void callZXing(View view) {
        LogProcessoDAO.getInstance().insertLogProcesso("public void callZXing(View view) {\n" +
                "        Intent it = new Intent(LeitorFuncActivity.this, CaptureActivity.class);\n" +
                "        startActivityForResult(it, REQUEST_CODE);", getLocalClassName());
        Intent it = new Intent(LeitorFuncActivity.this, CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogProcessoDAO.getInstance().insertLogProcesso("@Override\n" +
                "    public void onActivityResult(int requestCode, int resultCode, Intent data) {", getLocalClassName());
        if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {\n" +
                    "            String matricula = data.getStringExtra(\"SCAN_RESULT\");", getLocalClassName());
            String matricula = data.getStringExtra("SCAN_RESULT");
            if (matricula.length() == 8) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (matricula.length() == 8) {\n" +
                        "                matricula = matricula.substring(0, 7);", getLocalClassName());
                matricula = matricula.substring(0, 7);
                if (pbmContext.getMecanicoCTR().verMatricColab(Long.parseLong(matricula))) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().verMatricColab(Long.parseLong(matricula))) {\n" +
                            "                    colabBean = pbmContext.getMecanicoCTR().getColab(Long.parseLong(matricula));\n" +
                            "                    txtRetFunc.setText(matricula + \"\\n\" + colabBean.getNomeColab());", getLocalClassName());
                    colabBean = pbmContext.getMecanicoCTR().getColab(Long.parseLong(matricula));
                    txtRetFunc.setText(matricula + "\n" + colabBean.getNomeColab());
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    txtRetFunc.setText(\"Funcionário Inexistente\");", getLocalClassName());
                    txtRetFunc.setText("Funcionário Inexistente");
                }
            }
        }

    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {\n" +
                "        Intent it = new Intent(LeitorFuncActivity.this, TelaInicialActivity.class);", getLocalClassName());
        Intent it = new Intent(LeitorFuncActivity.this, TelaInicialActivity.class);
        startActivity(it);
        finish();
    }

}
