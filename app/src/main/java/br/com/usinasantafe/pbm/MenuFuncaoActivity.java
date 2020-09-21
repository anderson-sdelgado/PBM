package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class MenuFuncaoActivity extends ActivityGeneric {

    private ListView lista;
    private PBMContext pbmContext;

    private TextView textViewProcesso;
    private Handler customHandler = new Handler();
    private BoletimTO boletimTO;
    private ApontTO apontTO;
    private List apontList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcao);

        pbmContext = (PBMContext) getApplication();

        textViewProcesso = (TextView) findViewById(R.id.textViewProcesso);

        customHandler.postDelayed(updateTimerThread, 0);

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("FINALIZAR/INTERROPER");
        itens.add("FINALIZAR TURNO");
        itens.add("CALIBRAGEM DE PNEU");
        itens.add("TROCA DE PNEU");
        itens.add("HISTÓRICO");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuFuncao);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                ArrayList boletimPesqList = new ArrayList();
                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("atualBoletim");
                pesquisa.setValor(1L);
                boletimPesqList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("statusBoletim");
                pesquisa2.setValor(1L);
                boletimPesqList.add(pesquisa2);

                boletimTO = new BoletimTO();
                List boletimList = boletimTO.get(boletimPesqList);
                boletimTO = (BoletimTO) boletimList.get(0);

                apontTO = new ApontTO();
                apontList = apontTO.getAndOrderBy("idBolApont", boletimTO.getIdBoletim(), "idApont", false);

                if (text.equals("APONTAMENTO")) {

                    Intent it;

                    if (apontList.size() == 0) {
                        ColabBean colabBean = new ColabBean();
                        List colabList = colabBean.get("idColab", boletimTO.getIdFuncBoletim());
                        colabBean = (ColabBean) colabList.get(0);
                        EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                        List escalaTrabList = escalaTrabTO.get("idEscalaTrab", colabBean.getIdEscalaTrabColab());
                        escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                        if (Tempo.getInstance().verifDataHora(Tempo.getInstance().dataSHoraComTZ() + " " + escalaTrabTO.getHorarioEntEscalaTrab())) {
                            it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            pbmContext.setVerTela(1);
                            it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                            startActivity(it);
                            finish();
                        }
                    } else {

                        apontTO = (ApontTO) apontList.get(0);

                        if (apontTO.getDthrInicialApont().equals(Tempo.getInstance().datahora())) {
                            Toast.makeText(MenuFuncaoActivity.this, "POR FAVOR! ESPERE 1 MINUTO PARA REALIZAR UM NOVO APONTAMENTO.",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            if (apontTO.getDthrFinalApont().equals("")) {
                                it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                if (Tempo.getInstance().verifDataHora(apontTO.getDthrFinalApont())) {
                                    it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                    startActivity(it);
                                    finish();
                                } else {
                                    pbmContext.setVerTela(1);
                                    it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }

                        }

                    }

                } else if (text.equals("FINALIZAR/INTERROPER")) {

                    if (apontList.size() > 0) {
                        apontTO = (ApontTO) apontList.get(0);
                        if (apontTO.getParadaApont() == 0L) {
                            Intent it = new Intent(MenuFuncaoActivity.this, OpcaoInterFinalActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alerta.show();
                        }

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();
                    }

                } else if (text.equals("FINALIZAR TURNO")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                    alerta.setTitle("ATENÇÃO");

                    String label = "DESEJA REALMENTE FINALIZAR O TURNO?";

                    alerta.setMessage(label);

                    alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        Intent it;

                        if (apontList.size() > 0) {

                            apontTO = (ApontTO) apontList.get(0);
                            if (apontTO.getParadaApont() == 0L) {

                                if (apontTO.getParadaApont() == 0L) {
                                    apontTO.setDthrFinalApont(Tempo.getInstance().datahora());
                                    apontTO.setStatusApont(0L);
                                    apontTO.update();
                                }

                                boletimTO.setDthrFinalBoletim(Tempo.getInstance().datahora());
                                boletimTO.setStatusBoletim(2L);
                                boletimTO.update();

                                it = new Intent(MenuFuncaoActivity.this, MenuInicialActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                if (Tempo.getInstance().verifDataHora(apontTO.getDthrFinalApont())) {

                                    apontTO = (ApontTO) apontList.get(0);
                                    if (apontTO.getParadaApont() == 0L) {
                                        apontTO.setDthrFinalApont(Tempo.getInstance().datahora());
                                        apontTO.setStatusApont(0L);
                                        apontTO.update();
                                    }

                                    boletimTO.setDthrFinalBoletim(Tempo.getInstance().datahora());
                                    boletimTO.setStatusBoletim(2L);
                                    boletimTO.update();

                                    it = new Intent(MenuFuncaoActivity.this, MenuInicialActivity.class);
                                    startActivity(it);
                                    finish();

                                } else {
                                    pbmContext.setVerTela(2);
                                    it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            }

                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("O BOLETIM NÃO PODE SER ENCERRADO SEM APONTAMENTO! POR FAVOR, APONTE O MESMO.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alerta.show();
                        }

                        }

                    });


                    alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });

                    alerta.show();

                } else if (text.equals("HISTÓRICO")) {

                    Intent it = new Intent(MenuFuncaoActivity.this, ListaHistoricoActivity.class);
                    startActivity(it);
                    finish();

                }
                else if (text.equals("CALIBRAGEM DE PNEU")) {

                    pbmContext.setVerTela(3);
                    Intent it = new Intent(MenuFuncaoActivity.this, EquipActivity.class);
                    startActivity(it);
                    finish();

                }
                else if (text.equals("TROCA DE PNEU")) {

                    pbmContext.setVerTela(4);
                    Intent it = new Intent(MenuFuncaoActivity.this, EquipActivity.class);
                    startActivity(it);
                    finish();

                }

            }

        });

    }

    public void onBackPressed() {
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if (EnvioDadosServ.getInstance().getStatusEnvio() == 1) {
                textViewProcesso.setTextColor(Color.YELLOW);
                textViewProcesso.setText("Enviando Dados...");
            } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 2) {
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Existem Dados para serem Enviados");
            } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 3) {
                textViewProcesso.setTextColor(Color.GREEN);
                textViewProcesso.setText("Todos os Dados já foram Enviados");
            }

            customHandler.postDelayed(this, 10000);

        }
    };

}
