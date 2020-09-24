package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.model.bean.estaticas.OSBean;

public class DescrOSActivity extends ActivityGeneric {

    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descr_os);

        TextView textViewCodEquip = (TextView) findViewById(R.id.textViewCodEquip);
        TextView textViewDescEquip = (TextView) findViewById(R.id.textViewDescEquip);
        Button buttonOkDescrOS = (Button) findViewById(R.id.buttonOkDescrOS);
        Button buttonCancDescrOS = (Button) findViewById(R.id.buttonCancDescrOS);

        pbmContext = (PBMContext) getApplication();

        textViewCodEquip.setText(String.valueOf(pbmContext.getMecanicoCTR().getOS().getEquipOS()));
        textViewDescEquip.setText(pbmContext.getMecanicoCTR().getOS().getDescrEquipOS());

        buttonOkDescrOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(DescrOSActivity.this, ItemOSListaActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancDescrOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(DescrOSActivity.this, OSActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
