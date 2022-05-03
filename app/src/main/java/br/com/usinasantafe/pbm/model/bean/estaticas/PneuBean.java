package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbpneuest")
public class PneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idPneu;
    @DatabaseField
    private String codPneu;

    public PneuBean() {
    }

    public Long getIdPneu() {
        return idPneu;
    }

    public void setIdPneu(Long idPneu) {
        this.idPneu = idPneu;
    }

    public String getCodPneu() {
        return codPneu;
    }

    public void setCodPneu(String codPneu) {
        this.codPneu = codPneu;
    }
}
