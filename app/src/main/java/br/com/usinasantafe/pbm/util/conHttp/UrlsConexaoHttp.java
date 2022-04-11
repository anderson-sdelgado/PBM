package br.com.usinasantafe.pbm.util.conHttp;

import br.com.usinasantafe.pbm.PBMContext;

public class UrlsConexaoHttp {

    public static String urlPrincipal = "http://www.usinasantafe.com.br/pbmdev/view/";
    public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pbmdev/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pbm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp";

    public static String put = "?versao=" + PBMContext.versaoAplic.replace(".", "_");

    public static String ColabBean = urlPrincipal + "colab.php" + put;
    public static String ComponenteBean = urlPrincipal + "componente.php" + put;
    public static String EquipBean = urlPrincipal + "equip.php" + put;
    public static String EscalaTrabBean = urlPrincipal + "escalatrab.php" + put;
    public static String ParadaBean = urlPrincipal + "parada.php" + put;
    public static String REquipPneuBean = urlPrincipal + "requippneu.php" + put;
    public static String ServicoBean = urlPrincipal + "servico.php" + put;

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
