package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbescalatrabest")
public class EscalaTrabBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idEscalaTrab;
    @DatabaseField
    private String horarioEntEscalaTrab;
    @DatabaseField
    private String horarioSaiEscalaTrab;

    public EscalaTrabBean() {
    }

    public Long getIdEscalaTrab() {
        return idEscalaTrab;
    }

    public void setIdEscalaTrab(Long idEscalaTrab) {
        this.idEscalaTrab = idEscalaTrab;
    }

    public String getHorarioEntEscalaTrab() {
        return horarioEntEscalaTrab;
    }

    public void setHorarioEntEscalaTrab(String horarioEntEscalaTrab) {
        this.horarioEntEscalaTrab = horarioEntEscalaTrab;
    }

    public String getHorarioSaiEscalaTrab() {
        return horarioSaiEscalaTrab;
    }

    public void setHorarioSaiEscalaTrab(String horarioSaiEscalaTrab) {
        this.horarioSaiEscalaTrab = horarioSaiEscalaTrab;
    }
}
