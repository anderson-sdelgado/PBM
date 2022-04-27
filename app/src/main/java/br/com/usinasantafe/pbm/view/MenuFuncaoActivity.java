package br.com.usinasantafe.pbm.view;

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

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.Tempo;

public class MenuFuncaoActivity extends ActivityGeneric {

    private ListView menuFuncaoListView;
    private PBMContext pbmContext;

    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcao);

        pbmContext = (PBMContext) getApplication();
        textViewProcesso = (TextView) findViewById(R.id.textViewProcesso);

        LogProcessoDAO.getInstance().insertLogProcesso("customHandler.postDelayed(updateTimerThread, 0);\n" +
                "        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"APONTAMENTO\");\n" +
                "        itens.add(\"FINALIZAR/INTERROPER\");\n" +
                "        itens.add(\"FINALIZAR TURNO\");\n" +
                "        itens.add(\"CALIBRAGEM DE PNEU\");\n" +
                "        itens.add(\"TROCA DE PNEU\");\n" +
                "        itens.add(\"HISTÓRICO\");\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        menuFuncaoListView = (ListView) findViewById(R.id.listViewMenuFuncao);\n" +
                "        menuFuncaoListView.setAdapter(adapterList);", getLocalClassName());
        customHandler.postDelayed(updateTimerThread, 0);

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("FINALIZAR/INTERROPER");
        itens.add("FINALIZAR TURNO");
        itens.add("CALIBRAGEM DE PNEU");
        itens.add("TROCA DE PNEU");
        itens.add("HISTÓRICO");

        AdapterList adapterList = new AdapterList(this, itens);
        menuFuncaoListView = (ListView) findViewById(R.id.listViewMenuFuncao);
        menuFuncaoListView.setAdapter(adapterList);
        menuFuncaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("menuFuncaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                        "                String text = textView.getText().toString();", getLocalClassName());
                TextView textView = v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"APONTAMENTO\")) {", getLocalClassName());
                    if (!pbmContext.getMecanicoCTR().verApontBolApontando()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (!pbmContext.getMecanicoCTR().verApont()) {", getLocalClassName());
                        if (pbmContext.getMecanicoCTR().verifDataHoraTurno()) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(Tempo.getInstance().dthr() + \" \" + pbmContext.getMecanicoCTR().getEscalaTrab(pbmContext.getMecanicoCTR().getColabApont().getIdEscalaTrabColab()).getHorarioEntEscalaTrab())) {\n" +
                                    "                            Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);", getLocalClassName());
                            Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pbmContext.setVerTela(1);\n" +
                                    "                            Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                            pbmContext.setVerTela(1);
                            Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                            startActivity(it);
                            finish();
                        }
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                        if(pbmContext.getMecanicoCTR().verifDataHoraForcaFechBol()) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getMecanicoCTR().verifDataHoraUltApont()) {", getLocalClassName());
                            if (pbmContext.getMecanicoCTR().getUltApontBolApontando().getDthrInicialLongApontMecan() == Tempo.getInstance().dthrAtualLong()) {
                                LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().getUltApont().getDthrInicialApontMecan().equals(Tempo.getInstance().dthr())) {\n" +
                                        "                                Toast.makeText(MenuFuncaoActivity.this, \"POR FAVOR! ESPERE 1 MINUTO PARA REALIZAR UM NOVO APONTAMENTO.\",\n" +
                                        "                                        Toast.LENGTH_LONG).show();", getLocalClassName());
                                Toast.makeText(MenuFuncaoActivity.this, "POR FAVOR! ESPERE 1 MINUTO PARA REALIZAR UM NOVO APONTAMENTO.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                                if (pbmContext.getMecanicoCTR().getUltApontBolApontando().getDthrFinalApontMecan().equals("")) {
                                    LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().getUltApont().getDthrFinalApontMecan().equals(\"\")) {\n" +
                                            "                                    Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);", getLocalClassName());
                                    Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                    startActivity(it);
                                    finish();
                                } else {
                                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                                    if (pbmContext.getMecanicoCTR().verifDataHoraFinalUltApont()) {
                                        LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(pbmContext.getMecanicoCTR().getUltApont().getDthrFinalApontMecan())) {\n" +
                                                "                                        Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);", getLocalClassName());
                                        Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                        startActivity(it);
                                        finish();
                                    } else {
                                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                                "                                        pbmContext.setVerTela(1);\n" +
                                                "                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                                        pbmContext.setVerTela(1);
                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                }

                            }

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                    "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                            alerta.setMessage(\"O BOLETIM SERÁ ENCERRADO POR FALTA DE ENCERRAMENTO DO TURNO ANTERIOR!\");", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("O BOLETIM SERÁ ENCERRADO POR FALTA DE ENCERRAMENTO DO TURNO ANTERIOR!");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                            "                                @Override\n" +
                                            "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                            "                                    pbmContext.getMecanicoCTR().forcarFechBoletim();\n" +
                                            "                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                                    pbmContext.getMecanicoCTR().forcarFechBoletim();
                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);
                                    startActivity(it);
                                    finish();

                                }
                            });

                            alerta.show();

                        }

                    }

                } else if (text.equals("FINALIZAR/INTERROPER")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"FINALIZAR/INTERROPER\")) {", getLocalClassName());
                    if (pbmContext.getMecanicoCTR().verApontBolApontando()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().verApont()) {", getLocalClassName());
                        if(pbmContext.getMecanicoCTR().verifDataHoraForcaFechBol()) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if(Tempo.getInstance().verifDataHoraFechBoletim(pbmContext.getMecanicoCTR().getDthrApont())) {", getLocalClassName());
                            if (pbmContext.getMecanicoCTR().getUltApontBolApontando().getParadaApontMecan() == 0L) {
                                LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().getUltApont().getParadaApontMecan() == 0L) {\n" +
                                        "                                Intent it = new Intent(MenuFuncaoActivity.this, OpcaoInterroperFinalActivity.class);", getLocalClassName());
                                Intent it = new Intent(MenuFuncaoActivity.this, OpcaoInterroperFinalActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                        "                                alerta.setTitle(\"ATENÇÃO\");\n" +
                                        "                                alerta.setMessage(\"NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.\");", getLocalClassName());
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.");
                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                                "                                    @Override\n" +
                                                "                                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                                    }
                                });

                                alerta.show();
                            }

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                    "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                            alerta.setMessage(\"O BOLETIM SERÁ ENCERRADO POR FALTA DE ENCERRAMENTO DO TURNO ANTERIOR!\");", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("O BOLETIM SERÁ ENCERRADO POR FALTA DE ENCERRAMENTO DO TURNO ANTERIOR!");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                            "                                @Override\n" +
                                            "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                            "                                    pbmContext.getMecanicoCTR().forcarFechBoletim();\n" +
                                            "                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                                    pbmContext.getMecanicoCTR().forcarFechBoletim();
                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);
                                    startActivity(it);
                                    finish();
                                }
                            });

                            alerta.show();

                        }


                    } else {

                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(\"NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.\");", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("NÃO EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                        "                            @Override\n" +
                                        "                            public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                            }
                        });

                        alerta.show();
                    }

                } else if (text.equals("FINALIZAR TURNO")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"FINALIZAR TURNO\")) {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"DESEJA REALMENTE FINALIZAR O TURNO?\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("DESEJA REALMENTE FINALIZAR O TURNO?");
                    alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                    "                        @Override\n" +
                                    "                        public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                            if (pbmContext.getMecanicoCTR().verApontBolApontando()) {
                                LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().verApont()) {", getLocalClassName());
                                if (pbmContext.getMecanicoCTR().getUltApontBolApontando().getParadaApontMecan() == 0L) {

                                    LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().getUltApont().getParadaApontMecan() == 0L) {\n" +
                                            "                                    pbmContext.getMecanicoCTR().fecharBoletim();\n" +
                                            "                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                                    pbmContext.getMecanicoCTR().fecharBoletim();
                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);
                                    startActivity(it);
                                    finish();

                                } else {
                                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                                    if (pbmContext.getMecanicoCTR().verifDataHoraFinalUltApont()) {

                                        LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(pbmContext.getMecanicoCTR().getUltApont().getDthrFinalApontMecan())) {\n" +
                                                "                                        pbmContext.getMecanicoCTR().fecharBoletim();\n" +
                                                "                                        Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                                        pbmContext.getMecanicoCTR().fecharBoletim();
                                        Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);
                                        startActivity(it);
                                        finish();

                                    } else {

                                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                                "                                        pbmContext.setVerTela(2);\n" +
                                                "                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                                        pbmContext.setVerTela(2);
                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                }

                            } else {

                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                        "                                alerta.setTitle(\"ATENÇÃO\");\n" +
                                        "                                alerta.setMessage(\"O BOLETIM NÃO PODE SER ENCERRADO SEM APONTAMENTO! POR FAVOR, APONTE O MESMO.\");", getLocalClassName());
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                                alerta.setTitle("ATENÇÃO");
                                alerta.setMessage("O BOLETIM NÃO PODE SER ENCERRADO SEM APONTAMENTO! POR FAVOR, APONTE O MESMO.");
                                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                                "                                    @Override\n" +
                                                "                                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                                    }
                                });

                                alerta.show();
                            }

                        }

                    });

                    alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                    "                        @Override\n" +
                                    "                        public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                        }

                    });

                    alerta.show();

                } else if (text.equals("HISTÓRICO")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"HISTÓRICO\")) {\n" +
                            "                    Intent it = new Intent(MenuFuncaoActivity.this, ListaHistoricoActivity.class);", getLocalClassName());
                    Intent it = new Intent(MenuFuncaoActivity.this, ListaHistoricoActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("CALIBRAGEM DE PNEU")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CALIBRAGEM DE PNEU\")) {\n" +
                            "                    pbmContext.setVerTela(3);\n" +
                            "                    Intent it = new Intent(MenuFuncaoActivity.this, EquipActivity.class);", getLocalClassName());
                    pbmContext.setVerTela(3);
                    Intent it = new Intent(MenuFuncaoActivity.this, EquipActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("TROCA DE PNEU")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"TROCA DE PNEU\")) {\n" +
                            "                    pbmContext.setVerTela(4);\n" +
                            "                    Intent it = new Intent(MenuFuncaoActivity.this, EquipActivity.class);", getLocalClassName());
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

            if (EnvioDadosServ.status == 1) {
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Existem Dados para serem Enviados");
            } else if (EnvioDadosServ.status == 2) {
                textViewProcesso.setTextColor(Color.YELLOW);
                textViewProcesso.setText("Enviando Dados...");
            } else if (EnvioDadosServ.status == 3) {
                textViewProcesso.setTextColor(Color.GREEN);
                textViewProcesso.setText("Todos os Dados já foram Enviados");
            }

            customHandler.postDelayed(this, 10000);

        }
    };

}
