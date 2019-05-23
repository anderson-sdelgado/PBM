package br.com.usinasantafe.pbm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.to.estaticas.REquipPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemMedPneuTO;

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
                // TODO Auto-generated method stub

                if (!editTextPadrao.getText().toString().equals("")) {

                    Long qtde = Long.parseLong(editTextPadrao.getText().toString());

                    if (qtde < 1000) {

                        pbmContext.getItemMedPneuTO().setPressaoColItemMedPneu(qtde);
                        BoletimPneuTO boletimPneuTO = new BoletimPneuTO();
                        List boletimPneuList = boletimPneuTO.get("statusBolPneu", 1L);
                        boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(0);
                        boletimPneuList.clear();

                        ItemMedPneuTO itemMedPneuTO = new ItemMedPneuTO();
                        itemMedPneuTO = pbmContext.getItemMedPneuTO();
                        itemMedPneuTO.setIdBolItemMedPneu(boletimPneuTO.getIdBolPneu());
                        itemMedPneuTO.setDthrItemMedPneu(Tempo.getInstance().datahora());
                        itemMedPneuTO.insert();

                        REquipPneuTO rEquipPneuTO = new REquipPneuTO();
                        List rEquipPneuList = rEquipPneuTO.get("idEquip", boletimPneuTO.getEquipBolPneu());

                        List itemMedPneuList = itemMedPneuTO.get("idBolItemMedPneu", boletimPneuTO.getIdBolPneu());
                        int verCad = 0;
                        for(int i = 0; i < rEquipPneuList.size(); i++){
                            rEquipPneuTO = (REquipPneuTO) rEquipPneuList.get(i);
                            for(int j = 0; j < itemMedPneuList.size(); j++) {
                                itemMedPneuTO = (ItemMedPneuTO) itemMedPneuList.get(j);
                                if(rEquipPneuTO.getIdPosConfPneu() == itemMedPneuTO.getPosItemMedPneu()){
                                    verCad++;
                                }
                            }
                        }

                        if(rEquipPneuList.size() == verCad){
                            Intent it = new Intent(PressaoColPneuActivity.this, MenuFuncaoActivity.class);
                            startActivity(it);
                            finish();

                        }
                        else{
                            Intent it = new Intent(PressaoColPneuActivity.this, ListaPosPneuActivity.class);
                            startActivity(it);
                            finish();
                        }

                        itemMedPneuList.clear();
                        rEquipPneuList.clear();

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
                // TODO Auto-generated method stub
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
