package br.com.usinasantafe.pbm;

import android.app.Application;

import br.com.usinasantafe.pbm.control.ConfigCTR;
import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.control.PneuCTR;

public class PBMContext extends Application {

    public static String versaoAplic = "2.00";
    private ConfigCTR configCTR;
    private MecanicoCTR mecanicoCTR;
    private PneuCTR pneuCTR;
    private int verTela; //1 - Parada Normal; 2 - Parada para Fechar Boletim; 3 - Calibragem de Pneu; 4 - Troca de Pneu;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public PneuCTR getPneuCTR() {
        if(pneuCTR == null)
            pneuCTR = new PneuCTR();
        return pneuCTR;
    }

    public int getVerTela() {
        return verTela;
    }

    public void setVerTela(int verTela) {
        this.verTela = verTela;
    }
}
