package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;

public class SenhaActivity extends ActivityGeneric {

    private EditText editTextSenha;
    private PBMContext pbmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        pbmContext = (PBMContext) getApplication();

        editTextSenha = (EditText)  findViewById(R.id.editTextSenha);
        Button btOkSenha =  (Button) findViewById(R.id.buttonOkSenha);
        Button btCancSenha = (Button) findViewById(R.id.buttonCancSenha);

        btOkSenha.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {

                if (!pbmContext.getConfigCTR().hasElemConfig()) {

                    Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                    startActivity(it);
                    finish();

                } else {

                    if (pbmContext.getConfigCTR().verConfig(editTextSenha.getText().toString())) {

                        Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                        startActivity(it);
                        finish();

                    }

                }

            }
        });

        btCancSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(SenhaActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
