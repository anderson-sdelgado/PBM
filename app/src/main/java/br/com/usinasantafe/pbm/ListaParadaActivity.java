package br.com.usinasantafe.pbm;

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

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pbm.bo.ManipDadosVerif;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.estaticas.EscalaTrabTO;
import br.com.usinasantafe.pbm.to.estaticas.ParadaTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontamentoTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class ListaParadaActivity extends ActivityGeneric {

    private ListView lista;
    private ProgressDialog progressBar;
    private ArrayAdapter<String> adapter;
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

        ParadaTO paradaTO = new ParadaTO();
        List paradaList = paradaTO.orderBy("codParada",true);

        String itens[] = new String[paradaList.size()];

        for (int i = 0; i < paradaList.size(); i++) {
            paradaTO = (ParadaTO) paradaList.get(i);
            itens[i] = paradaTO.getCodParada() + " - " + paradaTO.getDescrParada();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);
        lista = (ListView) findViewById(R.id.listViewMotParada);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemListParada);
                textParada = textView.getText().toString();

                ParadaTO paradaTO = new ParadaTO();
                List paradaList = paradaTO.get("codParada", textParada.substring(0, textParada.indexOf('-')).trim());
                paradaTO = (ParadaTO) paradaList.get(0);

                ArrayList boletimPesqList = new ArrayList();
                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("idFuncBoletim");
                pesquisa.setValor(pbmContext.getColabTO().getIdColab());
                boletimPesqList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("statusBoletim");
                pesquisa2.setValor(1L);
                boletimPesqList.add(pesquisa2);

                BoletimTO boletimTO = new BoletimTO();
                List boletimList = boletimTO.get(boletimPesqList);
                boletimTO = (BoletimTO) boletimList.get(0);

                ApontamentoTO apontamentoTO = new ApontamentoTO();
                List apontamentoList = apontamentoTO.getAndOrderBy("idAponta", boletimTO.getIdBoletim(), "idAponta", false);

                ApontamentoTO apontTO = new ApontamentoTO();
                if(apontamentoList.size() == 0){
                    ColabTO colabTO = pbmContext.getColabTO();
                    EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                    List escalaTrabList = escalaTrabTO.get("idEscalaTrab",colabTO.getIdEscalaTrabColab());
                    escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                    apontTO.setDthrInicialAponta(Tempo.getInstance().dataSHora() + " " + escalaTrabTO.getHorarioEntEscalaTrab());
                }
                else{
                    apontTO.setDthrInicialAponta(apontamentoTO.getDthrFinalAponta());
                }

                apontTO.setIdBolAponta(boletimTO.getIdBoletim());
                apontTO.setIdExtBolAponta(boletimTO.getIdExtBoletim());
                apontTO.setOsAponta(0L);
                apontTO.setItemOSAponta(0L);
                apontTO.setParadaAponta(paradaTO.getIdParada());
                apontTO.setDthrFinalAponta(Tempo.getInstance().datahora());
                apontTO.setRealizAponta(0L);
                apontTO.setStatusAponta(0L);
                ManipDadosEnvio.getInstance().salvaApontamento(apontTO);
                Intent it = new Intent(  ListaParadaActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }

        });

        editPesqListParada.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ListaParadaActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        buttonAtualParada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ListaParadaActivity.this)) {

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Atualizando Paradas...");
                    progressBar.show();

                    ManipDadosVerif.getInstance().verDados("", "Parada"
                            , ListaParadaActivity.this, ListaParadaActivity.class, progressBar);

                } else {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaParadaActivity.this);
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

        buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ListaParadaActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();

            }
        });

    }

    public void onBackPressed()  {
    }

}
