package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class ItemOSDigActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_osdig);

        Button buttonOkItemOSDig = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancItemOSDig = (Button) findViewById(R.id.buttonCancPadrao);

        pbmContext = (PBMContext) getApplication();

        buttonOkItemOSDig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!editTextPadrao.getText().toString().equals("")) {

                    if(Long.parseLong(editTextPadrao.getText().toString()) < 1000){

                        pbmContext.getApontTO().setItemOSApont(Long.parseLong(editTextPadrao.getText().toString()));

                        ArrayList boletimPesqList = new ArrayList();
                        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                        pesquisa.setCampo("idFuncBoletim");
                        pesquisa.setValor(pbmContext.getColabTO().getIdColab());
                        boletimPesqList.add(pesquisa);

                        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                        pesquisa2.setCampo("statusBoletim");
                        pesquisa2.setValor(1L);
                        boletimPesqList.add(pesquisa2);

                        BoletimTO boletimTO = new BoletimTO();
                        List boletimList = boletimTO.get(boletimPesqList);
                        boletimTO = (BoletimTO) boletimList.get(0);

                        ApontTO apontaTO = new ApontTO();
                        List apontList = apontaTO.getAndOrderBy("idBolApont", boletimTO.getIdBoletim(), "idApont", false);
                        if(apontList.size() > 0) {
                            apontaTO = (ApontTO) apontList.get(0);
                            if(apontaTO.getParadaApont() == 0L){
                                apontaTO.setDthrFinalApont(Tempo.getInstance().datahora());
                                apontaTO.setStatusApont(0L);
                                apontaTO.update();
                            }
                        }

                        ApontTO apontTO = new ApontTO();
                        apontTO.setDthrInicialApont(Tempo.getInstance().datahora());
                        apontTO.setDthrFinalApont("");
                        apontTO.setIdBolApont(boletimTO.getIdBoletim());
                        apontTO.setIdExtBolApont(boletimTO.getIdExtBoletim());
                        apontTO.setOsApont(pbmContext.getApontTO().getOsApont());
                        apontTO.setItemOSApont(pbmContext.getApontTO().getItemOSApont());
                        apontTO.setParadaApont(0L);
                        apontTO.setRealizApont(0L);
                        apontTO.setStatusApont(0L);
                        apontTO.insert();

//                        Intent it = new Intent(ItemOSDigActivity.this, MenuInicialActivity.class);
                        Intent it = new Intent(ItemOSDigActivity.this, MenuFuncaoActivity.class);
                        startActivity(it);
                        finish();

                    }
                    else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(  ItemOSDigActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("VALOR ACIMA DO QUE O PERMITIDO. POR FAVOR, VERIFIQUE O VALOR!");
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

        buttonCancItemOSDig.setOnClickListener(new View.OnClickListener() {

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
        Intent it = new Intent(ItemOSDigActivity.this, OSActivity.class);
        startActivity(it);
        finish();
    }

}
