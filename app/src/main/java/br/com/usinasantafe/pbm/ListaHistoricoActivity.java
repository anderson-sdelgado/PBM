package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.BoletimBean;

public class ListaHistoricoActivity extends ActivityGeneric {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico);

        Button buttonRetHistorico = (Button) findViewById(R.id.buttonRetHistorico);

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

        ListView listaHistorico = (ListView) findViewById(R.id.listaHistorico);
        AdapterListHistorico adapterListHistorico = new AdapterListHistorico(this, apontList);
        listaHistorico.setAdapter(adapterListHistorico);

        buttonRetHistorico.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaHistoricoActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();
            }
        });


    }
}
