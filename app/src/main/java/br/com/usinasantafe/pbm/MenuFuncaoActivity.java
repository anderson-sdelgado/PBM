package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.estaticas.EscalaTrabTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class MenuFuncaoActivity extends ActivityGeneric {

    private ListView lista;
    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcao);

        pbmContext = (PBMContext) getApplication();

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("FINALIZAR/INTERROPER");
        itens.add("FINALIZAR TURNO");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuFuncao);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {

                    Intent it;

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

                    ApontTO apontTO = new ApontTO();
                    List apontamentoList = apontTO.getAndOrderBy("idAponta", boletimTO.getIdBoletim(), "idAponta", false);

                    if(apontamentoList.size() == 0){
                        ColabTO colabTO = pbmContext.getColabTO();
                        EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                        List escalaTrabList = escalaTrabTO.get("idEscalaTrab",colabTO.getIdEscalaTrabColab());
                        escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                        if(Tempo.getInstance().verifDataHora(Tempo.getInstance().dataSHora() + " " + escalaTrabTO.getHorarioEntEscalaTrab())){
                            it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                            startActivity(it);
                            finish();
                        }
                        else{
                            it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                            startActivity(it);
                            finish();
                        }
                    }
                    else{
                        apontTO = (ApontTO) apontamentoList.get(0);
                        if(apontTO.getDthrFinalApont().equals("")){
                            it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                            startActivity(it);
                            finish();
                        }
                        else{
                            if(Tempo.getInstance().verifDataHora(apontTO.getDthrFinalApont())){
                                it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                startActivity(it);
                                finish();
                            }
                            else{
                                it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                startActivity(it);
                                finish();
                            }
                        }
                    }

                } else if (text.equals("FINALIZAR/INTERROPER")) {
                    Intent it = new Intent(MenuFuncaoActivity.this, OpcaoInterFinalActivity.class);
                    startActivity(it);
                    finish();
                } else if (text.equals("FINALIZAR TURNO")) {
                    Intent it = new Intent(MenuFuncaoActivity.this, OpcaoInterFinalActivity.class);
                    startActivity(it);
                    finish();
                }

            }

        });

    }

    public void onBackPressed()  {
    }

}
