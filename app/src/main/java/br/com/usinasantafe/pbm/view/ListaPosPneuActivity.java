package br.com.usinasantafe.pbm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class ListaPosPneuActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ArrayList<REquipPneuBean> posPneuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pos_pneu);

        pbmContext = (PBMContext) getApplication();

        Button buttonCancPosPneu = findViewById(R.id.buttonCancPosPneu);
        Button buttonFinalCalibragem = findViewById(R.id.buttonFinalCalibragem);

        LogProcessoDAO.getInstance().insertLogProcesso("        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        if(pbmContext.getVerTela() == 3){\n" +
                "            itens = pbmContext.getPneuCTR().rEquipPneuCalibList();\n" +
                "        }\n" +
                "        else if(pbmContext.getVerTela() == 4){\n" +
                "            itens = pbmContext.getPneuCTR().rEquipPneuManutList();\n" +
                "        }\n" +
                "        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);\n" +
                "        ListView listaPosPneu = (ListView) findViewById(R.id.listaPosPneu);\n" +
                "        listaPosPneu.setAdapter(adapterList);", getLocalClassName());

        ArrayList<String> itens = new ArrayList<String>();

        if(pbmContext.getVerTela() == 3){
            posPneuList = pbmContext.getPneuCTR().rEquipPneuCalibList();
        } else if(pbmContext.getVerTela() == 4){
            posPneuList = pbmContext.getPneuCTR().rEquipPneuManutList();
        }

        ListView listaPosPneu = findViewById(R.id.listaPosPneu);
        AdapterListPosPneu adapterListPosPneu = new AdapterListPosPneu(this, posPneuList);
        listaPosPneu.setAdapter(adapterListPosPneu);

        listaPosPneu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaPosPneu.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {", getLocalClassName());
                if(pbmContext.getVerTela() == 3) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getVerTela() == 3) {\n" +
                            "                    LogProcessoDAO.getInstance().insertLogProcesso(\"if(pbmContext.getVerTela() == 3) {\\n\" +\n" +
                            "                            \"                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());\\n\" +\n" +
                            "                            \"                    pbmContext.getPneuCTR().getItemCalibPneuBean().setIdPosItemCalibPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());\\n\" +\n" +
                            "                            \"                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);\", getLocalClassName());\n" +
                            "                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());\n" +
                            "                    pbmContext.getPneuCTR().getItemCalibPneuBean().setIdPosItemCalibPneu(posPneuList.get(position).getIdPosConfPneu());\n" +
                            "                    posPneuList.clear();\n" +
                            "                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);", getLocalClassName());
                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());
                    pbmContext.getPneuCTR().getItemCalibPneuBean().setIdPosItemCalibPneu(posPneuList.get(position).getIdPosConfPneu());
                    posPneuList.clear();
                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);
                    startActivity(it);
                    finish();

                } else if(pbmContext.getVerTela() == 4) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pbmContext.getVerTela() == 4) {\n" +
                            "                    pbmContext.getPneuCTR().setItemManutPneuBean(new ItemManutPneuBean());\n" +
                            "                    pbmContext.getPneuCTR().getItemManutPneuBean().setIdPosItemManutPneu(posPneuList.get(position).getIdPosConfPneu());\n" +
                            "                    posPneuList.clear();\n" +
                            "                    Intent it = new Intent(ListaPosPneuActivity.this, ListaTipoManutActivity.class);", getLocalClassName());
                    pbmContext.getPneuCTR().setItemManutPneuBean(new ItemManutPneuBean());
                    pbmContext.getPneuCTR().getItemManutPneuBean().setIdPosItemManutPneu(posPneuList.get(position).getIdPosConfPneu());
                    posPneuList.clear();
                    Intent it = new Intent(ListaPosPneuActivity.this, ListaTipoManutActivity.class);
                    startActivity(it);
                    finish();

                }

            }

        });



        buttonFinalCalibragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pbmContext.getVerTela() == 3) {

                    LogProcessoDAO.getInstance().insertLogProcesso("buttonFinalCalibragem.setOnClickListener(new View.OnClickListener() {\n" +
                            "            @Override\n" +
                            "            public void onClick(View v) {", getLocalClassName());
                    if(pbmContext.getPneuCTR().verifFinalItemPneuBoletimCalibAberto()){

                        LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getPneuCTR().verifFinalItemPneuBoletimCalibAberto()){\n" +
                                "                        pbmContext.getPneuCTR().fecharBoletimPneu();\n" +
                                "                        Intent it = new Intent(ListaPosPneuActivity.this, TelaInicialActivity.class);", getLocalClassName());
                        pbmContext.getPneuCTR().fecharBoletimPneu();
                        Intent it = new Intent(ListaPosPneuActivity.this, TelaInicialActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        AlertDialog.Builder alerta = new AlertDialog.Builder(PressaoEncPneuActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(\"POR FAVOR, TERMINE A CALIBRAGEM EM TODOS OS PNEU DO EQUIPAMENTO.\");\n" +
                                "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                "                            @Override\n" +
                                "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                "                            }\n" +
                                "                        });\n" +
                                "                        alerta.show();", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPosPneuActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("POR FAVOR, TERMINE A CALIBRAGEM EM TODOS OS PNEU DO EQUIPAMENTO.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();
                    }

                } else if(pbmContext.getVerTela() == 4) {

                    LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPosPneu.setOnClickListener(new View.OnClickListener() {\n" +
                            "            @Override\n" +
                            "            public void onClick(View v) {\n" +
                            "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPosPneuActivity.this);\n" +
                            "                alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                alerta.setMessage(\"DESEJA REALMENTE FINALIZAR O BOLETIM DE MANUTENÇÃO DE PNEU?\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPosPneuActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("DESEJA REALMENTE FINALIZAR O BOLETIM DE MANUTENÇÃO DE PNEU?");
                    alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                    "                        @Override\n" +
                                    "                        public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                            pbmContext.getPneuCTR().fecharBoletimPneu();\n" +
                                    "                            Intent it = new Intent(ListaPosPneuActivity.this, TelaInicialActivity.class);", getLocalClassName());
                            pbmContext.getPneuCTR().fecharBoletimPneu();
                            Intent it = new Intent(ListaPosPneuActivity.this, TelaInicialActivity.class);
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

            }
        });

        buttonCancPosPneu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPosPneu.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPosPneuActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE CANCELAR O CALIBRAÇÃO DE PNEU?\");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPosPneuActivity.this);
                alerta.setTitle("ATENÇÃO");
                String msg = "";
                if(pbmContext.getVerTela() == 3){
                    msg = "DESEJA REALMENTE CANCELAR O CALIBRAÇÃO DE PNEU?";
                } else if(pbmContext.getVerTela() == 4){
                    msg = "DESEJA REALMENTE CANCELAR O MANUTENÇÃO DE PNEU?";
                }
                alerta.setMessage(msg);
                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pbmContext.getPneuCTR().deletePneuAberto();\n" +
                                "                        Intent it = new Intent(ListaPosPneuActivity.this, MenuFuncaoActivity.class);", getLocalClassName());
                        pbmContext.getPneuCTR().deletePneuAberto();
                        Intent it = new Intent(ListaPosPneuActivity.this, MenuFuncaoActivity.class);
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

    }

    public void onBackPressed() {
    }

}
