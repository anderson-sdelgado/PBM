package br.com.usinasantafe.pbm;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.usinasantafe.pbm.util.ConexaoWeb;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.VerifDadosServ;
import br.com.usinasantafe.pbm.model.bean.estaticas.ColabBean;

public class MenuInicialActivity extends ActivityGeneric {

    private ListView lista;
    private ProgressDialog progressBar;
    private PBMContext pbmContext;

    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        pbmContext = (PBMContext) getApplication();

        textViewProcesso = (TextView) findViewById(R.id.textViewProcesso);

        if(!checkPermission(Manifest.permission.CAMERA)){
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        customHandler.postDelayed(updateTimerThread, 0);

        progressBar = new ProgressDialog(this);

        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if(conexaoWeb.verificaConexao(this))
        {

            if(pbmContext.getConfigCTR().hasElements()){

                progressBar.setCancelable(true);
                progressBar.setMessage("Buscando Atualização...");
                progressBar.show();

                VerifDadosServ.getInstance().verAtualAplic(pbmContext.versaoAplic, this, progressBar);
            }

        }
        else{
            startTimer("OFF");
        }

        listarMenuInicial();

    }

    private void listarMenuInicial() {

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listaMenuInicial);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {

                    if(pbmContext.getConfigCTR().hasElements() && pbmContext.getMecanicoCTR().hasElementsColab()) {

                        pbmContext.getPneuCTR().clear();

                        Intent it = new Intent(MenuInicialActivity.this, LeitorFuncActivity.class);
                        startActivity(it);
                        finish();
                    }

                } else if (text.equals("CONFIGURAÇÃO")) {

                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("SAIR")) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }

        });

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void onBackPressed()  {
    }

    public void startTimer(String retorno) {

        Intent intent = new Intent(this, ReceberAlarme.class);
        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);

        if(!retorno.equals("OFF")){

            int pos1 = retorno.indexOf("=") + 1;
            int pos2 = retorno.indexOf("_") + 1;
            String minutos = retorno.substring(pos1, (pos2 - 1));

            pbmContext.getMecanicoCTR().insertParametro(Long.parseLong(minutos));

        }


        if(progressBar.isShowing()){
            progressBar.dismiss();
        }

        if(alarmeAtivo){

            Log.i("PMM", "NOVO TIMER");
            PendingIntent p = PendingIntent.getBroadcast(getApplicationContext(), 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 1);

            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, p);

        }
        else{
            Log.i("PMM", "TIMER já ativo");
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(pbmContext.getConfigCTR().hasElements()) {
                if (EnvioDadosServ.getInstance().getStatusEnvio() == 1) {
                    textViewProcesso.setTextColor(Color.YELLOW);
                    textViewProcesso.setText("Enviando Dados...");
                } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 2) {
                    textViewProcesso.setTextColor(Color.RED);
                    textViewProcesso.setText("Existem Dados para serem Enviados");
                } else if (EnvioDadosServ.getInstance().getStatusEnvio() == 3) {
                    textViewProcesso.setTextColor(Color.GREEN);
                    textViewProcesso.setText("Todos os Dados já foram Enviados");
                }
            }
            else{
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Aparelho sem Equipamento");
            }
            customHandler.postDelayed(this, 10000);
        }
    };

}
