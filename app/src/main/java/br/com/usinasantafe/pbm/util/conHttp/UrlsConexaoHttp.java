package br.com.usinasantafe.pbm.util.conHttp;

import br.com.usinasantafe.pbm.PBMContext;

public class UrlsConexaoHttp {

    private int tipoEnvio = 1;

    public static String urlPrincipal = "http://www.usinasantafe.com.br/pbmdev/";
    public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pbmdev/";

    public static String localPSTEstatica = "br.com.usinasantafe.pbm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pbm.util.conWEB.UrlsConexaoHttp";

    public static String put = "?versao=" + PBMContext.versaoAplic.replace(".", "_");

    public static String ColabTO = urlPrincipal + "colab.php" + put;
    public static String ComponenteTO = urlPrincipal + "componente.php" + put;
    public static String ParadaTO = urlPrincipal + "parada.php" + put;
    public static String ServicoTO = urlPrincipal + "servico.php" + put;
    public static String EscalaTrabTO = urlPrincipal + "escalatrab.php" + put;
    public static String EquipTO = urlPrincipal + "equip.php" + put;
    public static String REquipPneuTO = urlPrincipal + "requippneu.php" + put;

    public UrlsConexaoHttp() {
    }

    public String getsInsertBolFechado() {
        return urlPrincEnvio + "inserirbolfechado.php" + put;
    }

    public String getsInsertBolAberto() {
        return urlPrincEnvio + "inserirbolaberto.php" + put;
    }

    public String getsInsertApont() {
        return urlPrincEnvio + "inserirapont.php" + put;
    }

    public String getsInsertBolPneu() {
        return urlPrincEnvio + "inserirbolpneu.php" + put;
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("Equip")) {
            retorno = urlPrincipal + "verequip.php" + put;
        } else if (classe.equals("Colab")) {
            retorno = urlPrincipal + "colab.php" + put;
        } else if (classe.equals("Parada")) {
            retorno = urlPrincipal + "parada.php" + put;
        } else if (classe.equals("Atualiza")) {
            retorno = urlPrincipal + "atualaplic.php" + put;
        } else if (classe.equals("OS")) {
            retorno = urlPrincipal + "os.php" + put;
        } else if (classe.equals("Pneu")) {
            retorno = urlPrincipal + "pneu.php" + put;
        }
        return retorno;
    }

}
