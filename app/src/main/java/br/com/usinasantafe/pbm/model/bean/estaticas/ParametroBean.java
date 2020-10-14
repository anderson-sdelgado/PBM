package br.com.usinasantafe.pbm.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbparametroest")
public class ParametroBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idParametro;
    @DatabaseField
    private Long minutosParada;
    @DatabaseField
    private Long horaFechBoletim;

    public ParametroBean() {
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public Long getMinutosParada() {
        return minutosParada;
    }

    public void setMinutosParada(Long minutosParada) {
        this.minutosParada = minutosParada;
    }

    public Long getHoraFechBoletim() {
        return horaFechBoletim;
    }

    public void setHoraFechBoletim(Long horaFechBoletim) {
        this.horaFechBoletim = horaFechBoletim;
    }
}
