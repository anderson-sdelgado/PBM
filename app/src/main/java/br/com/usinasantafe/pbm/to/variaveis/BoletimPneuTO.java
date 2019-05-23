package br.com.usinasantafe.pbm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.pst.Entidade;

@DatabaseTable(tableName="tbbolpneuvar")
public class BoletimPneuTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idBolPneu;
    @DatabaseField
    private Long idApontBolPneu;
    @DatabaseField
    private Long funcBolPneu;
    @DatabaseField
    private Long equipBolPneu;
    @DatabaseField
    private String dthrBolPneu;
    @DatabaseField
    private Long statusBolPneu;  //1 - Aberto; 2 - Encerrado

    public BoletimPneuTO() {
    }

    public Long getIdBolPneu() {
        return idBolPneu;
    }

    public void setIdBolPneu(Long idBolPneu) {
        this.idBolPneu = idBolPneu;
    }

    public Long getFuncBolPneu() {
        return funcBolPneu;
    }

    public void setFuncBolPneu(Long funcBolPneu) {
        this.funcBolPneu = funcBolPneu;
    }

    public Long getEquipBolPneu() {
        return equipBolPneu;
    }

    public void setEquipBolPneu(Long equipBolPneu) {
        this.equipBolPneu = equipBolPneu;
    }

    public String getDthrBolPneu() {
        return dthrBolPneu;
    }

    public void setDthrBolPneu(String dthrBolPneu) {
        this.dthrBolPneu = dthrBolPneu;
    }

    public Long getStatusBolPneu() {
        return statusBolPneu;
    }

    public void setStatusBolPneu(Long statusBolPneu) {
        this.statusBolPneu = statusBolPneu;
    }

    public Long getIdApontBolPneu() {
        return idApontBolPneu;
    }

    public void setIdApontBolPneu(Long idApontBolPneu) {
        this.idApontBolPneu = idApontBolPneu;
    }
}
