package br.com.usinasantafe.pbm.util.conHttp;

import br.com.usinasantafe.pbm.PBMContext;

public class UrlsConexaoHttp {

    public static String put = "?versao=" + PBMContext.versaoWebService.replace(".", "_");

    public static String urlPrincipal = "https://www.usinasantafe.com.br/pbmdev/view/";
    public static String urlPrincEnvio = "https://www.usinasantafe.com.br/pbmdev/view/";

//    public static String urlPrincipal = "https://www.usinasantafe.com.br/pbmqa/view/";
//    public static String urlPrincEnvio = "https://www.usinasantafe.com.br/pbmqa/view/";
//
//    public static String urlPrincipal = "https://www.usinasantafe.com.br/pbmprod/" + versao + "/view/";
//    public static String urlPrincEnvio = "https://www.usinasantafe.com.br/pbmprod/" + versao + "/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pbm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp";

    public static String ColabBean = urlPrincipal + "colab.php";
    public static String ComponenteBean = urlPrincipal + "componente.php";
    public static String EquipBean = urlPrincipal + "equip.php";
    public static String EscalaTrabBean = urlPrincipal + "escalatrab.php";
    public static String ParadaBean = urlPrincipal + "parada.php";
    public static String PneuBean = urlPrincipal + "pneu.php";
    public static String REquipPneuBean = urlPrincipal + "requippneu.php";
    public static String ServicoBean = urlPrincipal + "servico.php";
    public static String TipoManutBean = urlPrincipal + "tipomanut.php";

    public UrlsConexaoHttp() {
    }

    public String getsInsertBolFechado() {
        return urlPrincEnvio + "inserirbolmecanfechado.php";
    }

    public String getsInsertBolAberto() {
        return urlPrincEnvio + "inserirbolmecanaberto.php";
    }

    public String getsInsertBolPneu() {
        return urlPrincEnvio + "inserirbolpneu.php";
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("Equip")) {
            retorno = urlPrincipal + "verequip.php";
        } else if (classe.equals("Colab")) {
            retorno = urlPrincipal + "colab.php";
        } else if (classe.equals("Parada")) {
            retorno = urlPrincipal + "parada.php";
        } else if (classe.equals("Atualiza")) {
            retorno = urlPrincipal + "atualaplic.php";
        } else if (classe.equals("OS")) {
            retorno = urlPrincipal + "os.php";
        } else if (classe.equals("Pneu")) {
            retorno = urlPrincipal + "pesqpneu.php";
        }
        return retorno;
    }

}
