package br.com.brotolegal.savdatabase.internet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

import br.com.brotolegal.savdatabase.LibFile.BlTPClient;
import br.com.brotolegal.savdatabase.LibFile.ExceptionNoFile;
import br.com.brotolegal.savdatabase.LibFile.Header1;
import br.com.brotolegal.savdatabase.LibFile.ObjFile;
import br.com.brotolegal.savdatabase.R;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.AcordoDAO;
import br.com.brotolegal.savdatabase.dao.AgendamentoDAO;
import br.com.brotolegal.savdatabase.dao.CampanhaDAO;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.MetaDAO;
import br.com.brotolegal.savdatabase.dao.OcorrenciaDAO;
import br.com.brotolegal.savdatabase.dao.PedCabTvsDAO;
import br.com.brotolegal.savdatabase.dao.PedidoCabMbDAO;
import br.com.brotolegal.savdatabase.dao.PedidoDetMbDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Agendamento;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Ocorrencia;
import br.com.brotolegal.savdatabase.entities.PedCabTvs;
import br.com.brotolegal.savdatabase.entities.PedDetTvs;
import br.com.brotolegal.savdatabase.entities.PedidoCabMb;
import br.com.brotolegal.savdatabase.entities.PedidoDetMb;
import br.com.brotolegal.savdatabase.entities.Pedidos;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.SALESORDERMB;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.eventbus.NotificationAgendamento;
import br.com.brotolegal.savdatabase.eventbus.NotificationCarga;
import br.com.brotolegal.savdatabase.eventbus.NotificationPedido;
import br.com.brotolegal.savdatabase.md5.CheckMD5;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionNoConexao;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionSavePedido;


public class AccessWebInfo  extends Thread {
    public static final int RETORNO_ARRAY_ESTRUTURADO               = 0;
    public static final int RETORNO_TIPO_ESTUTURADO                 = 1;
    public static final int RETORNO_STRING                          = 2;
    public static final int PROCESSO_FILE                           = 0;
    public static final int PROCESSO_CUSTOM                         = 1;
    public static final int PROCESSO_PEDIDOS                        = 2;
    public static final int PROCESSO_UPDATE                         = 3;
    public static final int PROCESSO_BACKEND_PEDIDO                 = 4;
    public static final int PROCESSO_CADASTRO_PREACORDO             = 5;
    public static final int PROCESSO_CONEXOES                       = 6;
    public static final int PROCESSO_ATUALIZACARGABACKGROUND        = 7;
    public static final int PROCESSO_AGENDAMENTO                    = 8;
    public static final int PROCESSO_EMAIL                          = 9;
    public static final int PROCESSO_AGENDAMENTO_ATRASADO           = 10;
    public static final int PROCESSO_AGENDAMENTO_JUSTIFICATICAS     = 11;
    public static final int PROCESSO_DEFAULT                        = 12;
    public static final int PROCESSO_DOWNLOAD                       = 13;
    public static final int PROCESSO_ATUALIZA_ACORDO_PROTHEUS       = 14;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST        =  9000;

    private Boolean PROCESSANDO = true;
    private String LOG = "ACCESSWEBINFO";
    private Handler handler;
    private Bundle params = new Bundle();
    private SoapObject spRetorno;
    private Context context;
    private Config config;
    private String SOAP_ACTION;
    private String METHOD_NAME;
    private int tipoRetorno;
    private int tipoProcesso;
    private SoapObject request;
    private Usuario user;
    private HandleSoap handlesoap;
    private SoapSerializationEnvelope envelope;
    private int NOTIFICATION_ID = 0;
    private int idNotification  = -1;
    private String NOTIFICATION_TEXT = "";

    private String codocorrencia = "";
    private String filename      = "";
    private String hash          = "";
    private String codtask       = "";
    private String mensagem      = "";

    private String CODIGO   = "";
    private String LOJA     = "";
    private String PEDIDO   = "";
    private String FILENAME = "";

    //Usado para liberar lançamento de pedidos
    private String MV_ZBLECRG  = "";
    private String MV_ZBLEPED  = "";
    private String MV_ZDTECRG  = "";
    private String MV_ZDTEPED  = "";
    private String MV_ZHRECRG  = "";
    private String MV_ZHREPED  = "";

    //Usado nas notificações
    private String NOTCODIGO   = "";
    private String NOTLOJA     = "";

    BlTPClient ftpclient = null;

    public AccessWebInfo(Handler handler, Context context, Usuario user, String SOAP_ACTION, String METHOD_NAME, int tipoRetorno, int tipoProcesso, Config config, HandleSoap handlesoap, int idNotification) {

        super();

        this.idNotification = idNotification;

        this.handler = handler;

        this.context = context;

        this.config = config;

        this.SOAP_ACTION = SOAP_ACTION;

        this.METHOD_NAME = METHOD_NAME;

        this.tipoRetorno = tipoRetorno;

        this.tipoProcesso = tipoProcesso;

        this.user = user;

        if (config == null){

            try {

                ConfigDAO dao = new ConfigDAO();

                dao.open();

                this.config = dao.seek(new String[]{"000"});

                dao.close();

                this.request = new SoapObject(this.config.getNSFull(), this.METHOD_NAME);

            } catch (Exception e) {

                toast(e.getMessage());

                return;

            }

        } else {

            this.request = new SoapObject(config.getNSFull(), this.METHOD_NAME);

        }

        this.handlesoap = handlesoap;

        this.envelope = new SoapServEnv(SoapEnvelope.VER11);

        if (this.SOAP_ACTION.equals("PUTTASKS")){

            NOTIFICATION_ID   = HelpInformation.NotificacaoCargaCompleta;

            NOTIFICATION_TEXT = "Enviando Solcitação de Carga Completa. Aguarde Retorno !!";

        }

        if (this.SOAP_ACTION.equals("PROCTASKS")) {

            NOTIFICATION_ID   = HelpInformation.NotificacaoCargaCompleta;

            NOTIFICATION_TEXT = "Processando Da Carga Completa Terminada Com Sucesso !!";

        }

    }

    @Override
    public void run() {

        try {

            PROCESSANDO = true;

            if (!verificaConexao()) {

                if (this.tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND) {

                    try {
                        PedidoCabMbDAO dao = new PedidoCabMbDAO();

                        dao.open();

                        PedidoCabMb ped = dao.seek(new String[]{PEDIDO});

                        if (ped != null) {

                            ped.setSTATUS("3");

                            dao.Update(ped);
                        }

                        dao.close();

                    } catch (Exception e){

                        ///
                    }

                    Log.i("TRANSMISSAO", "Falha Na Transmissão. Favor Transmitir Manualmente.");

                    params.putString("CERRO", "FEC");

                    params.putString("CMSGERRO", "Falha Na Transmissão Do Pedido "+PEDIDO+". Favor Transmitir Manualmente.");

                    sendmsg(params);

                    pausa(2000);

                    PROCESSANDO = false;

                }

                if (codocorrencia.trim().isEmpty()) {

                    atualizaOcorrencia(codocorrencia, "Falha Na Conexão", "0", "", "", null, App.getDataHora());

                    NotificationCarga notificationCarga = new NotificationCarga("000", "");

                    EventBus.getDefault().post(notificationCarga);

                }
                throw new Exception("Sem Conexão De Rede");




            }

            if (this.tipoProcesso == PROCESSO_PEDIDOS || this.tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND) {

                transmitir_salesorderByArray();

                transmitir_agendamentoByArray();

                return;

            }

            if (this.tipoProcesso == PROCESSO_AGENDAMENTO_JUSTIFICATICAS) {

                transmitir_agendamentoByArray();

                return;

            }

            if (this.tipoProcesso == PROCESSO_AGENDAMENTO_ATRASADO) {

                transmitir_agendamentoByArray();

                return;

            }

            if (this.tipoProcesso == PROCESSO_CONEXOES) {

                VerificaConexaoAtiva();

                return;

            }


            if (this.tipoProcesso == PROCESSO_CADASTRO_PREACORDO) {

                transmitir_preacordoByArray();

                return;

            }


            if (this.tipoProcesso == PROCESSO_BACKEND_PEDIDO) {

                transmitir_02();

                return;

            }

            if (this.tipoProcesso == PROCESSO_ATUALIZA_ACORDO_PROTHEUS) {

                getAcordo();

                return;

            }

            if (this.tipoProcesso == PROCESSO_UPDATE) {

                try {

                    updateVersion();

                    spRetorno = new SoapObject();
                    spRetorno.addProperty("CERRO", "000");
                    spRetorno.addProperty("CMSGERRO", "OK");

                    if (handlesoap != null)  handlesoap.setResult(spRetorno);

                    params.putString("CERRO", "EXE");

                    params.putString("CMSGERRO", "Em Processamento !!!");

                    sendmsg(params);


                } catch (Exception e){

                    spRetorno = new SoapObject();
                    spRetorno.addProperty("CERRO", "099");
                    spRetorno.addProperty("CMSGERRO", e.getMessage());

                    if (handlesoap != null)  handlesoap.setResult(spRetorno);


                }

                return;

            }

            if (this.tipoProcesso == PROCESSO_DOWNLOAD) {

                try {

                    dowloadFile(FILENAME);

                    spRetorno = new SoapObject();
                    spRetorno.addProperty("CERRO", "000");
                    spRetorno.addProperty("CMSGERRO", "OK");

                    if (handlesoap != null)  handlesoap.setResult(spRetorno);

                    params.putString("CERRO", "EXE");

                    params.putString("CMSGERRO", "Em Processamento !!!");

                    sendmsg(params);


                } catch (Exception e){

                    spRetorno = new SoapObject();
                    spRetorno.addProperty("CERRO", "999");
                    spRetorno.addProperty("CMSGERRO", e.getMessage());

                    if (handlesoap != null)  handlesoap.setResult(spRetorno);


                }

                return;

            }

            params.putString("CERRO", "---");

            params.putString("CMSGERRO","ENVIANDO PEDIDOS");

            sendmsg(params);

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            androidHttpTransport.call(SOAP_ACTION, envelope);

            PROCESSANDO = false;


            if ( (this.SOAP_ACTION.equals("PROCTASKS") ) )  {

                Log.i("DUMP" ,androidHttpTransport.requestDump);

                Log.i("DUMP" ,androidHttpTransport.responseDump);


            }

            switch (tipoRetorno) {

                case RETORNO_ARRAY_ESTRUTURADO:

                    SoapObject results = (SoapObject) envelope.bodyIn;

                    spRetorno = (SoapObject) results.getProperty(0);

                    break;

                case RETORNO_TIPO_ESTUTURADO:

                    spRetorno = (SoapObject) envelope.getResponse();

                    break;
            }

            if (handlesoap != null)  handlesoap.setResult(spRetorno);

            switch (tipoProcesso) {

                case PROCESSO_FILE: //processa o arquivo

                    switch (tipoRetorno) {

                        case RETORNO_ARRAY_ESTRUTURADO: {

                            for (int x = 0; x < spRetorno.getPropertyCount(); x++) {


                                SoapObject registro = (SoapObject) this.spRetorno.getProperty(x);

                                Log.i(LOG,"Lendo Tipo Estruturado Codigo = "+registro.getProperty("CERRO").toString() );

                                Boolean lMd5 = true;

                                if (registro.getProperty("CERRO").toString().equals("000")) {

                                    try {

                                        codocorrencia = registro.getProperty("CCODIGO").toString();

                                        filename      = registro.getProperty("CNOMEARQ").toString();

                                        hash          = "";

                                        codtask       = "";

                                        if ((this.SOAP_ACTION.equals("PROCTASKS") ) )  {

                                            hash = registro.getProperty("HASH").toString();

                                            mensagem = registro.getProperty("MENSAGEM").toString();

                                        }

                                        if ( (this.SOAP_ACTION.equals("PUTTASKS") ) ) {

                                            codtask       = registro.getProperty("CODTASK").toString();

                                            atualizaOcorrencia(codocorrencia,"Ocorrência Aceita No Servidor. Aguarde !!", "1", filename,codtask,App.getDataHora(),null);

                                            params.putString("CERRO", "FEC");

                                            params.putString("CMSGERRO", "");

                                            sendmsg(params);

                                            return;

                                        }

                                        if (codocorrencia.equals("000015") || codocorrencia.equals("000019")){ //metas mensal e campanha aberta

                                            if (codocorrencia.equals("000015")){
                                                try {

                                                    MetaDAO dao = new MetaDAO();

                                                    dao.open();

                                                    dao.deleteMetaMes();

                                                    dao.close();

                                                    try {

                                                        padraoFile(filename, false);

                                                        atualizaOcorrencia(codocorrencia, "Ocorrência Processada Com Sucesso", "0", filename,"",null,App.getDataHora());

                                                        NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                        EventBus.getDefault().post(notificationCarga);


                                                    } catch (Exception e){

                                                        atualizaOcorrencia(codocorrencia, e.getMessage(), "1", filename,null,null,null);

                                                        NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                        EventBus.getDefault().post(notificationCarga);

                                                    }
                                                } catch (Exception e){

                                                    atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,null,null,null);

                                                    NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                    EventBus.getDefault().post(notificationCarga);

                                                }
                                            }


                                            if (codocorrencia.equals("000019")){
                                                try {

                                                    CampanhaDAO dao = new CampanhaDAO();

                                                    dao.open();

                                                    dao.deleteMesAtual();

                                                    dao.close();

                                                    try {

                                                        padraoFile(filename, false);

                                                        atualizaOcorrencia(codocorrencia, "Ocorrência Processada Com Sucesso", "0", filename,"",null,App.getDataHora());

                                                        NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                        EventBus.getDefault().post(notificationCarga);


                                                    } catch (Exception e){

                                                        atualizaOcorrencia(codocorrencia, e.getMessage(), "1", filename,null,null,null);

                                                        NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                        EventBus.getDefault().post(notificationCarga);


                                                    }

                                                } catch (Exception e){

                                                    atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,null,null,null);

                                                    NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                    EventBus.getDefault().post(notificationCarga);

                                                }
                                            }

                                        } else {

                                            try {

                                                padraoFile(filename, true);

                                                if ( codocorrencia.equals("000001") )  atualizaTabela();

                                                atualizaOcorrencia(codocorrencia, "Ocorrência Processada Com Sucesso", "0", filename,"",null,App.getDataHora());

                                                NotificationCarga notificationCarga = new NotificationCarga("000","");

                                                EventBus.getDefault().post(notificationCarga);

                                            }  catch (ExceptionNoFile e) {

                                                params.putString("CERRO", "MMM");

                                                params.putString("CMSGERRO", e.getMessage());

                                                sendmsg(params);

                                                atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,null,null,null);

                                                pausa(2000);

                                            } catch (Exception e){

                                                atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,null,null,null);
                                            }

                                        }



                                    } catch (Exception e) {


                                        params.putString("CERRO", "MMM");

                                        params.putString("CMSGERRO", e.getMessage());

                                        sendmsg(params);

                                        atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,"",null,null);

                                        pausa(2000);

                                    }

                                    if ((!this.SOAP_ACTION.equals("PROCTASKS") ) ) {

                                        params.putString("CERRO", "FEC");

                                        params.putString("CMSGERRO", "");

                                        sendmsg(params);

                                    }

                                } else {

                                    if ((this.SOAP_ACTION.equals("PROCTASKS") ) )  {

                                        codocorrencia = registro.getProperty("CCODIGO").toString();

                                        filename      = registro.getProperty("CNOMEARQ").toString();

                                        mensagem      = registro.getProperty("MENSAGEM").toString();

                                        atualizaOcorrencia(codocorrencia, ((SoapObject) this.spRetorno.getProperty(x)).getProperty("CMSGERRO").toString()+ " - " +mensagem, "1", filename,"",null,null);

                                        NotificationCarga notificationCarga = new NotificationCarga("000",((SoapObject) this.spRetorno.getProperty(x)).getProperty("CMSGERRO").toString()+ " - " +mensagem);

                                        EventBus.getDefault().post(notificationCarga);

                                    } else {

                                        params.putString("CERRO", "FEC");

                                        params.putString("CMSGERRO", ((SoapObject) this.spRetorno.getProperty(x)).getProperty("CMSGERRO").toString());

                                        sendmsg(params);
                                    }
                                }

                            }
                            break;
                        }

                        case RETORNO_TIPO_ESTUTURADO:

                            if (this.spRetorno.getProperty("CERRO").toString().equals("000")) {

                                try {

                                    codocorrencia = this.spRetorno.getProperty("CCODIGO").toString();

                                    filename = this.spRetorno.getProperty("CNOMEARQ").toString();

                                    try {

                                        padraoFile(filename, true);

                                        atualizaOcorrencia(codocorrencia, "Ocorrência Processada Com Sucesso", "0", filename,"",null, App.getDataHora());

                                    } catch (Exception e){

                                        atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,"",null,null);

                                    }

                                } catch (ExceptionNoFile e) {

                                    params.putString("CERRO", "MMM");

                                    params.putString("CMSGERRO", e.getMessage());

                                    sendmsg(params);

                                    atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,"",null,null);

                                    pausa(2000);


                                } catch (Exception e) {


                                    params.putString("CERRO", "MMM");

                                    params.putString("CMSGERRO", e.getMessage());

                                    sendmsg(params);

                                    atualizaOcorrencia(codocorrencia, e.getMessage(), "9", filename,"",null,null);

                                    pausa(2000);

                                }

                                params.putString("CERRO", "FEC");

                                params.putString("CMSGERRO", "Fim Do Processamento !!");

                                sendmsg(params);

                            } else {

                                params.putString("CERRO", "FEC");
                                params.putString("CMSGERRO", this.spRetorno.getProperty("CMSGERRO").toString());

                                sendmsg(params);
                            }


                            break;
                    }

                    break;

                case PROCESSO_CUSTOM: {

                    params.putString("CERRO", "EXE");
                    params.putString("CMSGERRO", "Em Processamento !!!");
                    sendmsg(params);

                    break;
                }

                case PROCESSO_EMAIL: {

                    params.putString("CERRO",this.spRetorno.getProperty("FEC").toString());
                    params.putString("CMSGERRO",this.spRetorno.getProperty("CMSGERRO").toString());
                    sendmsg(params);

                    break;

                }

                default:
                    break;
            }

        } catch (Exception e) {

            //Cria retorno
            switch (tipoRetorno) {

                case RETORNO_ARRAY_ESTRUTURADO:

                    spRetorno = new SoapObject();

                    SoapObject array =  new SoapObject();
                    array.addProperty("CERRO", "099");
                    array.addProperty("CMSGERRO", e.getMessage());
                    spRetorno.addProperty("array",array);

                    break;

                case RETORNO_TIPO_ESTUTURADO:

                    spRetorno = new SoapObject();
                    spRetorno.addProperty("CERRO", "099");
                    spRetorno.addProperty("CMSGERRO", e.getMessage());

                    if (handlesoap != null)  handlesoap.setResult(spRetorno);

                    break;
            }


            switch (tipoProcesso) {

                case PROCESSO_CUSTOM: {

                    params.putString("CERRO", "EXE");

                    params.putString("CMSGERRO", "Em Processamento !!!");

                    sendmsg(params);

                    break;

                }
                case PROCESSO_FILE: {

                    params.putString("CERRO", "FEC");

                    params.putString("CMSGERRO", e.getMessage());

                    sendmsg(params);

                    break;
                }
                default: {

                    params.putString("CERRO"   , "FEC");

                    params.putString("CMSGERRO", e.getMessage());

                    sendmsg(params);

                    break;
                }

            }

            PROCESSANDO = false;

        }


    }

    private void atualizaTabela(){


        try {

            MV_ZBLECRG  = App.TotvsSN(MV_ZBLECRG);
            MV_ZBLEPED  = App.TotvsSN(MV_ZBLEPED);
            MV_ZDTECRG  = App.aaaammddToddmmaaaa(MV_ZDTECRG);
            MV_ZDTEPED  = App.aaaammddToddmmaaaa(MV_ZDTEPED);


            StatusDAO dao = new StatusDAO();

            dao.open();

            Status st = dao.seek(null);

            if (st == null){

                dao.insert(new Status("N",MV_ZBLEPED,MV_ZDTEPED,MV_ZHREPED,MV_ZBLECRG,MV_ZDTECRG,MV_ZHRECRG,"N","","","","0"));

            } else {

                st.setPEDIDO(MV_ZBLEPED);
                st.setPEDDATA(MV_ZDTEPED);
                st.setPEDHORA(MV_ZHREPED);
                st.setCARGA(MV_ZBLECRG);
                st.setCARDATA(MV_ZDTECRG);
                st.setCARHORA(MV_ZHRECRG);
                st.setULTATUAL("");

                dao.Update(st);

            }

            dao.close();
        } catch (Exception e){



        }

    }



    private void atualizaOcorrencia(String processo, String msg, String status, String filename, String codTask, String HoraInical,String HoraFinal) throws Exception {

        OcorrenciaDAO dao = new OcorrenciaDAO();

        dao.open();

        Ocorrencia ocorrencia = dao.seekByCodigo(new String[]{processo});

        if (ocorrencia != null) {

            ocorrencia.setSTATUS(status);
            ocorrencia.setARQUIVO(filename);
            ocorrencia.setOBS(msg);
            if (codTask != null) ocorrencia.setCODTAREFA(codTask);
            if (HoraInical != null) ocorrencia.setHORASOL(HoraInical);
            if (HoraFinal != null) ocorrencia.setHORAEXE(HoraFinal);

            dao.Update(ocorrencia);


        } else {

            dao.close();

            throw new Exception("Falha Na Atualização Da Ocorrência.\nOcorrência Não Encontrada.");
        }

        dao.close();

        if (processo.equals("000001") && (ocorrencia != null) && ocorrencia.getSTATUS().equals("0")){

            Calendar c = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String atualizacao  = format.format(c.getTime());

            StatusDAO daoST = new StatusDAO();

            daoST.open();

            Status st = daoST.seek(null);

            if (st != null) {

                st.setULTATUAL(atualizacao);

                daoST.Update(st);

            } else {

                //Ignora atualização do status;
            }

            daoST.close();

        }

    }

    private void padraoFile(String filename,Boolean deleteAll) throws Exception, ExceptionNoFile {

		/* Atributos do FTP */
        final String TEMP_FILENAME  = App.BasePath + "/" + App.AppPath + "/" + user.getCOD() + "/" + filename;
        final String TEMP_DIR       = App.BasePath + "/" + App.AppPath + "/" + user.getCOD();
        final String TEMP_FILE      = filename;
        final String UNZIP_FILENAME = App.BasePath + "/" + App.AppPath + "/" + user.getCOD() + "/" + user.getCOD() + ".txt";
        final String LOAD_FILE_FTP  = "SAV/" + user.getCOD() + "/envia/" + filename;
        final String LOG_FILE_FTP   = "SAV/" + user.getCOD() + "/envia/" + filename.trim().substring(0,filename.trim().length()-4)+".txt";
        final String PATH_FTP       = "SAV/" + user.getCOD() + "/envia/";
        final String endereco       = config.getIP();
        final String usuario        = config.getFTPUSER();
        final String senha          = config.getFTPPASS();
        boolean conexao = false;
        boolean download = false;
        boolean unzip = false;


        if (this.SOAP_ACTION == "PUTTASKS"){

            throw new Exception("Aguardando Processamento Do Arquivo !!");

        }

        atualizaOcorrencia(codocorrencia, "Inicio Do Processo De DownLoad !!!", "3", filename,null,null,null);

        EventBus.getDefault().post(new NotificationCarga("000",""));

			/* Criação do diretorio */

        File dir = new File(App.BasePath + "/" + App.AppPath + "/" + user.getCOD());

        try {
            if (dir.exists() == false) {
                dir.mkdirs();
            }

        } catch (Exception e) {


            throw new Exception("Falha Na Criação Do Diretório");



        }

        ftpclient = new BlTPClient();

        atualizaOcorrencia(codocorrencia, "Estabelecendo Conexão !!", "3", filename,null,null,null);

        EventBus.getDefault().post(new NotificationCarga("000",""));


        try {

            conexao = ftpclient.ftpConnect(endereco, usuario, senha, 21);

        } catch (IOException e1) {

            throw new Exception(e1.getMessage());


        } catch (Exception e1) {

            throw new Exception(e1.getMessage());

        }


        if (conexao) {

            atualizaOcorrencia(codocorrencia, "Inicio Do DownLoad Do Arquivo !!!", "3", filename,null,null,null);

            EventBus.getDefault().post(new NotificationCarga("000",""));

            sendmsg(params);

            if (!ftpclient.ftpExistFile(PATH_FTP, filename)) {

                throw new ExceptionNoFile();

            }

            try {

                ftpclient.ftpDownload(LOAD_FILE_FTP, TEMP_FILENAME);

                download = true;

                if ( (!hash.equals("anyType{}")) && (!hash.trim().isEmpty())) {

                    CheckMD5 md5 = new CheckMD5();

                    if (!md5.checkFile(hash, filename)) {

                        throw new Exception("Arquivo Corrompido !!!");
                    }

                } else {

                    throw new Exception("Validação MD5 Não Encontrada !!");

                }

            } catch (Exception e) {

                throw new Exception(e.getMessage());

            }

        }

        if (download) {

            atualizaOcorrencia(codocorrencia, "Descompactando Arquivo !!", "3", filename,null,null,null);

            EventBus.getDefault().post(new NotificationCarga("000",""));

            unGunzipFile(TEMP_FILENAME, UNZIP_FILENAME);

        } else {

            throw new Exception("Arquivo De Retorno Não Disponivel !");

        }


        //Apaga Arquivo Baixado zip
        File file = new File(TEMP_DIR, TEMP_FILE);

        file.delete();

        try {

            ftpclient.ftpRemoveFile(LOAD_FILE_FTP);

            ftpclient.ftpRemoveFile(LOG_FILE_FTP);


        } catch (Exception e) {

            //falha na exclusão do arquivo ftp

        }

        if (conexao) {

            ftpclient.ftpDisconnect();

            conexao = false;

        }

        atualizaOcorrencia(codocorrencia, "DownLoad Executado Com Sucesso!!!", "3", filename,null,null,null);

        EventBus.getDefault().post(new NotificationCarga("000",""));


        //Leitura do arquivo

        if (download) {

            ObjFile obj = new ObjFile(App.BasePath + "/" + App.AppPath + "/" + user.getCOD() + "/", user.getCOD() + ".txt");

            try {

                obj.loadListFile();

                for (Header1 hd : obj.getlistfiles()) {

                    if (obj.loadFile(hd.getARQUIVO())) {

                        try {

                            atualizaOcorrencia(codocorrencia,  "Gravando Tabela: " + hd.getARQUIVO(), "3", filename,null,null,null);

                            EventBus.getDefault().post(new NotificationCarga("000",""));

                            obj.saveToBD(context, user.getCOD(), deleteAll);

                        } catch (Exception e) {

                            throw new Exception(e.getMessage());

                        }

                    }
                }
            } catch (Exception e) {

                throw new Exception(e.getMessage());

            }

            //Apaga Arquivo Baixado zip
            file = new File(App.BasePath + "/" + App.AppPath + "/" + user.getCOD(), user.getCOD() + ".txt");

            file.delete();

        }
    }

    private void dowloadFile(String filename) throws Exception, ExceptionNoFile,ExceptionNoConexao {

        String  log = "DOWLOADFILE";
		/* Atributos do FTP */
        final String TEMP_FILENAME  = App.BasePath + "/" + App.AppPath + '/' + filename;
        final String TEMP_DIR       = App.BasePath + "/" + App.AppPath ;
        final String TEMP_FILE      = filename;
        final String LOAD_FILE_FTP  = "SAV/download/"+filename;
        final String PATH_FTP       = "SAV/download/";
        final String endereco       = config.getIP();
        final String usuario        = config.getFTPUSER();
        final String senha          = config.getFTPPASS();
        boolean conexao             = false;
        boolean download            = false;

        params.putString("CERRO", "---");

        params.putString("CMSGERRO","DOWNLOAD DO ARQUIVO !!");

        sendmsg(params);

    	/* Criação do diretorio */

        File dir = new File(App.BasePath + "/" + App.AppPath) ;

        try {
            if (dir.exists() == false) {

                dir.mkdirs();

            }

        } catch (Exception e) {

            throw new Exception("Falha Na Criação Do Diretório");

        }

        ftpclient = new BlTPClient();

		/* conexão */

        params.putString("CERRO", "MMM");
        params.putString("CMSGERRO", "Estabelecendo Conexão !!!");

        sendmsg(params);

        try {

            conexao = ftpclient.ftpConnect(endereco, usuario, senha, 21);

        } catch (Exception e1) {

            params.putString("CERRO", "MMM");
            params.putString("CMSGERRO", "Falha Na Conexão !!!");

            sendmsg(params);

            pausa(2000);

            params.putString("CERRO", "FEC");
            params.putString("CMSGERRO", "");

            sendmsg(params);

            throw new ExceptionNoConexao(e1.getMessage());

        }


        if (conexao) {

            params.putString("CERRO", "MMM");
            params.putString("CMSGERRO", "DownLoad do Arquivo !!!");

            sendmsg(params);

            if (!ftpclient.ftpExistFile(PATH_FTP, filename)) {

                throw new ExceptionNoFile();

            }

            try {

                ftpclient.ftpDownload(LOAD_FILE_FTP, TEMP_FILENAME);

                download = true;

            } catch (Exception e){

                throw new Exception(e.getMessage());
            }


        }

        if (download) {

            params.putString("CERRO", "MMM");
            params.putString("CMSGERRO", "Arquivo Baixado");

            sendmsg(params);

        } else {

            throw new Exception("Arquivo De Retorno Não Disponivel !");

        }

        if (conexao) {

            ftpclient.ftpDisconnect();

            conexao = false;

        }

        params.putString("CERRO", "MMM");
        params.putString("CMSGERRO", "Fim Do DownLoad !!!");
        sendmsg(params);

    }

    private void updateVersion() throws Exception, ExceptionNoFile {

        String filename = "SAV700.APK";
		/* Atributos do FTP */
        final String TEMP_FILENAME = App.BasePath + "/" + App.AppPath + "/"  + filename;
        final String TEMP_DIR  = App.BasePath + "/" + App.AppPath + "/" ;
        final String TEMP_FILE = filename;
        final String UNZIP_FILENAME = App.BasePath + "/" + App.AppPath + "/" + user.getCOD() + "/" + user.getCOD() + ".txt";
        final String LOAD_FILE_FTP = "SAV700/" + filename;
        final String PATH_FTP = "SAV700";
        final String endereco = config.getIP();
        final String usuario  = config.getFTPUSER();
        final String senha    = config.getFTPPASS();
        boolean conexao = false;
        boolean download = false;


        params.putString("CERRO", "---");

        params.putString("CMSGERRO","VERIFICANDO ARQUIVO....");

        sendmsg(params);

        pausa(1000);

        /* Criação do diretorio */

        File dir = new File(App.BasePath + "/" + App.AppPath + "/" + user.getCOD());

        try {
            if (dir.exists() == false) {
                dir.mkdirs();
            }

        } catch (Exception e) {


            throw new Exception("Falha Na Criação Do Diretório");


        }


        //Apaga Arquivo Anteriormente
        File file = new File(TEMP_DIR, "SAV700.APK");

        if (file.exists()==true){

            file.delete();

        }
        ftpclient = new BlTPClient();

			/* conexão */

        params.putString("CERRO", "MMM");
        params.putString("CMSGERRO", "Estabelecendo Conexão !!!");

        sendmsg(params);


        try {

            conexao = ftpclient.ftpConnect(endereco, usuario, senha, 21);

        } catch (IOException e1) {

            throw new Exception(e1.getMessage());


        } catch (Exception e1) {

            throw new Exception(e1.getMessage());

        }


        if (conexao) {

            params.putString("CERRO", "MMM");
            params.putString("CMSGERRO", "DownLoad do Arquivo !!!");

            sendmsg(params);

            if (!ftpclient.ftpExistFile(PATH_FTP, filename)) {

                throw new ExceptionNoFile();

            }

            try {

                ftpclient.ftpDownload(LOAD_FILE_FTP, TEMP_FILENAME);

                download = true;

            } catch (Exception e){

                //ignora

            }

        }


        if (conexao) {

            ftpclient.ftpDisconnect();

            conexao = false;

        }

        params.putString("CERRO", "MMM");
        params.putString("CMSGERRO", "Fim Do DownLoad !!!");
        sendmsg(params);



    }

    private void unGunzipFile(String compressedFile, String decompressedFile) throws Exception {
        byte[] buffer = new byte[1024];
        try {
            FileInputStream fileIn = new FileInputStream(compressedFile);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
            FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);
            int bytes_read;
            while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytes_read);
            }
            gZIPInputStream.close();
            fileOutputStream.close();
        } catch (IOException ex) {

            throw new Exception("Erro Na Descompactação ");

        }
    }

    private void pausa(long tempo) throws Exception{

//        if (handler != null) {
//
//            Thread.sleep(tempo);
//
//        }

        Thread.sleep(tempo);

    }

    public void refresh_acordo_02() throws  Exception {

        String SOAP_ACTION      = "SEEKACORDOS";

        String METHOD_NAME      = "SEEKACORDOS";

        List<PreAcordo> lsLista = new ArrayList<>();

        PreAcordoDAO dao  = new PreAcordoDAO();

        //BUSCA pre-acordos
        try {

            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", "Buscando os pré-acordos...Atualização");

            sendmsg(params);

            pausa(1000);

            dao.open();

            lsLista = dao.getAllToRefresh();

            dao.close();

            if (lsLista.size() == 0) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Nenhum Pré-Acordo Para Atualizar !");

                sendmsg(params);

                pausa(1000);

                params.putString("CERRO", "FEC");

                if (handler != null){

                    params.putString("CMSGERRO", "");

                } else {

                    params.putString("CMSGERRO", "Sincronização Terminada !!!");

                }

                sendmsg(params);

                pausa(2000);

                return;


            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                PROCESSANDO = false;

            }


            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            SoapObject soapAcordos0 = new SoapObject("", "ACORDOS");

            int x;

            x = 0;

            SoapObject soapAcordos1 = new SoapObject("", "ACORDOS");

            for (PreAcordo preacordo : lsLista) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Pré-Acordo:  " + preacordo.getCODMOBILE() + " " + String.valueOf(x++));

                sendmsg(params);

                soapAcordos1.addProperty("WSACORDOX", preacordo);

            }

            soapAcordos0.addSoapObject(soapAcordos1);

            request.addSoapObject(soapAcordos0);

            envelope.addMapping(METHOD_NAME, "PreAcordo", new PreAcordo().getClass());

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");


            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", "Transmitindo.....\nEnviando os Pré-Acordos para a Broto...");

            sendmsg(params);

            androidHttpTransport.call(SOAP_ACTION, envelope);

            spRetorno = (SoapObject) envelope.getResponse();

            String cerro = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                SoapObject ACORDOSRET = (SoapObject) this.spRetorno.getProperty("ACORDOSRET");

                for(int y = 0; y < ACORDOSRET.getPropertyCount(); y++ ) {

                    SoapObject reg = (SoapObject) ACORDOSRET.getProperty(y);

                    String CERRO        = reg.getPropertyAsString("CERRO");
                    String CMSGERRO     = reg.getPropertyAsString("CMSGERRO");


                    params.putString("CERRO", "MMM");

                    params.putString("CMSGERRO", CMSGERRO);

                    sendmsg(params);

                    if (CERRO.equals("000")) { //Gravo Nro Protheus


                        String CODMOBILE    = reg.getPropertyAsString("CODMOBILE").trim();
                        String NUM          = reg.getPropertyAsString("NUM");
                        String STATUS       = reg.getPropertyAsString("STATUS");
                        String HISTLIB      = reg.getPropertyAsString("HISTLIB");

                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setNUM(NUM);
                            pre.setStatusByTOTVS(STATUS);
                            pre.setHISTLIB(HISTLIB);
                            pre.setMENSAGEM("");
                            dao.Update(pre);

                        }

                        if ( (pre != null) && (STATUS.equals("L")) ){

                            //Atualiza Acordos Do Protheus
                            setCODIGO(NUM);
                            getAcordo();

                        }

                        dao.close();


                    }


                    if (CERRO.equals("001")) {   //001 - Acordo Mobile Inexistente

                        String CODMOBILE    = reg.getPropertyAsString("CODMOBILE").trim();

                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setMENSAGEM(CMSGERRO);

                            pre.setSTATUS("8");

                            dao.Update(pre);

                        }

                        dao.close();

                        params.putString("CERRO", "MMM");

                        params.putString("CMSGERRO",cerro+"-"+cmsgerro);

                        sendmsg(params);

                        pausa(2000);


                    }


                }
            } else {


                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO",cerro+"-"+cmsgerro);

                sendmsg(params);

                pausa(2000);
            }

        }
        catch (Exception e) {

            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", e.getMessage());

            sendmsg(params);

            pausa(2000);

        }


        params.putString("CERRO", "FEC");

        params.putString("CMSGERRO", "");

        sendmsg(params);

        pausa(2000);


    }


    public void getAcordo() throws Exception {

        try {

            String SOAP_ACTION = "GETACORDOS";

            String METHOD_NAME = "GETACORDOS";

            if (!verificaConexao()) {

                PROCESSANDO = false;

            }

            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            request.addProperty("CCODACO", CODIGO);

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = false;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            androidHttpTransport.call(SOAP_ACTION, envelope);

            //Log.i("DUMP", androidHttpTransport.requestDump);

            //Log.i("DUMP", androidHttpTransport.responseDump);

            SoapObject results = (SoapObject) envelope.bodyIn;

            spRetorno          = (SoapObject) envelope.getResponse();

            String cerro       = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro    = this.spRetorno.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")) {

                SoapObject wsacordototvs = (SoapObject) this.spRetorno.getProperty("ACORDOSTOTVS");

                try {

                    Acordo menAcordo = new Acordo();
                    Acordo webAcordo = new Acordo(
                            wsacordototvs.getPropertyAsString("CODIGO").trim(),
                            wsacordototvs.getPropertyAsString("DATA").trim(),
                            wsacordototvs.getPropertyAsString("DATAINICIAL").trim(),
                            wsacordototvs.getPropertyAsString("DATAFINAL").trim(),
                            wsacordototvs.getPropertyAsString("DATAPAGTO").trim(),
                            wsacordototvs.getPropertyAsString("TIPOPAGAMENTO").trim(),
                            wsacordototvs.getPropertyAsString("CLIENTE").trim(),
                            wsacordototvs.getPropertyAsString("LOJA").trim(),
                            wsacordototvs.getPropertyAsString("RAZAO").trim(),
                            wsacordototvs.getPropertyAsString("CNPJ").trim(),
                            wsacordototvs.getPropertyAsString("IE").trim(),
                            wsacordototvs.getPropertyAsString("CODVERBA").trim(),
                            wsacordototvs.getPropertyAsString("DESVERBA").trim(),
                            wsacordototvs.getPropertyAsString("TIPO").trim(),
                            wsacordototvs.getPropertyAsString("DESCTIPO").trim(),
                            wsacordototvs.getPropertyAsString("STATUS").trim(),
                            wsacordototvs.getPropertyAsString("DESCRICAO").trim(),
                            wsacordototvs.getPropertyAsString("OBS").trim(),
                            wsacordototvs.getPropertyAsString("CODMOBILE").trim(),
                            wsacordototvs.getPropertyAsString("CHAVFIN").trim(),
                            wsacordototvs.getPropertyAsString("HISTLIB").trim(),
                            Float.valueOf(wsacordototvs.getPropertyAsString("SALDO").trim()),
                            Float.valueOf(wsacordototvs.getPropertyAsString("VALOR").trim()),
                            wsacordototvs.getPropertyAsString("REDE").trim(),
                            wsacordototvs.getPropertyAsString("DESCREDE").trim()
                    );

                    AcordoDAO dao = new AcordoDAO();

                    dao.open();

                    menAcordo = dao.seek(new String[] {webAcordo.getCODIGO()});

                    if (menAcordo == null){

                        dao.insert(webAcordo);


                    } else {

                        dao.Update(webAcordo);

                    }

                    dao.close();

                } catch (Exception e){

                    Log.i(LOG,e.getMessage());

                }


            }
        }

        catch(Exception e)

        {

            params.putString("CERRO", "FEC");

            params.putString("CMSGERRO", e.getMessage());

            sendmsg(params);

            pausa(2000);

        }

    }

    public void transmitir_02() throws Exception {

        String SOAP_ACTION = "SEEKSALESORDERMB";

        String METHOD_NAME = "SEEKSALESORDERMB";


        if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND || tipoProcesso == PROCESSO_BACKEND_PEDIDO){

            NOTIFICATION_ID   = HelpInformation.NotificacaoRefreshPedido;

            NOTIFICATION_TEXT = "Pedido: "+PEDIDO+" Mudou O Status.";

        } else {

            NOTIFICATION_ID   = HelpInformation.NotificacaoRefreshPedidos;

            NOTIFICATION_TEXT = "Atualização Dos Pedidos Terminada Com Sucesso !!";



        }

        List<PedidoCabMb> lsLista = new ArrayList<>();

        PedidoCabMbDAO dao = new PedidoCabMbDAO();


        params.putString("CERRO", "MMM");

        params.putString("CMSGERRO", "Buscando Pedidos Para Atualizar !!");

        sendmsg(params);

        pausa(2000);



        //Busca retorno dos pedidos
        try

        {

            dao.open();

            lsLista = dao.getPedidosByStatus("4");

            dao.close();

            if (lsLista.size() == 0) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Nenhum Pedido Para Atualizar !");

                sendmsg(params);

                pausa(2000);

                params.putString("CERRO", "FEC");

                if (handler != null){

                    params.putString("CMSGERRO", "");

                } else {


                    params.putString("CMSGERRO", "Sincronização Terminada !!!");

                }



                sendmsg(params);

                return;

            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                PROCESSANDO = false;

            }


            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            SoapObject soapSALESORDERSMBCAB = new SoapObject("", "SALESORDERSMB");

            SoapObject soapSALESORDERSMBDET = new SoapObject("", "SALESORDERSMB");

            for (PedidoCabMb obj : lsLista) {


                soapSALESORDERSMBDET.addProperty("SALESORDERMB", new SALESORDERMB(obj.getNRO(), "", "", ""));

            }

            soapSALESORDERSMBCAB.addSoapObject(soapSALESORDERSMBDET);

            request.addSoapObject(soapSALESORDERSMBCAB);

            envelope.addMapping(METHOD_NAME, "SALESORDERMB", new SALESORDERMB().getClass());

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            androidHttpTransport.call(SOAP_ACTION, envelope);

            Log.i("DUMP" ,androidHttpTransport.requestDump);

            Log.i("DUMP" ,androidHttpTransport.responseDump);

            SoapObject results = (SoapObject) envelope.bodyIn;

            spRetorno = (SoapObject) envelope.getResponse();

            String cerro    = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

            SoapObject salesordersmb = (SoapObject) this.spRetorno.getProperty("SALESORDERSMB");

            if (cerro.equals("000")) {

                for (int x = 0; x < salesordersmb.getPropertyCount(); x++) {

                    SoapObject registro = (SoapObject) salesordersmb.getProperty(x);

                    String NRO = registro.getPropertyAsString("NRO").trim();

                    String CERRO = registro.getPropertyAsString("CERRO");

                    String CMSGERRO = registro.getPropertyAsString("CMSGERRO");

                    String CPROTHEUS = registro.getPropertyAsString("CPROTHEUS");

                    String CPROTHEUSB = registro.getPropertyAsString("CPROTHEUSB");

                    dao.open();

                    PedidoCabMb cab = dao.seek(new String[]{NRO});

                    dao.close();

                    if (CERRO.equals("000")) {

                        if (cab != null) {

                            if (CPROTHEUS.trim().isEmpty() && CPROTHEUSB.trim().isEmpty()){

                                params.putString("CERRO", "MMM");

                                params.putString("CMSGERRO", "Pedido: " + NRO + " Ainda Não Atualizado !");

                                sendmsg(params);

                                pausa(1000);

                            } else {

                                dao.open();

                                Calendar c = Calendar.getInstance();

                                SimpleDateFormat format_DATA = new SimpleDateFormat("dd/MM/yy");

                                SimpleDateFormat format_HORA = new SimpleDateFormat("HH:mm");

                                cab.setDTTRANS(format_DATA.format(c.getTime()));

                                cab.setHOTRANS(format_HORA.format(c.getTime()));

                                cab.setSTATUS("5");

                                cab.setCPROTHEUS(CPROTHEUS);

                                cab.setCPROTHEUSB(CPROTHEUSB);

                                cab.setMENSAGEM(CMSGERRO);

                                dao.Update(cab);

                                dao.close();

                                if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND || tipoProcesso == PROCESSO_BACKEND_PEDIDO){

                                    if (salesordersmb.getPropertyCount() == 1) {

                                        NOTIFICATION_ID   = HelpInformation.NotificacaoRefreshPedido;
                                        NOTIFICATION_TEXT = "Pedido " + cab.getNRO() + " => Pedido Protheus  " + CPROTHEUS;
                                        NOTCODIGO  =cab.getCODIGOFAT();
                                        NOTLOJA    =cab.getLOJAFAT();

                                    }
                                }
                                params.putString("CERRO", "MMM");

                                params.putString("CMSGERRO", "Pedido: " + NRO + " Transmitido Com Sucesso");

                                sendmsg(params);

                                pausa(1000);
                            }

                        }
                    }


                    if (CERRO.equals("002")) {   //002 - Pedido Mobile já existente!

                        try {

                            dao.open();

                            if (cab != null) {

                                if (!CPROTHEUS.trim().isEmpty()) {

                                    cab.setSTATUS("5");

                                    cab.setCPROTHEUS(CPROTHEUS);

                                    cab.setCPROTHEUS(CPROTHEUSB);

                                    cab.setMENSAGEM(CMSGERRO);

                                    dao.Update(cab);

                                } else {

                                    cab.setSTATUS("4");

                                    cab.setCPROTHEUS("");

                                    cab.setCPROTHEUSB("");

                                    cab.setMENSAGEM(CMSGERRO);

                                    dao.Update(cab);

                                    dao.Update(cab);

                                }

                            }

                            dao.close();

                        } catch (Exception e) {

                            params.putString("CERRO", "MMM");

                            params.putString("CMSGERRO", e.getMessage());

                            sendmsg(params);

                            pausa(3000);

                        }

                    }


//                    003 - Falha ao gravar pedido mobile!
//                    007 - Cliente não encontrado
//                    008 - Cliente não pertence ao vendedor.
//                    009 - Cliente bloqueado.
//                    010 - Venda não pode ser feita para cliente entrega.
//                    011 - Cliente não liberado.
//                    012 - Data Entrega inferior ao limite cadastrado no cliente (X dias após a Data de Emissão).
//                    013 - Data Entrega inferior a Data de Emissão.
//                    031 - Preço do Pedido Abaixo do Minimo
//                    032 - Valor do Pedido Abaixo do MInimo
//                    033 - Peso e Valor do Pedido Abaixo do Minimo
                    if (CERRO.equals("003") || CERRO.equals("007") || CERRO.equals("008") || CERRO.equals("009") ||
                            CERRO.equals("010") || CERRO.equals("011") || CERRO.equals("012") || CERRO.equals("013") ||
                            CERRO.equals("031") || CERRO.equals("032") || CERRO.equals("033")) {

                        try {

                            dao.open();

                            if (cab != null) {

                                cab.setSTATUS("9");

                                cab.setCPROTHEUS("");

                                cab.setCPROTHEUSB("");

                                cab.setMENSAGEM(CERRO + "-" + CMSGERRO);

                                dao.Update(cab);

                            } else {

                                Log.i(LOG,"Nao achou cabec !!!");

                            }

                            dao.close();

                        } catch (Exception e) {

                            params.putString("CERRO", "MMM");

                            params.putString("CMSGERRO", e.getMessage());

                            sendmsg(params);

                            pausa(3000);

                        }

                    }


                    if (CERRO.equals("999")) {

                        dao.open();

                        cab.setSTATUS("6");

                        cab.setMENSAGEM(cerro + "-" + cmsgerro);

                        dao.open();

                        dao.Update(cab);

                        dao.close();

                        SoapObject errositens = (SoapObject) salesordersmb.getProperty("ERROSITENS");

                        PedidoDetMbDAO detdao = new PedidoDetMbDAO();

                        detdao.open();

                        for (int y = 0; y < errositens.getPropertyCount(); y++) {

                            SoapObject reg = (SoapObject) errositens.getProperty(y);

                            if (!reg.getProperty("CERRO").toString().equals("000")) {

                                String ccodproduto_ = reg.getPropertyAsString("CCODPRODUTO");
                                String cerro_ = reg.getPropertyAsString("CERRO");
                                String cmsgerro_ = reg.getPropertyAsString("CMSGERRO");

                                PedidoDetMb det = detdao.seekByProduto(new String[]{NRO, ccodproduto_});

                                if (det != null) {

                                    det.setSTATUS("9");

                                    det.setMENSAGEM(cerro + "-" + cmsgerro);

                                    detdao.Update(det);

                                }

                            }

                        }

                        detdao.close();

                        params.putString("CERRO", "MMM");

                        params.putString("CMSGERRO", cmsgerro);

                        sendmsg(params);

                        pausa(3000);
                    }


                    //iNSERE OU ATUALIZA OS PEDIDOS TOTVS

                    if (!CPROTHEUS.trim().isEmpty()) {

                        try {

                            SoapObject PVTOTVS = (SoapObject) registro.getProperty("PVTOTVS");

                            SoapObject PVCABEC = (SoapObject) PVTOTVS.getProperty("PVCABEC");

                            SoapObject PVITENS = (SoapObject) PVTOTVS.getProperty("PVITENS");

                            InsereAtualizaPedido(PVCABEC, PVITENS);

                        } catch (Exception e){

                            //ignora erro

                        }
                    }

                    if (!CPROTHEUSB.trim().isEmpty()) {

                        try {


                            SoapObject PBTOTVS = (SoapObject) registro.getProperty("PBTOTVS");

                            SoapObject PVCABECB = (SoapObject) PBTOTVS.getProperty("PVCABEC");

                            SoapObject PVITENSB = (SoapObject) PBTOTVS.getProperty("PVITENS");

                            InsereAtualizaPedido(PVCABECB, PVITENSB);

                        }catch (Exception e){

                            //ignora erro
                        }

                    }


                }



            }else{

                //001 - Nome do usuário ou senha inválida!
                //004 - O Pedido poderá ser transmitido somente até <DD/MM/AA - 99:99:99>
                //005 - Recebimento de Pedido bloqueado manualmente, entrar em contato com o Apoio de Vendas.

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", cmsgerro);

                sendmsg(params);

                pausa(2000);


            }


        }
        catch(Exception e)

        {

            params.putString("CERRO", "FEC");

            params.putString("CMSGERRO", e.getMessage());

            sendmsg(params);

            pausa(2000);

        }

        params.putString("CERRO", "FEC");

        if (handler != null){

            params.putString("CMSGERRO", "");

        } else {

            params.putString("CMSGERRO", NOTIFICATION_TEXT);

        }

        sendmsg(params);

    }

    private void InsereAtualizaPedido(SoapObject pvcabec, SoapObject pvitens) throws ExceptionSavePedido{

        PedCabTvs         cabec    = new PedCabTvs();
        List<PedDetTvs>   detalhe  = new ArrayList<>();

        try {

            cabec.setFILIAL(pvcabec.getPropertyAsString("FILIAL"));
            cabec.setPEDIDO(pvcabec.getPropertyAsString("PEDIDO"));
            cabec.setPEDIDOMOBILE(pvcabec.getPropertyAsString("PEDIDOMOBILE"));
            cabec.setCLIENTE(pvcabec.getPropertyAsString("CLIENTE"));
            cabec.setLOJA(pvcabec.getPropertyAsString("LOJA"));
            cabec.setRAZAO(pvcabec.getPropertyAsString("RAZAO"));
            cabec.setSITUACAO(pvcabec.getPropertyAsString("SITUACAO"));
            cabec.setTOTALPEDIDO(Float.valueOf(pvcabec.getPropertyAsString("TOTALPEDIDO")));
            cabec.setTOTALFATURADO(Float.valueOf(pvcabec.getPropertyAsString("TOTALFATURADO")));
            cabec.setEMISSAO(pvcabec.getPropertyAsString("EMISSAO"));
            cabec.setENTREGA(pvcabec.getPropertyAsString("ENTREGA"));
            cabec.setORIGEM(pvcabec.getPropertyAsString("ORIGEM"));
            cabec.setTIPO(pvcabec.getPropertyAsString("TIPO"));
            cabec.setCONDPAGTO(pvcabec.getPropertyAsString("CONDPAGTO"));
            cabec.setCPDESCRICAO(pvcabec.getPropertyAsString("CPDESCRICAO"));
            cabec.setTIPOFRETE(pvcabec.getPropertyAsString("TIPOFRETE"));
            cabec.setDESCRICAO(pvcabec.getPropertyAsString("DESCRICAO"));
            cabec.setFORMAPAGTO(pvcabec.getPropertyAsString("FORMAPAGTO"));
            cabec.setVEND(pvcabec.getPropertyAsString("VEND"));
            cabec.setPEDIDOCLIENTE(pvcabec.getPropertyAsString("PEDIDOCLIENTE"));
            cabec.setOBSNOTA(pvcabec.getPropertyAsString("OBSNOTA"));
            cabec.setOBSPED(pvcabec.getPropertyAsString("OBSPED"));
            cabec.setNOTAFILIAL(pvcabec.getPropertyAsString("NOTAFILIAL"));
            cabec.setNOTAFISCAL(pvcabec.getPropertyAsString("NOTAFISCAL"));
            cabec.setNOTASERIE(pvcabec.getPropertyAsString("NOTASERIE"));
            cabec.setCODIGOENTREGA(pvcabec.getPropertyAsString("CODIGOENTREGA"));
            cabec.setLOJAENTREGA(pvcabec.getPropertyAsString("LOJAENTREGA"));
            cabec.setDESCFRETE(Float.valueOf(pvcabec.getPropertyAsString("DESCFRETE")));
            cabec.setFDPREVISTOS(Float.valueOf(pvcabec.getPropertyAsString("FDPREVISTOS")));
            cabec.setQTDENTREGA(Integer.valueOf(pvcabec.getPropertyAsString("QTDENTREGA")));

        } catch (Exception e){

            new ExceptionSavePedido("ERRO NO CABECALHO !!");

        }


        try {

            for( int x = 0; x < pvitens.getPropertyCount() ; x++){

                SoapObject det = (SoapObject) pvitens.getProperty(x);

                PedDetTvs de = new PedDetTvs();
                de.setFILIAL(det.getPropertyAsString("FILIAL"));
                de.setPEDIDO(det.getPropertyAsString("PEDIDO"));
                de.setITEM(det.getPropertyAsString("ITEM"));
                de.setPRODUTO(det.getPropertyAsString("PRODUTO"));
                de.setDESCRICAO(det.getPropertyAsString("DESCRICAO"));
                de.setUM(det.getPropertyAsString("UM"));
                de.setQTD(Float.valueOf(det.getPropertyAsString("QTD")));
                de.setDESCONTO(Float.valueOf(det.getPropertyAsString("DESCONTO")));
                de.setPRCVEN(Float.valueOf(det.getPropertyAsString("PRCVEN")));
                de.setDESCVER(Float.valueOf(det.getPropertyAsString("DESCVER")));
                de.setCODVERBA(det.getPropertyAsString("CODVERBA"));
                de.setDESCRICAOVERBA(det.getPropertyAsString("DESCRICAOVERBA"));
                de.setTOTAL(Float.valueOf(det.getPropertyAsString("TOTAL")));
                de.setACORDO(det.getPropertyAsString("ACORDO"));
                de.setPDFILIAL(det.getPropertyAsString("PDFILIAL"));
                de.setPDNUMERO(det.getPropertyAsString("PDNUMERO"));
                de.setSIMULADOR(det.getPropertyAsString("SIMULADOR"));
                de.setCOTA(det.getPropertyAsString("COTA"));

                detalhe.add(de);
            }



        } catch (Exception e){

            new ExceptionSavePedido("ERRO NO DETALHE !!");

        }

        try {

            PedCabTvsDAO dao = new PedCabTvsDAO();

            dao.open();

            dao.SavePedido(cabec,detalhe);

            dao.close();



        } catch (Exception E) {

            new ExceptionSavePedido("FALHA NA GRAVAÇÃO DO PEDIDO TOTVS !!!");

        }

    }

    public void transmitir_salesorderByArray() throws Exception{

        String SOAP_ACTION = "PUTSALEORDERBYARRAY";

        String METHOD_NAME = "PUTSALEORDERBYARRAY";

        List<Pedidos> lsLista = new ArrayList<>();

        PedidoCabMbDAO dao        = new PedidoCabMbDAO();

        //BUSCA TODOS OS PEDIDOS
        try {


            params.putString("CERRO", "---");

            params.putString("CMSGERRO", "Buscando os pedidos...");

            sendmsg(params);

            pausa(1000);

            dao.open();

            if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND){

                lsLista = dao.getPedidosToSendByPedidoAutomatico(PEDIDO,"0");

            } else {
                if (CODIGO.isEmpty()) {

                    lsLista = dao.getPedidosToSend("3");

                } else {

                    lsLista = dao.getPedidosToSendByCliente(CODIGO, LOJA, "3");
                }
            }
            dao.close();

            if (lsLista.size() == 0) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Nenhum Pedido Para Trasmitir !");

                sendmsg(params);

                pausa(1000);

                transmitir_02();

                return;

            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                if (this.tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND){

                    for (Pedidos pedido : lsLista) {

                        dao.open();

                        PedidoCabMb ped = dao.seek(new String[]{PEDIDO});

                        if (ped != null) {

                            ped.setSTATUS("3");

                            dao.Update(ped);
                        }

                        dao.close();

                    }

                    Log.i("TRANSMISSAO","Falha Na Transmissão. Favor Transmitir Manualmente.");

                    params.putString("CERRO", "FEC");

                    params.putString("CMSGERRO", "Falha Na Transmissão. Favor Transmitir Manualmente.");

                    sendmsg(params);

                    pausa(2000);

                } else {

                    params.putString("CERRO", "FEC");

                    params.putString("CMSGERRO", "Falha Na Transmissão...");

                    sendmsg(params);

                    pausa(2000);

                }

                PROCESSANDO = false;

                return;
            }


            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            SoapObject soapPedidos = new SoapObject("", "WSPEDIDOS");

            int x;

            x = 0;

            SoapObject soapPEDIDOSMOBILE = new SoapObject("", "PEDIDOSMOBILE");

            SoapObject soapWSPEDIDOS = new SoapObject("", "WSPEDIDOS");

            for (Pedidos pedido : lsLista) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Pedido: " + pedido.getCab().getNRO() + "-" + String.valueOf(x++));

                sendmsg(params);


                SoapObject soapWSPEDMOB = new SoapObject("", "WSPEDMOB");

                soapWSPEDMOB.addProperty("PVCABEC", pedido.getCab());

                SoapObject soapPVITENS = new SoapObject("", "PVITENS");

                for (PedidoDetMb item : pedido.getLsDetalhe()){

                    soapPVITENS.addProperty("WSPEDIDODETMB", item);

                }

                soapWSPEDMOB.addSoapObject(soapPVITENS);


                soapWSPEDIDOS.addSoapObject(soapWSPEDMOB);

            }

            soapPEDIDOSMOBILE.addSoapObject(soapWSPEDIDOS);

            request.addSoapObject(soapPEDIDOSMOBILE);

            envelope.addMapping(METHOD_NAME, "PedCabMb", new PedidoCabMb().getClass());

            envelope.addMapping(METHOD_NAME, "PedDetMb", new PedidoDetMb().getClass());

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", "Transmitindo.....\nEnviando os Pedidos para a Broto...");

            sendmsg(params);

            androidHttpTransport.call(SOAP_ACTION, envelope);

            Log.i("DUMP" ,androidHttpTransport.requestDump);
            Log.i("DUMP" ,androidHttpTransport.responseDump);


            spRetorno = (SoapObject) envelope.getResponse();

            String cerro = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                SoapObject RETPEDIDOSMB = (SoapObject) this.spRetorno.getProperty("RETPEDIDOSMB");

                for(int y = 0; y < RETPEDIDOSMB.getPropertyCount(); y++ ) {

                    SoapObject reg = (SoapObject) RETPEDIDOSMB.getProperty(y);

                    String CERRO            = reg.getPropertyAsString("CERRO");
                    String CMSGERRO         = reg.getPropertyAsString("CMSGERRO");
                    String NRO              = reg.getPropertyAsString("NRO").trim();
                    String CPROTHEUS        = reg.getPropertyAsString("CPROTHEUS");
                    String CPROTHEUSB       = reg.getPropertyAsString("CPROTHEUSB");
                    SoapObject ERROSITENS   = (SoapObject) reg.getProperty("ERROSITENS");

                    params.putString("CERRO", "MMM");

                    params.putString("CMSGERRO", CMSGERRO);

                    sendmsg(params);

                    Log.i(LOG, "CERRO = " + CERRO + " NRO " + NRO + " - " + String.valueOf(y));

                    if (CERRO.equals("000")) {

                        try {

                            dao.open();

                            PedidoCabMb cab = dao.seek(new String[]{NRO});

                            if (cab != null) {

                                cab.setSTATUS("4");

                                cab.setMENSAGEM(CMSGERRO);

                                dao.Update(cab);

                                if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND){

                                    NOTIFICATION_TEXT = NOTIFICATION_TEXT + " EM PROCESSAMENTO !";

                                }

                            }

                            dao.close();

                        } catch (Exception e) {

                            params.putString("CERRO", "MMM");

                            params.putString("CMSGERRO", e.getMessage());

                            sendmsg(params);

                            pausa(3000);

                        }

                    }


                    if (CERRO.equals("002")) {   //002 - Pedido Mobile já existente!

                        try {

                            dao.open();

                            PedidoCabMb cab = dao.seek(new String[]{NRO});

                            if (cab != null) {

                                if (!CPROTHEUS.trim().isEmpty()) {

                                    cab.setSTATUS("5");

                                    cab.setCPROTHEUS(CPROTHEUS);

                                    cab.setCPROTHEUSB(CPROTHEUSB);

                                    cab.setMENSAGEM(CMSGERRO);

                                    dao.Update(cab);

                                } else {

                                    cab.setSTATUS("4");

                                    cab.setCPROTHEUS("");

                                    cab.setCPROTHEUSB("");

                                    cab.setMENSAGEM(CMSGERRO);

                                    dao.Update(cab);


                                }

                            }

                            dao.close();

                        } catch (Exception e) {

                            params.putString("CERRO", "MMM");

                            params.putString("CMSGERRO", e.getMessage());

                            sendmsg(params);

                            pausa(3000);

                        }

                    }


//                    003 - Falha ao gravar pedido mobile!
//                    007 - Cliente não encontrado
//                    008 - Cliente não pertence ao vendedor.
//                    009 - Cliente bloqueado.
//                    010 - Venda não pode ser feita para cliente entrega.
//                    011 - Cliente não liberado.
//                    012 - Data Entrega inferior ao limite cadastrado no cliente (X dias após a Data de Emissão).
//                    013 - Data Entrega inferior a Data de Emissão.
//                    031 - Peso do Pedido Abaixo do Minimo
//                    032 - Valor do Pedido Abaixo do Minimo
//                    033 - Peso e Valor do Pedido Abaixo Do Minimo
                    if ("003@007@008@009@010@011@012@013@031@032@033".contains(CERRO)) {

                        try {

                            dao.open();

                            PedidoCabMb cab = dao.seek(new String[]{NRO});

                            if (cab != null) {

                                cab.setSTATUS("6");

                                cab.setCPROTHEUS("");

                                cab.setCPROTHEUSB("");

                                cab.setMENSAGEM(CERRO + "-" + CMSGERRO);

                                dao.Update(cab);

                            } else {

                                Log.i(LOG,"NAO ACHEI CABEC !!!"+NRO);

                            }

                            dao.close();

                        } catch (Exception e) {

                            params.putString("CERRO", "MMM");

                            params.putString("CMSGERRO", e.getMessage());

                            sendmsg(params);

                            pausa(3000);

                        }

                    }

                    if (CERRO.equals("999")) {

                        dao.open();

                        PedidoCabMb cab = dao.seek(new String[]{NRO});

                        if (cab != null) {

                            PedidoDetMbDAO detdao = new PedidoDetMbDAO();

                            cab.setSTATUS("6");

                            cab.setMENSAGEM("Erro Nos Produtos !!!");

                            cab.setOBSPED("Erro Nos Produtos !!!");

                            dao.Update(cab);

                            detdao.open();

                            for (int z = 0; z < ERROSITENS.getPropertyCount(); z++) {

                                SoapObject reg_ = (SoapObject) ERROSITENS.getProperty(z);

                                if (!reg_.getProperty("CERRO").toString().equals("000")) {

                                    String ccodproduto_ = reg_.getPropertyAsString("CCODPRODUTO");

                                    String cerro_ = reg_.getPropertyAsString("CERRO");

                                    String cmsgerro_ = reg_.getPropertyAsString("CMSGERRO");

                                    PedidoDetMb det = detdao.seekByProduto(new String[]{NRO, ccodproduto_});

                                    if (det != null) {

                                        det.setSTATUS("9");

                                        det.setMENSAGEM(cerro_ + "-" + cmsgerro_);

                                        detdao.Update(det);

                                    }

                                }

                                detdao.close();
                            }

                            dao.close();

                        }

                    }
                }

            } else {

                //001 - Nome do usuário ou senha inválida!
                //004 - O Pedido poderá ser transmitido somente até <DD/MM/AA - 99:99:99>
                //005 - Recebimento de Pedido bloqueado manualmente, entrar em contato com o Apoio de Vendas.

                if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND) {

                    dao.open();

                    PedidoCabMb ped = dao.seek(new String[]{PEDIDO});

                    if (ped != null) {

                        ped.setSTATUS("3");

                        dao.Update(ped);
                    }

                    dao.close();

                    params.putString("CERRO", "FEC");

                    params.putString("CMSGERRO", cerro + "-" + cmsgerro);

                    sendmsg(params);

                    pausa(2000);

                    Log.i("TRANSMISSAO","Falha Na Transmissão. Favor Transmitir Manualmente.");


                }
                else{
                    params.putString("CMSGERRO", cerro + "-" + cmsgerro);

                    sendmsg(params);

                    pausa(2000);

                }
            }

        }
        catch (Exception e) {

            if (tipoProcesso == PROCESSO_ATUALIZACARGABACKGROUND) {

                dao.open();

                PedidoCabMb ped = dao.seek(new String[]{PEDIDO});

                if (ped != null) {

                    ped.setSTATUS("3");

                    dao.Update(ped);
                }

                dao.close();

                params.putString("CERRO", "FEC");

                params.putString("CMSGERRO", "Falha Na Transm. Ped. "+PEDIDO+". Favor Transmitir Manualmente.");

                sendmsg(params);

                pausa(2000);

                Log.i("TRANSMISSAO","Falha Na Transm. Ped. "+PEDIDO+". Favor Transmitir Manualmente.");

            } else {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", e.getMessage());

                sendmsg(params);

                pausa(2000);

            }
        }

        transmitir_02();

    }

    public void transmitir_preacordoByArray() throws Exception{

        String SOAP_ACTION = "PUTACORDOS";

        String METHOD_NAME = "PUTACORDOS";

        List<PreAcordo> lsLista = new ArrayList<>();

        PreAcordoDAO dao  = new PreAcordoDAO();

        //BUSCA pre-acordos
        try {

            params.putString("CERRO", "---");

            params.putString("CMSGERRO", "Buscando os pré-acordos...Envio");

            sendmsg(params);

            pausa(1000);

            dao.open();

            lsLista = dao.getAllToSend("1");

            dao.close();

            if (lsLista.size() == 0) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Nenhum Novo Pré-Acordo Para Enviar !");

                sendmsg(params);

                pausa(2000);

                refresh_acordo_02();


                return;

            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                PROCESSANDO = false;

            }


            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            SoapObject soapAcordos0 = new SoapObject("", "ACORDOS");

            int x;

            x = 0;

            SoapObject soapAcordos1 = new SoapObject("", "ACORDOS");

            for (PreAcordo preacordo : lsLista) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Pré-Acordo:  " + preacordo.getCODMOBILE() + " " + String.valueOf(x++));

                sendmsg(params);

                soapAcordos1.addProperty("WSACORDOX", preacordo);

            }

            soapAcordos0.addSoapObject(soapAcordos1);

            request.addSoapObject(soapAcordos0);

            envelope.addMapping(METHOD_NAME, "PreAcordo", new PreAcordo().getClass());

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", "Transmitindo.....\nEnviando os Pré-Acordos para a Broto...");

            sendmsg(params);

            androidHttpTransport.call(SOAP_ACTION, envelope);

            Log.i("DUMP" ,androidHttpTransport.requestDump);
            Log.i("DUMP" ,androidHttpTransport.responseDump);

            spRetorno = (SoapObject) envelope.getResponse();

            String cerro = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                SoapObject ACORDOSRET = (SoapObject) this.spRetorno.getProperty("ACORDOSRET");

                for(int y = 0; y < ACORDOSRET.getPropertyCount(); y++ ) {

                    SoapObject reg = (SoapObject) ACORDOSRET.getProperty(y);

                    String CERRO     = reg.getPropertyAsString("CERRO");
                    String CMSGERRO  = reg.getPropertyAsString("CMSGERRO");
                    String CODMOBILE = reg.getPropertyAsString("CODMOBILE").trim();
                    String NUM       = reg.getPropertyAsString("NUM");

                    params.putString("CERRO", "MMM");

                    params.putString("CMSGERRO", CMSGERRO);

                    sendmsg(params);

                    if (CERRO.equals("000")) { //Gravo Nro Protheus

                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setNUM(NUM);
                            pre.setSTATUS("2");
                            dao.Update(pre);

                        }

                        dao.close();

                    }


                    if (CERRO.equals("002")) {   //002 - Pré-Acordo Já Existente.

                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setNUM(NUM);
                            pre.setMENSAGEM(CMSGERRO);
                            pre.setSTATUS("2");
                            dao.Update(pre);

                        }

                        dao.close();

                    }


                    if (CERRO.equals("003")) {//Vigência Final é menor que a Vigência Inicial.


                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setMENSAGEM(CMSGERRO);

                            dao.Update(pre);

                        }

                        dao.close();

                        params.putString("CERRO", "MMM");

                        params.putString("CMSGERRO",cerro+"-"+cmsgerro);

                        sendmsg(params);

                        pausa(2000);


                    }

                    if (CERRO.equals("004")) {//Data da emissão não pode ser superior a Vigência Final.

                        dao.open();

                        PreAcordo pre = dao.seek(new String[]{CODMOBILE});

                        if (pre != null) {

                            pre.setMENSAGEM(CMSGERRO);

                            dao.Update(pre);

                        }

                        dao.close();

                        params.putString("CERRO", "MMM");

                        params.putString("CMSGERRO",cerro+"-"+cmsgerro);

                        sendmsg(params);

                        pausa(2000);


                    }


                }
            } else {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO",cerro+"-"+cmsgerro);

                sendmsg(params);

                pausa(2000);
            }

        }
        catch (Exception e) {

            params.putString("CERRO", "MMM");

            params.putString("CMSGERRO", e.getMessage());

            sendmsg(params);

            pausa(2000);

        }


        refresh_acordo_02();

    }

    public void transmitir_agendamentoByArray() throws Exception{

        String SOAP_ACTION = "PUTAGENDAMENTOS";

        String METHOD_NAME = "PUTAGENDAMENTOS";

        if (this.tipoProcesso == PROCESSO_AGENDAMENTO_ATRASADO || this.tipoProcesso == PROCESSO_AGENDAMENTO_JUSTIFICATICAS) {

            params.putString("CERRO", "---");

            params.putString("CMSGERRO", "Buscando os Agendamentos...");

            sendmsg(params);

            pausa(1000);

        }

        List<Agendamento> lsLista = new ArrayList<>();

        AgendamentoDAO dao  = new AgendamentoDAO();

        //BUSCA agendamentos
        try {

            dao.open();

            if (this.tipoProcesso == PROCESSO_AGENDAMENTO_JUSTIFICATICAS){

                lsLista = dao.getAllAvulsoToSinc();

            } else {

                lsLista = dao.getAllByByStatus(CODIGO,LOJA,"T");

            }


            dao.close();

            if (lsLista.size() == 0) {

                sendNotificationAgendamento("AGENDAMENTOS","Agendamentos Sincronizados !","");

                params.putString("CERRO", "FEC");

                params.putString("CMSGERRO", "Fim Do Processamento !!!");

                sendmsg(params);

                pausa(1000);


                return;

            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                PROCESSANDO = false;

            }

            if (this.tipoProcesso == PROCESSO_AGENDAMENTO_ATRASADO || this.tipoProcesso == PROCESSO_AGENDAMENTO_JUSTIFICATICAS) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Processando...");

                sendmsg(params);

                pausa(1000);

            }

            SoapObject request = new SoapObject(config.getNSFull(), METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

            request.addProperty("CCODUSER", App.user.getCOD().trim());

            request.addProperty("CPASSUSER", App.user.getSENHA().trim());

            SoapObject soapAgenda0 = new SoapObject("", "AGENDAMENTOS");

            int x;

            x = 0;

            SoapObject soapAgenda1 = new SoapObject("", "AGENDAMENTOS");

            for (Agendamento agendamento : lsLista) {

                soapAgenda1.addProperty("AGENITEM", agendamento);

            }

            soapAgenda0.addSoapObject(soapAgenda1);

            request.addSoapObject(soapAgenda0);

            envelope.addMapping(METHOD_NAME, "Agendamento", new Agendamento().getClass());

            envelope.implicitTypes = true;

            envelope.setAddAdornments(false);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(config.getUrlFull());

            androidHttpTransport.debug = true;

            androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            androidHttpTransport.call(SOAP_ACTION, envelope);

            Log.i("DUMP" ,androidHttpTransport.requestDump);
            Log.i("DUMP" ,androidHttpTransport.responseDump);


            spRetorno = (SoapObject) envelope.getResponse();

            String cerro = this.spRetorno.getPropertyAsString("CERRO");

            String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

            if (cerro.equals("000")){

                SoapObject AGENDARET = (SoapObject) this.spRetorno.getProperty("AGENDAMENTOS");

                for(int y = 0; y < AGENDARET.getPropertyCount(); y++ ) {

                    SoapObject reg = (SoapObject) AGENDARET.getProperty(y);

                    String CERRO         = reg.getPropertyAsString("CERRO");
                    String CMSGERRO      = reg.getPropertyAsString("CMSGERRO");
                    String ID            = reg.getPropertyAsString("ID").trim();
                    String SITUACAO      = reg.getPropertyAsString("SITUACAO");

                    if (CERRO.equals("000")) { //Atualizo agendamento tablet

                        dao.open();

                        Agendamento age = dao.seek(new String[]{ID});

                        if (age != null) {

                            age.setSITUACAO(SITUACAO);
                            dao.Update(age);

                        }

                        dao.close();


                    }

                }


                sendNotificationAgendamento("AGENDAMENTOS","Agendamentos Enviados !","");

                NotificationAgendamento notificationAgendamento = new NotificationAgendamento("000","Agendamentos Enviados !");

                EventBus.getDefault().post(notificationAgendamento);

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", "Agendamentos Enviados !");

                sendmsg(params);

                pausa(1000);


            } else {

                sendNotificationAgendamento("AGENDAMENTOS",cmsgerro,"");

                NotificationAgendamento notificationAgendamento = new NotificationAgendamento("020",cmsgerro);

                EventBus.getDefault().post(notificationAgendamento);

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO",cmsgerro);

                sendmsg(params);

                pausa(1000);


            }

        }
        catch (Exception e) {

            sendNotificationAgendamento("AGENDAMENTOS", e.getMessage(), "");

            NotificationAgendamento notificationAgendamento = new NotificationAgendamento("020", e.getMessage());

            EventBus.getDefault().post(notificationAgendamento);

            if (this.tipoProcesso == PROCESSO_AGENDAMENTO_ATRASADO) {

                params.putString("CERRO", "MMM");

                params.putString("CMSGERRO", e.getMessage());

                sendmsg(params);

                pausa(1000);

            }

        }

        if (this.tipoProcesso == PROCESSO_AGENDAMENTO_ATRASADO || this.tipoProcesso == PROCESSO_AGENDAMENTO_JUSTIFICATICAS) {

            params.putString("CERRO", "FEC");

            params.putString("CMSGERRO", "");

            sendmsg(params);

            pausa(1000);

        }

    }

    public void VerificaConexaoAtiva() throws Exception{

        String SOAP_ACTION = "GETSTATUS";

        String METHOD_NAME = "GETSTATUS";

        List<Config> lsLista = new ArrayList<>();

        ConfigDAO dao  = new ConfigDAO();

        //BUSCA conexoes gravadas
        try {

            dao.open();

            lsLista = dao.getConexoes();

            dao.close();

            if (lsLista.size() == 0) {

                return;

            }

            PROCESSANDO = true;

            if (!verificaConexao()) {

                PROCESSANDO = false;

                return;

            }


            for (Config conf : lsLista) {

                try {

                    SoapObject request = new SoapObject(conf.getNSFull(), METHOD_NAME);

                    SoapSerializationEnvelope envelope = new SoapServEnv(SoapEnvelope.VER11);

                    request.addProperty("CCODUSER", App.user.getCOD().trim());

                    request.addProperty("CPASSUSER", App.user.getSENHA().trim());

                    envelope.implicitTypes = true;

                    envelope.setAddAdornments(false);

                    envelope.dotNet = true;

                    envelope.setOutputSoapObject(request);

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(conf.getUrlFull());

                    androidHttpTransport.debug = true;

                    androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    spRetorno = (SoapObject) envelope.getResponse();

                    String cerro = this.spRetorno.getPropertyAsString("CERRO");

                    String cmsgerro = this.spRetorno.getPropertyAsString("CMSGERRO");

                    if (cerro.equals("000")) {

                        conf.setCODIGO("000");

                        dao.open();

                        dao.Update(conf);

                        dao.close();

                        if (handlesoap != null) {

                            handlesoap.setResult(spRetorno);

                            handlesoap.processa();

                        }

                        break;

                    } else {

                    }

                } catch (Exception e){

                    Log.i(LOG,e.getMessage());

                }
            }
        }
        catch(Exception e){

            Log.i(LOG,e.getMessage());

        }

    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (    conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public void addParam(String field,String value){

        request.addProperty(field, value);

    }

    public void addParam(String field,Object value){

        request.addProperty(field, value);

    }

    public void addSoapObject(SoapObject obj){

        request.addSoapObject(obj);

    }

    public void addInfo(PropertyInfo pi){

        request.addProperty(pi);

    }

    public void addObjeto(String name, Object obj){

        envelope.addMapping(METHOD_NAME, name , obj.getClass());

    }

    public Boolean getPROCESSANDO() {
        return PROCESSANDO;
    }

    private void toast (String msg)    {


        Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();

    }

    private  void sendmsg(Bundle value){

        if (handler != null && value  != null)
        {

            Message msgObj = handler.obtainMessage();
            msgObj.setData(value);
            handler.sendMessage(msgObj);

            Log.i("MENSAGENS", "Notificação; "+NOTIFICATION_ID);
            Log.i("MENSAGENS", "ENVIOU MENSAGEM ");
            Log.i("MENSAGENS","CODIGO = "+CODIGO+" LOJA = "+ LOJA);

        } else {

            if (value != null){

                if (value.getString("CERRO").equals("FEC")) {

                    if (value.getString("CMSGERRO").isEmpty()) {


                        sendNotification("", NOTIFICATION_TEXT, "");

                        Log.i("MENSAGENS", "WEB-"+NOTIFICATION_TEXT);



                    } else {

                        sendNotification("", value.getString("CMSGERRO"), "");

                        Log.i("MENSAGENS", "WEB-"+value.getString("CMSGERRO"));
                    }

                    Log.i("MENSAGENS", "Notificação; "+NOTIFICATION_ID);

                    if (NOTIFICATION_ID == HelpInformation.NotificacaoCargaCompleta) {

                        Log.i("MENSAGENS", "WEB-NotificacaoCargaCompleta");

                        NotificationCarga notificationCarga = new NotificationCarga("000", "CARGA COMPLETA EXECUTADA !");

                        EventBus.getDefault().post(notificationCarga);

                    }


                    //eventbus
                    if (NOTIFICATION_ID == HelpInformation.NotificacaoRefreshPedido || NOTIFICATION_ID == HelpInformation.NotificacaoRefreshPedidos) {

                        Log.i("MENSAGENS", "WEB-NotificacaoRefreshPedidos ou NotificacaoRefreshPedido");

                        NotificationPedido notificationpedido = new NotificationPedido(value.getString("CERRO"), value.getString("CMSGERRO"),CODIGO,LOJA);

                        Log.i("MENSAGENS","CODIGO = "+CODIGO+" LOJA = "+ LOJA);

                        EventBus.getDefault().post(notificationpedido);

                    }

                }
            }

        }

    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getLOJA() {
        return LOJA;
    }

    public void setLOJA(String LOJA) {
        this.LOJA = LOJA;
    }

    public String getPEDIDO() {
        return PEDIDO;
    }

    public void setPEDIDO(String PEDIDO) {
        this.PEDIDO = PEDIDO;
    }

    public String getFILENAME() {return FILENAME;}

    public void setFILENAME(String FILENAME) {this.FILENAME = FILENAME;}

    public String getMV_ZBLECRG() {
        return MV_ZBLECRG;
    }

    public void setMV_ZBLECRG(String MV_ZBLECRG) {
        this.MV_ZBLECRG = MV_ZBLECRG;
    }

    public String getMV_ZBLEPED() {
        return MV_ZBLEPED;
    }

    public void setMV_ZBLEPED(String MV_ZBLEPED) {
        this.MV_ZBLEPED = MV_ZBLEPED;
    }

    public String getMV_ZDTECRG() {
        return MV_ZDTECRG;
    }

    public void setMV_ZDTECRG(String MV_ZDTECRG) {
        this.MV_ZDTECRG = MV_ZDTECRG;
    }

    public String getMV_ZDTEPED() {
        return MV_ZDTEPED;
    }

    public void setMV_ZDTEPED(String MV_ZDTEPED) {
        this.MV_ZDTEPED = MV_ZDTEPED;
    }

    public String getMV_ZHRECRG() {
        return MV_ZHRECRG;
    }

    public void setMV_ZHRECRG(String MV_ZHRECRG) {
        this.MV_ZHRECRG = MV_ZHRECRG;
    }

    public String getMV_ZHREPED() {
        return MV_ZHREPED;
    }

    public void setMV_ZHREPED(String MV_ZHREPED) {
        this.MV_ZHREPED = MV_ZHREPED;
    }

    private void sendNotification(String titulo, String message, String messageBody) {

        try {

            PendingIntent pendingIntent = PendingIntent.getActivity(App.getCustomAppContext(), 0, new Intent(), 0);

            //Take Notification Sound
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //Generate the Notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(App.getCustomAppContext())
                    .setSmallIcon(R.drawable.imglogo)
                    .setContentTitle("BROTO LEGAL")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) App.getCustomAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


        } catch (Exception e) {

            Log.i(LOG, e.getMessage());

        }
    }

    public String getCodocorrencia() {
        return codocorrencia;
    }

    public void setCodocorrencia(String codocorrencia) {
        this.codocorrencia = codocorrencia;
    }

    private void sendNotificationAgendamento(String titulo, String message, String messageBody) {

        try {

            PendingIntent pendingIntent = PendingIntent.getActivity(App.getCustomAppContext(), 0, new Intent(), 0);

            //Take Notification Sound
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //Generate the Notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(App.getCustomAppContext())
                    .setSmallIcon(R.drawable.imglogo)
                    .setContentTitle("BROTO LEGAL")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) App.getCustomAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(333333, notificationBuilder.build());


        } catch (Exception e) {

            Log.i(LOG, e.getMessage());

        }
    }


}



