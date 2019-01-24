package br.com.usinasantafe.pbm;

import android.app.Application;

import br.com.usinasantafe.pbm.to.estaticas.ColabTO;

public class PBMContext extends Application {

    public static String versaoAplic = "1.1";
    private ColabTO colabTO;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public ColabTO getColabTO() {
        return colabTO;
    }

    public void setColabTO(ColabTO colabTO) {
        this.colabTO = colabTO;
    }
}
