package br.com.usinasantafe.pbm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class LogBaseDadoActivity extends ActivityGeneric {

    private PBMContext pmmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_base_dado);

        pmmContext = (PBMContext) getApplication();

        Button buttonAvancaLogBaseDado = findViewById(R.id.buttonAvancaLogBaseDado);
        Button buttonRetLogBaseDado = findViewById(R.id.buttonRetLogBaseDado);

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaHistorico = findViewById(R.id.listaHistorico);\n" +
                "        AdapterListHistorico adapterListHistorico = new AdapterListHistorico(this, pmmContext.getConfigCTR().logProcessoList());\n" +
                "        listaHistorico.setAdapter(adapterListHistorico);", getLocalClassName());
        ListView listViewLogBaseDado = findViewById(R.id.listViewLogBaseDado);
        AdapterListBaseDado adapterListBaseDado = new AdapterListBaseDado(this, pmmContext.getConfigCTR().logBaseDadoList());
        listViewLogBaseDado.setAdapter(adapterListBaseDado);
        buttonAvancaLogBaseDado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAvancaLogProcesso.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "Intent it = new Intent(LogBaseDadoActivity.this, LogErroActivity.class);", getLocalClassName());
                Intent it = new Intent(LogBaseDadoActivity.this, LogErroActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonRetLogBaseDado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetLogProcesso.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "Intent it = new Intent(LogBaseDadoActivity.this, LogProcessoActivity.class);", getLocalClassName());
                Intent it = new Intent(LogBaseDadoActivity.this, LogProcessoActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed() {
    }

}