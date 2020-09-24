package br.com.usinasantafe.pbm.util.conHttp;

public class UrlsConexaoHttp {

    private int tipoEnvio = 1;

    public static String urlPrincipal = "http://www.usinasantafe.com.br/pbm/";
    public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pbm/";

    public static String localPSTEstatica = "br.com.usinasantafe.pbm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pbm.util.conWEB.UrlsConexaoHttp";

    public static String ColabTO = urlPrincipal + "colab.php";
    public static String ComponenteTO = urlPrincipal + "componente.php";
    public static String ParadaTO = urlPrincipal + "parada.php";
    public static String ServicoTO = urlPrincipal + "servico.php";
    public static String EscalaTrabTO = urlPrincipal + "escalatrab.php";
    public static String EquipTO = urlPrincipal + "equip.php";
    public static String REquipPneuTO = urlPrincipal + "requippneu.php";

    public UrlsConexaoHttp() {
    }

    public String getsInsertBolFechado() {
        return urlPrincEnvio + "inserirbolfechado.php";
    }

    public String getsInsertBolAberto() {
        return urlPrincEnvio + "inserirbolaberto.php";
    }

    public String getsInsertApont() {
        return urlPrincEnvio + "inserirapont.php";
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
            retorno = urlPrincipal + "pneu.php";
        }
        return retorno;
    }

}
