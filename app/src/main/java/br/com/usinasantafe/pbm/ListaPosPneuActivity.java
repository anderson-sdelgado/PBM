package br.com.usinasantafe.pbm;

import android.app.Activity;
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

import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.estaticas.REquipPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;
import br.com.usinasantafe.pbm.to.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemMedPneuTO;

public class ListaPosPneuActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private ListView lista;

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

        REquipPneuTO rEquipPneuTO = new REquipPneuTO();
        List rEquipPneuList = rEquipPneuTO.get("idEquip", pbmContext.getBoletimPneuTO().getEquipBolPneu());

        if(pbmContext.getVerTela() == 3){

            BoletimPneuTO boletimPneuTO = new BoletimPneuTO();
            List boletimPneuList = boletimPneuTO.get("statusBolPneu", 1L);

            if(boletimPneuList.size() == 0){

                ArrayList boletimPesqList = new ArrayList();
                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("atualBoletim");
                pesquisa.setValor(1L);
                boletimPesqList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("statusBoletim");
                pesquisa2.setValor(1L);
                boletimPesqList.add(pesquisa2);

                BoletimTO boletimTO = new BoletimTO();
                List boletimList = boletimTO.get(boletimPesqList);
                boletimTO = (BoletimTO) boletimList.get(0);
                boletimList.clear();
                boletimPesqList.clear();

                ColabTO colabTO = new ColabTO();
                List colabList = colabTO.get("idColab", boletimTO.getIdFuncBoletim());
                colabTO = (ColabTO) colabList.get(0);
                colabList.clear();

                boletimPneuTO.setIdApontBolPneu(0L);
                boletimPneuTO.setEquipBolPneu(pbmContext.getBoletimPneuTO().getEquipBolPneu());
                boletimPneuTO.setFuncBolPneu(colabTO.getMatricColab());
                boletimPneuTO.setDthrBolPneu(Tempo.getInstance().datahora());
                boletimPneuTO.setStatusBolPneu(1L);
                boletimPneuTO.insert();

                for(int i = 0; i < rEquipPneuList.size(); i++){
                    rEquipPneuTO = (REquipPneuTO) rEquipPneuList.get(i);
                    itens.add(rEquipPneuTO.getPosPneu());
                }

            }
            else{
                Log.i("PMM", "CHEGOU AKI 1");
                boletimPneuTO = (BoletimPneuTO) boletimPneuList.get(0);
                ItemMedPneuTO itemMedPneuTO = new ItemMedPneuTO();
                List itemMedPneuList = itemMedPneuTO.get("idBolItemMedPneu", boletimPneuTO.getIdBolPneu());
                boolean verCad;
                for(int i = 0; i < rEquipPneuList.size(); i++){
                    rEquipPneuTO = (REquipPneuTO) rEquipPneuList.get(i);
                    Log.i("PMM", "CHEGOU AKI 2 = " + rEquipPneuTO.getIdPosConfPneu());
                    verCad = true;
                    for(int j = 0; j < itemMedPneuList.size(); j++) {
                        itemMedPneuTO = (ItemMedPneuTO) itemMedPneuList.get(j);
                        Log.i("PMM", "CHEGOU AKI 3 = " + itemMedPneuTO.getPosItemMedPneu());
                        if(Objects.equals(rEquipPneuTO.getIdPosConfPneu(), itemMedPneuTO.getPosItemMedPneu())){
                            verCad = false;
                        }
                    }
                    if(verCad) {
                        Log.i("PMM", "CHEGOU AKI 4 = " + rEquipPneuTO.getPosPneu());
                        itens.add(rEquipPneuTO.getPosPneu());
                    }
                }
                itemMedPneuList.clear();
            }

            rEquipPneuList.clear();
            boletimPneuList.clear();

        }
        else if(pbmContext.getVerTela() == 4){

            for(int i = 0; i < rEquipPneuList.size(); i++){
                rEquipPneuTO = (REquipPneuTO) rEquipPneuList.get(i);
                itens.add(rEquipPneuTO.getPosPneu());
            }

        }

        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, R.layout.activity_item_lista, R.id.textViewItemList, itens);
        ListView listaPosPneu = (ListView) findViewById(R.id.listaPosPneu);
        listaPosPneu.setAdapter(adapterList);

        listaPosPneu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String posPneu = textView.getText().toString();

                REquipPneuTO rEquipPneuTO = new REquipPneuTO();
                ArrayList equipPneuPesqList = new ArrayList();
                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("idEquip");
                pesquisa.setValor(pbmContext.getBoletimPneuTO().getEquipBolPneu());
                equipPneuPesqList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("posPneu");
                pesquisa2.setValor(posPneu);
                equipPneuPesqList.add(pesquisa2);

                List rEquipPneuList = rEquipPneuTO.get(equipPneuPesqList);
                rEquipPneuTO = (REquipPneuTO) rEquipPneuList.get(0);
                rEquipPneuList.clear();
                equipPneuPesqList.clear();

                if(pbmContext.getVerTela() == 3) {
                    pbmContext.getItemMedPneuTO().setPosItemMedPneu(rEquipPneuTO.getIdPosConfPneu());
                    Intent it = new Intent(ListaPosPneuActivity.this, PneuCalibActivity.class);
                    startActivity(it);
                }
                else if(pbmContext.getVerTela() == 4){
                    pbmContext.getItemManutPneuTO().setPosItemManutPneu(rEquipPneuTO.getIdPosConfPneu());
                    Intent it = new Intent(ListaPosPneuActivity.this, PneuRetActivity.class);
                    startActivity(it);
                }

            }

        });

    }

    public void onBackPressed() {
    }

}
