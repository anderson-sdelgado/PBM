package br.com.usinasantafe.pbm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbapontavar")
public class ApontamentoTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idAponta;
    @DatabaseField
    private Long idBolAponta;
    @DatabaseField
    private Long idExtBolAponta;
    @DatabaseField
    private Long osAponta;
    @DatabaseField
    private Long itemOSAponta;
    @DatabaseField
    private Long paradaAponta;
    @DatabaseField
    private String dthrInicialAponta;
    @DatabaseField
    private String dthrFinalAponta;
    @DatabaseField
    private Long realizAponta;
    @DatabaseField
    private Long statusAponta; //0 - Não Enviado; 1 - Enviado

    public ApontamentoTO() {
    }

    public Long getIdAponta() {
        return idAponta;
    }

    public void setIdAponta(Long idAponta) {
        this.idAponta = idAponta;
    }

    public Long getIdBolAponta() {
        return idBolAponta;
    }

    public void setIdBolAponta(Long idBolAponta) {
        this.idBolAponta = idBolAponta;
    }

    public Long getIdExtBolAponta() {
        return idExtBolAponta;
    }

    public void setIdExtBolAponta(Long idExtBolAponta) {
        this.idExtBolAponta = idExtBolAponta;
    }

    public Long getOsAponta() {
        return osAponta;
    }

    public void setOsAponta(Long osAponta) {
        this.osAponta = osAponta;
    }

    public Long getItemOSAponta() {
        return itemOSAponta;
    }

    public void setItemOSAponta(Long itemOSAponta) {
        this.itemOSAponta = itemOSAponta;
    }

    public Long getParadaAponta() {
        return paradaAponta;
    }

    public void setParadaAponta(Long paradaAponta) {
        this.paradaAponta = paradaAponta;
    }

    public String getDthrInicialAponta() {
        return dthrInicialAponta;
    }

    public void setDthrInicialAponta(String dthrInicialAponta) {
        this.dthrInicialAponta = dthrInicialAponta;
    }

    public String getDthrFinalAponta() {
        return dthrFinalAponta;
    }

    public void setDthrFinalAponta(String dthrFinalAponta) {
        this.dthrFinalAponta = dthrFinalAponta;
    }

    public Long getRealizAponta() {
        return realizAponta;
    }

    public void setRealizAponta(Long realizAponta) {
        this.realizAponta = realizAponta;
    }

    public Long getStatusAponta() {
        return statusAponta;
    }

    public void setStatusAponta(Long statusAponta) {
        this.statusAponta = statusAponta;
    }
}
