package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbparadaest")
public class ParadaBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idParada;
    @DatabaseField
    private Long codParada;
    @DatabaseField
    private String descrParada;

    public ParadaBean() {
    }

    public Long getIdParada() {
        return idParada;
    }

    public void setIdParada(Long idParada) {
        this.idParada = idParada;
    }

    public Long getCodParada() {
        return codParada;
    }

    public void setCodParada(Long codParada) {
        this.codParada = codParada;
    }

    public String getDescrParada() {
        return descrParada;
    }

    public void setDescrParada(String descrParada) {
        this.descrParada = descrParada;
    }

}
