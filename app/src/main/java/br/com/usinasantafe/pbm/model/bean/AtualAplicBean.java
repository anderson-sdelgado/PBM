package br.com.usinasantafe.pbm.model.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by anderson on 24/07/2017.
 */

public class AtualAplicBean {

    private Long idEquipAtual;
    private String versaoAtual;
    private Long flagAtualApp;
    private Long minutosParada;
    private Long horaFechBoletim;

    public AtualAplicBean() {
    }

    public Long getFlagAtualApp() {
        return flagAtualApp;
    }

    public Long getIdEquipAtual() {
        return idEquipAtual;
    }

    public void setIdEquipAtual(Long idEquipAtual) {
        this.idEquipAtual = idEquipAtual;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    public void setFlagAtualApp(Long flagAtualApp) {
        this.flagAtualApp = flagAtualApp;
    }

    public Long getMinutosParada() {
        return minutosParada;
    }

    public void setMinutosParada(Long minutosParada) {
        this.minutosParada = minutosParada;
    }

    public Long getHoraFechBoletim() {
        return horaFechBoletim;
    }

    public void setHoraFechBoletim(Long horaFechBoletim) {
        this.horaFechBoletim = horaFechBoletim;
    }
}
