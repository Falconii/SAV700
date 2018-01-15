package br.com.brotolegal.savdatabase.internet;

import br.com.brotolegal.savdatabase.LibFile.BlTPClient;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.entities.Config;

/**
 * Created by Falconi on 19/04/2017.
 */

public class SendToWeb extends Thread
{

    private ConfigDAO configdao;

    private Config config;

    private BlTPClient ftpclient = null;

    public SendToWeb() throws Exception {


    }

    private void SeachingConection() throws Exception {

        configdao = new ConfigDAO();

        configdao.open();

    }
}
