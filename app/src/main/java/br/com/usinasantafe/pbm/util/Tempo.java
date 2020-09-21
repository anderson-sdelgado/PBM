package br.com.usinasantafe.pbm.util;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Tempo {

    private static Tempo instance = null;
    private boolean envioDado;
	private Context context;
    
	private int conBoletim;
	
	public Tempo() {
	}
	
    public static Tempo getInstance() {
        if (instance == null)
        instance = new Tempo();
        return instance;
    }

    public String datahora(){

        String dataCerta = "";

        TimeZone tz = TimeZone.getDefault();
        Date dataHora = new Date();
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        Long dt =  dataHora.getTime() - tz.getOffset(d.getTime());
        cal.setTimeInMillis(dt);

        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int ano = cal.get(Calendar.YEAR);
        int horas = cal.get(Calendar.HOUR_OF_DAY);
        int minutos = cal.get(Calendar.MINUTE);
        mes = mes + 1;

        String mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        String diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        String horasStr = "";
        if(horas < 10){
            horasStr = "0" + horas;
        }
        else{
            horasStr = String.valueOf(horas);
        }

        String minutosStr = "";
        if(minutos < 10){
            minutosStr = "0" + minutos;
        }
        else{
            minutosStr = String.valueOf(minutos);
        }

        dataCerta = ""+diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr;

        return dataCerta;

    }

    public String dataSHoraSemTZ(){

        String dataCerta = "";

        TimeZone tz = TimeZone.getDefault();
        Date dataHora = new Date();
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        Long dt =  dataHora.getTime() + tz.getOffset(d.getTime());
        cal.setTimeInMillis(dt);

        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int ano = cal.get(Calendar.YEAR);
        mes = mes + 1;

        String mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        String diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        dataCerta = ""+diaStr+"/"+mesStr+"/"+ano;

        return dataCerta;

    }

    public String dataSHoraComTZ(){

        String dataCerta = "";

        Date dataHora = new Date();
        Calendar cal = Calendar.getInstance();
        Long dt =  dataHora.getTime();
        cal.setTimeInMillis(dt);

        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int ano = cal.get(Calendar.YEAR);
        mes = mes + 1;

        String mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        String diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        dataCerta = ""+diaStr+"/"+mesStr+"/"+ano;

        return dataCerta;

    }


	public boolean isEnvioDado() {
		return envioDado;
	}

	public void setEnvioDado(boolean envioDado) {
		this.envioDado = envioDado;
	}

	public boolean verifDataHora(String dthrInicio){

        String diaStr = dthrInicio.substring(0, 2);
        String mesStr = dthrInicio.substring(3, 5);
        String anoStr = dthrInicio.substring(6, 10);
        String horaStr= dthrInicio.substring(11, 13);
        String minutoStr= dthrInicio.substring(14, 16);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaStr));
        cal.set(Calendar.MONTH, Integer.parseInt(mesStr) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(anoStr));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaStr));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutoStr));

        ParametroTO parametroTO = new ParametroTO();
        List parametroList = parametroTO.all();
        parametroTO = (ParametroTO) parametroList.get(0);

        Date dataHoraInicio = cal.getTime();
        dataHoraInicio = new Date(dataHoraInicio.getTime() + (parametroTO.getMinParametro() * 60 * 1000));

        TimeZone tz = TimeZone.getDefault();
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        Long dt =  dataHoraInicio.getTime() - tz.getOffset(d.getTime());
        calendar.setTimeInMillis(dt);

        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);
        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        mes = mes + 1;

        mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        String horasStr = "";
        if(horas < 10){
            horasStr = "0" + horas;
        }
        else{
            horasStr = String.valueOf(horas);
        }

        String minutosStr = "";
        if(minutos < 10){
            minutosStr = "0" + minutos;
        }
        else{
            minutosStr = String.valueOf(minutos);
        }

        Log.i("PBM", "DATA HORA DE INICIO  = " +diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr);

        Date dataHoraAtual = new Date();
        dataHoraAtual = new Date(dataHoraAtual.getTime() - tz.getOffset(d.getTime()));

        if(dataHoraAtual.after(dataHoraInicio)){
            Log.i("PBM", "DEPOIS");
            return false;
        }else{
            Log.i("PBM", "ANTES");
            return true;
        }

    }

    public String manipDHSemTZ(String dthr){

        String diaStr = dthr.substring(0, 2);
        String mesStr = dthr.substring(3, 5);
        String anoStr = dthr.substring(6, 10);
        String horaStr= dthr.substring(11, 13);
        String minutoStr= dthr.substring(14, 16);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaStr));
        cal.set(Calendar.MONTH, Integer.parseInt(mesStr) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(anoStr));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaStr));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutoStr));

        Date dataHoraInicio = cal.getTime();
        TimeZone tz = TimeZone.getDefault();
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        Long dt =  dataHoraInicio.getTime() - tz.getOffset(d.getTime());
        calendar.setTimeInMillis(dt);

        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);
        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        mes = mes + 1;

        mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        String horasStr = "";
        if(horas < 10){
            horasStr = "0" + horas;
        }
        else{
            horasStr = String.valueOf(horas);
        }

        String minutosStr = "";
        if(minutos < 10){
            minutosStr = "0" + minutos;
        }
        else{
            minutosStr = String.valueOf(minutos);
        }

        Log.i("PBM", "DATA HORA DE INICIO  = " +diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr);

        return diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr;

    }

    public String manipDHComTZ(String dthr){

        String diaStr = dthr.substring(0, 2);
        String mesStr = dthr.substring(3, 5);
        String anoStr = dthr.substring(6, 10);
        String horaStr= dthr.substring(11, 13);
        String minutoStr= dthr.substring(14, 16);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaStr));
        cal.set(Calendar.MONTH, Integer.parseInt(mesStr) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(anoStr));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaStr));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutoStr));

        Date dataHoraInicio = cal.getTime();
        TimeZone tz = TimeZone.getDefault();
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        Long dt =  dataHoraInicio.getTime() + tz.getOffset(d.getTime());
        calendar.setTimeInMillis(dt);

        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);
        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        mes = mes + 1;

        mesStr = "";
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        diaStr = "";
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        String horasStr = "";
        if(horas < 10){
            horasStr = "0" + horas;
        }
        else{
            horasStr = String.valueOf(horas);
        }

        String minutosStr = "";
        if(minutos < 10){
            minutosStr = "0" + minutos;
        }
        else{
            minutosStr = String.valueOf(minutos);
        }

        Log.i("PBM", "DATA HORA DE INICIO  = " +diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr);

        return diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr;

    }

}
