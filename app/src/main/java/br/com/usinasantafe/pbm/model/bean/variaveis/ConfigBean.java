package br.com.usinasantafe.pbm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pbm.model.pst.Entidade;

@DatabaseTable(tableName="tbconfigvar")
public class ConfigBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idConfig;
    @DatabaseField
    private Long equipConfig;
    @DatabaseField
    private String senhaConfig;
    @DatabaseField
    private Long flagLogErro;
    @DatabaseField
    private String dtServConfig;
    @DatabaseField
    private Long difDthrConfig;
    @DatabaseField
    private Long statusRetVerif; // 0 - Não Verificando; 1 - Verificando

    public ConfigBean() {
    }

    public Long getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Long idConfig) {
        this.idConfig = idConfig;
    }

    public Long getEquipConfig() {
        return equipConfig;
    }

    public void setEquipConfig(Long equipConfig) {
        this.equipConfig = equipConfig;
    }

    public String getSenhaConfig() {
        return senhaConfig;
    }

    public void setSenhaConfig(String senhaConfig) {
        this.senhaConfig = senhaConfig;
    }

    public Long getFlagLogErro() {
        return flagLogErro;
    }

    public void setFlagLogErro(Long flagLogErro) {
        this.flagLogErro = flagLogErro;
    }

    public Long getDifDthrConfig() {
        return difDthrConfig;
    }

    public void setDifDthrConfig(Long difDthrConfig) {
        this.difDthrConfig = difDthrConfig;
    }

    public String getDtServConfig() {
        return dtServConfig;
    }

    public void setDtServConfig(String dtServConfig) {
        this.dtServConfig = dtServConfig;
    }

    public Long getStatusRetVerif() {
        return statusRetVerif;
    }

    public void setStatusRetVerif(Long statusRetVerif) {
        this.statusRetVerif = statusRetVerif;
    }
}
