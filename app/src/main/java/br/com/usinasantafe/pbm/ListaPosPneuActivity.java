package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.usinasantafe.pbm.model.bean.variaveis.ItemCalibPneuBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ItemManutPneuBean;
import br.com.usinasantafe.pbm.util.Tempo;
import br.com.usinasantafe.pbm.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class ListaPosPneuActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ListView posPneuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pos_pneu);

        pbmContext = (PBMContext) getApplication();

        Button buttonAtualPosPneu = (Button) findViewById(R.id.buttonAtualPosPneu);

        buttonAtualPosPneu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String posPneu = textView.getText().toString();

                if(pbmContext.getVerTela() == 3) {

                    pbmContext.getPneuCTR().setItemCalibPneuBean(new ItemCalibPneuBean());
                    pbmContext.getPneuCTR().getItemCalibPneuBean().setPosItemCalibPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());

                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);
                    startActivity(it);
                    finish();

                }
                else if(pbmContext.getVerTela() == 4){

                    pbmContext.getPneuCTR().setItemManutPneuBean(new ItemManutPneuBean());
                    pbmContext.getPneuCTR().getItemManutPneuBean().setPosItemManutPneu(pbmContext.getPneuCTR().getREquipPneu(Long.parseLong(posPneu)).getIdPosConfPneu());

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
