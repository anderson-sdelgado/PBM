package br.com.usinasantafe.pbm.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.estaticas.REquipPneuBean;

/**
 * Created by anderson on 08/03/2018.
 */
public class AdapterListPosPneu extends BaseAdapter {

    private ArrayList itens;
    private LayoutInflater layoutInflater;
    private List resp;

    public AdapterListPosPneu(Context context, ArrayList itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_pos_pneu, null);
        TextView textViewPosPneu = view.findViewById(R.id.textViewPosPneu);

        REquipPneuBean rEquipPneuBean = (REquipPneuBean) itens.get(position);

        textViewPosPneu.setText(rEquipPneuBean.getPosPneu());

        if(rEquipPneuBean.getStatusPneu() == 1L) {
            textViewPosPneu.setTextColor(Color.RED);
        } else {
            textViewPosPneu.setTextColor(Color.BLUE);
        }

        return view;
    }
}
