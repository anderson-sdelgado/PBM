package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class ListaPosPneuActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ListView posPneuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pos_pneu);

        pbmContext = (PBMContext) getApplication();

//        Button buttonAtualPosPneu = (Button) findViewById(R.id.buttonAtualPosPneu);
//
//        buttonAtualPosPneu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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
            itens = pbmContext.getPneuCTR().rEquipPneuCalibList();
        }
        else if(pbmContext.getVerTela() == 4){
            itens = pbmContext.getPneuCTR().rEquipPneuManutList();
        }

        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);
        ListView listaPosPneu = (ListView) findViewById(R.id.listaPosPneu);
        listaPosPneu.setAdapter(adapterList);
        listaPosPneu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaPosPneu.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                        "                String posPneu = textView.getText().toString();", getLocalClassName());
                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String posPneu = textView.getText().toString();

                if(pbmContext.getVerTela() == 3) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if(pbmContext.getVerTela() == 3) {\n" +
                            "                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());\n" +
                            "                    pbmContext.getPneuCTR().getItemCalibPneuBean().setIdPosItemCalibPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());\n" +
                            "                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);", getLocalClassName());
                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());
                    pbmContext.getPneuCTR().getItemCalibPneuBean().setIdPosItemCalibPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());

                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);
                    startActivity(it);
                    finish();

                } else if(pbmContext.getVerTela() == 4){

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pbmContext.getVerTela() == 4){\n" +
                            "                    pbmContext.getPneuCTR().setItemManutPneuBean(new ItemManutPneuBean());\n" +
                            "                    pbmContext.getPneuCTR().getItemManutPneuBean().setIdPosItemManutPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());\n" +
                            "                    Intent it = new Intent(ListaPosPneuActivity.this, PneuRetActivity.class);", getLocalClassName());
                    pbmContext.getPneuCTR().setItemManutPneuBean(new ItemManutPneuBean());
                    pbmContext.getPneuCTR().getItemManutPneuBean().setIdPosItemManutPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());

                    Intent it = new Intent(ListaPosPneuActivity.this, PneuRetActivity.class);
                    startActivity(it);
                    finish();

                }

            }

        });

    }

    public void onBackPressed() {
    }

}
