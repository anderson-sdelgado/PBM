package br.com.usinasantafe.pbm;

import android.app.Application;

import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemManutPneuTO;
import br.com.usinasantafe.pbm.to.variaveis.ItemMedPneuTO;

public class PBMContext extends Application {

    public static String versaoAplic = "1.0";
    private ApontTO apontTO;
    private BoletimPneuTO boletimPneuTO;
    private ItemManutPneuTO itemManutPneuTO;
    private ItemMedPneuTO itemMedPneuTO;
    private int verTela; //1 - Parada Normal; 2 - Parada para Fechar Boletim; 3 - Calibragem de Pneu; 4 - Troca de Pneu;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public BoletimPneuTO getBoletimPneuTO() {
        if(boletimPneuTO == null)
            boletimPneuTO = new BoletimPneuTO();
        return boletimPneuTO;
    }

    public ItemManutPneuTO getItemManutPneuTO() {
        if(itemManutPneuTO == null)
            itemManutPneuTO = new ItemManutPneuTO();
        return itemManutPneuTO;
    }

    public ItemMedPneuTO getItemMedPneuTO() {
        if(itemMedPneuTO == null)
            itemMedPneuTO = new ItemMedPneuTO();
        return itemMedPneuTO;
    }

    public ApontTO getApontTO() {
        if(apontTO == null)
            apontTO = new ApontTO();
        return apontTO;
    }

    public int getVerTela() {
        return verTela;
    }

    public void setVerTela(int verTela) {
        this.verTela = verTela;
    }
}
