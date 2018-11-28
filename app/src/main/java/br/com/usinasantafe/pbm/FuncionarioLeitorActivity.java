package br.com.usinasantafe.pbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.to.estaticas.ColabTO;

public class FuncionarioLeitorActivity extends ActivityGeneric {

    public static final int REQUEST_CODE = 0;
    private PBMContext pbmContext;
    private TextView txtRetFunc;
    private String matricula;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_leitor);

        pbmContext = (PBMContext) getApplication();

        txtRetFunc = (TextView) findViewById(R.id.txtRetFunc);
        Button buttonOkFunc = (Button) findViewById(R.id.buttonOkFunc);
        Button buttonCancFunc = (Button) findViewById(R.id.buttonCancFunc);
        Button buttonDigFunc = (Button) findViewById(R.id.buttonDigFunc);
        nome = null;

        txtRetFunc.setText("");

        buttonOkFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(FuncionarioLeitorActivity.this, ListaTurnoActivity.class);
                startActivity(it);
                finish();

            }
        });

        buttonCancFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(FuncionarioLeitorActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonDigFunc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(FuncionarioLeitorActivity.this, FuncionarioDigActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void callZXing(View view){
        Intent it = new Intent(FuncionarioLeitorActivity.this, br.com.usinasantafe.pbm.zxing.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            matricula = data.getStringExtra("SCAN_RESULT");
            if(matricula.length() == 8){
                matricula = matricula.substring(0,7);
                ColabTO colabTO = new ColabTO();
                List listColab = colabTO.get("matricColab", Long.parseLong(matricula));
                if (listColab.size() > 0) {
                    colabTO = (ColabTO) listColab.get(0);
                    nome = colabTO.getNomeColab();
                    txtRetFunc.setText(matricula + "\n" + nome);
                }
                else{
                    txtRetFunc.setText("Funcionario Inexistente");
                }
            }
        }

    }

    public void onBackPressed()  {
    }

}
