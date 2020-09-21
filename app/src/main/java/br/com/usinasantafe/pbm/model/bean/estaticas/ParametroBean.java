package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbparametroest")
public class ParametroBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long minParametro;

    public ParametroBean() {
    }

    public Long getMinParametro() {
        return minParametro;
    }

    public void setMinParametro(Long minParametro) {
        this.minParametro = minParametro;
    }
}
