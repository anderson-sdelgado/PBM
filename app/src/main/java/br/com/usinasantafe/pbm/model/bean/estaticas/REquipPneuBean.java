package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;


@DatabaseTable(tableName="tbrequippneuest")
public class REquipPneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idREquipPneu;
    @DatabaseField
    private Long idEquip;
    @DatabaseField
    private Long idPosConfPneu;
    @DatabaseField
    private String posPneu;
    @DatabaseField
    private Long statusPneu;

    public REquipPneuBean() {
    }

    public Long getIdREquipPneu() {
        return idREquipPneu;
    }

    public void setIdREquipPneu(Long idREquipPneu) {
        this.idREquipPneu = idREquipPneu;
    }

    public Long getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
    }

    public Long getIdPosConfPneu() {
        return idPosConfPneu;
    }

    public void setIdPosConfPneu(Long idPosConfPneu) {
        this.idPosConfPneu = idPosConfPneu;
    }

    public String getPosPneu() {
        return posPneu;
    }

    public void setPosPneu(String posPneu) {
        this.posPneu = posPneu;
    }

    public Long getStatusPneu() {
        return statusPneu;
    }

    public void setStatusPneu(Long statusPneu) {
        this.statusPneu = statusPneu;
    }
}
