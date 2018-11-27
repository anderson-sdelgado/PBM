package br.com.usinasantafe.pbm.bo;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Tempo {

    private static Tempo instance = null;
    private boolean envioDado;
	private Context context;
    
	private int conBoletim;
	
	public Tempo() {
		// TODO Auto-generated constructor stub
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

    public String datahoraNormal(){

        String dataCerta = "";

        Date dataHora = new Date();
        Calendar cal = Calendar.getInstance();
        Long dt =  dataHora.getTime();
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

    public String dataSHora(){

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

	public boolean isEnvioDado() {
		return envioDado;
	}

	public void setEnvioDado(boolean envioDado) {
		this.envioDado = envioDado;
	}

	public String verifDataApont(String dtStr){

	    String dataCerta = "";

        String diaStr = dtStr.substring(0, 2);
        String mesStr = dtStr.substring(3, 5);
        String anoStr = dtStr.substring(6, 10);
        String horaStr = dtStr.substring(11, 13);
        String minutoStr = dtStr.substring(14, 16);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaStr));
        cal.set(Calendar.MONTH, Integer.parseInt(mesStr) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(anoStr));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaStr));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutoStr));

        cal.add(Calendar.HOUR, +2);
        cal.add(Calendar.MINUTE, +5);

        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int ano = cal.get(Calendar.YEAR);
        int horas = cal.get(Calendar.HOUR_OF_DAY);
        int minutos = cal.get(Calendar.MINUTE);
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

        horaStr = "";
        if(horas < 10){
            horaStr = "0" + horas;
        }
        else{
            horaStr = String.valueOf(horas);
        }

        String minutosStr = "";
        if(minutos < 10){
            minutosStr = "0" + minutos;
        }
        else{
            minutosStr = String.valueOf(minutos);
        }

        dataCerta = ""+diaStr+"/"+mesStr+"/"+ano+" "+horaStr+":"+minutosStr;

        return dataCerta;

    }

}
