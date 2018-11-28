package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pbm.to.variaveis.ConfiguracaoTO;

public class SenhaActivity extends ActivityGeneric {

    private EditText editTextSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        editTextSenha = (EditText)  findViewById(R.id.editTextSenha);
        Button btOkSenha =  (Button) findViewById(R.id.buttonOkSenha);
        Button btCancSenha = (Button) findViewById(R.id.buttonCancSenha);

        btOkSenha.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();

                if (!configuracaoTO.hasElements()) {

                    Intent it = new Intent(SenhaActivity.this, ConfiguracaoActivity.class);
                    startActivity(it);
                    finish();

                } else {

                    List<ConfiguracaoTO> lista = configuracaoTO.get("senhaConfig", editTextSenha.getText().toString());

                    if (lista.size() > 0) {

                        configuracaoTO.setEquipConfig(((ConfiguracaoTO) lista.get(0)).getEquipConfig());
                        Intent it = new Intent(SenhaActivity.this, ConfiguracaoActivity.class);
                        startActivity(it);
                        finish();

                    }

                }

            }
        });

        btCancSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(SenhaActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
