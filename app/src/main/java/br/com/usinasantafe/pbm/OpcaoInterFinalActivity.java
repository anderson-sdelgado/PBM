package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OpcaoInterFinalActivity extends ActivityGeneric {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_inter_final);

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("FINALIZAR");
        itens.add("INTERROPER");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listViewMenuOpcao);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("FINALIZAR")) {
                    Intent it = new Intent(OpcaoInterFinalActivity.this, MenuFuncaoActivity.class);
                    startActivity(it);
                    finish();
                } else if (text.equals("INTERROPER")) {
                    Intent it = new Intent(OpcaoInterFinalActivity.this,  MenuFuncaoActivity.class);
                    startActivity(it);
                    finish();
                }

            }

        });

    }

    public void onBackPressed()  {
    }

}
