package br.com.usinasantafe.pbm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tblogprocvar")
public class LogProcessoBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idLogProcesso;
    @DatabaseField
    private String processo;
    @DatabaseField
    private String activity;
    @DatabaseField
    private String dthr;
    @DatabaseField
    private Long dthrLong;

    public LogProcessoBean() {
    }

    public Long getIdLogProcesso() {
        return idLogProcesso;
    }

    public void setIdLogProcesso(Long idLogProcesso) {
        this.idLogProcesso = idLogProcesso;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDthr() {
        return dthr;
    }

    public void setDthr(String dthr) {
        this.dthr = dthr;
    }

    public Long getDthrLong() {
        return dthrLong;
    }

    public void setDthrLong(Long dthrLong) {
        this.dthrLong = dthrLong;
    }
}
