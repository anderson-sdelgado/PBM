package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.estaticas.ParadaBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.ConnectNetwork;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class ListaParadaActivity extends ActivityGeneric {

    private ListView paradaListView;
    private ProgressDialog progressBar;
    private ArrayAdapter<String> stringArrayAdapter;
    private PBMContext pbmContext;
    private String textParada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_parada);

        Button buttonAtualParada = (Button) findViewById(R.id.buttonAtualParada);
        Button buttonRetMenuParada = (Button) findViewById(R.id.buttonRetMenuParada);
        EditText editPesqListParada = (EditText) findViewById(R.id.editPesqListParada);

        pbmContext = (PBMContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("List<ParadaBean> paradaList = pbmContext.getMecanicoCTR().paradaList();\n" +
                "        String itens[] = new String[paradaList.size()];\n" +
                "        int i = 0;\n" +
                "        for (ParadaBean paradaBean : paradaList) {\n" +
                "            itens[i] = paradaBean.getCodParada() + \" - \" + paradaBean.getDescrParada();\n" +
                "            i++;\n" +
                "        }\n" +
                "        stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);\n" +
                "        paradaListView = (ListView) findViewById(R.id.listViewMotParada);\n" +
                "        paradaListView.setAdapter(stringArrayAdapter);", getLocalClassName());

        List<ParadaBean> paradaList = pbmContext.getMecanicoCTR().paradaList();
        String itens[] = new String[paradaList.size()];

        int i = 0;
        for (ParadaBean paradaBean : paradaList) {
            itens[i] = paradaBean.getCodParada() + " - " + paradaBean.getDescrParada();
            i++;
        }

        stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);
        paradaListView = (ListView) findViewById(R.id.listViewMotParada);
        paradaListView.setAdapter(stringArrayAdapter);
        paradaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("paradaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                        "                textParada = textView.getText().toString();\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                String label = \"DESEJA REALMENTE REALIZAR A PARADA '\" + textParada + \"' ?\";\n" +
                        "                alerta.setMessage(label);", getLocalClassName());
                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                textParada = textView.getText().toString();

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);
                alerta.setTitle("ATENÇÃO");
                String label = "DESEJA REALMENTE REALIZAR A PARADA '" + textParada + "' ?";
                alerta.setMessage(label);

                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pbmContext.getMecanicoCTR().setApontBean(new ApontMecanBean());\n" +
                                "                        pbmContext.getMecanicoCTR().getApontBean().setOsApontMecan(0L);\n" +
                                "                        pbmContext.getMecanicoCTR().getApontBean().setItemOSApontMecan(0L);\n" +
                                "                        pbmContext.getMecanicoCTR().getApontBean().setParadaApontMecan(pbmContext.getMecanicoCTR().getParadaCod(Long.parseLong(textParada.substring(0, textParada.indexOf('-')).trim())).getIdParada());\n" +
                                "                        pbmContext.getMecanicoCTR().getApontBean().setRealizApontMecan(1L);\n" +
                                "                        pbmContext.getMecanicoCTR().salvarApont();\n" +
                                "                        if(pbmContext.getVerTela() == 2){\n" +
                                "                            pbmContext.getMecanicoCTR().fecharBoletim();\n" +
                                "                        }\n" +
                                "                        Intent it = new Intent(  ListaParadaActivity.this, MenuInicialActivity.class);", getLocalClassName());
                        pbmContext.getMecanicoCTR().setApontBean(new ApontMecanBean());
                        pbmContext.getMecanicoCTR().getApontBean().setOsApontMecan(0L);
                        pbmContext.getMecanicoCTR().salvarApont(0L, pbmContext.getMecanicoCTR().getParadaCod(Long.parseLong(textParada.substring(0, textParada.indexOf('-')).trim())).getIdParada(), 1L);

                        if(pbmContext.getVerTela() == 2){
                            pbmContext.getMecanicoCTR().fecharBoletim();
                        }

                        Intent it = new Intent(  ListaParadaActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

                    }

                });

                alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                    }

                });

                alerta.show();

            }

        });

        editPesqListParada.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ListaParadaActivity.this.stringArrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        buttonAtualParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualParada.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                    progressBar = new ProgressDialog(v.getContext());\n" +
                            "                    progressBar.setCancelable(true);\n" +
                            "                    progressBar.setMessage(\"Atualizando Paradas...\");\n" +
                            "                    progressBar.show();\n" +
                            "                    VerifDadosServ.getInstance().verDados(\"\", \"Parada\"\n" +
                            "                            , ListaParadaActivity.this, ListaParadaActivity.class, progressBar);", getLocalClassName());
                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Atualizando Paradas...");
                    progressBar.show();

                    VerifDadosServ.getInstance().verDados("", "Parada"
                            , ListaParadaActivity.this, ListaParadaActivity.class, progressBar);

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);
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

        buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaParadaActivity.this, MenuFuncaoActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaParadaActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}
