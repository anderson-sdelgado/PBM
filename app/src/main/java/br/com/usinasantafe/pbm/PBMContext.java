package br.com.usinasantafe.pbm;

import android.app.Application;

import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;

public class PBMContext extends Application {

    public static String versaoAplic = "1.1";
    private ColabTO colabTO;
    private ApontTO apontTO;
    private int verTela; //1 - Parada Normal; 2 - Parada para Fechar Boletim

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public ApontTO getApontTO() {
        if(apontTO == null)
            apontTO = new ApontTO();
        return apontTO;
    }

    public ColabTO getColabTO() {
        return colabTO;
    }

    public void setColabTO(ColabTO colabTO) {
        this.colabTO = colabTO;
    }

    public int getVerTela() {
        return verTela;
    }

    public void setVerTela(int verTela) {
        this.verTela = verTela;
    }
}
