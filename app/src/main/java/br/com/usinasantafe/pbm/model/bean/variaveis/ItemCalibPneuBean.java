package br.com.usinasantafe.pbm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbitemcalibpneuvar")
public class ItemCalibPneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idItemCalibPneu;
    @DatabaseField
    private Long idBolItemCalibPneu;
    @DatabaseField
    private Long idPosItemCalibPneu;
    @DatabaseField
    private String nroPneuItemCalibPneu;
    @DatabaseField
    private Long pressaoEncItemCalibPneu;
    @DatabaseField
    private Long pressaoColItemCalibPneu;
    @DatabaseField
    private String dthrItemCalibPneu;

    public ItemCalibPneuBean() {
    }

    public Long getIdItemCalibPneu() {
        return idItemCalibPneu;
    }

    public void setIdItemCalibPneu(Long idItemCalibPneu) {
        this.idItemCalibPneu = idItemCalibPneu;
    }

    public Long getIdPosItemCalibPneu() {
        return idPosItemCalibPneu;
    }

    public void setIdPosItemCalibPneu(Long idPosItemCalibPneu) {
        this.idPosItemCalibPneu = idPosItemCalibPneu;
    }

    public String getNroPneuItemCalibPneu() {
        return nroPneuItemCalibPneu;
    }

    public void setNroPneuItemCalibPneu(String nroPneuItemCalibPneu) {
        this.nroPneuItemCalibPneu = nroPneuItemCalibPneu;
    }

    public Long getPressaoEncItemCalibPneu() {
        return pressaoEncItemCalibPneu;
    }

    public void setPressaoEncItemCalibPneu(Long pressaoEncItemCalibPneu) {
        this.pressaoEncItemCalibPneu = pressaoEncItemCalibPneu;
    }

    public Long getPressaoColItemCalibPneu() {
        return pressaoColItemCalibPneu;
    }

    public void setPressaoColItemCalibPneu(Long pressaoColItemCalibPneu) {
        this.pressaoColItemCalibPneu = pressaoColItemCalibPneu;
    }

    public Long getIdBolItemCalibPneu() {
        return idBolItemCalibPneu;
    }

    public void setIdBolItemCalibPneu(Long idBolItemCalibPneu) {
        this.idBolItemCalibPneu = idBolItemCalibPneu;
    }

    public String getDthrItemCalibPneu() {
        return dthrItemCalibPneu;
    }

    public void setDthrItemCalibPneu(String dthrItemCalibPneu) {
        this.dthrItemCalibPneu = dthrItemCalibPneu;
    }
}
