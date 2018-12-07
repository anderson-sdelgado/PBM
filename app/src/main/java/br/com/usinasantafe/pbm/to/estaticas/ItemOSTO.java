package br.com.usinasantafe.pbm.to.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbitemosest")
public class ItemOSTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idItemOS;
    @DatabaseField
    private Long idOS;
    @DatabaseField
    private Long seqItemOS;
    @DatabaseField
    private Long idServItemOS;
    @DatabaseField
    private Long idCompItemOS;

    public ItemOSTO() {
    }

    public Long getIdItemOS() {
        return idItemOS;
    }

    public void setIdItemOS(Long idItemOS) {
        this.idItemOS = idItemOS;
    }

    public Long getIdOS() {
        return idOS;
    }

    public void setIdOS(Long idOS) {
        this.idOS = idOS;
    }

    public Long getSeqItemOS() {
        return seqItemOS;
    }

    public void setSeqItemOS(Long seqItemOS) {
        this.seqItemOS = seqItemOS;
    }

    public Long getIdServItemOS() {
        return idServItemOS;
    }

    public void setIdServItemOS(Long idServItemOS) {
        this.idServItemOS = idServItemOS;
    }

    public Long getIdCompItemOS() {
        return idCompItemOS;
    }

    public void setIdCompItemOS(Long idCompItemOS) {
        this.idCompItemOS = idCompItemOS;
    }
}
