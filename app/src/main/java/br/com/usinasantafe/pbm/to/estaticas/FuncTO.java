package br.com.usinasantafe.pbm.to.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbfuncest")
public class FuncTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idFunc;
    @DatabaseField
    private Long matricFunc;
    @DatabaseField
    private String nomeFunc;

    public FuncTO() {
    }

    public Long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(Long idFunc) {
        this.idFunc = idFunc;
    }

    public Long getMatricFunc() {
        return matricFunc;
    }

    public void setMatricFunc(Long matricFunc) {
        this.matricFunc = matricFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public void setNomeFunc(String nomeFunc) {
        this.nomeFunc = nomeFunc;
    }
}
