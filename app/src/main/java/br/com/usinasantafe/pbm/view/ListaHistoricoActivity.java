package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class ListaHistoricoActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico);

        Button buttonRetHistorico = (Button) findViewById(R.id.buttonRetHistorico);

        pbmContext = (PBMContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaHistorico = (ListView) findViewById(R.id.listaHistorico);\n" +
                "        AdapterListHistorico adapterListHistorico = new AdapterListHistorico(this, pbmContext.getMecanicoCTR().apontList());\n" +
                "        listaHistorico.setAdapter(adapterListHistorico);", getLocalClassName());
        ListView listaHistorico = (ListView) findViewById(R.id.listaHistorico);
        AdapterListHistorico adapterListHistorico = new AdapterListHistorico(this, pbmContext.getMecanicoCTR().apontBolApontandoList());
        listaHistorico.setAdapter(adapterListHistorico);
        buttonRetHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetHistorico.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaHistoricoActivity.this, MenuFuncaoActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaHistoricoActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}
