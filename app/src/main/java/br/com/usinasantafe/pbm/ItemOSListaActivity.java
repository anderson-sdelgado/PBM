package br.com.usinasantafe.pbm;

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

import br.com.usinasantafe.pbm.model.bean.estaticas.ItemOSBean;
import br.com.usinasantafe.pbm.util.ConexaoWeb;

public class ItemOSListaActivity extends ActivityGeneric {

    private ListView itemOSListView;
    private List<ItemOSBean> itemOSList;
    private PBMContext pbmContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_os);

        pbmContext = (PBMContext) getApplication();

        Button buttonRetItemOS = (Button) findViewById(R.id.buttonRetItemOS);
        Button buttonAtualItemOS = (Button) findViewById(R.id.buttonAtualItemOS);

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

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ItemOSListaActivity.this)) {

                    progressBar = new ProgressDialog(ItemOSListaActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    pbmContext.getMecanicoCTR().atualDadosItemOS(ItemOSListaActivity.this, ItemOSListaActivity.class, progressBar);

                } else {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ItemOSListaActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();
                }
            }
        });

        AdapterList adapterList = new AdapterList(this, itens);
        itemOSListView = (ListView) findViewById(R.id.listItemOS);
        itemOSListView.setAdapter(adapterList);

        itemOSListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                ItemOSBean itemOSBean = itemOSList.get(position);
                pbmContext.getMecanicoCTR().getApontBean().setItemOSApont(itemOSBean.getSeqItemOS());
                pbmContext.getMecanicoCTR().getApontBean().setParadaApont(0L);
                pbmContext.getMecanicoCTR().getApontBean().setRealizApont(0L);
                pbmContext.getMecanicoCTR().salvarApont();

                Intent it = new Intent(ItemOSListaActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetItemOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ItemOSListaActivity.this, OSActivity.class);
                startActivity(it);
                finish();

            }
        });

    }

    public void onBackPressed()  {
    }

}
