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
    private Long ItemAponta;
    @DatabaseField
    private Long paradaAponta;
    @DatabaseField
    private String dthrAponta;
    @DatabaseField
    private Long statusItemAponta;

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

    public Long getItemAponta() {
        return ItemAponta;
    }

    public void setItemAponta(Long itemAponta) {
        ItemAponta = itemAponta;
    }

    public Long getParadaAponta() {
        return paradaAponta;
    }

    public void setParadaAponta(Long paradaAponta) {
        this.paradaAponta = paradaAponta;
    }

    public String getDthrAponta() {
        return dthrAponta;
    }

    public void setDthrAponta(String dthrAponta) {
        this.dthrAponta = dthrAponta;
    }

    public Long getStatusItemAponta() {
        return statusItemAponta;
    }

    public void setStatusItemAponta(Long statusItemAponta) {
        this.statusItemAponta = statusItemAponta;
    }
}
