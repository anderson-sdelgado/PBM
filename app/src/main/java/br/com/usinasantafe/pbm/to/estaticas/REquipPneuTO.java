package br.com.usinasantafe.pbm.to.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;


@DatabaseTable(tableName="tbrequippneuest")
public class REquipPneuTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idREquipPneu;
    @DatabaseField
    private Long idEquip;
    @DatabaseField
    private Long idPosConfPneu;
    @DatabaseField
    private String posPneu;

    public REquipPneuTO() {
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
}
