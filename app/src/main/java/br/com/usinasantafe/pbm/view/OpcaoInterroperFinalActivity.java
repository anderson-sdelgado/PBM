package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class OpcaoInterroperFinalActivity extends ActivityGeneric {

    private ListView listView;
    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_interroper_final);

        pbmContext = (PBMContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"FINALIZAR\");\n" +
                "        itens.add(\"INTERROPER\");\n" +
                "        itens.add(\"CANCELAR\");\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        lista = (ListView) findViewById(R.id.listViewMenuOpcao);\n" +
                "        lista.setAdapter(adapterList);", getLocalClassName());

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("FINALIZAR");
        itens.add("INTERROPER");
        itens.add("CANCELAR");

        AdapterList adapterList = new AdapterList(this, itens);
        listView = findViewById(R.id.listViewMenuOpcao);
        listView.setAdapter(adapterList);
        listView.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                    "                                    long id) {\n" +
                    "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                    "                String text = textView.getText().toString();", getLocalClassName());
            TextView textView = v.findViewById(R.id.textViewItemList);
            String text = textView.getText().toString();

            switch (text) {
                case "FINALIZAR": {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"FINALIZAR\")) {\n" +
                            "                    pbmContext.getMecanicoCTR().finalizarApont();\n" +
                            "                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);", getLocalClassName());
                    pbmContext.getMecanicoCTR().finalizarApont();
                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);
                    startActivity(it);
                    finish();
                    break;

                }
                case "INTERROPER": {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"INTERROPER\")) {\n" +
                            "                    pbmContext.getMecanicoCTR().interroperApont();\n" +
                            "                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);", getLocalClassName());
                    pbmContext.getMecanicoCTR().interroperApont();
                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);
                    startActivity(it);
                    finish();
                    break;

                }
                case "CANCELAR": {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CANCELAR\")) {\n" +
                            "                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);", getLocalClassName());
                    Intent it = new Intent(OpcaoInterroperFinalActivity.this, TelaInicialActivity.class);
                    startActivity(it);
                    finish();
                    break;

                }
            }

        });

    }

    public void onBackPressed()  {
    }

}
