package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class LogProcessoActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_processo);

        pbmContext = (PBMContext) getApplication();

        Button buttonAvancaLogProcesso = findViewById(R.id.buttonAvancaLogProcesso);
        Button buttonRetLogProcesso = findViewById(R.id.buttonRetLogProcesso);

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaHistorico = findViewById(R.id.listaHistorico);\n" +
                "        AdapterListHistorico adapterListHistorico = new AdapterListHistorico(this, pmmContext.getConfigCTR().logProcessoList());\n" +
                "        listaHistorico.setAdapter(adapterListHistorico);", getLocalClassName());
        ListView listViewLogProcesso = findViewById(R.id.listViewLogProcesso);
        AdapterListProcesso adapterListProcesso = new AdapterListProcesso(this, pbmContext.getConfigCTR().logProcessoList());
        listViewLogProcesso.setAdapter(adapterListProcesso);

        buttonAvancaLogProcesso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAvancaLogProcesso.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "Intent it = new Intent(LogProcessoActivity.this, LogBaseDadoActivity.class);", getLocalClassName());
                Intent it = new Intent(LogProcessoActivity.this, LogBaseDadoActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonRetLogProcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetLogProcesso.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(LogProcessoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(LogProcessoActivity.this, TelaInicialActivity.class);
                startActivity(it);
                finish();

            }

        });

    }

    public void onBackPressed() {
    }

}