package br.com.brotolegal.savdatabase.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 03/10/2016.
 */
public class Logradouros {

    private Logradouro logradouro;
    private List<Logradouro> lsLogradouros;


    public Logradouros() {

        load();

    }

    public Logradouro get(int indice){

        Logradouro logra = (Logradouro) lsLogradouros.get(0);

        try {


            logra = (Logradouro) lsLogradouros.get(indice);


        }
        catch (Exception e){

            //não faz nada


        }


        return logra;



    }
    public List<Logradouro> getLsLogradouros() {

        return lsLogradouros;

    }

    public int getIndice(String value){

        int indice = 0;

        int retorno = -1;

        for (Logradouro log : lsLogradouros ) {

            if (log.getAbreDescri().equals(value)) {

                retorno = indice;

            }

            indice++;

        }


        return retorno;


    }

    public int getValueByDescricao(String value){

        int indice = 0;

        int retorno = -1;

        for (Logradouro log : lsLogradouros ) {

            if (log.getLOGRADOURO().equals(value)) {

                retorno = indice;

            }

            indice++;

        }


        return retorno;


    }

    private void load()

    {

        lsLogradouros = new ArrayList<Logradouro>();


        lsLogradouros.add(new Logradouro("81","R","RUA"));
        lsLogradouros.add(new Logradouro("8","AV","AVENIDA"));
        lsLogradouros.add(new Logradouro("90","ROD","RODOVIA"));
        lsLogradouros.add(new Logradouro("65","PC","PRACA"));
        lsLogradouros.add(new Logradouro("645","ACAMP","ACAMPAMENTO"));
        lsLogradouros.add(new Logradouro("1","AC","ACESSO"));
        lsLogradouros.add(new Logradouro("2","AD","ADRO"));
        lsLogradouros.add(new Logradouro("501","ERA","AEROPORTO"));
        lsLogradouros.add(new Logradouro("4","AL","ALAMEDA"));
        lsLogradouros.add(new Logradouro("5","AT","ALTO"));
        lsLogradouros.add(new Logradouro("472","A","AREA"));
        lsLogradouros.add(new Logradouro("654","AE","AREAESPECIAL"));
        lsLogradouros.add(new Logradouro("465","ART","ARTERIA"));
        lsLogradouros.add(new Logradouro("7","ATL","ATALHO"));
        lsLogradouros.add(new Logradouro("8","AV","AVENIDA"));
        lsLogradouros.add(new Logradouro("651","AV-CONT","AVENIDACONTORNO"));
        lsLogradouros.add(new Logradouro("15","BX","BAIXA"));
        lsLogradouros.add(new Logradouro("470","BLO","BALAO"));
        lsLogradouros.add(new Logradouro("9","BAL","BALNEARIO"));
        lsLogradouros.add(new Logradouro("11","BC","BECO"));
        lsLogradouros.add(new Logradouro("10","BELV","BELVEDERE"));
        lsLogradouros.add(new Logradouro("12","BL","BLOCO"));
        lsLogradouros.add(new Logradouro("13","BSQ","BOSQUE"));
        lsLogradouros.add(new Logradouro("14","BVD","BOULEVARD"));
        lsLogradouros.add(new Logradouro("496","BCO","BURACO"));
        lsLogradouros.add(new Logradouro("16","C","CAIS"));
        lsLogradouros.add(new Logradouro("571","CALC","CALCADA"));
        lsLogradouros.add(new Logradouro("17","CAM","CAMINHO"));
        lsLogradouros.add(new Logradouro("23","CPO","CAMPO"));
        lsLogradouros.add(new Logradouro("495","CAN","CANAL"));
        lsLogradouros.add(new Logradouro("481","CHAP","CHACARA"));
        lsLogradouros.add(new Logradouro("19","CHAP","CHAPADAO"));
        lsLogradouros.add(new Logradouro("479","CIRC","CIRCULAR"));
        lsLogradouros.add(new Logradouro("21","COL","COLONIA"));
        lsLogradouros.add(new Logradouro("503","CMP-VR","COMPLEXOVIARIO"));
        lsLogradouros.add(new Logradouro("485","COND","CONDOMINIO"));
        lsLogradouros.add(new Logradouro("20","CJ","CONJUNTO"));
        lsLogradouros.add(new Logradouro("22","COR","CORREDOR"));
        lsLogradouros.add(new Logradouro("24","CRG","CORREGO"));
        lsLogradouros.add(new Logradouro("478","DSC","DESCIDA"));
        lsLogradouros.add(new Logradouro("27","DSV","DESVIO"));
        lsLogradouros.add(new Logradouro("28","DT","DISTRITO"));
        lsLogradouros.add(new Logradouro("468","EVD","ELEVADA"));
        lsLogradouros.add(new Logradouro("573","ENT-PART","ENTRADAPARTICULAR"));
        lsLogradouros.add(new Logradouro("652","EQ","ENTREQUADRA"));
        lsLogradouros.add(new Logradouro("30","ESC","ESCADA"));
        lsLogradouros.add(new Logradouro("474","ESP","ESPLANADA"));
        lsLogradouros.add(new Logradouro("32","ETC","ESTACAO"));
        lsLogradouros.add(new Logradouro("564","ESTC","ESTACIONAMENTO"));
        lsLogradouros.add(new Logradouro("33","ETD","ESTADIO"));
        lsLogradouros.add(new Logradouro("498","ETN","ESTANCIA"));
        lsLogradouros.add(new Logradouro("31","EST","ESTRADA"));
        lsLogradouros.add(new Logradouro("650","EST-MUN","ESTRADAMUNICIPAL"));
        lsLogradouros.add(new Logradouro("36","FAV","FAVELA"));
        lsLogradouros.add(new Logradouro("37","FAZ","FAZENDA"));
        lsLogradouros.add(new Logradouro("40","FRA","FEIRA"));
        lsLogradouros.add(new Logradouro("38","FER","FERROVIA"));
        lsLogradouros.add(new Logradouro("39","FNT","FONTE"));
        lsLogradouros.add(new Logradouro("43","FTE","FORTE"));
        lsLogradouros.add(new Logradouro("45","GAL","GALERIA"));
        lsLogradouros.add(new Logradouro("46","GJA","GRANJA"));
        lsLogradouros.add(new Logradouro("486","HAB","HABITACIONAL"));
        lsLogradouros.add(new Logradouro("50","IA","ILHA"));
        lsLogradouros.add(new Logradouro("52","JD","JARDIM"));
        lsLogradouros.add(new Logradouro("473","JDE","JARDINETE"));
        lsLogradouros.add(new Logradouro("53","LD","LADEIRA"));
        lsLogradouros.add(new Logradouro("499","LG","LAGO"));
        lsLogradouros.add(new Logradouro("55","LGA","LAGOA"));
        lsLogradouros.add(new Logradouro("54","LRG","LARGO"));
        lsLogradouros.add(new Logradouro("56","LOT","LOTEAMENTO"));
        lsLogradouros.add(new Logradouro("477","MNA","MARINA"));
        lsLogradouros.add(new Logradouro("497","MOD","MODULO"));
        lsLogradouros.add(new Logradouro("60","TEM","MONTE"));
        lsLogradouros.add(new Logradouro("59","MRO","MORRO"));
        lsLogradouros.add(new Logradouro("500","NUC","NUCLEO"));
        lsLogradouros.add(new Logradouro("67","PDA","PARADA"));
        lsLogradouros.add(new Logradouro("471","PDO","PARADOURO"));
        lsLogradouros.add(new Logradouro("62","PAR","PARALELA"));
        lsLogradouros.add(new Logradouro("72","PRQ","PARQUE"));
        lsLogradouros.add(new Logradouro("74","PSG","PASSAGEM"));
        lsLogradouros.add(new Logradouro("502","PSC-SUB","PASSAGEMSUBTERRANEA"));
        lsLogradouros.add(new Logradouro("73","PSA","PASSARELA"));
        lsLogradouros.add(new Logradouro("63","PAS","PASSEIO"));
        lsLogradouros.add(new Logradouro("64","PAT","PATIO"));
        lsLogradouros.add(new Logradouro("483","PNT","PONTA"));
        lsLogradouros.add(new Logradouro("76","PTE","PONTE"));
        lsLogradouros.add(new Logradouro("469","PTO","PORTO"));
        lsLogradouros.add(new Logradouro("504","PC-ESP","PRACADE"));
        lsLogradouros.add(new Logradouro("70","PR","PRAIA"));
        lsLogradouros.add(new Logradouro("71","PRL","PROLONGAMENTO"));
        lsLogradouros.add(new Logradouro("77","Q","QUADRA"));
        lsLogradouros.add(new Logradouro("79","QTA","QUINTA"));
        lsLogradouros.add(new Logradouro("475","QTAS","QUINTA"));
        lsLogradouros.add(new Logradouro("82","RAM","RAMAL"));
        lsLogradouros.add(new Logradouro("482","RMP","RAMPA"));
        lsLogradouros.add(new Logradouro("87","REC","RECANTO"));
        lsLogradouros.add(new Logradouro("487","RES","RESIDENCIAL"));
        lsLogradouros.add(new Logradouro("89","RET","RETA"));
        lsLogradouros.add(new Logradouro("88","RER","RETIRO"));
        lsLogradouros.add(new Logradouro("91","RTN","RETORNO"));
        lsLogradouros.add(new Logradouro("569","ROD-AN","RODOANEL"));
        lsLogradouros.add(new Logradouro("506","RTT","ROTATORIA"));
        lsLogradouros.add(new Logradouro("476","ROT","ROTULA"));
        lsLogradouros.add(new Logradouro("653","R-LIG","RUADE"));
        lsLogradouros.add(new Logradouro("566","R-PED","RUADE"));
        lsLogradouros.add(new Logradouro("94","SRV","SERVIDAO"));
        lsLogradouros.add(new Logradouro("95","ST","SETOR"));
        lsLogradouros.add(new Logradouro("92","SIT","SITIO"));
        lsLogradouros.add(new Logradouro("96","SUB","SUBIDA"));
        lsLogradouros.add(new Logradouro("98","TER","TERMINAL"));
        lsLogradouros.add(new Logradouro("100","TV","TRAVESSA"));
        lsLogradouros.add(new Logradouro("570","TV-PART","TRAVESSAPARTICULAR"));
        lsLogradouros.add(new Logradouro("452","TRV","TRECHO"));
        lsLogradouros.add(new Logradouro("99","TRV","TREVO"));
        lsLogradouros.add(new Logradouro("97","TCH","TRINCHEIRA"));
        lsLogradouros.add(new Logradouro("567","TUN","TUNEL"));
        lsLogradouros.add(new Logradouro("480","UNID","UNIDADE"));
        lsLogradouros.add(new Logradouro("565","VAL","VALA"));
        lsLogradouros.add(new Logradouro("106","VLE","VALE"));
        lsLogradouros.add(new Logradouro("568","VRTE","VARIANTE"));
        lsLogradouros.add(new Logradouro("453","VER","VEREDA"));
        lsLogradouros.add(new Logradouro("101","V","VIA"));
        lsLogradouros.add(new Logradouro("572","V-AC","VIADE"));
        lsLogradouros.add(new Logradouro("484","V-PED","VIADE"));
        lsLogradouros.add(new Logradouro("505","V-EVD","VIAELEVADO"));
        lsLogradouros.add(new Logradouro("646","V-EXP","VIAEXPRESSA"));
        lsLogradouros.add(new Logradouro("103","VD","VIADUTO"));
        lsLogradouros.add(new Logradouro("105","VLA","VIELA"));
        lsLogradouros.add(new Logradouro("104","VL","VILA"));
        lsLogradouros.add(new Logradouro("108","ZIG-ZAG","ZIGUE-ZAGUE"));


    }


}
