package br.com.usinasantafe.pbm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbapontavar")
public class ApontTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idApont;
    @DatabaseField
    private Long idBolApont;
    @DatabaseField
    private Long idExtBolApont;
    @DatabaseField
    private Long osApont;
    @DatabaseField
    private Long itemOSApont;
    @DatabaseField
    private Long paradaApont;
    @DatabaseField
    private String dthrInicialApont;
    @DatabaseField
    private String dthrFinalApont;
    @DatabaseField
    private Long realizApont;
    @DatabaseField
    private Long statusApont; //0 - Não Enviado; 1 - Enviado

    public ApontTO() {
    }

    public Long getIdApont() {
        return idApont;
    }

    public void setIdApont(Long idApont) {
        this.idApont = idApont;
    }

    public Long getIdBolApont() {
        return idBolApont;
    }

    public void setIdBolApont(Long idBolApont) {
        this.idBolApont = idBolApont;
    }

    public Long getIdExtBolApont() {
        return idExtBolApont;
    }

    public void setIdExtBolApont(Long idExtBolApont) {
        this.idExtBolApont = idExtBolApont;
    }

    public Long getOsApont() {
        return osApont;
    }

    public void setOsApont(Long osApont) {
        this.osApont = osApont;
    }

    public Long getItemOSApont() {
        return itemOSApont;
    }

    public void setItemOSApont(Long itemOSApont) {
        this.itemOSApont = itemOSApont;
    }

    public Long getParadaApont() {
        return paradaApont;
    }

    public void setParadaApont(Long paradaApont) {
        this.paradaApont = paradaApont;
    }

    public String getDthrInicialApont() {
        return dthrInicialApont;
    }

    public void setDthrInicialApont(String dthrInicialApont) {
        this.dthrInicialApont = dthrInicialApont;
    }

    public String getDthrFinalApont() {
        return dthrFinalApont;
    }

    public void setDthrFinalApont(String dthrFinalApont) {
        this.dthrFinalApont = dthrFinalApont;
    }

    public Long getRealizApont() {
        return realizApont;
    }

    public void setRealizApont(Long realizApont) {
        this.realizApont = realizApont;
    }

    public Long getStatusApont() {
        return statusApont;
    }

    public void setStatusApont(Long statusApont) {
        this.statusApont = statusApont;
    }



}
