package br.com.usinasantafe.pbm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbapontavar")
public class ApontMecanBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idApontMecan;
    @DatabaseField
    private Long idBolApontMecan;
    @DatabaseField
    private Long osApontMecan;
    @DatabaseField
    private Long itemOSApontMecan;
    @DatabaseField
    private Long paradaApontMecan;
    @DatabaseField
    private String dthrInicialApontMecan;
    @DatabaseField
    private Long dthrInicialLongApontMecan;
    @DatabaseField
    private String dthrFinalApontMecan;
    @DatabaseField
    private Long dthrFinalLongApontMecan;
    @DatabaseField
    private Long realizApontMecan;
    @DatabaseField
    private Long statusApontMecan;
    // 1 - Aberto Não Enviado;
    // 2 - Aberto Enviado;
    // 3 - Fechado Não Enviado;
    // 4 - Fechado Enviado;

    public ApontMecanBean() {
    }

    public Long getIdApontMecan() {
        return idApontMecan;
    }

    public void setIdApontMecan(Long idApontMecan) {
        this.idApontMecan = idApontMecan;
    }

    public Long getIdBolApontMecan() {
        return idBolApontMecan;
    }

    public void setIdBolApontMecan(Long idBolApontMecan) {
        this.idBolApontMecan = idBolApontMecan;
    }

    public Long getOsApontMecan() {
        return osApontMecan;
    }

    public void setOsApontMecan(Long osApontMecan) {
        this.osApontMecan = osApontMecan;
    }

    public Long getItemOSApontMecan() {
        return itemOSApontMecan;
    }

    public void setItemOSApontMecan(Long itemOSApontMecan) {
        this.itemOSApontMecan = itemOSApontMecan;
    }

    public Long getParadaApontMecan() {
        return paradaApontMecan;
    }

    public void setParadaApontMecan(Long paradaApontMecan) {
        this.paradaApontMecan = paradaApontMecan;
    }

    public String getDthrInicialApontMecan() {
        return dthrInicialApontMecan;
    }

    public void setDthrInicialApontMecan(String dthrInicialApontMecan) {
        this.dthrInicialApontMecan = dthrInicialApontMecan;
    }

    public Long getDthrInicialLongApontMecan() {
        return dthrInicialLongApontMecan;
    }

    public void setDthrInicialLongApontMecan(Long dthrInicialLongApontMecan) {
        this.dthrInicialLongApontMecan = dthrInicialLongApontMecan;
    }

    public String getDthrFinalApontMecan() {
        return dthrFinalApontMecan;
    }

    public void setDthrFinalApontMecan(String dthrFinalApontMecan) {
        this.dthrFinalApontMecan = dthrFinalApontMecan;
    }

    public Long getDthrFinalLongApontMecan() {
        return dthrFinalLongApontMecan;
    }

    public void setDthrFinalLongApontMecan(Long dthrFinalLongApontMecan) {
        this.dthrFinalLongApontMecan = dthrFinalLongApontMecan;
    }

    public Long getRealizApontMecan() {
        return realizApontMecan;
    }

    public void setRealizApontMecan(Long realizApontMecan) {
        this.realizApontMecan = realizApontMecan;
    }

    public Long getStatusApontMecan() {
        return statusApontMecan;
    }

    public void setStatusApontMecan(Long statusApontMecan) {
        this.statusApontMecan = statusApontMecan;
    }
}
