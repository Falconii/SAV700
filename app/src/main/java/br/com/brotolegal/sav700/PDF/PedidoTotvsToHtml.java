package br.com.brotolegal.sav700.PDF;

import java.util.List;

import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedCabTvs_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;

/**
 * Created by Falconi on 01/08/2017.
 */

public class PedidoTotvsToHtml {

    private PedCabTvs_fast cabec;
    private List<PedidoDetMB_fast> lsDetalhe;

    public PedidoTotvsToHtml() {
    }

    public PedidoTotvsToHtml(PedCabTvs_fast cabec, List<PedidoDetMB_fast> lsDetalhe) {
        this.cabec = cabec;
        this.lsDetalhe = lsDetalhe;
    }

    public PedCabTvs_fast getCabec() {
        return cabec;
    }

    public void setCabec(PedCabTvs_fast cabec) {
        this.cabec = cabec;
    }

    public List<PedidoDetMB_fast> getLsDetalhe() {
        return lsDetalhe;
    }

    public void setLsDetalhe(List<PedidoDetMB_fast> lsDetalhe) {
        this.lsDetalhe = lsDetalhe;
    }

    public String PedidoToHtml(){

        String html = "";


        html += "<!DOCTYPE html>";
        html += "<html>";
        html += "<head>";
        html += "<title>PEDIDO</title>";
        html += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
        html += "<STYLE>";
        html += "* { margin: 0pt; padding: 0pt; }";
        html += "";
        html += "       .cabecalho{";
        html += "            width           :100%       html += ";
        html += "";
        html += "        }";
        html += "        .celula {";
        html += "            background-color: blue;";
        html += "            border-width: 1px;";
        html += "            border-style: solid;";
        html += "            border-color: red;";
        html += "        }";
        html += "        .celula_titulo{";
        html += "            background-color:#CCCCCC;";
        html += "            color:blue;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 12px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: left;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "        .celula_texto_right{";
        html += "            background-color:#CCCCCC;";
        html += "            color:black;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 10px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: right;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "         .celula_texto_left{";
        html += "            background-color:#CCCCCC;";
        html += "            color:black;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 10px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: left;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "        .celula_texto_center{";
        html += "           background-color:#CCCCCC;";
        html += "           color:black;";
        html += "           font-family: arial, sans-serif;";
        html += "           font-size: 10px;";
        html += "           font-style: normal;";
        html += "           font-weight: normal;";
        html += "           font-variant: normal;";
        html += "           text-align: center;";
        html += "           letter-spacing: 0px;";
        html += "           line-height: 20px;";
        html += "        }";
        html += "</STYLE>";
        html += "</head>";
        html += "<body>";

        html += "<div>";
        html += "<table class=\"cabecalho\">";
        html += "<tr>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">Nº Pedido PROTHEUS</p>";
        html += "<p class=\"celula_texto_left\">07-121212</p>";
        html += "</div>";
        html += "</td>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">Nº Pedido Tablet</p>";
        html += "<p class=\"celula_texto_left\">00005820710808100111</p>";
        html += "</div>";
        html += "</td>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">Nº Pedido Cliente</p>";
        html += "<p class=\"celula_texto_left\">1230-900</p>";
        html += "</div>";
        html += "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">DATA EMISSÃO</p>";
        html += "<p class=\"celula_texto_left\">05/05/2019</p>";
        html += "</div>";
        html += "</td>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">DATA ENTREGA</p>";
        html += "<p class=\"celula_texto_left\">20/10/2017</p>";
        html += "</div>";
        html += "</td>";
        html += "<td>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">SITUAÇÃO</p>";
        html += "<p class=\"celula_texto_left\">1-ENTREGA</p>";
        html += "</div>";
        html += "</td>";
        ;
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan=\"3\">";
        html += "<div>";
        html += "<p class=\"celula_titulo\">DADOS DO CLIENTE FATURAMENTO</p>";
        html += "<p class=\"celula_texto_left\">CNPJ/CPF....:  026088890-09  I.E.: 244.244.556-90</p>";
        html += "<p class=\"celula_texto_left\">RAZÃO SOCIAL: JKLAJSLKAJSKJAKLSJALKJSLAJSLKJASLK</p>";
        html += "<p class=\"celula_texto_left\">ENDEREÇO....: RUA MICHEL MASLJUR, 236</p>";
        html += "<p class=\"celula_texto_left\">BAIRRO......: VILA AUROCAN CIDADE: CAMPINAS, SP CEP: 13034-180</p>";
        html += "</div>";
        html += "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td td colspan=\"3\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">DADOS DO CLIENTE FATURAMENTO</p>";
        html += "<p class=\"celula_texto_left\">CNPJ/CPF....:  026088890-09  I.E.: 244.244.556-90</p>";
        html += "<p class=\"celula_texto_left\">RAZÃO SOCIAL: JKLAJSLKAJSKJAKLSJALKJSLAJSLKJASLK</p>";
        html += "<p class=\"celula_texto_left\">ENDEREÇO....: RUA MICHEL MASLJUR, 236</p>";
        html += "<p class=\"celula_texto_left\">BAIRRO......: VILA AUROCAN CIDADE: CAMPINAS, SP CEP: 13034-180</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";

        html += "</tr>";
        html += "<tr>";
        html += "<td td colspan=\"3\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">COND. PAGTO</p>";
        html += "<p class=\"celula_texto_left\">CÓDIGO: 000-  DESCRIÇÃO DA CONDIÇÃO DE PAGAMENTO</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";

        html += "</tr>";
        html += "<tr>";
        html += "<td td colspan=\"2\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">OBS. PEDIDO</p>";
        html += "<p class=\"celula_texto_left\">KLSJDJSLKDJKSJDLKSJKDJS<br/>LAJDLKSJDKLJSKDJLKSJDLKJAD</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";
        html += "<td td colspan=\"2\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">OBS. NOTA FISCAL</p>";
        html += "<p class=\"celula_texto_left\">KLSJDJSLKDJKSJDLKSJKDJSLAJ<br/>DLKSJDKLJSKDJLKSJDLKJAD</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";

        html += "</tr>";
        html += "<tr>";
        html += "<td td colspan=\"2\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">FRETE:</p>";
        html += "<p class=\"celula_texto_left\">852 9898898989</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";
        html += "<td td colspan=\"2\"> ";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">TOTAL DO PEDIDO:</p>";
        html += "<p class=\"celula_texto_left\">R$ 9.000,00</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";

        html += "</tr>";
        html += "<tr>";
        html += "<td td colspan=\"2\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">VENDEDOR:</p>";
        html += "<p class=\"celula_texto_left\">909090-KJFLKJFKLJDSKFJKLDSJFLKSDJFLKDSJLFKJDSKLFJLDKSJFLK</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";
        html += "<td td colspan=\"2\">";
        html += "<div>";
        html += "<div>";
        html += "<p class=\"celula_titulo\">TELEFONE:</p>";
        html += "<p class=\"celula_texto_left\">(19) 9 9 9731-1957</p>";
        html += "</div>";
        html += "</div>";
        html += "</td>";

        html += "</tr>";
        html += "</table>";
        html += "</div>";
        html += "<div>";
        html += "<table class=\"cabecalho\" >";

        html += "<tr>";
        html += "<td> <p class=\"celula_texto_center\">ITEM</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">CÓDIGO</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">PRODUTO</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">QTD</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">P.UNIT.</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">DESC.</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">TOTAL</p> </td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td> <p class=\"celula_texto_center\">001</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">027260201</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">FEIJÃO BL 30X01</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">400</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">116,00</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">0.00;</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">46.400,00</p> </td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td> <p class=\"celula_texto_center\">001</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">027260201</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">FEIJÃO BL 30X01</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">400</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">116,00</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">0.00;</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">46.400,00</p> </td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td> <p class=\"celula_texto_center\">001</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">027260201</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">FEIJÃO BL 30X01</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">400</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">116,00</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">0.00;</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">46.400,00</p> </td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td> <p class=\"celula_texto_center\">001</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">027260201</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">FEIJÃO BL 30X01</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">400</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">116,00</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">0.00;</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">46.400,00</p> </td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td COLSPAN=\"05\">&nbsp;</td>";
        html += "<td> <p class=\"celula_texto_center\">TOTAL DO PEDIDO</p> </td>";
        html += "<td> <p class=\"celula_texto_center\">115.000,00</p> </td>";
        html += "</tr>";

        html += "</table>";

        html += "</div>";
        html += "</body>";
        html += "</html>";





        return html;



    }

    public String PedidoToHtml2(){

        String html = "";

        html += " <html> ";
        html += " <head> ";
        html += " <style>.col{padding:3px 20px 3px 20px}</style> ";
        html += " </head> ";
        html += " <body style=\"font-family:tahoma\"> ";
        html += " <div style=\"background:rgb(230,230,230); padding:5px ;border:1px solid black;\"> ";
        html += " <b style=\"color:rgb(51,153,255)\">Sample header</b> ";
       // html += " <img style=\"float:right\" height=\"25px\" width= \"80px\" src=\"resources/images/itext.png\" /> ";
        html += " </div> ";
        html += " <br /> ";
        html += " <table border='0' style='border-collapse: collapse;'> ";
        html += " <tr> ";
        html += " <td class=\"col\">String 1</td> ";
        html += " <td class=\"col\">: 1234354545</td> ";
        html += " </tr> ";
        html += " <tr> ";
        html += " <td class=\"col\">String 2</td> ";
        html += " <td class=\"col\">: rere</td> ";
        html += " </tr> ";
        html += " <tr> ";
        html += " <td class=\"col\">String 3</td> ";
        html += " <td class=\"col\">: ureuiu</td> ";
        html += " </tr> ";
        html += " <tr> ";
        html += " <td class=\"col\">Date</td> ";
        html += " <td class=\"col\">: dfdfjkdjk</td> ";
        html += " </tr> ";
        html += " </table> ";
        html += " <br /> ";
        html += " <br /> ";
        html += " <br /> ";
        html += " <hr/> ";
        html += " <br /> ";
        html += "         Contact us ";
        html += "         </body> ";
        html += " </html>";



        return html;



    }

    public String getCSSPedidoProtheus(){


        String html = "";


        html += "* { margin: 0pt; padding: 0pt; }";
        html += "";
        html += "       .cabecalho{";
        html += "            width           :100%       html += ";
        html += "";
        html += "        }";
        html += "        .celula {";
        html += "            background-color: blue;";
        html += "            border-width: 1px;";
        html += "            border-style: solid;";
        html += "            border-color: red;";
        html += "        }";
        html += "        .celula_titulo{";
        html += "            background-color:#CCCCCC;";
        html += "            color:blue;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 12px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: left;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "        .celula_texto_right{";
        html += "            background-color:#CCCCCC;";
        html += "            color:black;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 10px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: right;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "         .celula_texto_left{";
        html += "            background-color:#CCCCCC;";
        html += "            color:black;";
        html += "            font-family: arial, sans-serif;";
        html += "            font-size: 10px;";
        html += "            font-style: normal;";
        html += "            font-weight: normal;";
        html += "            font-variant: normal;";
        html += "            text-align: left;";
        html += "            letter-spacing: 0px;";
        html += "            line-height: 20px;";
        html += "        }";
        html += "";
        html += "        .celula_texto_center{";
        html += "           background-color:#CCCCCC;";
        html += "           color:black;";
        html += "           font-family: arial, sans-serif;";
        html += "           font-size: 10px;";
        html += "           font-style: normal;";
        html += "           font-weight: normal;";
        html += "           font-variant: normal;";
        html += "           text-align: center;";
        html += "           letter-spacing: 0px;";
        html += "           line-height: 20px;";
        html += "        }";


        return html;

    }
}
