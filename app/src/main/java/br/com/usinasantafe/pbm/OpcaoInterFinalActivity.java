package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;

public class OpcaoInterFinalActivity extends ActivityGeneric {

    private ListView lista;
    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_inter_final);

        ArrayList<String> itens = new ArrayList<String>();

        pbmContext = (PBMContext) getApplication();

        itens.add("FINALIZAR");
        itens.add("INTERROPER");
        itens.add("CANCELAR");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuOpcao);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

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

                if (text.equals("FINALIZAR")) {

                    apontaTO = (ApontTO) apontList.get(0);
                    apontaTO.setDthrFinalApont(Tempo.getInstance().datahora());
                    apontaTO.setRealizApont(1L);
                    apontaTO.setStatusApont(0L);
                    apontaTO.update();

                    Intent it = new Intent(OpcaoInterFinalActivity.this, MenuInicialActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("INTERROPER")) {

                    apontaTO = (ApontTO) apontList.get(0);
                    apontaTO.setDthrFinalApont(Tempo.getInstance().datahora());
                    apontaTO.setStatusApont(0L);
                    apontaTO.update();

                    Intent it = new Intent(OpcaoInterFinalActivity.this, MenuInicialActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("CANCELAR")) {

                    Intent it = new Intent(OpcaoInterFinalActivity.this, MenuInicialActivity.class);
                    startActivity(it);
                    finish();

                }

            }

        });

    }

    public void onBackPressed()  {
    }

}
