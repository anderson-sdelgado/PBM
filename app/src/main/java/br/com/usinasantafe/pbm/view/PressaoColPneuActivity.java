package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;

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

                if (!editTextPadrao.getText().toString().equals("")) {

                    Long qtde = Long.parseLong(editTextPadrao.getText().toString());

                    if (qtde < 1000) {

                        pbmContext.getPneuCTR().getItemCalibPneuBean().setPressaoColItemCalibPneu(qtde);
                        pbmContext.getPneuCTR().salvarItemCalibPneu();

                        if(pbmContext.getPneuCTR().verFinalBolCalib()){

                            pbmContext.getPneuCTR().fecharBoletim();

                            Intent it = new Intent(PressaoColPneuActivity.this, MenuInicialActivity.class);
                            startActivity(it);
                            finish();
                        }
                        else{
                            Intent it = new Intent(PressaoColPneuActivity.this, ListaPosPneuActivity.class);
                            startActivity(it);
                            finish();
                        }

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(PressaoColPneuActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("VALOR DE CALIBRAGEM ACIMA DO PERMITIDO! FAVOR VERIFICA A MESMA.");
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

        buttonCancPressaoCol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        Intent it = new Intent(PressaoColPneuActivity.this, PressaoEncPneuActivity.class);
        startActivity(it);
        finish();
    }

}
