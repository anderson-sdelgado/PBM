package br.com.usinasantafe.pbm.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.usinasantafe.pbm.PBMContext;
import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pbm.util.EnvioDadosServ;
import br.com.usinasantafe.pbm.util.VerifDadosServ;

public class TelaInicialActivity extends ActivityGeneric {

    private PBMContext pbmContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        pbmContext = (PBMContext) getApplication();
        customHandler.postDelayed(excluirBDThread, 0);

    }

    private Runnable excluirBDThread = new Runnable() {

        public void run() {

            LogProcessoDAO.getInstance().insertLogProcesso("clearBD();", getLocalClassName());
            clearBD();

            if(EnvioDadosServ.getInstance().verifDadosEnvio()){
                LogProcessoDAO.getInstance().insertLogProcesso("EnvioDadosServ.getInstance().verifDadosEnvio()", getLocalClassName());
                if(connectNetwork){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(connectNetwork){\n" +
                            "EnvioDadosServ.getInstance().envioDados()", getLocalClassName());
                    EnvioDadosServ.getInstance().envioDados(getLocalClassName());
                }
                else{
                    LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                            "                EnvioDadosServ.status = 1;", getLocalClassName());
                    EnvioDadosServ.status = 1;
                }
            }
            else{
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "            EnvioDadosServ.status = 3;", getLocalClassName());
                EnvioDadosServ.status = 3;
            }

            LogProcessoDAO.getInstance().insertLogProcesso("VerifDadosServ.status = 3;", getLocalClassName());
            VerifDadosServ.status = 3;

            LogProcessoDAO.getInstance().insertLogProcesso("atualizarAplic()", getLocalClassName());
            atualizarAplic();

        }
    };

    public void clearBD() {
        LogProcessoDAO.getInstance().insertLogProcesso("pbmContext.getMecanicoCTR().deleteBolMecanSemApont();\n" +
                "        pbmContext.getPneuCTR().deleteBoletimAberto();\n" +
                "        pbmContext.getMecanicoCTR().deleteBolMecanEnviado();\n" +
                "        pbmContext.getPneuCTR().deleteBoletimEnviado();\n" +
                "        pbmContext.getConfigCTR().deleteLogs();", getLocalClassName());
        pbmContext.getMecanicoCTR().deleteBolMecanSemApont();
        pbmContext.getPneuCTR().deleteBoletimAberto();
        pbmContext.getMecanicoCTR().deleteBolMecanEnviado();
        pbmContext.getPneuCTR().deleteBoletimEnviado();
        pbmContext.getConfigCTR().deleteLogs();
    }

    public void atualizarAplic(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void atualizarAplic(){", getLocalClassName());
        if (connectNetwork) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {", getLocalClassName());
            if (pbmContext.getConfigCTR().hasElemConfig()) {
                LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().hasElemConfig()\n" +
                        "                customHandler.postDelayed(updateTimerThread, 10000);", getLocalClassName());
                customHandler.postDelayed(encerraAtualThread, 10000);
                LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().verAtualAplic(pmmContext.versaoAplic, this, getLocalClassName());", getLocalClassName());
                pbmContext.getConfigCTR().verAtualAplic(pbmContext.versaoAPP, this, getLocalClassName());
            }
            else{
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "                VerifDadosServ.status = 3;\n" +
                        "goMenuInicial();", getLocalClassName());
                VerifDadosServ.status = 3;
                goMenuInicial();
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                    "                VerifDadosServ.status = 3;\n" +
                    "goMenuInicial();", getLocalClassName());
            VerifDadosServ.status = 3;
            goMenuInicial();
        }
    }

    private Runnable encerraAtualThread = new Runnable() {

        public void run() {
            LogProcessoDAO.getInstance().insertLogProcesso("    private Runnable updateTimerThread = new Runnable() {\n" +
                    "        public void run() {", getLocalClassName());
            LogProcessoDAO.getInstance().insertLogProcesso("verifEnvio();", getLocalClassName());
            if(VerifDadosServ.status < 3) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(VerifDadosServ.status < 3) {\n" +
                        "VerifDadosServ.getInstance().cancel();", getLocalClassName());
                VerifDadosServ.getInstance().cancel();
            }
            LogProcessoDAO.getInstance().insertLogProcesso("goMenuInicial();", getLocalClassName());
            goMenuInicial();
        }
    };

    public void goMenuInicial(){

        customHandler.removeCallbacks(encerraAtualThread);
        LogProcessoDAO.getInstance().insertLogProcesso("    public void goMenuInicial(){\n" +
                "        customHandler.removeCallbacks(encerraAtualThread);\n" +
                "        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);", getLocalClassName());
        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();

    }

}