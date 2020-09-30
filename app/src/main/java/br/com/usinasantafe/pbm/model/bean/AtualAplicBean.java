package br.com.usinasantafe.pbm.model.bean;

/**
 * Created by anderson on 24/07/2017.
 */

public class AtualAplicBean {

    private Long idEquipAtual;
    private String versaoAtual;
    private String versaoNova;

    public AtualAplicBean() {
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

    public String getVersaoNova() {
        return versaoNova;
    }

    public void setVersaoNova(String versaoNova) {
        this.versaoNova = versaoNova;
    }
}
