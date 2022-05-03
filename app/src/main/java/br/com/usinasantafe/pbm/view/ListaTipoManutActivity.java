package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.estaticas.TipoManutBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class ListaTipoManutActivity extends ActivityGeneric {

    private ListView tipoManutListView;
    private List<TipoManutBean> tipoManutList;
    private PBMContext pbmContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipo_manut);

        pbmContext = (PBMContext) getApplication();

        Button buttonRetTipoManut = findViewById(R.id.buttonRetTipoManut);
        Button buttonAtualTipoManut = findViewById(R.id.buttonAtualTipoManut);

        LogProcessoDAO.getInstance().insertLogProcesso("tipoManutList = pbmContext.getPneuCTR().tipoManutList();\n" +
                "        ArrayList<String> itens = new ArrayList<>();\n" +
                "        for(TipoManutBean tipoManutBean : tipoManutList){\n" +
                "            itens.add(tipoManutBean.getDescrTipoManut());\n" +
                "        }\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        tipoManutListView = findViewById(R.id.listTipoManut);\n" +
                "        tipoManutListView.setAdapter(adapterList);", getLocalClassName());

        tipoManutList = pbmContext.getPneuCTR().tipoManutList();
        ArrayList<String> itens = new ArrayList<>();

        for(TipoManutBean tipoManutBean : tipoManutList){
            itens.add(tipoManutBean.getDescrTipoManut());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        tipoManutListView = findViewById(R.id.listTipoManut);
        tipoManutListView.setAdapter(adapterList);
        tipoManutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("tipoManutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TipoManutBean tipoManutBean = tipoManutList.get(position);\n" +
                        "                pbmContext.getPneuCTR().getItemManutPneuBean().setIdTipoManutPneu(tipoManutBean.getIdTipoManut());\n" +
                        "                Intent it = new Intent(ListaTipoManutActivity.this, PneuRetActivity.class);", getLocalClassName());
                TipoManutBean tipoManutBean = tipoManutList.get(position);
                pbmContext.getPneuCTR().getItemManutPneuBean().setIdTipoManutPneu(tipoManutBean.getIdTipoManut());
                Intent it = new Intent(ListaTipoManutActivity.this, PneuRetActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonAtualTipoManut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualTipoManut.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                    progressBar = new ProgressDialog(ListaTipoManutActivity.this);\n" +
                            "                    progressBar.setCancelable(true);\n" +
                            "                    progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                            "                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                            "                    progressBar.setProgress(0);\n" +
                            "                    progressBar.setMax(100);\n" +
                            "                    progressBar.show();\n" +
                            "                    pbmContext.getPneuCTR().atualDadosTipoManut(ListaTipoManutActivity.this, ListaTipoManutActivity.class, progressBar);", getLocalClassName());

                    progressBar = new ProgressDialog(ListaTipoManutActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    pbmContext.getPneuCTR().atualDadosTipoManut(ListaTipoManutActivity.this, ListaTipoManutActivity.class, progressBar);

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaTipoManutActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaTipoManutActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                        @Override\n" +
                                    "                        public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                        }
                    });

                    alerta.show();
                }
            }
        });

        buttonRetTipoManut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetTipoManut.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaTipoManutActivity.this, PneuColActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaTipoManutActivity.this, ListaPosPneuActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}