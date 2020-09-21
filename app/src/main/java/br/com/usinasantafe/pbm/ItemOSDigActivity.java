package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;

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

                if (!editTextPadrao.getText().toString().equals("")) {

                    if(Long.parseLong(editTextPadrao.getText().toString()) < 1000){

                        pbmContext.getApontTO().setItemOSApont(Long.parseLong(editTextPadrao.getText().toString()));

                        ArrayList boletimPesqList = new ArrayList();
                        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                        pesquisa.setCampo("atualBoletim");
                        pesquisa.setValor(1L);
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

                        ApontTO apontTO = new ApontTO();

                        if(apontList.size() > 0) {
                            apontaTO = (ApontTO) apontList.get(0);
                            apontaTO.setDthrFinalApont(Tempo.getInstance().datahora());
                            apontaTO.setStatusApont(0L);
                            apontaTO.update();

                            apontTO.setDthrInicialApont(Tempo.getInstance().datahora());

                        }else{
                            ColabBean colabBean = new ColabBean();
                            List colabList = colabBean.get("idColab", boletimTO.getIdFuncBoletim());
                            colabBean = (ColabBean) colabList.get(0);
                            EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                            List escalaTrabList = escalaTrabTO.get("idEscalaTrab", colabBean.getIdEscalaTrabColab());
                            escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                            apontTO.setDthrInicialApont(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + escalaTrabTO.getHorarioEntEscalaTrab()));
                        }

                        apontTO.setDthrFinalApont("");
                        apontTO.setIdBolApont(boletimTO.getIdBoletim());
                        apontTO.setIdExtBolApont(boletimTO.getIdExtBoletim());
                        apontTO.setOsApont(pbmContext.getApontTO().getOsApont());
                        apontTO.setItemOSApont(pbmContext.getApontTO().getItemOSApont());
                        apontTO.setParadaApont(0L);
                        apontTO.setRealizApont(0L);
                        apontTO.setStatusApont(0L);
                        apontTO.insert();

                        Intent it = new Intent(ItemOSDigActivity.this, MenuInicialActivity.class);
//                        Intent it = new Intent(ItemOSDigActivity.this, MenuFuncaoActivity.class);
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
