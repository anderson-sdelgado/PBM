package br.com.usinasantafe.pbm.model.bean;

/**
 * Created by anderson on 24/07/2017.
 */
public class AtualAplicBean {

    private Long idEquip;
    private Long nroEquip;
    private String versao;
    private Long flagAtualApp;
    private Long minutosParada;
    private Long horaFechBoletim;
    private String token;

    public AtualAplicBean() {
    }

    public Long getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
    }

    public Long getNroEquip() {
        return nroEquip;
    }

    public void setNroEquip(Long nroEquip) {
        this.nroEquip = nroEquip;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Long getFlagAtualApp() {
        return flagAtualApp;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
