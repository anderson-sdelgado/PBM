package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pbm.to.estaticas.ColabTO;

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

                    ColabTO colabTO = pbmContext.getColabTO();

//                    Intent it = new Intent(MenuFuncaoActivity.this, OSActivity.class);
//                    startActivity(it);
//                    finish();

                } else if (text.equals("FINALIZAR/INTERROPER")) {
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
