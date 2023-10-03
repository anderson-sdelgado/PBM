package br.com.usinasantafe.pbm;

import android.app.Application;

import br.com.usinasantafe.pbm.control.ConfigCTR;
import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;

public class PBMContext extends Application {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static String versaoWS = "3.00";
    public static String versaoAPP = "3.01";
    private ConfigCTR configCTR;
    private MecanicoCTR mecanicoCTR;
    private int verTela;
    // 1 - Parada Normal;
    // 2 - Parada para Fechar Boletim;
    // 3 - Calibragem de Pneu;
    // 4 - Troca de Pneu;
    // 5 - Configuração;
    // 6 - Log

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public ConfigCTR getConfigCTR() {
        if(configCTR == null)
            configCTR = new ConfigCTR();
        return configCTR;
    }

    public MecanicoCTR getMecanicoCTR() {
        if(mecanicoCTR == null)
            mecanicoCTR = new MecanicoCTR();
        return mecanicoCTR;
    }


    public int getVerTela() {
        return verTela;
    }

    public void setVerTela(int verTela) {
        this.verTela = verTela;
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            LogErroDAO.getInstance().insertLogErro(ex);
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        }
    };

}
