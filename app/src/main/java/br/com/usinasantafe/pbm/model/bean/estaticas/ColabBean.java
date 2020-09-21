package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbcolabest")
public class ColabBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idColab;
    @DatabaseField
    private Long matricColab;
    @DatabaseField
    private String nomeColab;
    @DatabaseField
    private Long idEscalaTrabColab;

    public ColabBean() {
    }

    public Long getIdColab() {
        return idColab;
    }

    public void setIdColab(Long idColab) {
        this.idColab = idColab;
    }

    public Long getMatricColab() {
        return matricColab;
    }

    public void setMatricColab(Long matricColab) {
        this.matricColab = matricColab;
    }

    public String getNomeColab() {
        return nomeColab;
    }

    public void setNomeColab(String nomeColab) {
        this.nomeColab = nomeColab;
    }

    public Long getIdEscalaTrabColab() {
        return idEscalaTrabColab;
    }

    public void setIdEscalaTrabColab(Long idEscalaTrabColab) {
        this.idEscalaTrabColab = idEscalaTrabColab;
    }
}
