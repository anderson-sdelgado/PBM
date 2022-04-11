package br.com.usinasantafe.pbm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbboletimvar")
public class BoletimMecanBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idBolMecan;
    @DatabaseField
    private Long idExtBolMecan;
    @DatabaseField
    private Long idFuncBolMecan;
    @DatabaseField
    private Long idEquipBolMecan;
    @DatabaseField
    private String dthrInicialBolMecan;
    @DatabaseField
    private Long dthrInicialLongBolMecan;
    @DatabaseField
    private String dthrFinalBolMecan;
    @DatabaseField
    private Long dthrFinalLongBolMecan;
    @DatabaseField
    private Long statusFechBolMecan; // 0 - Automaticamente; 1 - Normal
    @DatabaseField
    private Long statusBolMecan; // 1 - Aberto; 2 - Fechado; 3 - Enviado
    @DatabaseField
    private Long statusApontBolMecan; //0 - Não esta sendo apontado; 1 - Esta sendo Apontado

    public BoletimMecanBean() {
    }

    public Long getIdBolMecan() {
        return idBolMecan;
    }

    public void setIdBolMecan(Long idBolMecan) {
        this.idBolMecan = idBolMecan;
    }

    public Long getIdExtBolMecan() {
        return idExtBolMecan;
    }

    public void setIdExtBolMecan(Long idExtBolMecan) {
        this.idExtBolMecan = idExtBolMecan;
    }

    public Long getIdFuncBolMecan() {
        return idFuncBolMecan;
    }

    public void setIdFuncBolMecan(Long idFuncBolMecan) {
        this.idFuncBolMecan = idFuncBolMecan;
    }

    public Long getIdEquipBolMecan() {
        return idEquipBolMecan;
    }

    public void setIdEquipBolMecan(Long idEquipBolMecan) {
        this.idEquipBolMecan = idEquipBolMecan;
    }

    public String getDthrInicialBolMecan() {
        return dthrInicialBolMecan;
    }

    public void setDthrInicialBolMecan(String dthrInicialBolMecan) {
        this.dthrInicialBolMecan = dthrInicialBolMecan;
    }

    public Long getDthrInicialLongBolMecan() {
        return dthrInicialLongBolMecan;
    }

    public void setDthrInicialLongBolMecan(Long dthrInicialLongBolMecan) {
        this.dthrInicialLongBolMecan = dthrInicialLongBolMecan;
    }

    public String getDthrFinalBolMecan() {
        return dthrFinalBolMecan;
    }

    public void setDthrFinalBolMecan(String dthrFinalBolMecan) {
        this.dthrFinalBolMecan = dthrFinalBolMecan;
    }

    public Long getDthrFinalLongBolMecan() {
        return dthrFinalLongBolMecan;
    }

    public void setDthrFinalLongBolMecan(Long dthrFinalLongBolMecan) {
        this.dthrFinalLongBolMecan = dthrFinalLongBolMecan;
    }

    public Long getStatusFechBolMecan() {
        return statusFechBolMecan;
    }

    public void setStatusFechBolMecan(Long statusFechBolMecan) {
        this.statusFechBolMecan = statusFechBolMecan;
    }

    public Long getStatusBolMecan() {
        return statusBolMecan;
    }

    public void setStatusBolMecan(Long statusBolMecan) {
        this.statusBolMecan = statusBolMecan;
    }

    public Long getStatusApontBolMecan() {
        return statusApontBolMecan;
    }

    public void setStatusApontBolMecan(Long statusApontBolMecan) {
        this.statusApontBolMecan = statusApontBolMecan;
    }
}
