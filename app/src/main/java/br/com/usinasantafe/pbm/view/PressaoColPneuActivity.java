package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class PressaoColPneuActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressao_col_pneu);

        pbmContext = (PBMContext) getApplication();

        Button buttonOkPressaoCol = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancPressaoCol = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkPressaoCol.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPressaoCol.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {\n" +
                            "                    Long qtde = Long.parseLong(editTextPadrao.getText().toString());", getLocalClassName());
                    Long qtde = Long.parseLong(editTextPadrao.getText().toString());
                    if (qtde < 1000) {

                        LogProcessoDAO.getInstance().insertLogProcesso("if (qtde < 1000) {\n" +
                                "                        pbmContext.getPneuCTR().getItemCalibPneuBean().setPressaoColItemCalibPneu(qtde);\n" +
                                "                        pbmContext.getPneuCTR().salvarItemCalibPneu();", getLocalClassName());
                        pbmContext.getPneuCTR().getItemCalibPneuBean().setPressaoColItemCalibPneu(qtde);
                        pbmContext.getPneuCTR().salvarItemCalibPneu();

                        if(pbmContext.getPneuCTR().verFinalBolCalib()){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getPneuCTR().verFinalBolCalib()){\n" +
                                    "                            pbmContext.getPneuCTR().fecharBoletim();\n" +
                                    "                            Intent it = new Intent(PressaoColPneuActivity.this, MenuInicialActivity.class);", getLocalClassName());
                            pbmContext.getPneuCTR().fecharBoletim();
                            Intent it = new Intent(PressaoColPneuActivity.this, MenuInicialActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            Intent it = new Intent(PressaoColPneuActivity.this, ListaPosPneuActivity.class);", getLocalClassName());
                            Intent it = new Intent(PressaoColPneuActivity.this, ListaPosPneuActivity.class);
                            startActivity(it);
                            finish();
                        }

                    } else {

                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        AlertDialog.Builder alerta = new AlertDialog.Builder(PressaoColPneuActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(\"VALOR DE CALIBRAGEM ACIMA DO PERMITIDO! FAVOR VERIFICA A MESMA.\");", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(PressaoColPneuActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("VALOR DE CALIBRAGEM ACIMA DO PERMITIDO! FAVOR VERIFICA A MESMA.");
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

        buttonCancPressaoCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPressaoCol.setOnClickListener(new View.OnClickListener() {\n" +
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
                "        Intent it = new Intent(PressaoColPneuActivity.this, PressaoEncPneuActivity.class);", getLocalClassName());
        Intent it = new Intent(PressaoColPneuActivity.this, PressaoEncPneuActivity.class);
        startActivity(it);
        finish();
    }

}
