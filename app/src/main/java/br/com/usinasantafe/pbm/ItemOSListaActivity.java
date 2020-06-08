package br.com.usinasantafe.pbm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pbm.bo.ConexaoWeb;
import br.com.usinasantafe.pbm.bo.ManipDadosReceb;
import br.com.usinasantafe.pbm.bo.Tempo;
import br.com.usinasantafe.pbm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pbm.to.estaticas.ColabTO;
import br.com.usinasantafe.pbm.to.estaticas.ComponenteTO;
import br.com.usinasantafe.pbm.to.estaticas.EscalaTrabTO;
import br.com.usinasantafe.pbm.to.estaticas.ItemOSTO;
import br.com.usinasantafe.pbm.to.estaticas.OSTO;
import br.com.usinasantafe.pbm.to.estaticas.ServicoTO;
import br.com.usinasantafe.pbm.to.variaveis.ApontTO;
import br.com.usinasantafe.pbm.to.variaveis.BoletimTO;

public class ItemOSListaActivity extends ActivityGeneric {

    private ListView lista;
    private List listItemOS;
    private PBMContext pbmContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item_os);

        pbmContext = (PBMContext) getApplication();

        Button buttonRetItemOS = (Button) findViewById(R.id.buttonRetItemOS);
        Button buttonAtualItemOS = (Button) findViewById(R.id.buttonAtualItemOS);

        OSTO osto = new OSTO();
        List osList = osto.get("nroOS", pbmContext.getApontTO().getOsApont());
        osto = (OSTO) osList.get(0);
        osList.clear();

        ItemOSTO itemOSTO = new ItemOSTO();
        listItemOS = itemOSTO.getAndOrderBy("idOS", osto.getIdOS(),"seqItemOS",true);

        ArrayList<String> itens = new ArrayList<String>();

        for(int i = 0; i < listItemOS.size(); i++){
            itemOSTO = (ItemOSTO) listItemOS.get(i);

            ServicoTO servicoTO = new ServicoTO();
            List servicoList = servicoTO.get("idServico", itemOSTO.getIdServItemOS());
            if(servicoList.size() > 0){
                servicoTO = (ServicoTO) servicoList.get(0);
            }
            else{
                servicoTO.setDescrServico("SERVIÇO INEXISTENTE");
            }
            servicoList.clear();

            ComponenteTO componenteTO = new ComponenteTO();
            List componenteList = componenteTO.get("idComponente", itemOSTO.getIdCompItemOS());
            if(componenteList.size() > 0){
                componenteTO = (ComponenteTO) componenteList.get(0);
            }
            else{
                componenteTO.setCodComponente("0");
                componenteTO.setDescrComponente("COMPONENTE INEXISTENTE");
            }
            componenteList.clear();

            itens.add(itemOSTO.getSeqItemOS() + " - " + servicoTO.getDescrServico() + " - "
                    + componenteTO.getCodComponente() + " - " + componenteTO.getDescrComponente());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listItemOS);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                ItemOSTO itemOSTO = (ItemOSTO) listItemOS.get(position);
                pbmContext.getApontTO().setItemOSApont(itemOSTO.getSeqItemOS());

                ArrayList boletimPesqList = new ArrayList();
                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("atualBoletim");
                pesquisa.setValor(1L);
                boletimPesqList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("statusBoletim");
                pesquisa2.setValor(1L);
                boletimPesqList.add(pesquisa2);

                BoletimTO boletimTO = new BoletimTO();
                List boletimList = boletimTO.get(boletimPesqList);
                boletimTO = (BoletimTO) boletimList.get(0);

                ApontTO apontaTO = new ApontTO();
                List apontList = apontaTO.getAndOrderBy("idBolApont", boletimTO.getIdBoletim(), "idApont", false);

                ApontTO apontTO = new ApontTO();

                if(apontList.size() > 0) {
                    apontaTO = (ApontTO) apontList.get(0);
                    apontaTO.setDthrFinalApont(Tempo.getInstance().datahora());
                    apontaTO.setStatusApont(0L);
                    apontaTO.update();

                    apontTO.setDthrInicialApont(Tempo.getInstance().datahora());

                }else{
                    ColabTO colabTO = new ColabTO();
                    List colabList = colabTO.get("idColab", boletimTO.getIdFuncBoletim());
                    colabTO = (ColabTO) colabList.get(0);
                    EscalaTrabTO escalaTrabTO = new EscalaTrabTO();
                    List escalaTrabList = escalaTrabTO.get("idEscalaTrab",colabTO.getIdEscalaTrabColab());
                    escalaTrabTO = (EscalaTrabTO) escalaTrabList.get(0);
                    apontTO.setDthrInicialApont(Tempo.getInstance().manipDHSemTZ(Tempo.getInstance().dataSHoraSemTZ() + " " + escalaTrabTO.getHorarioEntEscalaTrab()));
                }

                apontTO.setDthrFinalApont("");
                apontTO.setIdBolApont(boletimTO.getIdBoletim());
                apontTO.setIdExtBolApont(boletimTO.getIdExtBoletim());
                apontTO.setOsApont(pbmContext.getApontTO().getOsApont());
                apontTO.setItemOSApont(pbmContext.getApontTO().getItemOSApont());
                apontTO.setParadaApont(0L);
                apontTO.setRealizApont(0L);
                apontTO.setStatusApont(0L);
                apontTO.insert();

                Intent it = new Intent(ItemOSListaActivity.this, MenuInicialActivity.class);
//                Intent it = new Intent(ItemOSListaActivity.this, MenuFuncaoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetItemOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ItemOSListaActivity.this, OSActivity.class);
                startActivity(it);
            }
        });

        buttonAtualItemOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ItemOSListaActivity.this)) {
                    progressBar = new ProgressDialog(ItemOSListaActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();
                    ManipDadosReceb.getInstance().setContext(ItemOSListaActivity.this);
                    ManipDadosReceb.getInstance().atualItemOSBD(progressBar);
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ItemOSListaActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();
                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
