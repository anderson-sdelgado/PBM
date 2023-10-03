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
        textViewProcesso = findViewById(R.id.textViewProcesso);

        LogProcessoDAO.getInstance().insertLogProcesso("customHandler.postDelayed(updateTimerThread, 0);\n" +
                "        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"APONTAMENTO\");\n" +
                "        itens.add(\"FINALIZAR/INTERROPER\");\n" +
                "        itens.add(\"FINALIZAR TURNO\");\n" +
                "        itens.add(\"HISTÓRICO\");\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        menuFuncaoListView = (ListView) findViewById(R.id.listViewMenuFuncao);\n" +
                "        menuFuncaoListView.setAdapter(adapterList);", getLocalClassName());
        customHandler.postDelayed(updateTimerThread, 0);

        ArrayList<String> itens = new ArrayList<>();

        itens.add("APONTAMENTO");
        itens.add("FINALIZAR/INTERROPER");
        itens.add("FINALIZAR TURNO");
        itens.add("HISTÓRICO");

        AdapterList adapterList = new AdapterList(this, itens);
        menuFuncaoListView = findViewById(R.id.listViewMenuFuncao);
        menuFuncaoListView.setAdapter(adapterList);
        menuFuncaoListView.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("menuFuncaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                    "                                    long id) {\n" +
                    "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                    "                String text = textView.getText().toString();", getLocalClassName());
            TextView textView = v.findViewById(R.id.textViewItemList);
            String text = textView.getText().toString();

            switch (text) {
                case "APONTAMENTO": {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"APONTAMENTO\")) {", getLocalClassName());
                    if (!pbmContext.getMecanicoCTR().verApontBolApontando()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (!pbmContext.getMecanicoCTR().verApont()) {", getLocalClassName());
                        if (pbmContext.getMecanicoCTR().verifDataHoraTurno()) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(Tempo.getInstance().dthr() + \" \" + pbmContext.getMecanicoCTR().getEscalaTrab(pbmContext.getMecanicoCTR().getColabApont().getIdEscalaTrabColab()).getHorarioEntEscalaTrab())) {\n" +
                                    "                            Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);", getLocalClassName());
                            Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                            startActivity(it);
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pbmContext.setVerTela(1);\n" +
                                    "                            Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                            pbmContext.setVerTela(1);
                            Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                            startActivity(it);
                        }
                        finish();
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                        if (pbmContext.getMecanicoCTR().getUltApontBolApontando().getDthrFinalApontMecan().equals("")) {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                    "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                        alerta.setMessage(\"EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER! POR FAVOR, FINALIZE OU INTERROMPA O MESMO PRA REALIZAR OUTRO APONTAMENTO.\");", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("EXISTE APONTAMENTO PARA FINALIZAR/INTERROMPER! POR FAVOR, FINALIZE OU INTERROMPA O MESMO PRA REALIZAR OUTRO APONTAMENTO.");
                            alerta.setPositiveButton("OK", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                            @Override\n" +
                                    "                            public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
                            alerta.show();

                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                            if (pbmContext.getMecanicoCTR().verifDataHoraFinalUltApont()) {
                                LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(pbmContext.getMecanicoCTR().getUltApont().getDthrFinalApontMecan())) {\n" +
                                        "                                        Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);", getLocalClassName());
                                Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
                                startActivity(it);
                            } else {
                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                                        pbmContext.setVerTela(1);\n" +
                                        "                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                                pbmContext.setVerTela(1);
                                Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                startActivity(it);
                            }
                            finish();
                        }
                    }
                    break;
                }
                case "FINALIZAR/INTERROPER": {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"FINALIZAR/INTERROPER\")) {", getLocalClassName());
                    if (pbmContext.getMecanicoCTR().verApontBolApontando()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pbmContext.getMecanicoCTR().verApont()) {", getLocalClassName());
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
                            alerta.setPositiveButton("OK", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                    @Override\n" +
                                    "                                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
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
                        alerta.setPositiveButton("OK", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                "                            @Override\n" +
                                "                            public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
                        alerta.show();
                    }
                    break;
                }
                case "FINALIZAR TURNO": {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"FINALIZAR TURNO\")) {\n" +
                            "                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                            "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                    alerta.setMessage(\"DESEJA REALMENTE FINALIZAR O TURNO?\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("DESEJA REALMENTE FINALIZAR O TURNO?");
                    alerta.setPositiveButton("SIM", (dialog, which) -> {
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

                            } else {
                                LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                                if (pbmContext.getMecanicoCTR().verifDataHoraFinalUltApont()) {

                                    LogProcessoDAO.getInstance().insertLogProcesso("if (Tempo.getInstance().verifDataHoraParada(pbmContext.getMecanicoCTR().getUltApont().getDthrFinalApontMecan())) {\n" +
                                            "                                        pbmContext.getMecanicoCTR().fecharBoletim();\n" +
                                            "                                        Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                                    pbmContext.getMecanicoCTR().fecharBoletim();
                                    Intent it = new Intent(MenuFuncaoActivity.this, TelaInicialActivity.class);
                                    startActivity(it);

                                } else {

                                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                            "                                        pbmContext.setVerTela(2);\n" +
                                            "                                        Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);", getLocalClassName());
                                    pbmContext.setVerTela(2);
                                    Intent it = new Intent(MenuFuncaoActivity.this, ListaParadaActivity.class);
                                    startActivity(it);
                                }
                            }
                            finish();

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuFuncaoActivity.this);\n" +
                                    "                                alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                                alerta.setMessage(\"O BOLETIM NÃO PODE SER ENCERRADO SEM APONTAMENTO! POR FAVOR, APONTE O MESMO.\");", getLocalClassName());
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(MenuFuncaoActivity.this);
                            alerta1.setTitle("ATENÇÃO");
                            alerta1.setMessage("O BOLETIM NÃO PODE SER ENCERRADO SEM APONTAMENTO! POR FAVOR, APONTE O MESMO.");
                            alerta1.setPositiveButton("OK", (dialog1, which1) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                    @Override\n" +
                                    "                                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
                            alerta1.show();
                        }

                    });

                    alerta.setNegativeButton("NÃO", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                            "                        @Override\n" +
                            "                        public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));
                    alerta.show();
                    break;

                }
                case "HISTÓRICO": {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"HISTÓRICO\")) {\n" +
                            "                    Intent it = new Intent(MenuFuncaoActivity.this, ListaHistoricoActivity.class);", getLocalClassName());
                    Intent it = new Intent(MenuFuncaoActivity.this, ListaHistoricoActivity.class);
                    startActivity(it);
                    finish();
                    break;

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
