package br.com.brotolegal.sav700.VerificaWeb;

import java.util.ArrayList;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.Config;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.PROCESSO_CONEXOES;
import static br.com.brotolegal.savdatabase.internet.AccessWebInfo.RETORNO_TIPO_ESTUTURADO;

/**
 * Created by Falconi on 05/04/2017.
 */

public class ConexaoAtiva {

    private Integer ativa;

    private List<Config> lsconexoes;

    private int Status;

    private String TAG = "CONEXAOATIVA";

    public ConexaoAtiva() {

        this.ativa      = -1;

        this.lsconexoes = new ArrayList<>();

        this.Status     = 0;


    }

    public Config getConexaoAtiva(){

        if ( ativa == -1  || this.ativa >= this.lsconexoes.size() ){

            return null;

        }

        return lsconexoes.get(this.ativa);

    }

    public void refresh(){

        BuscaConexaoAtiva();

    }

    private void BuscaConexaoAtiva(){

        CallBack callBack = new CallBack();

        AccessWebInfo acessoWeb = new AccessWebInfo(null, App.getCustomAppContext(), App.user, "GETSTATUS", "GETSTATUS",RETORNO_TIPO_ESTUTURADO,PROCESSO_CONEXOES, null, callBack,-1);

        acessoWeb.start();

    }

    private class CallBack extends HandleSoap{


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }


            String cerro       = result.getPropertyAsString("CERRO");
            String cmsgerro    = result.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                String MV_ZBLECRG  = App.TotvsSN(result.getPropertyAsString("MV_ZBLECRG"));
                String MV_ZBLEPED  = App.TotvsSN(result.getPropertyAsString("MV_ZBLEPED"));
                String MV_ZDTECRG  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTECRG"));
                String MV_ZDTEPED  = App.aaaammddToddmmaaaa(result.getPropertyAsString("MV_ZDTEPED"));
                String MV_ZHRECRG  = result.getPropertyAsString("MV_ZHRECRG");
                String MV_ZHREPED  = result.getPropertyAsString("MV_ZHREPED");

                StatusDAO dao = new StatusDAO();

                dao.open();

                Status st = dao.seek(null);

                if (st == null){

                    dao.insert(new Status("N",MV_ZBLEPED,MV_ZDTEPED,MV_ZHREPED,MV_ZBLECRG,MV_ZDTECRG,MV_ZHRECRG,"N","","","","0"));

                } else {

                    st.setAPPBLOCK("N");
                    st.setPEDIDO(MV_ZBLEPED);
                    st.setPEDDATA(MV_ZDTEPED);
                    st.setPEDHORA(MV_ZHREPED);
                    st.setCARGA(MV_ZBLECRG);
                    st.setCARDATA(MV_ZDTECRG);
                    st.setCARHORA(MV_ZHRECRG);

                    dao.Update(st);

                }

                dao.close();

            } else {

                //Altera algumas mensagens


            }

        }

        @Override
        public void processaArray() throws Exception {

        }
    }
}
