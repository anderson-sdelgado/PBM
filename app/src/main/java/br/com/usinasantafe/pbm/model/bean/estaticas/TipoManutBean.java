package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbtipomanutest")
public class TipoManutBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idTipoManut;
    @DatabaseField
    private String descrTipoManut;

    public TipoManutBean() {
    }

    public Long getIdTipoManut() {
        return idTipoManut;
    }

    public void setIdTipoManut(Long idTipoManut) {
        this.idTipoManut = idTipoManut;
    }

    public String getDescrTipoManut() {
        return descrTipoManut;
    }

    public void setDescrTipoManut(String descrTipoManut) {
        this.descrTipoManut = descrTipoManut;
    }
}
