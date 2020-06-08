package br.com.usinasantafe.pbm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ParadaTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListHistorico extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;

    public AdapterListHistorico(Context context, List itens) {
        // TODO Auto-generated constructor stub
        this.itens = itens;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = layoutInflater.inflate(R.layout.activity_item_historico, null);
        TextView textViewHistApont = (TextView) view.findViewById(R.id.textViewHistApont);
        TextView textViewHistHrInicial = (TextView) view.findViewById(R.id.textViewHistHrInicial);
        TextView textViewHistHrFinal = (TextView) view.findViewById(R.id.textViewHistHrFinal);

        ApontTO apontTO = (ApontTO) itens.get(position);
        if(apontTO.getParadaApont() == 0) {
            textViewHistApont.setText("TRABALHANDO: OS " + apontTO.getOsApont() + " - ITEM " + apontTO.getItemOSApont());
            textViewHistApont.setTextColor(Color.BLUE);
        }
        else{
            ParadaTO paradaTO = new ParadaTO();
            List paradaList = paradaTO.get("idParada", apontTO.getParadaApont());
            paradaTO = (ParadaTO) paradaList.get(0);
            textViewHistApont.setText("PARADA: " + paradaTO.getCodParada() + " - " + paradaTO.getDescrParada());
            textViewHistApont.setTextColor(Color.RED);
        }

        textViewHistHrInicial.setText("HORÁRIO INICIAL: " + Tempo.getInstance().manipDHComTZ(apontTO.getDthrInicialApont()));
        textViewHistHrFinal.setText("HORÁRIO FINAL: " + Tempo.getInstance().manipDHComTZ(apontTO.getDthrFinalApont()));

        return view;
    }
}
