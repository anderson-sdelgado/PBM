package br.com.usinasantafe.pbm.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.usinasantafe.pbm.control.ConfigCTR;
import br.com.usinasantafe.pbm.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pbm.model.dao.LogErroDAO;


public class Tempo {

    private static Tempo instance = null;
	
	public Tempo() {
	}
	
    public static Tempo getInstance() {
        if (instance == null)
        instance = new Tempo();
        return instance;
    }

    private String dthrAtualBaseString(){
        Date dataHora = new Date();
        return dthrLongToString(dataHora.getTime() + dif());
    }

//    private Long addTimeZone(Long dthrLong){
//        TimeZone tz = TimeZone.getDefault();
//        Date date = new Date();
//        return dthrLong + tz.getOffset(date.getTime());
//    }

    public Long addMinutoLong(Long dthrLong, int minuto){
        dthrLong = dthrLong + (minuto * 60 * 1000);
        return dthrLong;
    }

    public Long subDiaLong(int dia){
        return dthrStringToLong(dthrAtualString()) - (dia*24*60*60*1000);
    }

    public Long dthrLongFinalizarBol(Long dthrLongInicialTurno, String dthrFinalTurno){
        Long dthrLongFinalTurno = dthrStringToLong(dthrFinalTurno);
        if(dthrLongInicialTurno > dthrLongFinalTurno){
            dthrLongFinalTurno = dthrLongFinalTurno + (1*24*60*60*1000);
        }
        return dthrLongFinalTurno;
    }

    ///////////////////////////////////// STRING TO LONG ///////////////////////////////////////////

    public Long dthrStringToLong(String dthrString){
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            date = format.parse(dthrString + ":00");
        } catch (ParseException e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }
        return date.getTime();
    }

    public Long dtStringToLong(String dtString){
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(dtString);
        } catch (ParseException e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }
        return date.getTime();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// LONG TO STRING ///////////////////////////////////////////

    public String dthrLongToString(Long dthrLong){
        Date dt = new Date (dthrLong);
        SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
        return df.format(dt);
    }

    public String dtLongToString(Long dthrLong){
        Date dt = new Date (dthrLong);
        SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        return df.format(dt);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// DATA/HORA ATUAL ///////////////////////////////////////////

    public String dthrAtualString(){
        return dthrLongToString(dthrAtualLong());
    }

    public String dtAtualString(){
        return dtLongToString(dthrAtualLong());
    }

    public Long dthrAtualLong(){
        return dthrStringToLong(dthrAtualBaseString());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// DATA/HORA ATUAL ///////////////////////////////////////////

    public boolean verifHora(String hrInicio){
        Long dthrLongInicio = dthrStringToLong(dtAtualString() + " " + hrInicio);
        if(dthrAtualLong() > dthrLongInicio){
            return false;
        } else {
            return true;
        }
    }

    public boolean verifDataHoraParada(String dthrUltApont, Long qtdeMinutosParada){
        Long dthrLongInicio = dthrStringToLong(dthrUltApont) + (qtdeMinutosParada * 60 * 1000);
        Long dthrAtualLong = dthrAtualLong();
        Log.i("PBM", "DATA ULTIMO APONTAMENTO = " + dthrUltApont);
        Log.i("PBM", "DATA ULTIMO APONTAMENTO MAIS O MINUTOS = " + dthrLongToString(dthrLongInicio));
        Log.i("PBM", "DATA ATUAL = " + dthrLongToString(dthrAtualLong));
        if(dthrAtualLong > dthrLongInicio){
            return false;
        } else {
            return true;
        }
    }

    public boolean verifDataHoraForcaFechBol(Long dthrLongUltApont, Long qtdeHoraForcaFechBol){
        Long dthrLongInicio = dthrLongUltApont + (qtdeHoraForcaFechBol * 60 * 60 * 1000);
        Long dthrAtualLong = dthrAtualLong();
        Log.i("PBM", "DATA ULTIMO APONTAMENTO = " + dthrLongToString(dthrLongInicio));
        Log.i("PBM", "DATA ATUAL = " + dthrLongToString(dthrAtualLong));
        if(dthrAtualLong > dthrLongInicio){
            return true;
        } else {
            return false;
        }
    }

    public boolean verDthrServ(String dthrServ){

        Long longDtServ = dthrStringToLong(dthrServ);

        Date dataHoraCel = new Date();
        Long longDtCel =  dataHoraCel.getTime();

        Long dthrDif =  longDtServ - longDtCel;
        Long diaDif = dthrDif/24/60/60/1000;

        if((diaDif >= 0) && (diaDif <= 15)){
            return true;
        }
        else{
            return false;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// DIFERENÇA SERVIDOR ///////////////////////////////////////

    public Long dif(){
        ConfigBean configBean = new ConfigBean();
        List<ConfigBean> configList = configBean.all();
        Long dif = 0L;
        if(configList.size() > 0){
            configBean = configList.get(0);
            dif = configBean.getDifDthrConfig();
        }
        configList.clear();
        return dif;
    }

    public void zerarDifTempo(){
        ConfigCTR configCTR = new ConfigCTR();
        if (configCTR.hasElemConfig()) {
            if (verDthrServ(configCTR.getConfig().getDtServConfig())) {
                configCTR.setDifDthrConfig(0L);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}
