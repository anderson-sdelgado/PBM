package br.com.usinasantafe.pbm.util.conHttp;

import br.com.usinasantafe.pbm.PBMContext;

public class UrlsConexaoHttp {

    public static String versao = "versao_" + PBMContext.versaoWS.replace(".", "_");

//    public static String url = "https://www.usinasantafe.com.br/pbmdev/view/";
    public static String url = "https://www.usinasantafe.com.br/pbmqa/view/";
//    public static String url = "https://www.usinasantafe.com.br/pbmprod/" + versao + "/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pbm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pbm.util.conHttp.UrlsConexaoHttp";

    public static String ColabBean = url + "colab.php";
    public static String ComponenteBean = url + "componente.php";
    public static String EscalaTrabBean = url + "escalatrab.php";
    public static String ParadaBean = url + "parada.php";
    public static String ServicoBean = url + "servico.php";

    public UrlsConexaoHttp() {
    }

    public String getsInsertBoletimMecan() {
        return url + "inserirboletimmecan.php";
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("Equip")) {
            retorno = url + "pesqequip.php";
        } else if (classe.equals("Colab")) {
            retorno = url + "colab.php";
        } else if (classe.equals("Parada")) {
            retorno = url + "parada.php";
        } else if (classe.equals("Atualiza")) {
            retorno = url + "atualaplic.php";
        } else if (classe.equals("OS")) {
            retorno = url + "os.php";
        } else if (classe.equals("Pneu")) {
            retorno = url + "pesqpneu.php";
        }
        return retorno;
    }

}
