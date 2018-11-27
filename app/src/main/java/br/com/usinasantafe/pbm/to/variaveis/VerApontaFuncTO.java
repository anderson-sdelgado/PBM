package br.com.usinasantafe.pbm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbapontfuncvar")
public class VerApontaFuncTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idFuncApont;
    @DatabaseField
    private String dthrApont;

    public VerApontaFuncTO() {
    }

    public Long getIdFuncApont() {
        return idFuncApont;
    }

    public void setIdFuncApont(Long idFuncApont) {
        this.idFuncApont = idFuncApont;
    }

    public String getDthrApont() {
        return dthrApont;
    }

    public void setDthrApont(String dthrApont) {
        this.dthrApont = dthrApont;
    }
}
