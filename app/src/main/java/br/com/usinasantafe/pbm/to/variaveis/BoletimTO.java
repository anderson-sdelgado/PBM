package br.com.usinasantafe.pbm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tbboletimvar")
public class BoletimTO {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idBoletim;
    @DatabaseField
    private Long idFuncBoletim;
    @DatabaseField
    private String dthrInicialBoletim;
    @DatabaseField
    private String dthrFinalBoletim;
    @DatabaseField
    private Long statusBoletim;

    public BoletimTO() {
    }

    public Long getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(Long idBoletim) {
        this.idBoletim = idBoletim;
    }

    public Long getIdFuncBoletim() {
        return idFuncBoletim;
    }

    public void setIdFuncBoletim(Long idFuncBoletim) {
        this.idFuncBoletim = idFuncBoletim;
    }

    public String getDthrInicialBoletim() {
        return dthrInicialBoletim;
    }

    public void setDthrInicialBoletim(String dthrInicialBoletim) {
        this.dthrInicialBoletim = dthrInicialBoletim;
    }

    public String getDthrFinalBoletim() {
        return dthrFinalBoletim;
    }

    public void setDthrFinalBoletim(String dthrFinalBoletim) {
        this.dthrFinalBoletim = dthrFinalBoletim;
    }

    public Long getStatusBoletim() {
        return statusBoletim;
    }

    public void setStatusBoletim(Long statusBoletim) {
        this.statusBoletim = statusBoletim;
    }
}
