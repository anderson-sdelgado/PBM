package br.com.usinasantafe.pbm.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.control.MecanicoCTR;
import br.com.usinasantafe.pbm.model.bean.estaticas.ParadaBean;
import br.com.usinasantafe.pbm.model.bean.variaveis.ApontMecanBean;

/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListHistorico extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;

    public AdapterListHistorico(Context context, List itens) {
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

        ApontMecanBean apontMecanBean = (ApontMecanBean) itens.get(position);

        textViewHistHrInicial.setText("HORÁRIO INICIAL: " + apontMecanBean.getDthrInicialApontMecan());
        textViewHistHrFinal.setText("HORÁRIO FINAL: " + apontMecanBean.getDthrFinalApontMecan());

        if(apontMecanBean.getParadaApontMecan() == 0) {
            textViewHistApont.setText("TRABALHANDO: OS " + apontMecanBean.getNroOSApontMecan() + " - ITEM " + apontMecanBean.getItemOSApontMecan());
            textViewHistApont.setTextColor(Color.BLUE);
        }
        else{

            MecanicoCTR mecanicoCTR = new MecanicoCTR();
            ParadaBean paradaBean = mecanicoCTR.getParadaId(apontMecanBean.getParadaApontMecan());
            textViewHistApont.setText("PARADA: " + paradaBean.getCodParada() + " - " + paradaBean.getDescrParada());
            textViewHistApont.setTextColor(Color.RED);
        }

        return view;
    }
}
