package br.com.usinasantafe.pbm.to.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbparametroest")
public class ParametroTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long minParametro;

    public ParametroTO() {
    }

    public Long getMinParametro() {
        return minParametro;
    }

    public void setMinParametro(Long minParametro) {
        this.minParametro = minParametro;
    }
}
