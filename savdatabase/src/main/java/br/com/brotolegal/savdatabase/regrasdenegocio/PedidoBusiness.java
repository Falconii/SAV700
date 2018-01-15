package br.com.brotolegal.savdatabase.regrasdenegocio;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.AcordoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.CondPagtoDAO;
import br.com.brotolegal.savdatabase.dao.ContratoDAO;
import br.com.brotolegal.savdatabase.dao.FreteDAO;
import br.com.brotolegal.savdatabase.dao.ImpostoDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.PedidoDetMbDAO;
import br.com.brotolegal.savdatabase.dao.PoliticaDAO;
import br.com.brotolegal.savdatabase.dao.ProdutoDAO;
import br.com.brotolegal.savdatabase.dao.SimuladorDAO;
import br.com.brotolegal.savdatabase.dao.TabPrecoCabecDAO;
import br.com.brotolegal.savdatabase.dao.VerbaDAO;
import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.CondPagto;
import br.com.brotolegal.savdatabase.entities.Contrato;
import br.com.brotolegal.savdatabase.entities.Frete;
import br.com.brotolegal.savdatabase.entities.Imposto;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Politica;
import br.com.brotolegal.savdatabase.entities.Produto;
import br.com.brotolegal.savdatabase.entities.Simulador;
import br.com.brotolegal.savdatabase.entities.TabPrecoCabec;
import br.com.brotolegal.savdatabase.entities.Verba;

public class PedidoBusiness {

    private PedidoCabMb cabec;
    private PedidoCabMbDAO cabecdao;
    private PedidoDetMb det;
    private PedidoDetMbDAO detdao;
    private Context context;
    private String cUser;

    //CLASSES
    private Cliente cliente;
    private CondPagto condpagto;
    private Produto   produto;
    private Politica  politica;
    private Verba     verba;
    private Acordo    acordo;


    //DAOS
    private ClienteDAO clientedao;
    private ContratoDAO contratodao;
    private ProdutoDAO produtodao;
    private CondPagtoDAO condpagtodao;
    private TabPrecoCabecDAO tabprecocabecdao;
    private PoliticaDAO politicadao;
    private VerbaDAO    verbadao;
    private AcordoDAO   acordodao;
    private FreteDAO    fretedao;
    private ImpostoDAO  impostodao;
    private SimuladorDAO simuladordao;

    //PEDIDO
    private List<PedidoDetMb> lsDetalhe;
    private List<Contrato> lsContrato;
    private List<CondPagto> lsCondPagto;
    private List<TabPreco> lsTabPreco;
    private List<TabPrecoCabec> lsTabPrecoCabec;
    private Float TOTAL       = 0f;
    private Float TOTALDESC   = 0f;
    private Float TOTALVERBA  = 0f;
    private Float TOTALPESO   = 0f;
    private Float FRETEFEIJAO = 0f;
    private Float FRETEARROZ  = 0f;
    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private DecimalFormat format_05 = new DecimalFormat(",##0.00000");
    private List<String[]> lsCategoria;
    private List<String[]> lsMarca;
    private String LOG        = "PEDIDOBUSINESS";

    public PedidoBusiness() throws Exception {

        lsCondPagto = new ArrayList<CondPagto>();

        lsTabPreco = new ArrayList<TabPreco>();

        cabec = new PedidoCabMb();

        det = new PedidoDetMb();

        cliente = new Cliente();

        condpagto = new CondPagto();

        produto = new Produto();

        politica = new Politica();

        verba = new Verba();

        acordo = new Acordo();

        clientedao = new ClienteDAO();

        contratodao = new ContratoDAO();

        cabecdao = new PedidoCabMbDAO();

        detdao = new PedidoDetMbDAO();

        produtodao = new ProdutoDAO();

        condpagtodao = new CondPagtoDAO();

        tabprecocabecdao = new TabPrecoCabecDAO();

        politicadao = new PoliticaDAO();

        verbadao = new VerbaDAO();

        acordodao = new AcordoDAO();

        fretedao  = new FreteDAO();

        impostodao = new ImpostoDAO();

        simuladordao = new SimuladorDAO();

        Novo();

    }

    public boolean Novo() {

        boolean retorno = true;

        cabec.setNRO("");

        cabec.setVEND(App.user.getCODVEN());

        lsDetalhe = new ArrayList<PedidoDetMb>();

        lsTabPrecoCabec = new ArrayList<>();

        lsCategoria = new ArrayList<>();

        lsMarca = new ArrayList<>();

        cliente = new Cliente();

        FRETEFEIJAO = 0f;

        FRETEARROZ  = 0f;


        Validar();

        return retorno;

    }

    public void load(String pedido) throws ExceptionLoadPedido, ExceptionItemProduto {

        cabecdao.open();

        cabec = cabecdao.seek(new String[]{pedido});

        cabecdao.close();

        if (cabec == null) {

            throw new ExceptionLoadPedido("Cabecalho Do Pedido " + pedido + " Não Encontrado !!!");
        }

        detdao.open();

        lsDetalhe = detdao.getAllByPedido(new String[]{pedido});

        if (lsDetalhe.size() == 0) {

            throw new ExceptionLoadPedido("Itens Do Pedido " + pedido + " Não Encontrados !!!");

        }

        detdao.close();

        LoadCabec();

        Validar();

    }

    public void save() throws ExceptionSavePedido {

        try {

            recalculo();

            cabecdao.open();

            cabecdao.SavePedido(getCabec(), getLsDetalhe());

        } finally {

            cabecdao.close();

        }


    }

    public void Validar() {

        String status    = "3";
        String statusdet = "3";

        if (cabec.getSTATUS().equals("6")) {

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

            if (tabpreco.getFLAGFAIXA().equals("1")){

                if (cabec.getFDSPREVISTO().compareTo(cabec.getFDSREAIS()) > 0){

                    status = "2";

                    cabec.setMENSAGEM("Quantidade FDS Vendido Inferior A Qtd FDS Previstos");

                }

            }

            try {


                produtodao.open();

                verbadao.open();

                acordodao.open();

                if (lsDetalhe.size() == 0) {

                    statusdet = "2";

                }
                for (PedidoDetMb det : lsDetalhe) {

                    det.setSTATUS("3");

                    det.setMENSAGEM("");

                    if (!det.getPRODUTO().equals("")) {
                        produto = produtodao.seek(new String[]{det.getPRODUTO()});


                        if (produto != null) {

                            det.setValidoByName("PRODUTO", true);

                        } else {

                            det.setValidoByName("PRODUTO", false);

                        }

                    } else {

                        det.set_Produto("O Produto é Campo Obrigatório");

                        det.setValidoByName("PRODUTO", false);

                        det.setSTATUS("2");

                    }


                    //Verba


                    if (!det.getCODVERBA().trim().equals("")) {

                        Verba ver = verbadao.seek(new String[]{det.getCODVERBA()});

                        if (ver != null) {

                            det.setValidoByName("CODVERBA", true);
                            det.set_Verba(ver.getCODIGO()+"-"+ver.getDESCRICAO());

                        } else {

                            det.set_Verba("Nao Encontrei Verba " + det.getCODVERBA());
                            det.setValidoByName("CODVERBA", false);
                            det.setSTATUS("2");

                        }

                    } else {


                        if (det.getDESCVER().compareTo(0f) > 0){

                            det.setValidoByName("CODVERBA", false);

                        } else {

                            det.setValidoByName("CODVERBA", true);

                        }

                    }


                    //Verba bonificacao

                    if (!det.getCODVERBA2().trim().equals("")) {

                        Verba ver = verbadao.seek(new String[]{det.getCODVERBA2()});

                        if (ver != null) {

                            det.setValidoByName("CODVERBA2", true);
                            det.set_Verba2(ver.getCODIGO()+"-"+ver.getDESCRICAO());

                        } else {

                            det.set_Verba2("Nao Encontrei Verba " + det.getCODVERBA2());
                            det.setValidoByName("CODVERBA2", false);
                            det.setSTATUS("2");

                        }

                    } else {


                        if ((det.getBONIQTD().compareTo(0f) > 0)){

                            det.setValidoByName("CODVERBA2", false);

                        } else {

                            det.setValidoByName("CODVERBA2", true);

                        }

                    }


                    //Acordo
                    if (!det.getACORDO().trim().equals("")) {

                        Acordo acor = acordodao.seek(new String[]{det.getACORDO()});

                        if (acor != null) {

                            det.setValidoByName("ACORDO", true);


                        } else {

                            det.set_Acordo("Nao Encontrei Acordo " + det.getACORDO());
                            det.setValidoByName("ACORDO", false);
                            det.setSTATUS("2");

                        }

                    } else {


                        det.setValidoByName("ACORDO", true);


                    }

                    validarItem(det);

                    det.setSTATUS();

                    if (!det.getNRO().isEmpty()) {

                        if (!det.getSTATUS().equals("3")) {

                            statusdet = "2";

                        }

                    }
                }


            } catch (Exception e) {

                det.setSTATUS("2");

                statusdet = "2";

            } finally {


                produtodao.close();

                verbadao.close();

                acordodao.close();


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
    }

    public void SetTipoPedido(String tipo) {

        try {

            cabec.setTIPO(tipo);

            tabprecocabecdao.open();

            lsTabPreco = tabprecocabecdao.seekTabela(cliente.getTABELA(), cabec.getTIPO());

            lsTabPrecoCabec = tabprecocabecdao.seekTabelaCarga(cliente.getTABELA());

            tabprecocabecdao.close();

            if (lsTabPreco.size() > 0) {

                cabec.setTABPRECO(cliente.getTABELA());

                cabec.set_TabPreco(lsTabPreco.get(0).getDESCRICAO());

                if (lsTabPreco.get(0).getFLAGFAIXA().equals("1")) {

                    cabec.setFDSPREVISTO(lsTabPreco.get(0).getFAIXAATE());

                } else {

                    if (lsTabPreco.get(0).getFLAGFAIXA().equals("2")) {

                        cabec.setFDSPREVISTO(0f);
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


    public List<PedidoDetMb> getLsDetalhe() {
        return lsDetalhe;
    }


    public void setLsDetalhe(List<PedidoDetMb> lsDetalhe) {
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


                } else {

                    cabec.set_ClienteEntRazao("");

                }

                clientedao.close();

            }

            if (cliente != null) {

                this.setCliente(cabec.getCODIGOFAT(), cabec.getLOJAFAT());

                LoadItens();

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


    public void LoadItens() throws ExceptionItemProduto {

        try {

            produtodao.open();

            verbadao.open();

            acordodao.open();

            for (PedidoDetMb det : lsDetalhe) {


                produto = produtodao.seek(new String[]{det.getPRODUTO()});


                if (produto != null) {

                    det.set_Produto("Cod: " + det.getPRODUTO().trim() + " " + produto.getDESCRICAO().trim() + " Und. " + produto.getUM());

                }

                //Verba


                if (!det.getCODVERBA().trim().equals("")) {

                    Verba ver = verbadao.seek(new String[]{det.getCODVERBA()});

                    if (ver != null) {

                        det.set_Verba(ver.getCODIGO() + "-" + ver.getDESCRICAO());

                    } else {

                        det.set_Verba("Nao Encontrei Verba " + det.getCODVERBA());
                        det.setValidoByName("CODVERBA", false);

                    }

                } else {


                    det.set_Verba("");

                }

                //Acordo
                if (!det.getACORDO().trim().equals("")) {

                    Acordo acor = acordodao.seek(new String[]{det.getACORDO()});

                    if (acor != null) {

                        det.set_Acordo(acor.getCODIGO() + "-" + acor.getCODIGO());

                    } else {

                        det.set_Acordo("Nao Encontrei Acordo " + det.getACORDO());
                        det.setValidoByName("ACORDO", false);

                    }

                } else {


                    det.set_Acordo("");


                }

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        } finally {


            produtodao.close();

            verbadao.close();

            acordodao.close();


        }

    }


    public void setCliente(String codigo, String loja) throws IOException, ExceptionItemProduto {

        try {

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

                tabprecocabecdao.open();

                lsTabPreco = tabprecocabecdao.seekTabela(cliente.getTABELA(), cabec.getTIPO());

                lsTabPrecoCabec = tabprecocabecdao.seekTabelaCarga(cliente.getTABELA());

                tabprecocabecdao.close();

                if (lsTabPreco.size() > 0) {

                    cabec.setTABPRECO(cliente.getTABELA());

                    cabec.set_TabPreco(lsTabPreco.get(0).getDESCRICAO());

                } else {

                    cabec.setTABPRECO("");

                    cabec.set_TabPreco("Tab. Preço Não Definida.");

                }

                recalculoItensPedido();

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

            cliente = clientedao.seek(new String[]{codigo, loja});

            clientedao.close();


            if (cliente != null) {

                fretedao.open();

                Frete freteArroz = fretedao.getFretArroz(cabec.getFDSPREVISTO(),cliente.getREDE(),cliente.getESTADO(),cliente.getCODCIDADE());

                fretedao.close();

                if (freteArroz == null){

                    freteArroz = new Frete();

                    freteArroz.setFRETE(2.56f);

                }

                Frete freteFeijao = fretedao.getFreteFeijao(cabec.getFDSPREVISTO(),cliente.getREDE(),cliente.getESTADO(),cliente.getCODCIDADE());

                if (freteFeijao == null){

                    freteFeijao = new Frete();

                    freteFeijao.setFRETE(2.56f);

                }

                this.FRETEARROZ = freteArroz.getFRETE();

                this.FRETEFEIJAO = freteFeijao.getFRETE();

                cabec.set_ClienteEntRazao(cliente.getCODIGO() + "-" + cliente.getLOJA() + " " + cliente.getRAZAOPA() + " Frete ARROZ "+format_02.format(freteArroz.getFRETE())+" Frete FEIJÃO "+format_02.format(freteFeijao.getFRETE()));

                cabec.setCODIGOENT(codigo);

                cabec.setLOJAENT(loja);

            } else {

                throw new ExceptionItemProduto("Cliente Entrega " + cliente.getCODIGO() + "-" + cliente.getLOJA() + "Não Encontrado !!!");

            }

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());
        }

    }


	/*
	 *
	 *  Itens Do Pedido
	 *
	 */

    public int newItem() throws ExceptionItemProduto, Exception {

        PedidoDetMb det = new PedidoDetMb();

        int item = 0;


        //Não deixa entrar item sem preencher o item anterior
        if (lsDetalhe.size() >= 1) {

            if (lsDetalhe.get(lsDetalhe.size() - 1).getPRODUTO().trim().equals("")) {

                throw new ExceptionItemProduto("Prencha O Item Anterior !!!");

            }

        }

        if (lsDetalhe.size() == 0) {

            item = 1;

        } else {
            try {

                item = Integer.parseInt(lsDetalhe.get(lsDetalhe.size() - 1).getITEM()) + 1;

            } catch (Exception e) {

                item = 1;

            }
        }

        det.setNRO(cabec.getNRO());

        det.setITEM(String.format("%03d", item));

        det.setQTD(1f);

        det.setBONIQTD(0f);

        lsDetalhe.add(det);

        Validar();

        return item;

    }


    public void ajustaNro() {

        for (int x = 0; x < lsDetalhe.size(); x++) {

            PedidoDetMb obj = lsDetalhe.get(x);

            if (!obj.getNRO().isEmpty()) {

                obj.setNRO(cabec.getNRO());

                lsDetalhe.set(x, obj);
            }
        }

    }

    public void LoadDetalhe_V2() throws ExceptionItemProduto, Exception {

        boolean lExiste = false;

        boolean lAchou = false;

        List<PedidoDetMb> lsItens = new ArrayList<>();

        lsDetalhe = new ArrayList<>();

        String TipoPedido = "@" + cabec.getTIPO();

        List<TabPreco> tabprecos = new ArrayList<>();

        TabPrecoCabecDAO dao = new TabPrecoCabecDAO();

        dao.open();

        if (!cabec.getSTATUS().equals("1") && !cabec.getSTATUS().equals("2") && !cabec.getSTATUS().equals("3")) {

            lsCategoria.add(new String[]{"000", "TODAS !!"});

            lsMarca.add(new String[]{"000", "TODAS !!"});

            lsDetalhe = dao.seekDetalheByPedido(cabec.getTABPRECO(), cabec.getNRO());

            dao.close();

            return;


        } else {

            detdao.open();

            lsItens = detdao.getAllByPedido(new String[]{cabec.getNRO()});

            detdao.close();

            tabprecos = dao.seekTabela(cabec.getTABPRECO(), TipoPedido);

        }

        dao.close();

        lsCategoria.add(new String[]{"000", "TODAS !!"});

        lsMarca.add(new String[]{"000", "TODAS !!"});

        lExiste = false;

        //categoria
        for (TabPreco tab : tabprecos) {

            for (String[] categoria : lsCategoria) {

                if (categoria[0].equals(tab.getGRUPO())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsCategoria.add(new String[]{tab.getGRUPO(), tab.getDESCRICAOGRUPO().trim()});

            }

            lExiste = false;


            //marca
            for (String[] marca : lsMarca) {

                if (marca[0].equals(tab.getMARCA())) {

                    lExiste = true;

                    break;

                }

            }

            if (!lExiste) {

                lsMarca.add(new String[]{tab.getMARCA(), tab.getDESCRICAOMARCA().trim()});

            }

            PedidoDetMb det = new PedidoDetMb();

            int item = 0;

            if (lsDetalhe.size() == 0) {

                item = 1;

            } else {

                try {

                    item = Integer.parseInt(lsDetalhe.get(lsDetalhe.size() - 1).getITEM()) + 1;

                } catch (Exception e) {

                    item = 1;

                }
            }

            lAchou = false;

            det.setPRODUTO(tab.getPRODUTO());

            for (PedidoDetMb ite : lsItens) {

                if (ite.getPRODUTO().equals(det.getPRODUTO())) {

                    det = ite;

                    lsItens.remove(ite);

                    lAchou = true;

                    break;

                }
            }

            if (lAchou) {

                det.setITEM(String.format("%03d", item));

                det.set_Produto(tab.getDESCRICAOPRODUTO());

                det.setUM(tab.getUNID());

                det.set_Grupo(tab.getGRUPO());

                det.set_Marca(tab.getMARCA());

                det.setCONVERSAO(tab.getCONVERSAO());

            } else {

                det.setITEM(String.format("%03d", item));

                det.set_Produto(tab.getDESCRICAOPRODUTO());

                det.setUM(tab.getUNID());

                det.set_Grupo(tab.getGRUPO());

                det.set_Marca(tab.getMARCA());

                det.setCONVERSAO(tab.getCONVERSAO());

                det.setQTD(0f);

                det.setBONIQTD(0f);

            }

            lsDetalhe.add(det);

        }

    }


    public PedidoDetMb getItemBySelf(String item) throws ExceptionItemProduto {

        PedidoDetMb obj = null;

        try {

            obj = lsDetalhe.get(getPosicaoByItem(item));

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }

        return obj;

    }

    public void setItemVerba(String item, String Codigo) throws ExceptionItemProduto {

        try {

            PedidoDetMb obj = lsDetalhe.get(getPosicaoByItem(item));

            verbadao.open();

            Verba ver = verbadao.seek(new String[]{Codigo});

            verbadao.close();

            if (ver != null) {

                obj.setCODVERBA(Codigo);

                obj.set_Verba(Codigo + "-" + ver.getDESCRICAO());

            } else {

                throw new ExceptionItemProduto("Verba " + Codigo + " Não Encontrada !!");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }

    public void setItemVerba2(String item, String Codigo) throws ExceptionItemProduto {

        try {

            PedidoDetMb obj = lsDetalhe.get(getPosicaoByItem(item));

            verbadao.open();

            Verba ver = verbadao.seek(new String[]{Codigo});

            verbadao.close();

            if (ver != null) {

                obj.setCODVERBA2(Codigo);

                obj.set_Verba2(Codigo + "-" + ver.getDESCRICAO());

            } else {

                throw new ExceptionItemProduto("Verba Bonif. " + Codigo + " Não Encontrada !!");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }



    public void setItemSimulador(String item, String Codigo, String Cliente, String Loja, String Produto) throws ExceptionItemProduto {

        try {

            PedidoDetMb obj = lsDetalhe.get(getPosicaoByItem(item));

            simuladordao.open();

            Simulador simulador = simuladordao.seek(new String[]{Codigo,Cliente,Loja,Produto});

            simuladordao.close();

            if (simulador != null) {

                obj.setSIMULADOR(Codigo);

                obj.setDESCVER(0f);

                obj.setCODVERBA("");

                obj.set_Verba("");

                obj.setACORDO("");

                obj.set_Acordo("");

                obj.setPRCVEN(simulador.getPRECOAPROVADO());

                obj.setBONIPREC(simulador.getPRECOAPROVADO());

            } else {

                throw new ExceptionItemProduto("Simulador " + Codigo + " Não Encontrado.");

            }

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }


    public void setItemAcordo(String item, String Codigo) throws ExceptionItemProduto {

        try {

            PedidoDetMb obj = lsDetalhe.get(getPosicaoByItem(item));

            acordodao.open();

            Acordo acor = acordodao.seek(new String[]{Codigo});

            acordodao.close();

            if (acor != null) {

                obj.setACORDO(Codigo);

                obj.set_Verba(acor.getCODVERBA() + "-" + acor.getDESVERBA());

                obj.setCODVERBA(acor.getCODVERBA());

                obj.set_Acordo(acor.getCODIGO() + " Saldo: " + format_02.format(acor.getSALDO()));

            } else {

                throw new ExceptionItemProduto("Acordo " + Codigo + " Não Encontrado.");

            }


        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }


    public void setItemProduto(String item, String Codigo) throws ExceptionItemProduto {

        int pos = 0;

        try {

            pos = getPosicaoByItem(item);

            //Monta Preco no proprio item;
            FormandoPreco(pos);

            //Calculo o valor total do item

            recalculoItem(pos);

            //Valida descontos

            validarItem(lsDetalhe.get(pos));

            recalculo();

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

        total = lsDetalhe.get(pos).getBONIQTD() * lsDetalhe.get(pos).getPRECOFORMACAO();

        tot = new BigDecimal(total);

        tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

        lsDetalhe.get(pos).setBONITOTAL(tot.floatValue());

    }

    public void recalculoItensPedido() {


        for (PedidoDetMb ped : lsDetalhe) {

            try {

                //setItemProduto(ped.getITEM(),ped.getPRODUTO());

                //Calculo o valor total do item

                for (int x = 0; x < lsDetalhe.size(); x++) {

                    if (lsDetalhe.get(x).getNRO().isEmpty()) {

                        continue;

                    }

                    recalculoItem(x);

                    validarItem(lsDetalhe.get(x));

                }

                recalculo();

            } catch (ExceptionItemProduto e) {

                Log.i("SAV", e.getMessage());

            }

        }

    }


    public void ajustaPrcVenByPreco(PedidoDetMb obj, Float preco) {
        BigDecimal desc;
        float tabela = obj.getPRECOFORMACAO();
        float venda = preco;
        int cmp = Float.compare(tabela, venda);

        if (cmp > 0) { //tabela maior venda

            desc = new BigDecimal(((tabela - venda) / tabela) * 100);

            desc.setScale(5, BigDecimal.ROUND_HALF_UP);

            obj.setDESCON(desc.floatValue());

        } else if (cmp < 0) { //tabela menor venda

            obj.setDESCON(0f);

            obj.setCODVERBA("");

            obj.set_Verba("");

            obj.setACORDO("");

            obj.set_Acordo("");


        } else { //tabela = venda

            obj.setDESCON(0f);

            obj.setDESCON(0f);

            obj.setCODVERBA("");

            obj.set_Verba("");

            obj.setACORDO("");

            obj.set_Acordo("");

        }

    }

    public void recalculo() {


        Float TOTALPEDIDO = 0f;
        Float TOTALDESCONTO = 0f;
        Float TOTALVERBA = 0f;
        Float QTDBINIFICADA = 0f;
        Float VLRBONIFICADO = 0f;
        Float FDSPREVISTO = 0f;
        Float FDSREAIS    = 0f;

        BigDecimal precven;
        BigDecimal desc;
        BigDecimal tot;
        Double total;
        Double preco;


        for (PedidoDetMb obj : lsDetalhe) {

            if (obj.getNRO().isEmpty()) continue;


            if (!obj.getSIMULADOR().trim().isEmpty()){

                precven = new BigDecimal(obj.getPRCVEN());

                desc    = new BigDecimal(0f);


            } else {

                desc = new BigDecimal(obj.getDESCON());

                desc.setScale(5, BigDecimal.ROUND_HALF_UP);

                if (Float.compare(obj.getPRCVEN(), obj.getPRECOFORMACAO()) > 0) {

                    precven = new BigDecimal(obj.getPRCVEN());

                } else {

                    precven = new BigDecimal(obj.getPRECOFORMACAO());

                }

                //Total venda

                preco = precven.doubleValue() - (precven.doubleValue() * (desc.doubleValue() / 100));

                precven = new BigDecimal(preco);

                precven = precven.setScale(2, BigDecimal.ROUND_HALF_UP);


            }


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

            obj.setBONIPREC(precven.floatValue());

            total = obj.getBONIQTD() * precven.doubleValue();

            tot = new BigDecimal(total);

            tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

            obj.setBONITOTAL(tot.floatValue());


            QTDBINIFICADA += obj.getBONIQTD();

            VLRBONIFICADO += tot.floatValue();

            FDSREAIS  += (obj.getQTD() + obj.getBONIQTD()) * obj.getCONVERSAO();

        }

        cabec.setTOTALPEDIDO(TOTALPEDIDO);
        cabec.setTOTALDESCONTO(TOTALDESCONTO);
        cabec.setTOTALVERBA(TOTALVERBA);
        cabec.setQTDBINIFICADA(QTDBINIFICADA);
        cabec.setVLRBONIFICADO(VLRBONIFICADO);
        cabec.setFDSREAIS(FDSREAIS);


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


    public void validarItem(PedidoDetMb obj) throws ExceptionItemProduto {

        try {

            if (cabec.getSTATUS().equals("6")) {

                return;

            }

            obj._ValidaOK();

            obj.setMENSAGEM("");


            if (!obj.getSIMULADOR().trim().isEmpty()){


            } else {

                //Valida descontos

                Float descMaximo = obj.getDESCONTOMAIS() + obj.getDESCONTOPOL();

                Float descVerba = 0f;

                Float desconto = obj.getDESCON();

                if (Float.compare(desconto, descMaximo) > 0) {

                    obj.setPRCVEN(obj.getPRECOFORMACAO());
                    obj.setDESCON(0f);
                    obj.setCODVERBA("");
                    obj.set_Verba("");
                    obj.setACORDO("");
                    obj.set_Acordo("");
                    obj.setDESCVER(0f);

                    obj.setMENSAGEM("Desconto Acima Do Permitido !!! " + format_05.format(descMaximo - desconto));
                    obj.setValidoByName("PRCVEN", true);
                    obj.setValidoByName("DESCON", true);
                    obj.setValidoByName("CODVERBA", true);
                    obj.setValidoByName("ACORDO", true);
                    throw new ExceptionItemProduto("Desconto Acima Do Permitido !!! " + format_05.format(descMaximo - desconto));

                } else {


                    if (Float.compare(obj.getDESCON(), 0) == 0) {


                        if (obj.getBONIQTD() == 0) {

                            obj.setDESCON(0f);
                            obj.setCODVERBA("");
                            obj.set_Verba("");
                            obj.setACORDO("");
                            obj.set_Acordo("");
                            obj.setDESCVER(0f);

                            obj.setValidoByName("DESCON", true);
                            obj.setValidoByName("CODVERBA", true);
                            obj.setValidoByName("DESCVER", true);
                            obj.setValidoByName("ACORDO", true);

                        }


                    } else {

                        if (Float.compare(obj.getDESCON(), obj.getDESCONTOPOL()) > 0) {

                            descVerba = obj.getDESCON() - obj.getDESCONTOPOL();

                        }

                        obj.setDESCVER(descVerba);

                        if (Float.compare(obj.getDESCVER(), 0) == 0) {

                            obj.setCODVERBA("");
                            obj.set_Verba("");
                            obj.setACORDO("");
                            obj.set_Acordo("");

                            obj.setValidoByName("CODVERBA", true);
                            obj.setValidoByName("ACORDO", true);

                        }
                    }

                }
            }
            // Junta qtd e bonificacao para validar

            if ((obj.getQTD()+obj.getBONIQTD()) > 0) {

                //qtd
                obj.setValidoByName("QTD", true);

                if (obj.getQTD() < 0) {

                    obj.setMENSAGEM("Favor Informar A Qtd Do Item");
                    obj.setValidoByName("QTD", false);


                    if (!obj.getNRO().isEmpty()) {

                        throw new ExceptionItemProduto("Favor Informar A Qtd Do Item");

                    }

                }


                //Bonificacao

                obj.setValidoByName("BONIQTD", true);
                if (obj.getBONIQTD() < 0) {

                    obj.setBONIQTD(0f);
                    obj.setValidoByName("BONIQTD", false);
                    throw new ExceptionItemProduto("Qtd De Bonificação Inválida.");


                } else if (obj.getBONIQTD() > 0) {


                    if (obj.getCODVERBA2().trim().equals("")) {

                        obj.setMENSAGEM("Favor Justificar A Bonificação Com Uma verba bonif. Ou Acordo.");
                        obj.setValidoByName("CODVERBA2", false);
                        obj.setValidoByName("ACORDO", false);
                        throw new ExceptionItemProduto("Favor Justificar O desconto Com Uma Verba Ou Acordo.");

                    }


                }

            } else {

                if (!obj.getNRO().isEmpty()) {

                    obj.setMENSAGEM("Favor Verificar as QTDs do item");

                    obj.setValidoByName("QTD", false);

                    throw new ExceptionItemProduto("Favor Verificar as QTDs do item");
                }

            }

            //Verba
            if (obj.getDESCVER() > 0) {


                if (obj.getCODVERBA().trim().equals("")) {

                    obj.setMENSAGEM("Favor Justificar O desconto Com Uma verba !!!");
                    obj.setValidoByName("CODVERBA", false);
                    obj.setValidoByName("ACORDO", false);
                    throw new ExceptionItemProduto("Favor Justificar O desconto Com Uma Verba de Venda E Ou Acordo.");

                }


                if ((obj.getCODVERBA().trim().equals("")) && (obj.getACORDO().trim().equals(""))) {

                    obj.setMENSAGEM("Favor Justificar O desconto Com Um Acordo !!!");
                    obj.setValidoByName("ACORDO", false);
                    throw new ExceptionItemProduto("Favor Justificar O desconto Com Uma verba E Ou Acordo!!!");

                }


            }


            obj.setSTATUS();

        } catch (Exception e) {

            obj.setSTATUS("2");

            throw new ExceptionItemProduto(e.getMessage());

        }


    }


    public void IniciaItem(PedidoDetMb obj) throws ExceptionItemProduto {

        try {

            if (obj.getDESCVER() == 0 && obj.getBONIQTD() == 0) {

                obj.setACORDO("");
                obj.set_Acordo("");
                obj.setCODVERBA("");
                obj.set_Verba("");

            }

        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }


    }


    public void RemoveItem(PedidoDetMb obj) throws ExceptionItemProduto {


        try {

            int pos = getPosicaoByItem(obj.getITEM());

            if (pos == -1) {

                throw new ExceptionItemProduto("Produto " + obj.get_Produto());


            } else {

                lsDetalhe.remove(pos);

                recalculo();

            }


        } catch (Exception e) {


            throw new ExceptionItemProduto(e.getMessage());


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


    public void FormandoPreco(int pos) throws ExceptionItemProduto {

        Float fator = 0f;

        Float frete = 0f;

        try {

            String Codigo = lsDetalhe.get(pos).getPRODUTO();

            Contrato contrato = null;

            float preco = 0f;

            int posTabPreco = seekTabPreco(Codigo);

            if (posTabPreco == -1) {

                throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "- Produto Não Existe Na Lista De Preço");

            }

            //Tabela de Preco

            TabPreco tabpreco = lsTabPreco.get(posTabPreco);

            lsDetalhe.get(pos).setPRECOTABELA(tabpreco.getPRCVEN());

            lsDetalhe.get(pos).setACRESCIMOMAIS(tabpreco.getACRESCIMOMAIS());

            lsDetalhe.get(pos).setDESCONTOMAIS(tabpreco.getDESCONTOMAIS());

            lsDetalhe.get(pos).setUSATAXAFIN(cliente.getTAXAFIN().equals("S") ? "SIM" : "NãO");

            lsDetalhe.get(pos).setTAXAFIN(condpagto.getJUROS());

            lsDetalhe.get(pos).setFATOR(tabpreco.getFATOR());

            if  (tabpreco.getFLAGFAIXA().equals("X")) {

                lsDetalhe.get(pos).setPRECOTABELA(tabpreco.getPRCBASE());

                fator = tabpreco.getFATORNUM().floatValue();

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

                            lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                        } else {

                            int indice = seekContratoBYChave(lsDetalhe.get(pos).getPRODUTO(), produto.getMARCA(), produto.getGRUPO(), contrato.getChave());


                            if (indice != -1) {

                                contrato = lsContrato.get(indice);

                                lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                            } else {

                                Log.i(LOG, "Não Encontrei Contrato");


                            }
                        }
                    }


                    if (produto.getGRUPO().equals("3.02")){

                        frete = this.FRETEARROZ;


                    } else {


                        frete = this.FRETEFEIJAO;

                    }

                    //Imposto

                    impostodao.open();

                    Imposto imposto = impostodao.seek(new String[]{"SP", produto.getGRUPO(), "0"});

                    impostodao.close();

                    if (imposto == null) {

                        lsDetalhe.get(pos).setIMPOSTO(0f);

                    } else {

                        lsDetalhe.get(pos).setIMPOSTO(imposto.getTOTALIMPOSTO());

                    }


                    //TAXA FINANCEIRA


                    if (cliente.getTAXAFIN().equals("S")) {

                        preco =  (tabpreco.getPRCBASE() + frete ) / ( ( fator - condpagto.getJuros().floatValue() - lsDetalhe.get(pos).getDESCCONTRATO() - lsDetalhe.get(pos).getIMPOSTO() ) / 100 );

                    } else {

                        preco = ((tabpreco.getPRCVEN() * 1)) / ((1 - (lsDetalhe.get(pos).getDESCCONTRATO() / 100)) * (1 - (cliente.getDESCCLIENTE() / 100)));
                    }

                    preco = preco / ( 1 - ( tabpreco.getPOLITICABASE()/100) );

                    //Desconto retira foi cancelado
                    //preco = preco - cabec.getDESCRET();

                    BigDecimal tot = new BigDecimal(preco);

                    tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

                    preco = tot.floatValue();

                    lsDetalhe.get(pos).setPRCVEN(preco);

                    lsDetalhe.get(pos).setPRECOFORMACAO(preco);

                    lsDetalhe.get(pos).setDESCON(0f);

                    lsDetalhe.get(pos).setBONIPREC(preco);


                    //Busca Politica

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

                    } else {

                        lsDetalhe.get(pos).setDESCONTOPOL(politica.getDESCONTO());

                    }


                } else {


                    throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "-Produto Da Lista Não Existe No Cadastro De Produto.");

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

                            lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

                        } else {

                            int indice = seekContratoBYChave(lsDetalhe.get(pos).getPRODUTO(), produto.getMARCA(), produto.getGRUPO(), contrato.getChave());


                            if (indice != -1) {

                                contrato = lsContrato.get(indice);

                                lsDetalhe.get(pos).setDESCCONTRATO(contrato.getPERCTOF() + contrato.getPERCADIC() + contrato.getTAXAFIN());

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

                    preco = preco - cabec.getDESCRET();

                    BigDecimal tot = new BigDecimal(preco);

                    tot = tot.setScale(2, BigDecimal.ROUND_HALF_UP);

                    preco = tot.floatValue();

                    lsDetalhe.get(pos).setPRCVEN(preco);

                    lsDetalhe.get(pos).setPRECOFORMACAO(preco);

                    lsDetalhe.get(pos).setDESCON(0f);

                    lsDetalhe.get(pos).setBONIPREC(preco);

                    //Imposto

                    lsDetalhe.get(pos).setIMPOSTO(0f);

                    //Verba

//                verbadao.open();
//
//                Verba ver = verbadao.seek(new String[]{lsDetalhe.get(pos).get_Verba()});
//
//                verbadao.close();
//
//                if (ver != null) {
//
//                    lsDetalhe.get(pos).set_Verba(ver.getCODIGO() + "-" + ver.getDESCRICAO());
//
//                } else {
//
//                    lsDetalhe.get(pos).set_Verba("");
//
//                }


                    //Busca Politica

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

                    } else {

                        lsDetalhe.get(pos).setDESCONTOPOL(politica.getDESCONTO());

                    }


                } else {


                    throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "-Produto Da Lista Não Existe No Cadastro De Produto.");

                }
            }
        } catch (Exception e) {

            throw new ExceptionItemProduto(e.getMessage());

        }

    }


    public void trocaBasePreco() throws ExceptionItemProduto {


        try {


            for (int pos = 0; pos < lsDetalhe.size(); pos++) {


                //Monta Preco no proprio item;
                FormandoPreco(pos);

                //Calculo o valor total do item

                recalculoItem(pos);

                //Valida descontos

                validarItem(lsDetalhe.get(pos));

                recalculo();
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

                String Codigo   = lsDetalhe.get(pos).getPRODUTO();

                int posTabPreco = seekTabPrecoTemporaria(lsLista,Codigo);

                if (posTabPreco == -1) {

                    throw new ExceptionItemProduto("Cod: " + Codigo.trim() + "- Produto Não Existe Na Lista De Preço\nNão Posso Trocar A Tabela De Preço.");

                }

            }

        } catch (Exception e) {


            throw new ExceptionItemProduto(e.getMessage());

        }

    }


}
