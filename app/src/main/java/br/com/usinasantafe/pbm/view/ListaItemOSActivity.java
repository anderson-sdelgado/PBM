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
import br.com.usinasantafe.pbm.model.bean.estaticas.ItemOSBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.ConnectNetwork;

public class ListaItemOSActivity extends ActivityGeneric {

    private ListView itemOSListView;
    private List<ItemOSBean> itemOSList;
    private PBMContext pbmContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_os);

        pbmContext = (PBMContext) getApplication();

        Button buttonRetItemOS = findViewById(R.id.buttonRetItemOS);
        Button buttonAtualItemOS = findViewById(R.id.buttonAtualItemOS);

        LogProcessoDAO.getInstance().insertLogProcesso("itemOSList = pbmContext.getMecanicoCTR().itemOSList();\n" +
                "        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        for(ItemOSBean itemOSBean : itemOSList){\n" +
                "            itens.add(itemOSBean.getSeqItemOS() + \" - \"\n" +
                "                    + pbmContext.getMecanicoCTR().getServico(itemOSBean.getIdServItemOS()).getDescrServico() + \" - \"\n" +
                "                    + pbmContext.getMecanicoCTR().getComponente(itemOSBean.getIdCompItemOS()).getCodComponente() + \" - \"\n" +
                "                    + pbmContext.getMecanicoCTR().getComponente(itemOSBean.getIdCompItemOS()).getDescrComponente());\n" +
                "        }", getLocalClassName());

        itemOSList = pbmContext.getMecanicoCTR().itemOSList();
        ArrayList<String> itens = new ArrayList<String>();

        for(ItemOSBean itemOSBean : itemOSList){
            itens.add(itemOSBean.getSeqItemOS() + " - "
                    + pbmContext.getMecanicoCTR().getServico(itemOSBean.getIdServItemOS()).getDescrServico() + " - "
                    + pbmContext.getMecanicoCTR().getComponente(itemOSBean.getIdCompItemOS()).getCodComponente() + " - "
                    + pbmContext.getMecanicoCTR().getComponente(itemOSBean.getIdCompItemOS()).getDescrComponente());
        }

        buttonAtualItemOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualItemOS.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                    progressBar = new ProgressDialog(ListaItemOSActivity.this);\n" +
                            "                    progressBar.setCancelable(true);\n" +
                            "                    progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                            "                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                            "                    progressBar.setProgress(0);\n" +
                            "                    progressBar.setMax(100);\n" +
                            "                    progressBar.show();\n" +
                            "                    pbmContext.getMecanicoCTR().atualDadosItemOS(ListaItemOSActivity.this, ListaItemOSActivity.class, progressBar);", getLocalClassName());
                    progressBar = new ProgressDialog(ListaItemOSActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    pbmContext.getMecanicoCTR().atualDadosItemOS(ListaItemOSActivity.this, ListaItemOSActivity.class, progressBar);

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaItemOSActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaItemOSActivity.this);
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

        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        itemOSListView = (ListView) findViewById(R.id.listItemOS);\n" +
                "        itemOSListView.setAdapter(adapterList);", getLocalClassName());
        AdapterList adapterList = new AdapterList(this, itens);
        itemOSListView = (ListView) findViewById(R.id.listItemOS);
        itemOSListView.setAdapter(adapterList);
        itemOSListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("itemOSListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                ItemOSBean itemOSBean = itemOSList.get(position);\n" +
                        "                pbmContext.getMecanicoCTR().salvarApont(itemOSBean.getSeqItemOS(), 0L, 0L);\n" +
                        "                Intent it = new Intent(ListaItemOSActivity.this, MenuInicialActivity.class);", getLocalClassName());
                ItemOSBean itemOSBean = itemOSList.get(position);
                pbmContext.getMecanicoCTR().salvarApont(itemOSBean.getSeqItemOS(), 0L, 0L);
                Intent it = new Intent(ListaItemOSActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetItemOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetItemOS.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaItemOSActivity.this, OSActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaItemOSActivity.this, OSActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}
