package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 02/08/2016.
 */


public class Pedidos {

    private PedidoCabMb cab;

    private List<PedidoDetMb> lsDetalhe;

    public Pedidos(PedidoCabMb cab) {

        this.cab = cab;

        this.lsDetalhe = new ArrayList<>();

    }

    public PedidoCabMb getCab() {
        return cab;
    }

    public void setCab(PedidoCabMb cab) {
        this.cab = cab;
    }

    public List<PedidoDetMb> getLsDetalhe() {
        return lsDetalhe;
    }

    public void setLsDetalhe(List<PedidoDetMb> lsDetalhe) {
        this.lsDetalhe = lsDetalhe;
    }
}



