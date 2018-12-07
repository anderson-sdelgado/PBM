package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.to.estaticas.ComponenteTO;
import br.com.usinasantafe.pbm.to.estaticas.ItemOSTO;
import br.com.usinasantafe.pbm.to.estaticas.ServicoTO;

public class ListaItemOSActivity extends ActivityGeneric {

    private ListView lista;
    private List listItemOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_os);

        Button buttonRetItemOS = (Button) findViewById(R.id.buttonRetItemOS);

        ItemOSTO itemOSTO = new ItemOSTO();
        listItemOS = itemOSTO.orderBy("seqItemOS",true);

        ArrayList<String> itens = new ArrayList<String>();

        for(int i = 0; i < listItemOS.size(); i++){
            itemOSTO = (ItemOSTO) listItemOS.get(i);

            ServicoTO servicoTO = new ServicoTO();
            List servicoList = servicoTO.get("idServico", itemOSTO.getIdServItemOS());
            servicoTO = (ServicoTO) servicoList.get(0);
            servicoList.clear();

            ComponenteTO componenteTO = new ComponenteTO();
            List componenteList = componenteTO.get("idComponente", itemOSTO.getIdCompItemOS());
            componenteTO = (ComponenteTO) componenteList.get(0);
            componenteList.clear();

            itens.add(itemOSTO.getSeqItemOS() + " - " + servicoTO.getDescrServico() + " - "
                    + componenteTO.getCodComponente() + " - " + componenteTO.getDescrComponente());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listItemOS);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ListaItemOSActivity.this, MenuFuncaoActivity.class);
                startActivity(it);

            }

        });

        buttonRetItemOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaItemOSActivity.this, OSActivity.class);
                startActivity(it);
            }
        });



    }

    public void onBackPressed()  {
    }

}
