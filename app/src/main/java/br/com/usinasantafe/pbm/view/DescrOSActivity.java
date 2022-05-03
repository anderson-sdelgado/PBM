package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;

public class DescrOSActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descr_os);

        TextView textViewCodEquip = findViewById(R.id.textViewCodEquip);
        TextView textViewDescrEquip = findViewById(R.id.textViewDescrEquip);
        Button buttonOkDescrOS = findViewById(R.id.buttonOkDescrOS);
        Button buttonCancDescrOS = findViewById(R.id.buttonCancDescrOS);

        pbmContext = (PBMContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("textViewCodEquip.setText(String.valueOf(pbmContext.getMecanicoCTR().getOS().getEquipOS()));\n" +
                "        textViewDescrEquip.setText(pbmContext.getMecanicoCTR().getOS().getDescrEquipOS());", getLocalClassName());
        textViewCodEquip.setText(String.valueOf(pbmContext.getMecanicoCTR().getEquipOS().getNroEquip()));
            textViewDescrEquip.setText(pbmContext.getMecanicoCTR().getEquipOS().getDescrClasseEquip());

        buttonOkDescrOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkDescrOS.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(DescrOSActivity.this, ListaItemOSActivity.class);", getLocalClassName());
                Intent it = new Intent(DescrOSActivity.this, ListaItemOSActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancDescrOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancDescrOS.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(DescrOSActivity.this, OSActivity.class);", getLocalClassName());
                Intent it = new Intent(DescrOSActivity.this, OSActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
