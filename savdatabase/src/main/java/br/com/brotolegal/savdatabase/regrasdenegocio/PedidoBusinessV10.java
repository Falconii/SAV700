package br.com.brotolegal.savdatabase.regrasdenegocio;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AcordoDAO;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.CondPagtoDAO;
import br.com.brotolegal.savdatabase.dao.ContratoDAO;
import br.com.brotolegal.savdatabase.dao.CotaDAO;
import br.com.brotolegal.savdatabase.dao.FreteDAO;
import br.com.brotolegal.savdatabase.dao.FreteMedioDAO;
import br.com.brotolegal.savdatabase.dao.ImpostoDAO;
import br.com.brotolegal.savdatabase.dao.MotivosTrocaDEvDAO;
import br.com.brotolegal.savdatabase.dao.PedDetTvsDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.PedidoDetMbDAO;
import br.com.brotolegal.savdatabase.dao.PoliticaDAO;
import br.com.brotolegal.savdatabase.dao.ProdutoDAO;
import br.com.brotolegal.savdatabase.dao.SimuladorDAO;
import br.com.brotolegal.savdatabase.dao.TabPrecoCabecDAO;
import br.com.brotolegal.savdatabase.dao.VerbaDAO;
import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.Cota;
import br.com.brotolegal.savdatabase.entities.Frete;
import br.com.brotolegal.savdatabase.entities.FreteMedio;
import br.com.brotolegal.savdatabase.entities.Imposto;
import br.com.brotolegal.savdatabase.entities.MotivosTrocaDev;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.NoDataProgress;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMB_fast;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Politica;
import br.com.brotolegal.savdatabase.entities.Produto;
import br.com.brotolegal.savdatabase.entities.Simulador;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.entities.Verba;

public class PedidoBusinessV10 {

    public static int FORMANDOPRECO_FULL    = 0;
    public static int FORMANDOPRECO_FRETE   = 1;

    private PedidoCabMb cabec;
    private PedidoCabMbDAO cabecdao;
    private PedidoDetMb det;
    private PedidoDetMbDAO detdao;
    private Context context;
    private String cUser;
    private PedidoDetMb Edicao;

    //CLASSES
    private Cliente cliente;
    private Cliente clienteEntrega;
    private CondPagto condpagto;
    private Produto produto;
    private Politica politica;
    private Verba verba;
    private Acordo acordo;


    //DAOS
    private ClienteDAO       clientedao;
    private ContratoDAO      contratodao;
    private ProdutoDAO       produtodao;
    private CondPagtoDAO     condpagtodao;
    private TabPrecoCabecDAO tabprecocabecdao;
    private PoliticaDAO      politicadao;
    private VerbaDAO         verbadao;
    private AcordoDAO        acordodao;
    private FreteDAO         fretedao;
    private ImpostoDAO       impostodao;
    private SimuladorDAO     simuladordao;
    private AgendamentoDAO   agendamentodao;
    private FreteMedioDAO    fretemedioDAO;

    //PEDIDO
    private List<PedidoDetMB_fast> lsDetalhe;
    private List<Contrato>    lsContrato;
    private List<CondPagto>   lsCondPagto;
    private List<TabPreco>    lsTabPreco;
    private List<TabPrecoCabec> lsTabPrecoCabec;
    private Float TOTAL             = 0f;
    private Float TOTALDESC         = 0f;
    private Float TOTALVERBA        = 0f;
    private Float TOTALPESO         = 0f;
    private Float FRETEFEIJAO       = 0f;
    private Float FRETEARROZ        = 0f;
    private Float APROVEITAMENTO    = 0f;
    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");
    private List<String[]> lsCategoria;
    private List<String[]> lsMarca;
    private String LOG = "PEDIDOBUSINESS";
    private String Agendamento = "";
    private List<Float>  FAIXAS = new ArrayList<>();




    public PedidoBusinessV10() throws Exception {


        clientedao   = new ClienteDAO();

        contratodao  = new ContratoDAO();

        cabecdao     = new PedidoCabMbDAO();

        detdao       = new PedidoDetMbDAO();

        produtodao   = new ProdutoDAO();

        condpagtodao = new CondPagtoDAO();

        tabprecocabecdao = new TabPrecoCabecDAO();

        politicadao      = new PoliticaDAO();

        verbadao  = new VerbaDAO();

        acordodao = new AcordoDAO();

        fretedao  = new FreteDAO();

        impostodao = new ImpostoDAO();

        simuladordao   = new SimuladorDAO();

        agendamentodao = new AgendamentoDAO();

        fretemedioDAO  = new FreteMedioDAO();

        Novo();

    }

    public boolean Novo() {

        boolean retorno = true;

        cabec = new PedidoCabMb();

        det = new PedidoDetMb();

        cliente = new Cliente();

        condpagto = new CondPagto();

        produto = new Produto();

        politica = new Politica();

        verba = new Verba();

        acordo = new Acordo();

        cabec.setNRO("");

        cabec.setVEND(App.user.getCODVEN());

        lsCondPagto = new ArrayList<CondPagto>();

        lsTabPreco = new ArrayList<TabPreco>();

        lsDetalhe = new ArrayList<PedidoDetMB_fast>();

        lsTabPrecoCabec = new ArrayList<>();

        lsCategoria = new ArrayList<>();

        lsMarca = new ArrayList<>();

        cliente = new Cliente();

        FRETEFEIJAO = 0f;

        FRETEARROZ  = 0f;

        Edicao      = new PedidoDetMb();

        Agendamento = "";

        cabec.setOBSPED("");

        recalculo();

        Validar();

        return retorno;

    }

    public void loadItensNewOrder() throws ExceptionLoadPedido, ExceptionItemProduto {

        boolean lExiste   = false;

        int i = 0;

        detdao.open();

        lsDetalhe = detdao.getTabelaPedFast(new String[] {cabec.getNRO(),cabec.getCODIGOFAT(),cabec.getLOJAFAT(),cabec.getTABPRECO(),"@"+cabec.getTIPO()} );

        if (lsDetalhe.size() == 0) {

            throw new ExceptionLoadPedido("Itens Do Pedido " + cabec.getNRO() + " Não Encontrados !!!");

        }

        detdao.close();

        lsCategoria.add(new String[]{"000", "TODAS !!"});

        lsMarca.add(new String[]{"000", "TODAS !!"});


        //Popula campos de itens vazios
        for(PedidoDetMB_fast det : lsDetalhe) {

            if (det.getNRO().isEmpty()) {

                FormandoPreco_V2(FORMANDOPRECO_FULL,i,cabec.getFDSPREVISTO(),0f,0f);

                i++;

            }

            //categoria

            for (String[] categoria : lsCategoria) {

                if (categoria[0].equals(det.get_CodGrupo())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsCategoria.add(new String[]{det.get_CodGrupo(), det.get_Grupo().trim()});

            }

            lExiste = false;


            //marca
            for (String[] marca : lsMarca) {

                if (marca[0].equals(det.get_CodMarca())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsMarca.add(new String[]{det.get_CodMarca(), det.get_Marca().trim()});

            }
        }

        Validar();



    }

    public void loadOnlyCabec(String pedido) throws ExceptionLoadPedido, ExceptionItemProduto {

        cabecdao.open();

        cabec = cabecdao.seek(new String[]{pedido});

        cabecdao.close();

        if (cabec == null) {

            throw new ExceptionLoadPedido("Cabeçalho Do Pedido " + pedido + " Não Encontrado !!!");
        }

        LoadCabec();

    }

    public void load(String pedido,Boolean novaTabela) throws ExceptionLoadPedido, ExceptionItemProduto {

        boolean lExiste   = false;

        int i = 0;

        cabecdao.open();

        cabec = cabecdao.seek(new String[]{pedido});

        cabecdao.close();

        if (cabec == null) {

            throw new ExceptionLoadPedido("Cabeçalho Do Pedido " + pedido + " Não Encontrado !!!");
        }

        LoadCabec();

        detdao.open();

        lsDetalhe = detdao.getTabelaPedFast(new String[] {pedido,cabec.getCODIGOFAT(),cabec.getLOJAFAT(),cabec.getTABPRECO(),"@"+cabec.getTIPO()} );

        if (lsDetalhe.size() == 0) {

            throw new ExceptionLoadPedido("Itens Do Pedido " + pedido + " Não Encontrados !!!");

        }

        detdao.close();

        lsCategoria.add(new String[]{"000", "TODAS !!"});

        lsMarca.add(new String[]{"000", "TODAS !!"});

        for(PedidoDetMB_fast det : lsDetalhe) {

            if (novaTabela){

                Float prcven  = det.getPRCVEN();

                Float prcbon  = det.getBONIPREC();

                FormandoPreco_V2(PedidoBusinessV10.FORMANDOPRECO_FULL, i ,cabec.getFDSPREVISTO(),0f,0f);

                ajustaPrcVenByPreco(lsDetalhe.get(Integer.parseInt(det.getITEM())-1),prcven);

                lsDetalhe.get(i).setBONIPREC(prcbon);

            } else {

                if (det.getNRO().isEmpty()) {

                    FormandoPreco_V2(FORMANDOPRECO_FULL,i,cabec.getFDSPREVISTO(),FRETEARROZ,FRETEFEIJAO);

                }
            }

            i++;

            //categoria

            for (String[] categoria : lsCategoria) {

                if (categoria[0].equals(det.get_CodGrupo())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsCategoria.add(new String[]{det.get_CodGrupo(), det.get_Grupo().trim()});

            }

            lExiste = false;


            //marca
            for (String[] marca : lsMarca) {

                if (marca[0].equals(det.get_CodMarca())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsMarca.add(new String[]{det.get_CodMarca(), det.get_Marca().trim()});

            }
        }

        Validar();

    }

    public void save(String latitude, String longitude) throws ExceptionSavePedido {


        try {

            recalculo();

            Validar();

            agendamentodao.open();

            cabecdao.open();

            cabecdao.SavePedidoFast(getCabec(), getLsDetalhe());

            Agendamento age = agendamentodao.seek(new String[] {getAgendamento()});

            if (age != null){

                age.setSITUACAO("T");

                age.setMOBILE(cabec.getNRO());

                if (age.getLATITUDE().trim().isEmpty()){

                    age.setLATITUDE(latitude);

                }

                if (age.getLONGITUDE().trim().isEmpty()){

                    age.setLONGITUDE(longitude);

                }

                age.setMOTIVONVENDA("");

                age.setMOTIVONVISITA("");

                if (!agendamentodao.Update(age)){

                    throw new ExceptionSavePedido("Falha Na Atualização Do Agendamento");

                };

            } else {

                age = new Agendamento("  ",getAgendamento(),cabec.getCODIGOFAT(),cabec.getLOJAFAT(),App.getHoje(),App.getHoraHHMM(),cabec.getNRO(),App.user.getCODVEN(),"", "", "", "", "", "", "T", "A", "",latitude,longitude,App.getHoje(),App.getHoraHHMM());

                if (agendamentodao.insert(age) == null){

                    throw new ExceptionSavePedido("Falha Na Inclusão Do Agendamento");

                };

            }


        } finally {

            cabecdao.close();

            agendamentodao.close();

        }


    }

    public void Validar() {

        String status    = "3";
        String statusdet = "3";
        String msgerror  = "";

        Log.i("TEMVERBA","COMECEI A VALIDAÇÃO");

        if (!"1,2,3".contains(cabec.getSTATUS())) {

            return;

        }

        try {


            try {

                //Pedidos encerrados não serão validados

                if (Integer.parseInt(cabec.getSTATUS()) > 3) {

                    return;

                }

            } catch (Exception e) {

                //ignora erro e vai para validacao
            }


            //Cebecalho

            cabec.setMENSAGEM("");

            //cliente
            clientedao.open();

            if (clientedao.seek(new String[]{cabec.getCODIGOFAT(), cabec.getLOJAFAT()}) == null) {

                cabec.setValidoByName("CODIGOFAT", false);

                status = "2";

            } else {

                cabec.setValidoByName("CODIGOFAT", true);

            }


            //cliente entrega
            if (!cabec.getCODIGOENT().equals("")) {

                if (clientedao.seek(new String[]{cabec.getCODIGOENT(), cabec.getLOJAENT()}) == null) {

                    cabec.setValidoByName("CODIGOENT", false);

                    status = "2";

                } else {

                    cabec.setValidoByName("CODIGOENT", true);

                }

                clientedao.close();

            } else {

                cabec.setValidoByName("CODIGOENT", true);

            }


            clientedao.close();

            //cond pagamento
            condpagtodao.open();

            if ((condpagtodao.seek(new String[]{cabec.getCOND()})) == null) {

                cabec.setValidoByName("COND", false);

                status = "2";

            } else {

                cabec.setValidoByName("COND", true);

            }

            condpagtodao.close();

            //tabela de preco
            tabprecocabecdao.open();

            lsTabPreco = tabprecocabecdao.seekTabela(cabec.getTABPRECO(), cabec.getTIPO());

            tabprecocabecdao.close();

            if (lsTabPreco.size() == 0) {

                cabec.setValidoByName("TABPRECO", false);

                status = "2";

            } else {

                cabec.setValidoByName("TABPRECO", true);

            }


            TabPreco tabpreco = lsTabPreco.get(getIndiceByCodigo(cabec.getTABPRECO()));

            if (tabpreco.getFLAGFAIXA().equals("1") || tabpreco.getFLAGFAIXA().equals("X") ) {

                Float Previstos = Arredondamento(cabec.getFDSPREVISTO(),2);

                Float Reais     = Arredondamento(cabec.getFDSREAIS(),2);

                if ( Float.compare(Reais,Previstos)  < 0) {

                    status = "2";

                    cabec.setMENSAGEM("Quantidade FDS Realizado Inferior A Qtd FDS Previstos");

                }

            }


            if (cabec.getRETIRA().equals("1") || "003,005,006,007".contains(cabec.getTIPO())) {

                // Não valida pedido minimo para cliente retira

            } else {


                //Valida Peso Minimo

                Float VlrMin = 0f;

                try {

                    VlrMin = Float.valueOf(cliente.getPEDMINVLR());

                } catch (Exception e) {

                    VlrMin = 0f;

                }


                //Valida Qtd Minima

                Float QtdMin = 0f;

                try {

                    QtdMin = Float.valueOf(cliente.getPEDMINPES());

                } catch (Exception e) {

                    QtdMin = 0f;

                }

                if ( (Float.compare(cabec.getVLRBONIFICADO() + cabec.getTOTALPEDIDO(), VlrMin) < 0) && (Float.compare(cabec.getPESOLIQUIDO(), QtdMin) < 0)) {

                    status = "2";

                    cabec.setMENSAGEM("QTD Do Ped. Inferior A Minima: " + format_02.format(QtdMin) + " Kgr. ou Valor Do Ped. Inferior Ao Minimo: R$ " + format_02.format(VlrMin));

                }


            }



            try {


                produtodao.open();

                verbadao.open();

                acordodao.open();

                simuladordao.open();

                if (lsDetalhe.size() == 0) {

                    statusdet = "2";

                }

                for (PedidoDetMB_fast det : lsDetalhe) {

                    try {

                        if (det.getNRO().isEmpty()) continue;

                        Log.i("TEMVERBA","PONTO A => "+det.getCODVERBA()+"-"+det.getCODVERBA()+"-"+det.get_Verba());

                        det._ValidaOK();

                        det.setMENSAGEM("");

                        det.setSTATUS();

                        if (!det.getPRODUTO().equals("")) {

                            produto = produtodao.seek(new String[]{det.getPRODUTO()});

                            if (produto != null) {

                                det.set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), true);

                            } else {

                                det.set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), false);
                            }

                        } else {

                            det.set_Produto("O Produto é Campo Obrigatório");

                            det.set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), false);

                            det.setSTATUS("2");

                        }

                        Log.i("TEMVERBA","PONTO B => "+det.getCODVERBA()+"-"+det.getCODVERBA()+"-"+det.get_Verba());

                        //troca
                        if (this.getCabec().getTIPO().equals("005") || this.getCabec().getTIPO().equals("006")) {

                            if (getEdicao().getPRCVEN().compareTo(0f) == 0) {

                                getEdicao().setPRCVEN(getEdicao().getPRECOFORMACAO());

                            }
                            det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);


                            if (getCabec().getTOTALPEDIDO().compareTo(0f) > 0 && det.getBONIQTD().compareTo(0f) > 0) {

                                det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);

                            } else {

                                if (det.getQTD().compareTo(0f) <= 0) {
                                    det.setMENSAGEM("Favor Informar A Qtd Do Item");
                                    det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), false);
                                }
                            }


                            det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                            det.set_isValid(Edicao.getIndiceByNameColunas("LOTE"), true);
                            det.set_isValid(Edicao.getIndiceByNameColunas("EMPACOTAMENTO"), true);
                            det.set_isValid(Edicao.getIndiceByNameColunas("VENCIMENTO"), true);


                            if (det.getCODVERBA().trim().isEmpty())        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                            if (det.getLOTE().trim().isEmpty())            det.set_isValid(Edicao.getIndiceByNameColunas("LOTE"), false);
                            if (det.getEMPACOTAMENTO().trim().isEmpty())   det.set_isValid(Edicao.getIndiceByNameColunas("EMPACOTAMENTO"), false);
                            if (det.getVENCIMENTO().trim().isEmpty())      det.set_isValid(Edicao.getIndiceByNameColunas("VENCIMENTO"), false);

                            det.setDESCON(0f);
                            det.setACORDO("");
                            det.set_Acordo("");
                            det.setDESCVER(0f);

                        } else {

                            //bonificação
                            if ("003#011#013".contains(this.getCabec().getTIPO())) { //bonificações

                                det.set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);

                                if (det.getBONIQTD() == 0) {

                                    det.setCODVERBA2("");
                                    det.set_Verba2("");
                                    det.setACORDO2("");
                                    det.set_Acordo2("");
                                    det.setDESCVER2(0f);

                                    det.set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), false);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("DESCON2"), false);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);


                                } else if (det.getBONIQTD() > 0) {


                                    if (det.getCODVERBA2().trim().equals("")) {

                                        det.setMENSAGEM("Favor Justificar A Bonificação Com Uma verba bonif. Ou Acordo.");
                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                                        msgerror += "Favor Justificar O desconto Com Uma Verba Ou Acordo.\n";

                                    }

                                }

                            } else {

                                det.set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);

                                if (det.getBONIQTD() == 0) {

                                    det.setCODVERBA2("");
                                    det.set_Verba2("");
                                    det.setACORDO2("");
                                    det.set_Acordo2("");
                                    det.setDESCVER2(0f);

                                    det.set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);


                                } else if (det.getBONIQTD() > 0) {


                                    if (det.getCODVERBA2().trim().equals("")) {

                                        det.setMENSAGEM("Favor Justificar A Bonificação Com Uma verba bonif. Ou Acordo.");
                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                                        msgerror += "Favor Justificar O desconto Com Uma Verba Ou Acordo.\n";

                                    }

                                }

                                //outros pedidos este campo é obrigatorio
                                //qtd venda é obrigatorio
                                det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);

                                if (getCabec().getTOTALPEDIDO().compareTo(0f) > 0 && det.getBONIQTD().compareTo(0f) > 0) {

                                    det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);

                                } else {
                                    if (det.getQTD().compareTo(0f) <= 0) {
                                        det.setMENSAGEM("Favor Informar A Qtd Do Item");
                                        det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), false);
                                    }
                                }
                            }

                            //liberador de preço coluna venda
                            if (!det.getSIMULADOR().trim().equals("") || !det.getPEDDIST().trim().equals("") || !det.getACORDO().trim().equals("") || !det.getCOTA().trim().equals("") ) {

                                //Acordo
                                if (!det.getACORDO().trim().equals("")) {

                                    Acordo acor = acordodao.seek(new String[]{det.getACORDO()});

                                    if (acor != null) {

                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                                    } else {

                                        det.set_Acordo("Nao Encontrei Acordo " + det.getACORDO());
                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);
                                        det.setSTATUS("2");

                                    }

                                } else {

                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                                }

                                if (!det.getSIMULADOR().trim().equals("")) {

                                    Simulador simulador = simuladordao.seek(new String[]{det.getSIMULADOR(), cabec.getCODIGOFAT(), cabec.getLOJAFAT(), det.getPRODUTO()});

                                    if (simulador != null) {

                                        det.setCODVERBA("");
                                        det.set_Verba("");

                                        det.setACORDO("");
                                        det.set_Acordo("");

                                        det.setDESCON(0f);

                                        if (det.getPRCVEN().compareTo(simulador.getPRECOAPROVADO()) < 0) {

                                            det.setPRCVEN(simulador.getPRECOAPROVADO());
                                            //det.setBONIPREC(simulador.getPRECOAPROVADO());

                                            msgerror += "Preço Não Pode Ser Menor Que O Preço Do Simulador " + format_05.format(simulador.getPRECOAPROVADO() + "\n");

                                        }

                                    }

                                }
                                if (!det.getPEDDIST().trim().equals("")) {

                                    try {

                                        // Procurar O Pedido Distribuição

                                        PedDetTvsDAO dao = new PedDetTvsDAO();

                                        dao.open();

                                        ChavePedDistribuicao detalhe = dao.seekByMobile(new String[]{det.getPEDDISTFIL(), det.getPEDDIST(), det.getPEDDISTITEM()});

                                        dao.close();

                                        if (detalhe != null) {

                                            det.setCODVERBA("");
                                            det.set_Verba("");

                                            det.setACORDO("");
                                            det.set_Acordo("");

                                            det.setDESCON(0f);

                                            if (det.getPRCVEN().compareTo(detalhe.getPrcven()) < 0) {
                                                det.setPRCVEN(detalhe.getPrcven());
                                                //det.setBONIPREC(detalhe.getPRCVEN());
                                                msgerror += "Preço Não Pode Ser Menor Que O Preço Do Ped. Distribuição " + format_05.format(det.getPRCVEN()) + "\n";
                                            }

                                        } else {

                                            msgerror += "Pedido De Distribuição Venda Inválido.\n";
                                            det.set_isValid(Edicao.getIndiceByNameColunas("PEDDIST"), false);
                                            det.setSTATUS("2");

                                        }

                                    } catch (Exception e) {

                                        Log.i(LOG, e.getMessage());

                                    }

                                }


                                if (!det.getCOTA().trim().equals("")){

                                    try {


                                        // Procurar A Cota

                                        CotaDAO dao = new CotaDAO();

                                        dao.open();

                                        Cota detalhe = dao.seek(new String[]{det.getCOTA()});

                                        dao.close();

                                        if (detalhe == null){

                                            detalhe = new Cota();

                                            detalhe.set_PRECOFINAL(det.getPRECOFORMACAO());

                                        } else {

                                            detalhe.CalculoFinal(det.getDESCCONTRATO(),det.getTAXAFIN(),det.getCONVERSAO());

                                        }


                                        det.setCODVERBA("");
                                        det.set_Verba("");

                                        det.setACORDO("");
                                        det.set_Acordo("");

                                        det.setDESCON(0f);

                                        if (det.getPRCVEN().compareTo(detalhe.get_PRECOFINAL()) < 0) {

                                            det.setPRCVEN(detalhe.get_PRECOFINAL());

                                            msgerror += "Preço Não Pode Ser Menor Que O Preço Da Cota " + format_05.format(detalhe.get_PRECOFINAL()) + "\n";
                                        }

                                    } catch (Exception e) {

                                        Log.i(LOG, e.getMessage());

                                    }

                                }


                                //liberador de preço coluna bonificacao
                                if (!det.getSIMULADOR2().trim().equals("") || !det.getPEDDIST2().trim().equals("") || !det.getACORDO2().trim().equals("")) {

                                    //Acordo2
                                    if (!det.getACORDO2().trim().equals("")) {

                                        Acordo acor = acordodao.seek(new String[]{det.getACORDO2()});

                                        if (acor != null) {

                                            det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                                        } else {

                                            det.set_Acordo2("Nao Encontrei Acordo " + det.getACORDO());
                                            det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                                            det.setSTATUS("2");

                                        }

                                    } else {

                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                                    }

                                    if (!det.getSIMULADOR2().trim().equals("")) {

                                        Simulador simulador = simuladordao.seek(new String[]{det.getSIMULADOR2(), cabec.getCODIGOFAT(), cabec.getLOJAFAT(), det.getPRODUTO()});

                                        if (simulador != null) {

                                            det.setCODVERBA2("");
                                            det.set_Verba2("");

                                            det.setACORDO2("");
                                            det.set_Acordo2("");
                                            det.setBONIPREC(simulador.getPRECOAPROVADO());

                                        }

                                    }
                                    if (!det.getPEDDIST2().trim().equals("")) {

                                        try {

                                            // Procurar O Pedido Distribuição

                                            PedDetTvsDAO dao = new PedDetTvsDAO();

                                            dao.open();

                                            ChavePedDistribuicao detalhe = dao.seekByMobile(new String[]{det.getPEDDISTFIL2(), det.getPEDDIST2(), det.getPEDDISTITEM2()});

                                            dao.close();

                                            if (detalhe != null) {

                                                det.setCODVERBA2("");
                                                det.set_Verba2("");

                                                det.setACORDO2("");
                                                det.set_Acordo2("");

                                                det.setBONIPREC(detalhe.getPrcven());
                                                det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                                det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                                            } else {

                                                msgerror += "Pedido De Distribuição BONIFICAÇÃO Inválido.\n";
                                                det.set_isValid(Edicao.getIndiceByNameColunas("PEDDIST2"), false);
                                                det.setSTATUS("2");


                                            }
                                        } catch (Exception e) {

                                            Log.i(LOG, e.getMessage());

                                        }

                                    }
                                }
                            }

                            Log.i("TEMVERBA","PONTO C => "+det.getCODVERBA()+"-"+det.get_Verba());

                            //Verba
                            if (!det.getCODVERBA().trim().equals("")) {

                                Verba ver = verbadao.seek(new String[]{det.getCODVERBA()});

                                if (ver != null) {

                                    if (TemVerba(det.getDESCONTOPOL(),det.getPERMAX(),det.getDESCON())){


                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                                        det.set_Verba(ver.getCODIGO() + "-" + ver.getDESCRICAO());


                                    } else {

                                        if (det.get_UsaPoliticaV().equals("S")) {

                                            if (getCabec().getSALDOAPROVEITAMENTO().compareTo(0f) < 0) {

                                                det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                                                det.set_Verba("SALDO NEGATIVO APROV. POLÍTCA");

                                            } else {

                                                det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                                                det.set_Verba(ver.getCODIGO() + "-" + ver.getDESCRICAO());

                                            }
                                        }

                                    }
                                } else {

                                    det.set_Verba("Nao Encontrei Verba " + det.getCODVERBA());
                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                                    det.setSTATUS("2");

                                }

                            } else {
                                if (det.getDESCVER().compareTo(0f) > 0) {

                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);

                                } else {

                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);

                                }

                            }


                            Log.i("TEMVERBA","PONTO D => "+det.getCODVERBA()+"-"+det.get_Verba());

                            //Verba bonificacao

                            if (!det.getCODVERBA2().trim().equals("")) {

                                Verba ver = verbadao.seek(new String[]{det.getCODVERBA2()});

                                if (ver != null) {

                                    if (det.get_UsaPolitica().equals("S")){

                                        if (getCabec().getSALDOAPROVEITAMENTO().compareTo(0f) < 0) {

                                            det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                            det.set_Verba2("SALDO NEGATIVO APROV. POLÍTCA");

                                        } else {

                                            det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                            det.set_Verba2(ver.getCODIGO() + "-" + ver.getDESCRICAO());

                                        }

                                    }

                                    else {

                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                        det.set_Verba2(ver.getCODIGO() + "-" + ver.getDESCRICAO());


                                    }


                                } else {

                                    det.set_Verba2("Nao Encontrei Verba " + det.getCODVERBA2());
                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                    det.setSTATUS("2");

                                }

                            } else {

                                if ( det.getPEDDIST2().equals("") ) {

                                    if (det.getBONIQTD().compareTo(0f) > 0) {

                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);

                                    } else {


                                        det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                        det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                                    }

                                } else {

                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                                }
                            }


                            Log.i("TEMVERBA","PONTO E => "+det.getCODVERBA()+"-"+det.get_Verba());
                            //Vendas
                            //Valida descontos

                            Float descMaximo = det.getDESCONTOMAIS() + det.getDESCONTOPOL();

                            Float descVerba = 0f;

                            Float desconto = det.getDESCON();

                            if (Float.compare(desconto, descMaximo) > 0) {

                                det.setPRCVEN(det.getPRECOFORMACAO());
                                det.setDESCON(0f);
                                det.setCODVERBA("");
                                det.set_Verba("");
                                det.setACORDO("");
                                det.set_Acordo("");
                                det.setDESCVER(0f);

                                det.setMENSAGEM("Desconto Acima Do Permitido !!! " + format_05.format(descMaximo - desconto));
                                det.set_isValid(Edicao.getIndiceByNameColunas("PRCVEN"), true);
                                det.set_isValid(Edicao.getIndiceByNameColunas("DESCON"), true);
                                det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                                det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);
                                msgerror += "Desconto Acima Do Permitido !!! " + format_05.format(descMaximo) + "\n";


                            } else {

                                if (Float.compare(det.getDESCON(), det.getDESCONTOPOL()) > 0) {

                                    descVerba = det.getDESCON() - det.getDESCONTOPOL();

                                }

                                det.setDESCVER(descVerba);




                                if ( (!TemVerba(det.getDESCONTOPOL(),det.getPERMAX(),det.getDESCON())) && (Float.compare(det.getDESCVER(), 0) == 0)) {

                                    det.setCODVERBA("");
                                    det.set_Verba("");
                                    det.setACORDO("");
                                    det.set_Acordo("");

                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                                }
                            }

                            Log.i("TEMVERBA","PONTO F => "+det.getCODVERBA()+"-"+det.get_Verba());

                            //Verba
                            if (det.getDESCVER() > 0) {


                                if (det.getCODVERBA().trim().equals("")) {

                                    det.setMENSAGEM("Favor Justificar O desconto Com Uma verba !!!");
                                    det.set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);
                                    msgerror += "Favor Justificar O desconto Com Uma Verba de Venda E Ou Acordo.\n";

                                }


                                if ((det.getCODVERBA().trim().equals("")) && (det.getACORDO().trim().equals(""))) {

                                    det.setMENSAGEM("Favor Justificar O desconto Com Um Acordo !!!");
                                    det.set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);
                                    msgerror += "Favor Justificar O desconto Com Uma verba E Ou Acordo!!\n!";

                                }

                            }

                        }

                        if (!det.getNRO().isEmpty()) {

                            det.setSTATUS();

                            if ((det.getSTATUS().equals("2"))) statusdet = "2";

                        } else {

                            det.setSTATUS("2");

                        }


                    } catch (Exception e) {

                        det.setSTATUS("2");

                        det.setMENSAGEM(msgerror);

                        statusdet = "2";


                    }


                }


            } catch (Exception e) {

                det.setSTATUS("2");

                statusdet = "2";

            } finally {


                produtodao.close();

                verbadao.close();

                acordodao.close();

                simuladordao.close();


            }


        } catch (Exception e) {

            det.setSTATUS("2");

        }


        if (statusdet.equals("2")) {

            cabec.setSTATUS("2");
            cabec.setMENSAGEM("Favor verificar os Itens !!");

        } else {

            cabec.setSTATUS(status);

        }

    }

    private int seekTabPreco(String Codigo) {

        int retorno = -1;

        for (int x = 0; x < lsTabPreco.size(); x++) {


            if (lsTabPreco.get(x).getPRODUTO().equals(Codigo)) {

                retorno = x;

                break;

            }

        }

        return retorno;

    }

    private int seekTabPrecoTemporaria(List<TabPreco> lsTab, String Codigo) {

        int retorno = -1;

        for (int x = 0; x < lsTab.size(); x++) {

            if (lsTab.get(x).getPRODUTO().equals(Codigo)) {

                retorno = x;

                break;

            }

        }

        return retorno;

    }

    public PedidoDetMb getEdicao() {
        return Edicao;
    }

    public void setEdicao(PedidoDetMb edicao) {
        Edicao = edicao;
    }

    private int seekContratoBYChave(String produto, String marca, String categoria, String Chave) {

        int retorno = -1;

        for (int x = 0; x < lsContrato.size(); x++) {


            if (getLsContrato().get(x).getTIPO() == null) {

                retorno = -1;

                continue;

            }
            switch (getLsContrato().get(x).getTIPO().charAt(0)) {

                case 'P':

                    if (produto.equals(getLsContrato().get(x).getChave())) {

                        retorno = x;

                    }

                    break;

                case 'M':


                    if (marca.equals(getLsContrato().get(x).getChave())) {

                        retorno = x;

                    }

                    break;

                case 'C':

                    if (categoria.equals(getLsContrato().get(x).getChave())) {

                        retorno = x;

                    }

                    break;
            }

            if (retorno != -1) {

                break;
            }

        }
        return retorno;

    }

    public String getErros() {

        String Html = "";


        return Html;

    }

    public void Close() {

        cabecdao.close();

        detdao.close();

        clientedao.close();

        contratodao.close();

        produtodao.close();

        condpagtodao.close();

        tabprecocabecdao.close();

        politicadao.close();

        verbadao.close();

        acordodao.close();

        fretedao.close();

        impostodao.close();

        simuladordao.close();

        agendamentodao.close();

        fretemedioDAO.close();
    }

    public void SetTipoPedido(String tipo) {

        try {

            if ( "003#011#013".contains(tipo) ){

                condpagtodao.open();

                condpagto = condpagtodao.seek(new String[]{"033"});

                if (condpagto != null) {

                    cabec.setCOND(condpagto.getCODIGO());

                    cabec.set_Cond(condpagto.getDESCRICAO());

                    lsCondPagto = new ArrayList<CondPagto>();

                    lsCondPagto.add(condpagto);


                } else {

                    cabec.setCOND("");

                    cabec.set_Cond("Cond. Pagto Não Definida !!");

                }

                condpagtodao.close();

            } else {


                if (cabec.getCOND().equals("033")) {

                    condpagtodao.open();

                    condpagto = condpagtodao.seek(new String[]{cliente.getCONDPAGTO()});

                    if (condpagto != null) {

                        cabec.setCOND(condpagto.getCODIGO());

                        cabec.set_Cond(condpagto.getDESCRICAO());

                        lsCondPagto = new ArrayList<CondPagto>();

                        lsCondPagto.add(condpagto);

                        if (cliente.getFLAGALTERACP().equals("S")) {

                            lsCondPagto = condpagtodao.seekMedia(condpagto.getMEDIA());

                        }


                    } else {

                        cabec.setCOND("");

                        cabec.set_Cond("Cond. Pagto Não Definida !!");

                    }

                    condpagtodao.close();

                }




            }

            String Tabela = cabec.getTABPRECO();

            if (Tabela.isEmpty()) Tabela = cliente.getTABELA();

            cabec.setTIPO(tipo);

            tabprecocabecdao.open();

            lsTabPreco      = tabprecocabecdao.seekTabela(Tabela, cabec.getTIPO());

            lsTabPrecoCabec = tabprecocabecdao.seekTabelaCarga(cliente.getTABELA());

            tabprecocabecdao.close();

            if (lsTabPreco.size() > 0) {

                cabec.setTABPRECO(Tabela);

                for (TabPreco tab : lsTabPreco) {

                    if (tab.getCODIGO().equals(Tabela)){

                        cabec.set_TabPreco(tab.getDESCRICAO());

                        if (tab.getFLAGFAIXA().equals("1")) {

                            cabec.setFDSPREVISTO(tab.getFAIXAATE());

                        } else {

                            if (tab.getFLAGFAIXA().equals("2")) {

                                cabec.setFDSPREVISTO(0f);
                            }
                        }

                    }

                }

            } else {

                cabec.setTABPRECO("");

                cabec.set_TabPreco("Tab. Preço Não Definida.");

            }


        } catch (Exception e) {

            Log.i(LOG, e.getMessage());

        }
    }

    public PedidoCabMb getCabec() {
        return cabec;
    }

    public void setCabec(PedidoCabMb cabec) {
        this.cabec = cabec;
    }

    public PedidoCabMbDAO getCabecdao() {
        return cabecdao;
    }

    public void setCabecdao(PedidoCabMbDAO cabecdao) {
        this.cabecdao = cabecdao;
    }

    public PedidoDetMb getDet() {
        return det;
    }

    public void setDet(PedidoDetMb det) {
        this.det = det;
    }

    public PedidoDetMbDAO getDetdao() {
        return detdao;
    }

    public void setDetdao(PedidoDetMbDAO detdao) {
        this.detdao = detdao;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CondPagto getCondpagto() {
        return condpagto;
    }

    public void setCondpagto(CondPagto condpagto) {

        this.condpagto = condpagto;

        this.cabec.setCOND(condpagto.getCODIGO());

        this.cabec.set_Cond(condpagto.getDESCRICAO());

    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Politica getPolitica() {
        return politica;
    }

    public void setPolitica(Politica politica) {
        this.politica = politica;
    }

    public ClienteDAO getClientedao() {
        return clientedao;
    }

    public void setClientedao(ClienteDAO clientedao) {
        this.clientedao = clientedao;
    }

    public ContratoDAO getContratodao() {
        return contratodao;
    }

    public void setContratodao(ContratoDAO contratodao) {
        this.contratodao = contratodao;
    }

    public ProdutoDAO getProdutodao() {
        return produtodao;
    }

    public void setProdutodao(ProdutoDAO produtodao) {
        this.produtodao = produtodao;
    }

    public CondPagtoDAO getCondpagtodao() {
        return condpagtodao;
    }

    public void setCondpagtodao(CondPagtoDAO condpagtodao) {
        this.condpagtodao = condpagtodao;
    }

    public TabPrecoCabecDAO getTabprecocabecdao() {
        return tabprecocabecdao;
    }

    public void setTabprecocabecdao(TabPrecoCabecDAO tabprecocabecdao) {
        this.tabprecocabecdao = tabprecocabecdao;
    }

    public PoliticaDAO getPoliticadao() {
        return politicadao;
    }

    public void setPoliticadao(PoliticaDAO politicadao) {
        this.politicadao = politicadao;
    }

    public List<PedidoDetMB_fast> getLsDetalhe() {
        return lsDetalhe;
    }

    public void setLsDetalhe(List<PedidoDetMB_fast> lsDetalhe) {
        this.lsDetalhe = lsDetalhe;
    }

    public List<Contrato> getLsContrato() {
        return lsContrato;
    }

    public void setLsContrato(List<Contrato> lsContrato) {
        this.lsContrato = lsContrato;
    }

    public List<CondPagto> getLsCondPagto() {
        return lsCondPagto;
    }

    public void setLsCondPagto(List<CondPagto> lsCondPagto) {
        this.lsCondPagto = lsCondPagto;
    }

    public List<TabPreco> getLsTabPreco() {
        return lsTabPreco;
    }

    public void setLsTabPreco(List<TabPreco> lsTabPreco) {
        this.lsTabPreco = lsTabPreco;
    }

    public Float getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(Float tOTAL) {
        TOTAL = tOTAL;
    }

    public Float getTOTALDESC() {
        return TOTALDESC;
    }

    public void setTOTALDESC(Float tOTALDESC) {
        TOTALDESC = tOTALDESC;
    }

    public Float getTOTALVERBA() {
        return TOTALVERBA;
    }

    public void setTOTALVERBA(Float tOTALVERBA) {
        TOTALVERBA = tOTALVERBA;
    }

    public Float getTOTALPESO() {
        return TOTALPESO;
    }

    public void setTOTALPESO(Float tOTALPESO) {
        TOTALPESO = tOTALPESO;
    }

    public Verba getVerba() {
        return verba;
    }

    public List<String[]> getLsCategoria() {
        return lsCategoria;
    }

    public List<String[]> getLsMarca() {
        return lsMarca;
    }

    public List<TabPrecoCabec> getLsTabPrecoCabec() {
        return lsTabPrecoCabec;
    }

    /*
             *
             *
             *   Cabecalhos Dos Pedidos
             *
             *
             */
    public void LoadCabec() throws ExceptionLoadPedido {

        try {

            if (cabec.getCODIGOFAT().trim().equals("")) {

                cliente = new Cliente();

            } else {

                clientedao.open();

                cliente = clientedao.seek(new String[]{cabec.getCODIGOFAT(), cabec.getLOJAFAT()});

                if (!cabec.getCODIGOENT().trim().isEmpty()){

                    Cliente entrega = clientedao.seek(new String[] {cabec.getCODIGOENT(),cabec.getLOJAENT()});

                    if (entrega != null){

                        cabec.set_ClienteEntRazao(entrega.getCODIGO()+"-"+entrega.getLOJA()+" "+entrega.getRAZAO().trim());

                    } else {

                        cabec.set_ClienteEntRazao("");

                    }

                    setClienteEntrega(entrega.getCODIGO(),entrega.getLOJA());

                } else {

                    cabec.set_ClienteEntRazao("");

                }

                clientedao.close();

            }

            if (cliente != null) {

                this.setCliente(cabec.getCODIGOFAT(), cabec.getLOJAFAT());

            } else {

                throw new ExceptionLoadPedido("Cliente Não Encontrado !!!");
            }


        } catch (ExceptionItemProduto e) {

            throw new ExceptionLoadPedido(e.getMessage());

        } catch (IOException e1) {

            throw new ExceptionLoadPedido(e1.getMessage());

        } catch (Exception e) {

            throw new ExceptionLoadPedido(e.getMessage());
        }


    }

    public void setCliente(String codigo, String loja) throws IOException, ExceptionItemProduto {

        try {

            String CodTab = cabec.getTABPRECO();

            String CodCP  = cabec.getCOND();

            clientedao.open();

            cliente = clientedao.seek(new String[]{codigo, loja});

            clientedao.close();


            if (cliente != null) {

                cabec.setCODIGOFAT(cliente.getCODIGO());

                cabec.setLOJAFAT(cliente.getLOJA());

                cabec.set_ClienteFatRazao(cliente.getCODIGO() + "-" + cliente.getLOJA() + " " + cliente.getRAZAOPA());

                cabec.set_ClienteFatCnpj(cliente.getCNPJ());

                cabec.set_ClienteFatIE(cliente.getIE());

                cabec.set_ClienteCodRede(cliente.getREDE());

                setClienteEntrega(cliente.getCODIGO(),cliente.getLOJA());

                contratodao.open();

                lsContrato = contratodao.getSeekFull(cliente.getCONTRATO());

                cabec.setCONTRATO(cliente.getCONTRATO());

                contratodao.close();

                if (CodCP.isEmpty()) CodCP = cliente.getCONDPAGTO();

                condpagtodao.open();

                condpagto = condpagtodao.seek(new String[]{CodCP});

                if (condpagto != null) {

                    cabec.setCOND(condpagto.getCODIGO());

                    cabec.set_Cond(condpagto.getDESCRICAO());

                    lsCondPagto = new ArrayList<CondPagto>();

                    lsCondPagto.add(condpagto);

                    if (cliente.getFLAGALTERACP().equals("S")) {

                        lsCondPagto = condpagtodao.seekMedia(condpagto.getMEDIA());

                    }


                } else {

                    cabec.setCOND("");

                    cabec.set_Cond("Cond. Pagto Não Definida !!");

                }

                condpagtodao.close();

                if (CodTab.isEmpty()) CodTab = cliente.getTABELA();

                tabprecocabecdao.open();

                lsTabPreco = tabprecocabecdao.seekTabela(CodTab, cabec.getTIPO());

                lsTabPrecoCabec = tabprecocabecdao.seekTabelaCarga(cliente.getTABELA());

                tabprecocabecdao.close();

                if (lsTabPreco.size() > 0) {

                    cabec.setTABPRECO(CodTab);

                    for(TabPreco tab : lsTabPreco){

                        if (tab.getCODIGO().equals(CodTab)){

                            cabec.set_TabPreco(tab.getDESCRICAO());

                        }

                    }


                } else {

                    cabec.setTABPRECO("");

                    cabec.set_TabPreco("Tab. Preço Não Definida.");

                }

            } else {


                throw new ExceptionItemProduto("Cliente Não Encontrado !!!");

            }
        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());
        }

    }

    public void setClienteEntrega(String codigo, String loja) throws IOException, ExceptionItemProduto {

        try {

            clientedao.open();

            clienteEntrega = clientedao.seek(new String[]{codigo, loja});

            clientedao.close();


            if (clienteEntrega != null) {

                Float qtd = 0f;

                try {

                    qtd = cabec.getFDSPREVISTO() / cabec.getQTDENTREGA();

                } catch (Exception e) {

                    qtd = cabec.getFDSPREVISTO();
                }

                this.tabprecocabecdao.open();

                TabPrecoCabec cab = this.tabprecocabecdao.seek(new String[] {this.cabec.getTABPRECO()});

                this.tabprecocabecdao.close();

                if (cab != null) {

                    if ( (cab.getTIPOFRETE().equals("M"))) {

                        fretemedioDAO.open();

                        FreteMedio medio = fretemedioDAO.getFretemedio(this.cabec.getTABPRECO(),this.clienteEntrega.getESTADO(),qtd);

                        fretemedioDAO.close();

                        if (medio != null){

                            this.FRETEARROZ  = medio.getVALOR();

                            this.FRETEFEIJAO = medio.getVALOR();

                        } else {

                            this.FRETEARROZ = 0f;

                            this.FRETEFEIJAO = 0f;
                        }

                    } else {


                        fretedao.open();

                        Frete freteArroz = fretedao.getFretArroz(qtd, clienteEntrega.getREDE(), clienteEntrega.getESTADO(), clienteEntrega.getCODCIDADE());

                        if (freteArroz == null) {

                            freteArroz = new Frete();

                            freteArroz.setFRETE(3.50f);

                        }


                        Frete freteFeijao = fretedao.getFreteFeijao(qtd, clienteEntrega.getREDE(), clienteEntrega.getESTADO(), clienteEntrega.getCODCIDADE());

                        if (freteFeijao == null) {

                            freteFeijao = new Frete();

                            freteFeijao.setFRETE(3.50f);

                        }

                        fretedao.close();

                        this.FRETEARROZ = freteArroz.getFRETE();

                        this.FRETEFEIJAO = freteFeijao.getFRETE();

                    }

                } else {

                    this.FRETEARROZ = 0f;

                    this.FRETEFEIJAO = 0f;
                }


                cabec.set_ClienteEntRazao(clienteEntrega.getCODIGO() + "-" + clienteEntrega.getLOJA() + " " + clienteEntrega.getRAZAOPA() + " Frete ARROZ " + format_02.format( this.FRETEARROZ ) + " Frete FEIJÃO " + format_02.format( this.FRETEFEIJAO ));

                cabec.setCODIGOENT(codigo);

                cabec.setLOJAENT(loja);
            }

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());
        }

    }

    public PedidoDetMB_fast getItemBySelf(String item) throws ExceptionItemProduto {

        PedidoDetMB_fast obj = null;

        try {

            obj = lsDetalhe.get(getPosicaoByItem(item));

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }

        return obj;

    }

    public void setItemVerba(String Codigo) throws ExceptionItemProduto {

        try {

            verbadao.open();

            Verba ver = verbadao.seek(new String[]{Codigo});

            verbadao.close();

            if (ver != null) {

                Edicao.setCODVERBA(Codigo);

                Edicao.set_Verba(Codigo + "-" + ver.getDESCRICAO());

                Edicao.set_UsaPoliticaV((ver.getTIPO().equals("P") ? "S" : "N"));

            } else {

                throw new ExceptionItemProduto("Verba " + Codigo + " Não Encontrada !!");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemMotivo(String Tipo, String Codigo) throws ExceptionItemProduto {

        try {

            MotivosTrocaDEvDAO dao = new MotivosTrocaDEvDAO();

            dao.open();

            MotivosTrocaDev motivo = dao.seek(new String[] {Tipo,Codigo});

            dao.close();

            if (motivo != null) {

                Edicao.setCODVERBA(Codigo);

                Edicao.set_MotDev(Codigo + "-" + motivo.getDESCRICAO());

            } else {

                throw new ExceptionItemProduto("Motivo " + Codigo + " Não Encontrado !!");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemVerba2(String Codigo) throws ExceptionItemProduto {

        try {

            verbadao.open();

            Verba ver = verbadao.seek(new String[]{Codigo});

            verbadao.close();

            if (ver != null) {

                Edicao.setCODVERBA2(Codigo);

                Edicao.set_Verba2(Codigo + "-" + ver.getDESCRICAO());

                Edicao.set_UsaPolitica((ver.getTIPO().equals("P") ? "S" : "N"));

                Edicao.setBONIPREC(Edicao.getPRCVEN());

            } else {

                throw new ExceptionItemProduto("Verba Bonif." + Codigo + " Não Encontrada !!");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemAcordo(String Codigo) throws ExceptionItemProduto {

        try {

            acordodao.open();

            Acordo acor = acordodao.seek(new String[]{Codigo});

            acordodao.close();

            if (acor != null) {

                Edicao.setACORDO(Codigo);

                Edicao.set_Verba(acor.getCODVERBA() + "-" + acor.getDESVERBA());

                Edicao.setCODVERBA(acor.getCODVERBA());

                Edicao.set_Acordo(acor.getCODIGO() + " Saldo: " + format_02.format(acor.getSALDO()));

            } else {

                throw new ExceptionItemProduto("Acordo " + Codigo + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemAcordo2(String Codigo) throws ExceptionItemProduto {

        try {

            acordodao.open();

            Acordo acor = acordodao.seek(new String[]{Codigo});

            acordodao.close();

            if (acor != null) {

                Edicao.setACORDO2(Codigo);

                Edicao.set_Verba2(acor.getCODVERBA() + "-" + acor.getDESVERBA());

                Edicao.setCODVERBA2(acor.getCODVERBA());

                Edicao.set_Acordo2(acor.getCODIGO() + " Saldo: " + format_02.format(acor.getSALDO()));

            } else {

                throw new ExceptionItemProduto("Acordo " + Codigo + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemSimulador(String Codigo, String Cliente, String Loja, String Produto) throws ExceptionItemProduto {

        try {

            simuladordao.open();

            Simulador simulador = simuladordao.seek(new String[]{Codigo,Cliente,Loja,Produto});

            simuladordao.close();

            if (simulador != null) {

                Edicao.setSIMULADOR(Codigo);

                Edicao.setPRCVEN(simulador.getPRECOAPROVADO());

                // Edicao.setBONIPREC(simulador.getPRECOAPROVADO());

            } else {

                throw new ExceptionItemProduto("Simulador " + Codigo + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemPedDistr(String filial, String pedido, String item) throws ExceptionItemProduto {

        try {

            PedDetTvsDAO dao = new PedDetTvsDAO();

            dao.open();

            ChavePedDistribuicao det = dao.seekByMobile(new String[] {filial,pedido,item});

            dao.close();

            if (det != null){

                Edicao.setPEDDISTFIL("");

                Edicao.setPEDDISTITEM(item);

                Edicao.setPEDDIST(pedido);

                Edicao.setPRCVEN(det.getPrcven());

                Edicao.setACORDO("");

                Edicao.set_Verba("");

                Edicao.setCODVERBA("");

                Edicao.set_Acordo("");

            } else {

                throw new ExceptionItemProduto("Pedido De Distruibuição " + pedido + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }


    public void setItemPedDistr2(String filial, String pedido, String item) throws ExceptionItemProduto {

        try {

            PedDetTvsDAO dao = new PedDetTvsDAO();

            dao.open();

            ChavePedDistribuicao det = dao.seekByMobile(new String[] {filial,pedido,item});

            dao.close();

            if (det != null){

                Edicao.setPEDDISTFIL2("");

                Edicao.setPEDDISTITEM2(item);

                Edicao.setPEDDIST2(pedido);

                Edicao.setBONIPREC(det.getPrcven());

                Edicao.setACORDO2("");

                Edicao.set_Verba2("");

                Edicao.setCODVERBA2("");

                Edicao.set_Acordo2("");

            } else {

                throw new ExceptionItemProduto("Pedido De Distruibuição " + pedido + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void recalculoItem(int pos) {

        //Total venda
        double total = lsDetalhe.get(pos).getQTD() * (lsDetalhe.get(pos).getPRCVEN() - (lsDetalhe.get(pos).getPRCVEN() * (lsDetalhe.get(pos).getDESCON() / 100)));

        BigDecimal tot = new BigDecimal(total);

        tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

        lsDetalhe.get(pos).setTOTAL(tot.floatValue());

        //Total Bonifcação
        if (getCondpagto().getCODIGO().equals("033")){

            if (lsDetalhe.get(pos).getBONIPREC().compareTo(0f)==0){

                lsDetalhe.get(pos).setBONIPREC(tot.floatValue());

            }
        } else {

            //lsDetalhe.get(pos).setBONIPREC(tot.floatValue());

        }

        total = lsDetalhe.get(pos).getBONIQTD() * lsDetalhe.get(pos).getBONIPREC();

        tot = new BigDecimal(total);

        tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

        lsDetalhe.get(pos).setBONITOTAL(tot.floatValue());

    }

    public void ajustaPrcVenByPreco(PedidoDetMb obj, Float preco) {

        Float desc;

        float tabela = obj.getPRECOFORMACAO();

        float venda = preco;

        int cmp = Float.compare(tabela, venda);


        if (!obj.getSIMULADOR().trim().isEmpty() || !obj.getPEDDIST().trim().isEmpty() || !obj.getCOTA().trim().isEmpty()){

            obj.setPRCVEN(preco);

            return ;
        }

        if (cmp > 0) { //tabela maior venda

            desc = ((tabela - venda) / tabela);

            desc = Arredondamento(desc,4);

            obj.setDESCON(desc * 100);

        } else if (cmp < 0) { //tabela menor venda

            obj.setDESCON(0f);

        } else { //tabela = venda

            obj.setDESCON(0f);

            obj.setCODVERBA("");

            obj.set_Verba("");

            obj.setACORDO("");

            obj.set_Acordo("");

        }

    }

    public void ajustaPrcVenByPreco(PedidoDetMB_fast obj, Float preco) {

        Float desc;

        float tabela = obj.getPRECOFORMACAO();

        float venda = preco;

        int cmp = Float.compare(tabela, venda);

        obj.setPRCVEN(preco);

        if (!obj.getSIMULADOR().trim().isEmpty() || !obj.getPEDDIST().trim().isEmpty() || !obj.getCOTA().trim().isEmpty()){

            return ;
        }

        if (cmp > 0) { //tabela maior venda

            desc = ((tabela - venda) / tabela);

            desc = Arredondamento(desc,4);

            obj.setDESCON(desc * 100);

        } else if (cmp < 0) { //tabela menor venda

            obj.setDESCON(0f);

        } else { //tabela = venda

            obj.setDESCON(0f);

            //pedidos troca e devolução não alteram verba

            if ( !("005,006".contains(cabec.getTIPO()))) {

                obj.setCODVERBA("");

                obj.set_Verba("");

            }

            obj.setACORDO("");

            obj.set_Acordo("");

        }

    }

    public void recalculo() {


        Float TOTALPEDIDO            = 0f;
        Float TOTALDESCONTO          = 0f;
        Float TOTALVERBA             = 0f;
        Float QTDBINIFICADA          = 0f;
        Float VLRBONIFICADO          = 0f;
        Float FDSREAIS               = 0f;
        Float APROVEITAMENTO         = 0f;
        Float USADOAPROVEITAMENTO    = 0f;
        Float PESOBRUTO              = 0f;
        Float PESOLIQUIDO            = 0f;
        BigDecimal precven;
        BigDecimal desc;
        BigDecimal tot;
        Double total;
        Double preco;


        for (PedidoDetMB_fast obj : lsDetalhe) {

            if (obj.getNRO().isEmpty()) continue;

            if (cabec.getTIPO().equals("005") || this.getCabec().getTIPO().equals("006")){

                precven = new BigDecimal(obj.getPRCVEN());

                desc = new BigDecimal(0f);


            } else {

                if (!obj.getSIMULADOR().trim().isEmpty() || !obj.getPEDDIST().trim().isEmpty() || !obj.getCOTA().trim().isEmpty()) {

                    precven = new BigDecimal(obj.getPRCVEN());

                    desc = new BigDecimal(0f);

                    desc.setScale(5, BigDecimal.ROUND_HALF_UP);

                } else {

                    desc = new BigDecimal(obj.getDESCON());

                    precven = new BigDecimal(obj.getPRCVEN());

                    desc.setScale(5, BigDecimal.ROUND_HALF_UP);

                    if (Float.compare(obj.getPRCVEN(), obj.getPRECOFORMACAO()) > 0) {

                        precven = new BigDecimal(obj.getPRCVEN());

                    } else {

                        precven = new BigDecimal(obj.getPRECOFORMACAO());

                    }

                }

            }

            //Total venda

            preco = precven.doubleValue() - (precven.doubleValue() * (desc.doubleValue() / 100));

            precven = new BigDecimal(preco);

            precven = precven.setScale(2, BigDecimal.ROUND_HALF_UP);

            total = obj.getQTD() * precven.doubleValue();

            tot = new BigDecimal(total);

            tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

            obj.setPRCVEN(precven.floatValue());

            obj.setDESCON(desc.floatValue());

            obj.setTOTAL(tot.floatValue());

            TOTALPEDIDO += tot.floatValue();

            TOTALDESCONTO += desc.floatValue();

            TOTALVERBA += 0f;

            //Total Bonifcação

            if (getCondpagto().getCODIGO().equals("033")){

                if (obj.getBONIPREC().compareTo(0f)==0){

                    obj.setBONIPREC(precven.floatValue());

                }
            } else {

                if (!obj.getSIMULADOR2().trim().isEmpty() || !obj.getPEDDIST2().trim().isEmpty()) {

                    //obj.setBONIPREC(precven.floatValue());

                } else {

                    //obj.setBONIPREC(precven.floatValue());

                }

            }

            total = obj.getBONIQTD() * obj.getBONIPREC().doubleValue();

            tot = new BigDecimal(total);

            tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

            obj.setBONITOTAL(tot.floatValue());


            QTDBINIFICADA += obj.getBONIQTD();

            VLRBONIFICADO += tot.floatValue();

            FDSREAIS  += (obj.getQTD() + obj.getBONIQTD()) * obj.getCONVERSAO();

            APROVEITAMENTO += obj.getAproveitamento();

            if (obj.get_UsaPoliticaV().equals("S")){

                USADOAPROVEITAMENTO += ( Arredondamento((obj.getPRECOFORMACAO() * (obj.getDESCVER()/100)),2) * obj.getQTD() ) ;

            }

            if (obj.get_UsaPolitica().equals("S")){

                USADOAPROVEITAMENTO += obj.getBONITOTAL();

            }

            PESOBRUTO   += ((obj.getQTD() + obj.getBONIQTD() ) * obj.get_PesoBruto());

            PESOLIQUIDO += ((obj.getQTD() + obj.getBONIQTD() ) * obj.get_PesoLiquido());

        }

        cabec.setTOTALPEDIDO(TOTALPEDIDO);
        cabec.setTOTALDESCONTO(TOTALDESCONTO);
        cabec.setTOTALVERBA(TOTALVERBA);
        cabec.setQTDBINIFICADA(QTDBINIFICADA);
        cabec.setVLRBONIFICADO(VLRBONIFICADO);
        cabec.setFDSREAIS(FDSREAIS);
        cabec.setAPROVEITAMENTO(APROVEITAMENTO);
        cabec.setSALDOAPROVEITAMENTO(APROVEITAMENTO - USADOAPROVEITAMENTO);
        cabec.setPESOBRUTO(PESOBRUTO);
        cabec.setPESOLIQUIDO(PESOLIQUIDO);

    }

    private int getPosicaoByItem(String item) {

        int posicao = -1;

        for (int x = 0; x < lsDetalhe.size(); x++) {

            if (lsDetalhe.get(x).getITEM().equals(item)) {

                posicao = x;

                break;

            }

        }

        return posicao;

    }

    public void validarItemEdicao()  {

        String msgerror = "";

        try {

            produtodao.open();

            verbadao.open();

            acordodao.open();

            simuladordao.open();

            getEdicao()._ValidaOK();

            getEdicao().setMENSAGEM("");

            getEdicao().setSTATUS();

            if (!getEdicao().getPRODUTO().equals("")) {

                produto = produtodao.seek(new String[]{getEdicao().getPRODUTO()});

                if (produto != null) {

                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), true);

                } else {

                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), false);
                }

            } else {

                getEdicao().set_Produto("O Produto é Campo Obrigatório");

                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("PRODUTO"), false);

                getEdicao().setSTATUS("2");

            }

            //troca
            if (this.getCabec().getTIPO().equals("005") || this.getCabec().getTIPO().equals("006")) {

                if (getEdicao().getPRCVEN().compareTo(0f) == 0) {

                    getEdicao().setPRCVEN(getEdicao().getPRECOFORMACAO());

                }

                det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);


                if (getCabec().getTOTALPEDIDO().compareTo(0f) > 0 && getEdicao().getBONIQTD().compareTo(0f) > 0) {

                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);

                } else {

                    if (getEdicao().getQTD().compareTo(0f) <= 0) {
                        getEdicao().setMENSAGEM("Favor Informar A Qtd Do Item");
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("QTD"), false);
                    }
                }

                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("LOTE"), true);
                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("EMPACOTAMENTO"), true);
                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("VENCIMENTO"), true);


                if (getEdicao().getCODVERBA().trim().isEmpty())        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"),false);
                if (getEdicao().getLOTE().trim().isEmpty())            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("LOTE"), false);
                if (getEdicao().getEMPACOTAMENTO().trim().isEmpty())   getEdicao().set_isValid(Edicao.getIndiceByNameColunas("EMPACOTAMENTO"), false);
                if (getEdicao().getVENCIMENTO().trim().isEmpty())      getEdicao().set_isValid(Edicao.getIndiceByNameColunas("VENCIMENTO"), false);

                getEdicao().setDESCON(0f);
                getEdicao().setACORDO("");
                getEdicao().set_Acordo("");
                getEdicao().setDESCVER(0f);

            } else {

                //bonificação
                if ("003#011#013".contains(this.getCabec().getTIPO()) ) { //bonificações

                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);

                    if (getEdicao().getBONIQTD() == 0) {

                        getEdicao().setCODVERBA2("");
                        getEdicao().set_Verba2("");
                        getEdicao().setACORDO2("");
                        getEdicao().set_Acordo2("");
                        getEdicao().setDESCVER2(0f);

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), false);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("DESCON2"), false);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);


                    } else if (getEdicao().getBONIQTD() > 0) {


                        if (getEdicao().getCODVERBA2().trim().equals("")) {

                            getEdicao().setMENSAGEM("Favor Justificar A Bonificação Com Uma verba bonif. Ou Acordo.");
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                            msgerror += "Favor Justificar O desconto Com Uma Verba Ou Acordo.\n";

                        }

                    }

                } else {

                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);

                    if (getEdicao().getBONIQTD() == 0) {

                        getEdicao().setCODVERBA2("");
                        getEdicao().set_Verba2("");
                        getEdicao().setACORDO2("");
                        getEdicao().set_Acordo2("");
                        getEdicao().setDESCVER2(0f);

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("BONIQTD"), true);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);


                    } else if (getEdicao().getBONIQTD() > 0) {


                        if (getEdicao().getCODVERBA2().trim().equals("")) {

                            getEdicao().setMENSAGEM("Favor Justificar A Bonificação Com Uma verba bonif. Ou Acordo Bonif.");
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                            msgerror += "Favor Justificar O desconto Com Uma Verba Ou Acordo.\n";

                        }

                    }


                    //outros pedidos este campo é obrigatorio
                    //qtd venda é obrigatorio
                    det.set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);


                    if (getCabec().getTOTALPEDIDO().compareTo(0f) > 0 && getEdicao().getBONIQTD().compareTo(0f) > 0) {

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("QTD"), true);

                    } else {

                        if (getEdicao().getQTD().compareTo(0f) <= 0) {
                            getEdicao().setMENSAGEM("Favor Informar A Qtd Do Item");
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("QTD"), false);
                        }
                    }
                }

                //liberador de preço coluna venda
                if (!getEdicao().getSIMULADOR().trim().equals("") || !getEdicao().getPEDDIST().trim().equals("") || !getEdicao().getACORDO().trim().equals("") || !getEdicao().getCOTA().trim().equals("")) {

                    //Acordo
                    if (!getEdicao().getACORDO().trim().equals("")) {

                        Acordo acor = acordodao.seek(new String[]{getEdicao().getACORDO()});

                        if (acor != null) {

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                        } else {

                            getEdicao().set_Acordo("Nao Encontrei Acordo " + getEdicao().getACORDO());
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);
                            getEdicao().setSTATUS("2");

                        }

                    } else {

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                    }

                    if (!getEdicao().getSIMULADOR().trim().equals("")) {

                        Simulador simulador = simuladordao.seek(new String[]{getEdicao().getSIMULADOR(), cabec.getCODIGOFAT(), cabec.getLOJAFAT(), getEdicao().getPRODUTO()});

                        if (simulador != null) {

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");

                            getEdicao().setACORDO("");
                            getEdicao().set_Acordo("");

                            getEdicao().setDESCON(0f);

                            if (getEdicao().getPRCVEN().compareTo(simulador.getPRECOAPROVADO()) < 0) {

                                getEdicao().setPRCVEN(simulador.getPRECOAPROVADO());

                                msgerror += "Preço Não Pode Ser Menor Que O Preço Do Simulador " + format_05.format(simulador.getPRECOAPROVADO() + "\n");

                            }

                        }

                    }
                    if (!getEdicao().getPEDDIST().trim().equals("")) {

                        try {

                            // Procurar O Pedido Distribuição

                            PedDetTvsDAO dao = new PedDetTvsDAO();

                            dao.open();

                            ChavePedDistribuicao detalhe = dao.seekByMobile(new String[]{getEdicao().getPEDDISTFIL(), getEdicao().getPEDDIST(), getEdicao().getPEDDISTITEM()});

                            dao.close();

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");

                            getEdicao().setACORDO("");
                            getEdicao().set_Acordo("");

                            getEdicao().setDESCON(0f);

                            if (getEdicao().getPRCVEN().compareTo(detalhe.getPrcven()) < 0) {
                                getEdicao().setPRCVEN(detalhe.getPrcven());
                                //getEdicao().setBONIPREC(detalhe.getPRCVEN());
                                msgerror += "Preço Não Pode Ser Menor Que O Preço Do Ped. Distribuição " + format_05.format(detalhe.getPrcven()) + "\n";
                            }


                        } catch (Exception e) {

                            Log.i(LOG, e.getMessage());

                        }

                    }

                    if (!getEdicao().getCOTA().trim().equals("")){


                        try {

                            // Procurar A Cota

                            CotaDAO dao = new CotaDAO();

                            dao.open();

                            Cota detalhe = dao.seek(new String[]{getEdicao().getCOTA()});

                            dao.close();

                            if (detalhe == null){

                                detalhe = new Cota();

                                detalhe.set_PRECOFINAL(det.getPRECOFORMACAO());

                            } else {

                                detalhe.CalculoFinal(getEdicao().getDESCCONTRATO(),getEdicao().getTAXAFIN(),det.getCONVERSAO());
                            }

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");

                            getEdicao().setACORDO("");
                            getEdicao().set_Acordo("");

                            getEdicao().setDESCON(0f);

                            if (getEdicao().getPRCVEN().compareTo(detalhe.get_PRECOFINAL()) < 0) {
                                getEdicao().setPRCVEN(detalhe.get_PRECOFINAL());
                                //getEdicao().setBONIPREC(detalhe.getPRCVEN());
                                msgerror += "Preço Não Pode Ser Menor Que O Preço Da Cota " + format_05.format(detalhe.get_PRECOFINAL()) + "\n";
                            }


                        } catch (Exception e) {

                            Log.i(LOG, e.getMessage());

                        }


                    }

                    //liberador de preço coluna bonificacao
                    if (!getEdicao().getSIMULADOR2().trim().equals("") || !getEdicao().getPEDDIST2().trim().equals("") || !getEdicao().getACORDO2().trim().equals("")) {

                        //Acordo2
                        if (!getEdicao().getACORDO2().trim().equals("")) {

                            Acordo acor = acordodao.seek(new String[]{getEdicao().getACORDO2()});

                            if (acor != null) {

                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                            } else {

                                getEdicao().set_Acordo2("Nao Encontrei Acordo " + getEdicao().getACORDO());
                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), false);
                                getEdicao().setSTATUS("2");

                            }

                        } else {

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);

                        }

                        if (!getEdicao().getSIMULADOR2().trim().equals("")) {

                            Simulador simulador = simuladordao.seek(new String[]{getEdicao().getSIMULADOR2(), cabec.getCODIGOFAT(), cabec.getLOJAFAT(), getEdicao().getPRODUTO()});

                            if (simulador != null) {

                                getEdicao().setCODVERBA2("");
                                getEdicao().set_Verba2("");

                                getEdicao().setACORDO2("");
                                getEdicao().set_Acordo2("");
                                getEdicao().setBONIPREC(simulador.getPRECOAPROVADO());

                            }

                        }
                        if (!getEdicao().getPEDDIST2().trim().equals("")) {

                            try {

                                // Procurar O Pedido Distribuição

                                PedDetTvsDAO dao = new PedDetTvsDAO();

                                dao.open();

                                ChavePedDistribuicao detalhe = dao.seekByMobile(new String[]{getEdicao().getPEDDISTFIL2(), getEdicao().getPEDDIST2(), getEdicao().getPEDDISTITEM2()});

                                dao.close();

                                getEdicao().setCODVERBA2("");
                                getEdicao().set_Verba2("");

                                getEdicao().setACORDO2("");
                                getEdicao().set_Acordo2("");

                                getEdicao().setBONIPREC(detalhe.getPrcven());
                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);
                                msgerror = "";

                            } catch (Exception e) {

                                Log.i(LOG, e.getMessage());

                            }

                        }
                    }
                }

                //Verba
                if (!getEdicao().getCODVERBA().trim().equals("")) {

                    Verba ver = verbadao.seek(new String[]{getEdicao().getCODVERBA()});

                    if (ver != null) {

                        if (ver.getTIPO().equals("P")) {

                            if (getCabec().getSALDOAPROVEITAMENTO().compareTo(0f) < 0) {

                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                                getEdicao().set_Verba2("SALDO NEGATIVO APROV. POLÍTCA");

                            } else {

                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                                getEdicao().set_Verba(ver.getCODIGO() + "-" + ver.getDESCRICAO());

                            }

                        }
                    } else {

                        getEdicao().set_Verba("Nao Encontrei Verba " + getEdicao().getCODVERBA());
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                        getEdicao().setSTATUS("2");

                    }

                } else {
                    if (getEdicao().getDESCVER().compareTo(0f) > 0) {

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);

                    } else {

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);

                    }

                }


                //Verba bonificacao

                if (!getEdicao().getCODVERBA2().trim().equals("")) {

                    Verba ver = verbadao.seek(new String[]{getEdicao().getCODVERBA2()});

                    if (ver != null) {

                        if (getEdicao().get_UsaPolitica().equals("S")){

                            if (getCabec().getSALDOAPROVEITAMENTO().compareTo(0f) < 0) {

                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                                getEdicao().set_Verba2("SALDO NEGATIVO APROV. POLÍTCA");

                            } else {

                                getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                                getEdicao().set_Verba2(ver.getCODIGO() + "-" + ver.getDESCRICAO());
                            }

                        } else {

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                            getEdicao().set_Verba2(ver.getCODIGO() + "-" + ver.getDESCRICAO());
                        }

                    } else {

                        getEdicao().set_Verba2("Nao Encontrei Verba " + getEdicao().getCODVERBA2());
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);
                        getEdicao().setSTATUS("2");

                    }

                } else {

                    if (getEdicao().getPEDDIST2().equals("")) {

                        if (getEdicao().getBONIQTD().compareTo(0f) > 0) {

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), false);

                        } else {

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);

                        }

                    } else {

                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA2"), true);
                        getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO2"), true);

                    }
                }

                //Vendas
                //Valida descontos

                Float descMaximo = getEdicao().getDESCONTOMAIS() + getEdicao().getDESCONTOPOL();

                Float descVerba = 0f;

                Float desconto = getEdicao().getDESCON();

                if (Float.compare(desconto, descMaximo) > 0) {

                    getEdicao().setPRCVEN(getEdicao().getPRECOFORMACAO());
                    getEdicao().setDESCON(0f);
                    getEdicao().setCODVERBA("");
                    getEdicao().set_Verba("");
                    getEdicao().setACORDO("");
                    getEdicao().set_Acordo("");
                    getEdicao().setDESCVER(0f);

                    getEdicao().setMENSAGEM("Desconto Acima Do Permitido !!! " + format_05.format(descMaximo - desconto));
                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("PRCVEN"), true);
                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("DESCON"), true);
                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                    getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);
                    msgerror += "Desconto Acima Do Permitido !!! " + format_05.format(descMaximo) + "\n";


                } else {

                    if (Float.compare(getEdicao().getDESCON(), getEdicao().getDESCONTOPOL()) > 0) {

                        descVerba = getEdicao().getDESCON() - getEdicao().getDESCONTOPOL();

                    }

                    getEdicao().setDESCVER(descVerba);

                    if (TemVerba(getEdicao().getDESCONTOPOL(),getEdicao().getPERMAX(),getEdicao().getDESCON())) {

                        Log.i("TEMVERBA","Verba Sem Fundamento");

                        getEdicao().setCODVERBA("900000");

                    } else {

                        if (getEdicao().getCODVERBA().trim().equals("900000")){

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");

                        }

                    }

                    Log.i("TEMVERBA","VERBA ASSOCIADA: "+det.getCODVERBA()+"-"+det.get_Verba());

                    if ( (!TemVerba(getEdicao().getDESCONTOPOL(),getEdicao().getPERMAX(),getEdicao().getDESCON())) && (Float.compare(getEdicao().getDESCVER(), 0) == 0)) {

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");
                            getEdicao().setACORDO("");
                            getEdicao().set_Acordo("");

                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), true);
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), true);
                            msgerror = "";


                        }
                    }

                    Log.i("TEMVERBA","VERBA ASSOCIADA: "+det.getCODVERBA()+"-"+det.get_Verba());

                    //Verba
                    if (getEdicao().getDESCVER() > 0) {


                        if (getEdicao().getCODVERBA().trim().equals("900000")){

                            getEdicao().setCODVERBA("");
                            getEdicao().set_Verba("");

                        }

                        if (getEdicao().getCODVERBA().trim().equals("")) {

                            getEdicao().setMENSAGEM("Favor Justificar O desconto Com Uma verba !!!");
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("CODVERBA"), false);
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);
                            msgerror += "Favor Justificar O desconto Com Uma Verba de Venda E Ou Acordo.\n";

                        }


                        if ((getEdicao().getCODVERBA().trim().equals("")) && (getEdicao().getACORDO().trim().equals(""))) {

                            getEdicao().setMENSAGEM("Favor Justificar O desconto Com Um Acordo !!!");
                            getEdicao().set_isValid(Edicao.getIndiceByNameColunas("ACORDO"), false);

                        }

                    }

                     Log.i("TEMVERBA","VERBA ASSOCIADA: "+det.getCODVERBA()+"-"+det.get_Verba());
                }
                if (!getEdicao().getNRO().isEmpty()) {

                    getEdicao().setSTATUS();

                } else {

                    getEdicao().setSTATUS("2");
                }

                if (!msgerror.isEmpty()) Toast.makeText(App.getCustomAppContext(),msgerror, Toast.LENGTH_SHORT).show();

            } catch (Exception e){

                getEdicao().setSTATUS("2");

            }
        finally {


                produtodao.close();

                verbadao.close();

                acordodao.close();

                simuladordao.close();


            }


        }

    public void AdicionaProdutoAnterior(List<PedidoDetMB_fast> old_values, int opcaoRecalculo) throws Exception {

        if (old_values == null || old_values.size() == 0){

            return;

        }

        for(PedidoDetMB_fast old : old_values){

            for(int x = 0 ; x < lsDetalhe.size() ; x++){

                if (lsDetalhe.get(x).getPRODUTO().equals(old.getPRODUTO())){

                    Float prcven = old.getPRCVEN();

                    Float prcbon = old.getBONIPREC();

                    Float perc   = old.getDESCON();

                    lsDetalhe.get(x).setBONIPREC(prcbon);

                    lsDetalhe.get(x).atualizar(old);

                    //Ignora recalculo para sim.,ped dist e cota
                    if (!lsDetalhe.get(x).getSIMULADOR().trim().isEmpty() || !lsDetalhe.get(x).getPEDDIST().trim().isEmpty() || !lsDetalhe.get(x).getCOTA().trim().isEmpty()){

                        continue;
                    }

                    switch (opcaoRecalculo){

                        case 1: { //mantém o desconto

                            lsDetalhe.get(x).setDESCON(perc);

                            lsDetalhe.get(x).setBONIPREC(prcbon);

                            break;
                        }
                        case 2: { //mantém o preço anterior

                            ajustaPrcVenByPreco(lsDetalhe.get(x), prcven);

                            lsDetalhe.get(x).setBONIPREC(prcbon);

                            break;
                        }

                        default:{

                            //preço base não faço nada...

                        }


                    };

                }

            }


        }

    }

    public int getIndiceByCodigo(String codigo) {

        int retorno = -1;

        int x = 0;

        for (TabPrecoCabec tab : lsTabPrecoCabec) {

            if (tab.getCODIGO().equals(codigo)) {

                retorno = x;

                break;

            }

            x++;

        }

        return retorno;

    }


    public void FormandoPreco_V2(int tipo, int pos, Float FDPrevistos,Float fretearroz,Float fretefeijao) throws ExceptionItemProduto {

        Float fator = 0f;

        Float frete = 0f;

        Float FreteArroz  = 0f;

        Float FreteFeijao = 0f;

        try {


            //Calcula frete
            if (tipo == FORMANDOPRECO_FRETE){

                FreteArroz   = fretearroz;

                FreteFeijao  = fretefeijao;


            }  else {


                FreteArroz   = this.FRETEARROZ;

                FreteFeijao  = this.FRETEFEIJAO;


            }


            String Codigo = lsDetalhe.get(pos).getPRODUTO();

            Contrato contrato = null;

            float preco = 0f;

            int posTabPreco = seekTabPreco(Codigo);

            if (posTabPreco == -1) {

                throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "- Produto Não Existe Na Lista De Preço");

            }

            //Tabela de Preco

            TabPreco tabpreco = lsTabPreco.get(posTabPreco);

            if  (tabpreco.getFLAGFAIXA().equals("X")) {

                //Vou trabalhar só com variaveis
                if (tipo == FORMANDOPRECO_FRETE){

                    if (lsDetalhe.get(pos).get_CodGrupo().equals("3.02")) {

                        frete = FreteArroz * lsDetalhe.get(pos).getCONVERSAO();

                    } else {

                        frete = FreteFeijao * lsDetalhe.get(pos).getCONVERSAO();

                    }

                    //Arredondando frete

                    frete = Arredondamento(frete,3);

                    frete = Arredondamento(frete,2);

                    Float descarga = ((clienteEntrega.getVLRDESCARGA2() * 30) *  lsDetalhe.get(pos).getCONVERSAO());

                    BigDecimal tot2 = new BigDecimal(Float.toString(descarga));

                    tot2 = tot2.setScale(2, BigDecimal.ROUND_HALF_UP);

                    descarga = tot2.floatValue();

                    if (tabpreco.getTIPOFRETE().equals("C")) frete += descarga;

                    //Frete retira

                    if (cabec.getRETIRA().equals("1") || FDPrevistos.compareTo(0f) == 0) frete = 0f;

                    //Imposto

                    //Pego no detalhe

                    BigDecimal prcbase = BigDecimal.valueOf(lsDetalhe.get(pos).getPRECOTABELA() + frete); //  + (!clienteEntrega.getPARTICIPANTE().trim().isEmpty() ? tabpreco.getVLRCAMPANHA() *  produto.getCONVERSAO() : 0));

                    prcbase = prcbase.setScale(2, BigDecimal.ROUND_HALF_UP);

                    //TAXA FINANCEIRA

                    if (cliente.getTAXAFIN().equals("S")) {

                        preco = (prcbase.floatValue()) / ((Float.valueOf(lsDetalhe.get(pos).getFATOR()) - condpagto.getJuros2().floatValue() - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO()) / 100);

                    } else {

                        preco = (prcbase.floatValue()) / ((Float.valueOf(lsDetalhe.get(pos).getFATOR()) - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO()) / 100);
                    }

                    preco = preco / (1 - (lsDetalhe.get(pos).getPOLITICABASE() / 100));

                    BigDecimal tot3 = new BigDecimal(Float.toString(preco));

                    tot3 = tot3.setScale(2, BigDecimal.ROUND_HALF_UP);

                    preco = tot3.floatValue();

                    //ignora preço calculado
                    if (!getEdicao().getSIMULADOR().trim().equals("") ||
                            !getEdicao().getPEDDIST().trim().equals("") ||
                            !getEdicao().getCOTA().trim().equals("")) {

                        //não faz nada

                    } else {

                        lsDetalhe.get(pos).setLsFreste(FDPrevistos,preco);

                    }

                }
                else
                {
                    lsDetalhe.get(pos).setPRECOTABELA(tabpreco.getPRCBASE());

                    lsDetalhe.get(pos).setACRESCIMOMAIS(tabpreco.getACRESCIMOMAIS());

                    lsDetalhe.get(pos).setDESCONTOMAIS(tabpreco.getDESCONTOMAIS());

                    lsDetalhe.get(pos).setUSATAXAFIN(cliente.getTAXAFIN().equals("S") ? "SIM" : "NãO");

                    if (tabpreco.getTIPOPRAZO().equals("C")) {

                        lsDetalhe.get(pos).setTAXAFIN(condpagto.getJUROS());

                    } else {

                        lsDetalhe.get(pos).setTAXAFIN(tabpreco.getPERPRAZO().toString());

                    }
                    lsDetalhe.get(pos).setFATOR(tabpreco.getFATOR());

                    lsDetalhe.get(pos).setPOLITICABASE(tabpreco.getPOLITICABASE());

                    produtodao.open();

                    produto = produtodao.seek(new String[]{Codigo});

                    produtodao.close();

                    if (produto != null) {

                        lsDetalhe.get(pos).setPRODUTO(Codigo);

                        lsDetalhe.get(pos).setUM(produto.getUM());

                        lsDetalhe.get(pos).set_Produto(produto.getDESCRICAO().trim() + " Und. " + produto.getUM());

                        /* Alterada a maneira de buscar o desconto do contrato
                           Se o campo TIPOCONTRATO da tabela de Preço for igual a "M", utilizo o percentual que está no
                           detalhe da tabela de preço. Caso contrário utilizo o desconto do cadastro de contratos.
                           15/02/2018
                         */

                        if (tabpreco.getTIPOCONTRATO().equals("M")){

                            lsDetalhe.get(pos).setDESCCONTRATO(tabpreco.getPERCONTRATO());

                        } else {

                            //Procura o Desconto de Contrato
                            lsDetalhe.get(pos).setDESCCONTRATO(0f);

                            if (lsContrato.size() == 0) {

                                lsDetalhe.get(pos).setDESCCONTRATO(0f);

                            } else {

                                contrato = lsContrato.get(0);

                                if (contrato.getTIPO().equals("T")) {

                                    if (cliente.getTIPOCON().equals("1") || cliente.getTIPOCON().trim().isEmpty()) { //normal ou loja

                                        lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                    } else {

                                        lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCCD() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                    }

                                } else {

                                    int indice = seekContratoBYChave(lsDetalhe.get(pos).getPRODUTO(), produto.getMARCA(), produto.getGRUPO(), contrato.getChave());

                                    if (indice != -1) {

                                        contrato = lsContrato.get(indice);

                                        if (cliente.getTIPOCON().equals("1") || cliente.getTIPOCON().trim().isEmpty()) { //normal ou loja

                                            lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                        } else {

                                            lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCCD() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                        }

                                    } else {

                                        Log.i(LOG, "Não Encontrei Contrato");

                                    }
                                }
                            }
                        }

                        if (produto.getGRUPO().equals("3.02")) {

                            frete = FreteArroz * produto.getCONVERSAO();

                        } else {

                            frete = FreteFeijao * produto.getCONVERSAO();

                        }

                        //Arredondando frete

                        frete = Arredondamento(frete,3);

                        frete = Arredondamento(frete,2);

                        Float descarga = ((clienteEntrega.getVLRDESCARGA2() * 30) * produto.getCONVERSAO());

                        BigDecimal tot2 = new BigDecimal(Float.toString(descarga));

                        tot2 = tot2.setScale(2, BigDecimal.ROUND_HALF_UP);

                        descarga = tot2.floatValue();

                        lsDetalhe.get(pos).setFRETE(frete);

                        lsDetalhe.get(pos).setVLRDESCARGA2(descarga);

                        if (tabpreco.getTIPOFRETE().equals("C")) frete += descarga;

                        //Frete retira

                        if (cabec.getRETIRA().equals("1")) frete = 0f;

                        //Imposto

                        impostodao.open();

                        Imposto imposto = impostodao.seek(new String[]{clienteEntrega.getESTADO(), produto.getGRUPO(), produto.getORIGEM()});

                        impostodao.close();

                        if (imposto == null) {

                            lsDetalhe.get(pos).setIMPOSTO(0f);

                        } else {

                            lsDetalhe.get(pos).setIMPOSTO(imposto.getTOTALIMPOSTO());

                        }

                        lsDetalhe.get(pos).setORIGEM(produto.getORIGEM());

                        BigDecimal prcbase = BigDecimal.valueOf(lsDetalhe.get(pos).getPRECOTABELA() + frete); //  + (!clienteEntrega.getPARTICIPANTE().trim().isEmpty() ? tabpreco.getVLRCAMPANHA() *  produto.getCONVERSAO() : 0));

                        prcbase = prcbase.setScale(2, BigDecimal.ROUND_HALF_UP);


                        //TAXA FINANCEIRA

                        try {
                            if (lsDetalhe.get(pos).getUSATAXAFIN().equals("SIM")) {

                                if (tabpreco.getTIPOFRETE().equals("C")) {

                                    preco = (prcbase.floatValue()) / ((Float.valueOf(lsDetalhe.get(pos).getFATOR()) - condpagto.getJuros2().floatValue() - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO()) / 100);

                                } else {

                                    preco = (prcbase.floatValue()) / ((Float.valueOf(lsDetalhe.get(pos).getFATOR()) - tabpreco.getPERPRAZO().floatValue() - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO()) / 100);

                                }
                            } else {

                                preco = (prcbase.floatValue()) / ((Float.valueOf(lsDetalhe.get(pos).getFATOR()) - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO()) / 100);
                            }
                        } catch (Exception e){

                            preco = 0f;

                        }
                        preco = preco / (1 - (lsDetalhe.get(pos).getPOLITICABASE() / 100));

                        BigDecimal tot3 = new BigDecimal(Float.toString(preco));

                        tot3 = tot3.setScale(2, BigDecimal.ROUND_HALF_UP);

                        preco = tot3.floatValue();

                        //ignora preço calculado
                        if (!getEdicao().getSIMULADOR().trim().equals("") ||
                                !getEdicao().getPEDDIST().trim().equals("") ||
                                !getEdicao().getCOTA().trim().equals("")) {

                            //não faz nada

                        } else {

                            lsDetalhe.get(pos).setPRCVEN(preco);

                        }

                        lsDetalhe.get(pos).setPRECOFORMACAO(preco);

                        if (getCondpagto().getCODIGO().equals("033")) {

                            if (lsDetalhe.get(pos).getBONIPREC().compareTo(0f) == 0) {

                                lsDetalhe.get(pos).setBONIPREC(preco);

                            }
                        }


                        //Busca Politica
                        if (App.TotvsSIMNAO(tabpreco.getFLAGDESCCANAL()).equals("NÃO")) {

                            lsDetalhe.get(pos).setDESCONTOPOL(0f);

                        } else {

                            politicadao.open();

                            ParamPolitica param = new ParamPolitica();

                            param.setCCLIENTE(cliente.getCODIGO());
                            param.setCLOJA(cliente.getLOJA());
                            param.setCCANAL(cliente.getCANAL());
                            param.setCGRUPO(produto.getGRUPO());
                            param.setCREGIAO(cliente.getREGIAO());
                            param.setCMARCA(produto.getMARCA());
                            param.setCREDE(cliente.getREDE());
                            param.setCPRODUTO(produto.getCODIGO());
                            param.setCVENDE(cliente.getVEND());
                            param.setCSUPER(cliente.getGA());
                            param.setCGEREN(cliente.getGNV());

                            politica = politicadao.getPolitica(param);

                            politicadao.close();

                            if (politica == null) {

                                lsDetalhe.get(pos).setDESCONTOPOL(0f);
                                lsDetalhe.get(pos).setPERMAX(0f);

                            } else {

                                lsDetalhe.get(pos).setDESCONTOPOL(politica.getDESCONTO());
                                lsDetalhe.get(pos).setPERMAX(politica.getDESCLIM());

                            }
                        }


                        //Busca DNA
                        if (App.TotvsSIMNAO(tabpreco.getFLAGDESCCANAL()).equals("NÃO")) {

                            lsDetalhe.get(pos).setDNADESCONTO(0f);
                            lsDetalhe.get(pos).setDNAVALOR(0f);

                        } else {

                            politicadao.open();

                            ParamPolitica param = new ParamPolitica();

                            param.setCCLIENTE(cliente.getCODIGO());
                            param.setCLOJA(cliente.getLOJA());
                            param.setCCANAL(cliente.getCANAL());
                            param.setCGRUPO(produto.getGRUPO());
                            param.setCREGIAO(cliente.getREGIAO());
                            param.setCMARCA(produto.getMARCA());
                            param.setCREDE(cliente.getREDE());
                            param.setCPRODUTO(produto.getCODIGO());
                            param.setCVENDE(cliente.getVEND());
                            param.setCSUPER(cliente.getGA());
                            param.setCGEREN(cliente.getGNV());

                            politica = politicadao.getDNA(param);

                            politicadao.close();

                            if (politica == null) {

                                lsDetalhe.get(pos).setDNADESCONTO(0f);
                                lsDetalhe.get(pos).setDNAVALOR(0f);

                            } else {

                                lsDetalhe.get(pos).setDNADESCONTO(0f);

                                lsDetalhe.get(pos).setDNAVALOR(politica.getDESCONTO() * lsDetalhe.get(pos).getCONVERSAO());

                                if ((lsDetalhe.get(pos).getPRECOFORMACAO().compareTo(0f) != 0)) {

                                    BigDecimal precoformacao;

                                    BigDecimal descpol;

                                    BigDecimal dnavlr;

                                    BigDecimal novoper;

                                    precoformacao = new BigDecimal(lsDetalhe.get(pos).getPRECOFORMACAO());

                                    descpol = new BigDecimal(lsDetalhe.get(pos).getDESCONTOPOL());

                                    dnavlr = new BigDecimal(lsDetalhe.get(pos).getDNAVALOR());

                                    novoper = new BigDecimal((precoformacao.doubleValue() - (precoformacao.doubleValue() * (descpol.doubleValue() / 100))) - dnavlr.doubleValue());

                                    novoper = new BigDecimal(100 - ((novoper.doubleValue() / precoformacao.doubleValue()) * 100));

                                    novoper = novoper.setScale(2, BigDecimal.ROUND_HALF_UP);

                                    lsDetalhe.get(pos).setDESCONTOPOL(novoper.floatValue());

                                }

                            }
                        }

                    } else {


                        throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "-Produto Da Lista Não Existe No Cadastro De Produto.");

                    }

                }
            } else {

                produtodao.open();

                produto = produtodao.seek(new String[]{Codigo});

                produtodao.close();

                if (produto != null) {

                    lsDetalhe.get(pos).setPRODUTO(Codigo);

                    lsDetalhe.get(pos).setUM(produto.getUM());

                    lsDetalhe.get(pos).set_Produto(produto.getDESCRICAO().trim() + " Und. " + produto.getUM());

                    //Procura o Desconto de Contrato
                    lsDetalhe.get(pos).setDESCCONTRATO(0f);

                    if (lsContrato.size() == 0) {

                        lsDetalhe.get(pos).setDESCCONTRATO(0f);

                    } else {

                        contrato = lsContrato.get(0);

                        if (contrato.getTIPO().equals("T")) {

                            if (cliente.getTIPOCON().equals("1") || cliente.getTIPOCON().trim().isEmpty()) { //normal ou loja

                                lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                            } else {

                                lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCCD() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                            }

                        } else {

                            int indice = seekContratoBYChave(lsDetalhe.get(pos).getPRODUTO(), produto.getMARCA(), produto.getGRUPO(), contrato.getChave());

                            if (indice != -1) {

                                contrato = lsContrato.get(indice);

                                if (cliente.getTIPOCON().equals("1") || cliente.getTIPOCON().trim().isEmpty()) { //normal ou loja

                                    lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                } else {

                                    lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCCD() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                                }

                            } else {

                                Log.i(LOG, "Não Encontrei Contrato");


                            }
                        }
                    }


                    //TAXA FINANCEIRA

                    if (cliente.getTAXAFIN().equals("S")) {

                        preco = ((tabpreco.getPRCVEN() * condpagto.getJuros().floatValue())) / ((1 - (lsDetalhe.get(pos).getDESCCONTRATO() / 100)) * (1 - (cliente.getDESCCLIENTE() / 100)));

                    } else {

                        preco = ((tabpreco.getPRCVEN() * 1)) / ((1 - (lsDetalhe.get(pos).getDESCCONTRATO() / 100)) * (1 - (cliente.getDESCCLIENTE() / 100)));
                    }

                    //DESCONTO RETIRA SÓ NA TABELA VELHA
                    preco = preco - cabec.getDESCRET();

                    BigDecimal tot = new BigDecimal(preco);

                    tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

                    preco = tot.floatValue();

                    //ignora preço calculado
                    if (    !getEdicao().getSIMULADOR().trim().equals("") ||
                            !getEdicao().getPEDDIST().trim().equals("") ||
                            !getEdicao().getCOTA().trim().equals("") ) {
                        //não faz nada

                    } else {

                        lsDetalhe.get(pos).setPRCVEN(preco);

                    }


                    lsDetalhe.get(pos).setPRECOFORMACAO(preco);

                    lsDetalhe.get(pos).setDESCON(0f);

                    if (getCondpagto().getCODIGO().equals("033")){

                        if (lsDetalhe.get(pos).getBONIPREC().compareTo(0f)==0){

                            lsDetalhe.get(pos).setBONIPREC(preco);

                        }
                    } else {

                        //lsDetalhe.get(pos).setBONIPREC(preco);

                    }

                    //Imposto

                    lsDetalhe.get(pos).setIMPOSTO(0f);

                    //Busca Politica
                    if (App.TotvsSIMNAO(tabpreco.getFLAGDESCCANAL()).equals("NÃO")){

                        lsDetalhe.get(pos).setDESCONTOPOL(0f);
                        lsDetalhe.get(pos).setPERMAX(0f);

                    } else {

                        politicadao.open();

                        ParamPolitica param = new ParamPolitica();

                        param.setCCLIENTE(cliente.getCODIGO());
                        param.setCLOJA(cliente.getLOJA());
                        param.setCCANAL(cliente.getCANAL());
                        param.setCGRUPO(produto.getGRUPO());
                        param.setCREGIAO(cliente.getREGIAO());
                        param.setCMARCA(produto.getMARCA());
                        param.setCREDE(cliente.getREDE());
                        param.setCPRODUTO(produto.getCODIGO());
                        param.setCVENDE(cliente.getVEND());
                        param.setCSUPER(cliente.getGA());
                        param.setCGEREN(cliente.getGNV());

                        politica = politicadao.getPolitica(param);

                        politicadao.close();

                        if (politica == null) {

                            lsDetalhe.get(pos).setDESCONTOPOL(0f);
                            lsDetalhe.get(pos).setPERMAX(0f);

                        } else {

                            lsDetalhe.get(pos).setDESCONTOPOL(politica.getDESCONTO());
                            lsDetalhe.get(pos).setPERMAX(politica.getDESCLIM());

                        }
                    }


                    //Busca DNA
                    if (App.TotvsSIMNAO(tabpreco.getFLAGDESCCANAL()).equals("NÃO")){

                        lsDetalhe.get(pos).setDNADESCONTO(0f);
                        lsDetalhe.get(pos).setDNAVALOR(0f);

                    } else {

                        politicadao.open();

                        ParamPolitica param = new ParamPolitica();

                        param.setCCLIENTE(cliente.getCODIGO());
                        param.setCLOJA(cliente.getLOJA());
                        param.setCCANAL(cliente.getCANAL());
                        param.setCGRUPO(produto.getGRUPO());
                        param.setCREGIAO(cliente.getREGIAO());
                        param.setCMARCA(produto.getMARCA());
                        param.setCREDE(cliente.getREDE());
                        param.setCPRODUTO(produto.getCODIGO());
                        param.setCVENDE(cliente.getVEND());
                        param.setCSUPER(cliente.getGA());
                        param.setCGEREN(cliente.getGNV());

                        politica = politicadao.getDNA(param);

                        politicadao.close();

                        if (politica == null) {

                            lsDetalhe.get(pos).setDNADESCONTO(0f);
                            lsDetalhe.get(pos).setDNAVALOR(0f);

                        } else {

                            lsDetalhe.get(pos).setDNADESCONTO(0f);

                            lsDetalhe.get(pos).setDNAVALOR(politica.getDESCONTO() * lsDetalhe.get(pos).getCONVERSAO());

                            if ( (lsDetalhe.get(pos).getPRECOFORMACAO().compareTo(0f) != 0) ) {

                                BigDecimal precoformacao;

                                BigDecimal descpol;

                                BigDecimal dnavlr;

                                BigDecimal novoper;

                                precoformacao = new BigDecimal(lsDetalhe.get(pos).getPRECOFORMACAO());

                                descpol       = new BigDecimal(lsDetalhe.get(pos).getDESCONTOPOL());

                                dnavlr        = new BigDecimal(lsDetalhe.get(pos).getDNAVALOR());

                                novoper       = new BigDecimal((precoformacao.doubleValue() - (precoformacao.doubleValue() * (descpol.doubleValue() / 100))) - dnavlr.doubleValue());

                                novoper       = new BigDecimal(100 - ((novoper.doubleValue() / precoformacao.doubleValue()) * 100));

                                novoper       = novoper.setScale(2, BigDecimal.ROUND_HALF_UP);

                                lsDetalhe.get(pos).setDESCONTOPOL(novoper.floatValue());

                            }

                        }
                    }


                } else {

                    throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "-Produto Da Lista Não Existe No Cadastro De Produto.");

                }
            }
        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Log.i("DUMP",exceptionAsString);

            throw new ExceptionItemProduto(e.getMessage());

        }

    }



    public void trocaBasePreco() throws ExceptionItemProduto {


        try {

            for (int pos = 0; pos < lsDetalhe.size(); pos++) {

                FormandoPreco_V2(FORMANDOPRECO_FULL,pos,cabec.getFDSPREVISTO(),FRETEARROZ,FRETEFEIJAO);

                recalculoItem(pos);
            }

        } catch (Exception e) {


            throw new ExceptionItemProduto(e.getMessage());

        }

    }


    public void trocaBasePrecoCP() throws ExceptionItemProduto {


        try {

            for (int pos = 0; pos < lsDetalhe.size(); pos++) {

                FormandoPreco_V2(FORMANDOPRECO_FULL,pos,cabec.getFDSPREVISTO(),FRETEARROZ,FRETEFEIJAO);

                recalculoItem(pos);
            }

        } catch (Exception e) {


            throw new ExceptionItemProduto(e.getMessage());

        }

    }

    public void atualizaTabela() throws ExceptionItemProduto{

        try {

            tabprecocabecdao.open();

            lsTabPreco      = tabprecocabecdao.seekTabela(cabec.getTABPRECO(), cabec.getTIPO());

            tabprecocabecdao.close();


        } catch ( Exception e){

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void validaTrocaDeTabela(String tabela) throws ExceptionItemProduto {

        List<TabPreco> lsLista = new ArrayList<>();


        try {

            tabprecocabecdao.open();

            lsLista = tabprecocabecdao.seekTabela(tabela, cabec.getTIPO());

            tabprecocabecdao.close();

            for (int pos = 0; pos < lsDetalhe.size(); pos++) {

                if (lsDetalhe.get(pos).getNRO().isEmpty()){

                    continue;

                }

                String Codigo     = lsDetalhe.get(pos).getPRODUTO();

                String Descricao  = lsDetalhe.get(pos).get_Produto();

                int posTabPreco = seekTabPrecoTemporaria(lsLista,Codigo);

                if (posTabPreco == -1) {

                    throw new ExceptionItemProduto("Cod: " + Codigo.trim() + " - "+Descricao+" Não Existe Na Lista De Preço\nNão Posso Trocar A Tabela De Preço.");

                }

            }

        } catch (Exception e) {


            throw new ExceptionItemProduto(e.getMessage());

        }

    }

    public boolean TemItens(){

        if (lsDetalhe == null || lsDetalhe.size() == 0){

            return false;

        }

//        for(PedidoDetMB_fast det :lsDetalhe){
//
//            if (!(det.getNRO().isEmpty())){
//
//                return true;
//
//            }
//
//
//        }

        return true;

    }

    public ArrayList<PedidoDetMB_fast> ItensUsados(){

        ArrayList<PedidoDetMB_fast> usados = new ArrayList<>();

        if (lsDetalhe == null || lsDetalhe.size() == 0){

            return usados;

        }

        for(PedidoDetMB_fast det :lsDetalhe){

            if (!(det.getNRO().isEmpty())){

                usados.add(det);

            }


        }

        return usados;

    }

    public void setAgendamento(String agendamento) {
        Agendamento = agendamento;
    }

    public String getAgendamento() {
        return Agendamento;
    }

    public Float getAPROVEITAMENTO() {
        return APROVEITAMENTO;
    }

    public void setAPROVEITAMENTO(Float APROVEITAMENTO) {
        this.APROVEITAMENTO = APROVEITAMENTO;
    }

    public Cliente getClienteEntrega() {
        return clienteEntrega;
    }

    public void setClienteEntrega(Cliente clienteEntrega) {
        this.clienteEntrega = clienteEntrega;
    }

    private Float Arredondamento(Float value, int cd ){

        Float retorno = 0f;

        BigDecimal tot2 = new BigDecimal(Float.toString(value));

        tot2 = tot2.setScale(cd, BigDecimal.ROUND_HALF_UP);

        retorno = tot2.floatValue();

        return retorno;

    }

    public Float getFRETEFEIJAO() {
        return FRETEFEIJAO;
    }

    public void setFRETEFEIJAO(Float FRETEFEIJAO) {
        this.FRETEFEIJAO = FRETEFEIJAO;
    }

    public Float getFRETEARROZ() {
        return FRETEARROZ;
    }

    public void setFRETEARROZ(Float FRETEARROZ) {
        this.FRETEARROZ = FRETEARROZ;
    }

    public List<Float> getFAIXAS() {
        try {

            fretedao.open();

            this.FAIXAS = fretedao.getFaixas();

            fretedao.close();

        }catch (Exception e){

            this.FAIXAS = new ArrayList<>();
        }

        return this.FAIXAS;
    }

    public void loadMarcasECategorias(String Marca, String Categoria){

        Map<String,String> marcas     = new TreeMap<String, String>();
        Map<String,String> categorias = new TreeMap<String, String>();

        for(PedidoDetMB_fast obj : lsDetalhe ) {

            if (Categoria.equals("000") && Marca.equals("000")) {

                marcas.put(obj.get_CodMarca(), obj.get_Marca());
                categorias.put(obj.get_CodGrupo(), obj.get_Grupo());

            } else {

                if ( Marca.equals("000") || obj.get_CodMarca().equals(Marca) ) {
                    marcas.put(obj.get_CodMarca(), obj.get_Marca());

                }

                if ( Categoria.equals("000") || obj.get_CodGrupo().equals(Categoria) ) {

                    categorias.put(obj.get_CodGrupo(), obj.get_Grupo());
                    if (Marca.equals("XXX"))  marcas.put(obj.get_CodMarca(), obj.get_Marca());

                }
            }
        }

        this.lsCategoria = new ArrayList<>();this.lsCategoria.add(new String[] {"000","TODAS"});
        this.lsMarca     = new ArrayList<>();this.lsMarca.add(new String[]     {"000","TODAS"});

        for(Map.Entry<String,String> categoria : categorias.entrySet()) this.lsCategoria.add(new String[] {categoria.getKey(),categoria.getValue().trim()});
        for(Map.Entry<String,String> marca     : marcas.entrySet()) this.lsMarca.add(new String[] {marca.getKey(),marca.getValue().trim()});

    }

    public CondPagto seekcp(String codigo){

        CondPagto cond = null;

        try {

            condpagtodao.open();

            cond = condpagtodao.seek(new String[]{codigo});

            condpagtodao.close();

        } catch (Exception e){

            cond = null;
        }

        return cond;
    }

    public List<CondPagto> getLsCondPagtoByFiltro() {

        List<CondPagto> retorno = new ArrayList<>();

        if ("003#011#005#006#007".contains(getCabec().getTIPO())) {

            if ("003#011".contains(getCabec().getTIPO())) {

                retorno.add(seekcp("033"));

            } else {

                retorno.add(seekcp(getCliente().getCONDPAGTO()));

            }

        } else {

            return lsCondPagto;

        }

        return retorno;

    }

    public Boolean TemVerba(Float perc, Float percLim, Float percDesconto){

        Boolean retorno = true;

        Float Limite = 0f;


        //Nao tem desconto no item
        if (percDesconto.compareTo(0f) == 0) {

            return false;

        }

        //Caiu na verba
        if (Float.compare(perc,percDesconto) < 0) {

            return false;


        }
        try {

            Limite = perc * (percLim / 100);

            if (Float.compare(percDesconto,Limite)  > 0){

                retorno = true;

            } else {

                retorno = false;

            }


        } catch (Exception e){

            retorno = false;

        }

        return retorno;
    }
}
