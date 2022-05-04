package br.com.usinasantafe.pbm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.variaveis.LogErroBean;


/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListErro extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private TextView textViewErroId;
    private TextView textViewErroEquip;
    private TextView textViewErroDthr;
    private TextView textViewErroDescr;

    public AdapterListErro(Context context, List itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_log_erro, null);
        textViewErroId = view.findViewById(R.id.textViewErroId);
        textViewErroEquip = view.findViewById(R.id.textViewErroEquip);
        textViewErroDthr = view.findViewById(R.id.textViewErroDthr);
        textViewErroDescr = view.findViewById(R.id.textViewErroDescr);

        LogErroBean logErroBean = (LogErroBean) itens.get(position);

        textViewErroId.setText("ID = " + logErroBean.getIdLogErro());
        textViewErroEquip.setText("ID EQUIP = " + logErroBean.getIdEquip());
        textViewErroDthr.setText("DTHR = " + logErroBean.getDthr());
        textViewErroDescr.setText(logErroBean.getException());

        return view;
    }

}
