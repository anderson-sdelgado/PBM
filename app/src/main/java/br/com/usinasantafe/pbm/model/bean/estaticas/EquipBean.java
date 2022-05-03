package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbequipest")
public class EquipBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idEquip;
    @DatabaseField
    private Long nroEquip;
    @DatabaseField
    private String descrClasseEquip;

    public EquipBean() {
    }

    public Long getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
    }

    public Long getNroEquip() {
        return nroEquip;
    }

    public void setNroEquip(Long nroEquip) {
        this.nroEquip = nroEquip;
    }

    public String getDescrClasseEquip() {
        return descrClasseEquip;
    }

    public void setDescrClasseEquip(String descrClasseEquip) {
        this.descrClasseEquip = descrClasseEquip;
    }
}
