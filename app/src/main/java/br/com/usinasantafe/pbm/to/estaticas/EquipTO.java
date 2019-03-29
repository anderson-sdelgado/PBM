package br.com.usinasantafe.pbm.to.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbequipest")
public class EquipTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idEquip;
    @DatabaseField
    private Long numEquip;

    public EquipTO() {
    }

    public Long getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
    }

    public Long getNumEquip() {
        return numEquip;
    }

    public void setNumEquip(Long numEquip) {
        this.numEquip = numEquip;
    }
}
