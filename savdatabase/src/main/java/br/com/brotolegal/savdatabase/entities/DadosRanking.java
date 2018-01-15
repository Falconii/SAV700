package br.com.brotolegal.savdatabase.entities;

import java.util.ArrayList;

import br.com.brotolegal.savdatabase.database.ObjRegister;

/**
 * Created by Falconi on 04/07/2016.
 */
public class DadosRanking extends ObjRegister {

    protected String TMP_CAMPANHA;
    protected String TMP_CODIGO;
    protected String TMP_PARTICIPANTE;
    protected Integer TMP_PONTUACAO;
    protected Integer TMP_POSICAO;

    protected static final String _OBJETO = "br.com.brotolegal.savdatabase.entities.DadosRanking";


    public DadosRanking(){

        super(_OBJETO,"DADOSRANKING");

        loadColunas();

        InicializaFields();
    }

    public DadosRanking(String TMP_CAMPANHA, String TMP_CODIGO, String TMP_PARTICIPANTE, Integer TMP_PONTUACAO, Integer TMP_POSICAO) {

        super(_OBJETO,"DADOSRANKING");

        loadColunas();

        InicializaFields();

        this.TMP_CAMPANHA = TMP_CAMPANHA;
        this.TMP_CODIGO = TMP_CODIGO;
        this.TMP_PARTICIPANTE = TMP_PARTICIPANTE;
        this.TMP_PONTUACAO = TMP_PONTUACAO;
        this.TMP_POSICAO = TMP_POSICAO;
    }

    public String getTMP_CAMPANHA() {
        return TMP_CAMPANHA;
    }

    public void setTMP_CAMPANHA(String TMP_CAMPANHA) {
        this.TMP_CAMPANHA = TMP_CAMPANHA;
    }

    public String getTMP_CODIGO() {
        return TMP_CODIGO;
    }

    public void setTMP_CODIGO(String TMP_CODIGO) {
        this.TMP_CODIGO = TMP_CODIGO;
    }

    public String getTMP_PARTICIPANTE() {
        return TMP_PARTICIPANTE;
    }

    public void setTMP_PARTICIPANTE(String TMP_PARTICIPANTE) {
        this.TMP_PARTICIPANTE = TMP_PARTICIPANTE;
    }

    public Integer getTMP_PONTUACAO() {
        return TMP_PONTUACAO;
    }

    public void setTMP_PONTUACAO(Integer TMP_PONTUACAO) {
        this.TMP_PONTUACAO = TMP_PONTUACAO;
    }

    public Integer getTMP_POSICAO() {
        return TMP_POSICAO;
    }

    public void setTMP_POSICAO(Integer TMP_POSICAO) {
        this.TMP_POSICAO = TMP_POSICAO;
    }

    @Override
    public void loadColunas() {

        _colunas = new ArrayList<String>();

        _colunas.add("TMP_CAMPANHA");
        _colunas.add("TMP_CODIGO");
        _colunas.add("TMP_PARTICIPANTE");
        _colunas.add("TMP_PONTUACAO");
        _colunas.add("TMP_POSICAO");


    }
}
