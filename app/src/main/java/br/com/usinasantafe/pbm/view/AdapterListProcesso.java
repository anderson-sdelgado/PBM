package br.com.usinasantafe.pbm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pbm.R;
import br.com.usinasantafe.pbm.model.bean.variaveis.LogProcessoBean;

public class AdapterListProcesso extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private TextView textViewProcId;
    private TextView textViewProcDthr;
    private TextView textViewProcTela;
    private TextView textViewProcDescr;

    public AdapterListProcesso(Context context, List itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_log_processo, null);
        textViewProcId = view.findViewById(R.id.textViewProcId);
        textViewProcDthr = view.findViewById(R.id.textViewProcDthr);
        textViewProcTela = view.findViewById(R.id.textViewProcTela);
        textViewProcDescr = view.findViewById(R.id.textViewProcDescr);

        LogProcessoBean logProcessoBean = (LogProcessoBean) itens.get(position);

        textViewProcId.setText("ID: " + logProcessoBean.getIdLogProcesso());
        textViewProcDthr.setText("DTHR: " + logProcessoBean.getDthr());
        textViewProcTela.setText("TELA: " + logProcessoBean.getActivity());
        textViewProcDescr.setText(logProcessoBean.getProcesso());

        return view;
    }

}
