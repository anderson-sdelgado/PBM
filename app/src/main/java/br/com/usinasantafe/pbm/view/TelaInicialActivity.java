package br.com.usinasantafe.pbm.view;

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
//            atualizarAplic();

        }
    };

    public void clearBD() {
        LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getCheckListCTR().deleteChecklist();\n" +
                "        pmmContext.getMotoMecFertCTR().deleteBolEnviado();\n" +
                "        pmmContext.getConfigCTR().deleteLogs();", getLocalClassName());
        pbmContext.getMecanicoCTR().deleteBoletimEnviado();

//        pbmContext.getMotoMecFertCTR().deleteBolEnviado();
//        pbmContext.getConfigCTR().deleteLogs();
    }

}