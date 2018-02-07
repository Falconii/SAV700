package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ConfigDAO;
import br.com.brotolegal.savdatabase.dao.DispositivoDAO;
import br.com.brotolegal.savdatabase.dao.StatusDAO;
import br.com.brotolegal.savdatabase.dao.UsuarioDAO;
import br.com.brotolegal.savdatabase.entities.Config;
import br.com.brotolegal.savdatabase.entities.Dispositivo;
import br.com.brotolegal.savdatabase.entities.MOBPARAMETER;
import br.com.brotolegal.savdatabase.entities.Status;
import br.com.brotolegal.savdatabase.entities.Usuario;
import br.com.brotolegal.savdatabase.internet.AccessWebInfo;
import br.com.brotolegal.savdatabase.internet.HandleSoap;

public class BemVindoActivity extends AppCompatActivity {

    private int Result  = -1;

    private Dialog dialog;

    private String LOG  = "BEMVINDO";

    private Toolbar toolbar;

    private Spinner spConexao;

    private List<Config> conexoes;

    private conexaoAdapter conexaoadapter;

    private Config config;

    private String StatusRede = "Status Não Verificado.";

    private verRede verrede;

    private Dispositivo dispositivo;

    private EditText edUser;

    private EditText edPassWord;

    private EditText edAtivo;

    private TextView tvLinha01_01;

    private TextView tvLinha01_02;

    private TextView tvLinha02_01;

    private TextView tvLinha02_02;

    private TextView tvLinha03_01;

    private TextView tvLinha03_02;

    private TextView tvLinha04_01;

    private TextView tvLinha04_02;

    private Inclusao inclusao;

    private Alocacao alocacao;

    private MOBPARAMETER mobparameter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        try{
            if( Build.VERSION.SDK_INT >= 9){
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        }catch (Exception e)

        {
            Toast.makeText(getBaseContext(),"Erro: " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        try {

            toolbar = (Toolbar) findViewById(R.id.tb_bem_vindo);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("SAV 7.0 " + getResources().getString(R.string.app_versao));
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);

            toolbar.inflateMenu(R.menu.menu_bemvindo);


            spConexao   = (Spinner) findViewById(R.id.spConexao);

            edUser      = (EditText) findViewById(R.id.edUser_335);

            edPassWord  = (EditText) findViewById(R.id.edPass_335);

            edAtivo     = (EditText) findViewById(R.id.edAtivo);

            //Busca arquivo de configuração
            try {

                ConfigDAO dao = new ConfigDAO();

                dao.open();

                config = dao.seek(new String[]{"000"});

                conexoes =  dao.getConexoes();

                dao.close();

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                finish();

            }

            //Busca arquivo dispositivo
            try {

                DispositivoDAO dao = new DispositivoDAO();

                dao.open();

                dispositivo = dao.seek(new String[]{"000000"});

                if (dispositivo.getTOKEN().isEmpty()){


                    try {

                        dispositivo.setTOKEN(FirebaseInstanceId.getInstance().getToken());

                        dao.Update(dispositivo);

                    } catch(Exception e){

                        //

                    }

                }

                dao.close();

                edAtivo.setText(dispositivo.getCHAPA().trim());

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                finish();

            }

            tvLinha01_01 = (TextView) findViewById(R.id.tvLinha01_01_17);

            tvLinha01_02 = (TextView) findViewById(R.id.tvLinha01_02_17);

            tvLinha02_01 = (TextView) findViewById(R.id.tvLinha02_01_17);

            tvLinha02_02 = (TextView) findViewById(R.id.tvLinha02_02_17);

            tvLinha03_01 = (TextView) findViewById(R.id.tvLinha03_01_17);

            tvLinha03_02 = (TextView) findViewById(R.id.tvLinha03_02_17);

            tvLinha04_01 = (TextView) findViewById(R.id.tvLinha04_01_17);

            tvLinha04_02 = (TextView) findViewById(R.id.tvLinha04_02_17);

            tvLinha01_01.setText("Fabrincante: "+dispositivo.getFABRICANTE());

            tvLinha01_02.setText("Modelo: "+dispositivo.getMODELO());

            tvLinha02_01.setText("S.O: "+dispositivo.getVERSAO());

            tvLinha02_02.setText(dispositivo.getBUILD());

            tvLinha03_01.setText("IMEI: ");

            tvLinha03_02.setText(dispositivo.getIMEI());

            tvLinha04_01.setText("TOKEN: ");

            tvLinha04_02.setText(dispositivo.get_TOKEN());


            conexaoadapter = new conexaoAdapter(BemVindoActivity.this, R.layout.conexoes_opcoes, conexoes);

            spConexao.setAdapter(conexaoadapter);

            spConexao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if ((conexaoadapter.getEscolha() == -2)) {

                        conexaoadapter.setEscolha(-1);

                    } else {

                        conexaoadapter.setEscolha(position);

                        config = conexoes.get(position);

                        config.setCODIGO("000");

                        verrede = new verRede();

                        conexaoadapter.setStatusRede(true, "Verificando...", null);

                        AccessWebInfo acessoWeb = new AccessWebInfo(mHandler, getBaseContext(), App.user, "GETSTATUS", "GETSTATUS", 1, 1, config, verrede,-1);

                        acessoWeb.addParam("CCODUSER" , "");

                        acessoWeb.addParam("CPASSUSER", "");

                        acessoWeb.start();

                        try {

                            ConfigDAO dao = new ConfigDAO();

                            dao.open();

                            dao.Update(config);

                            dao.close();

                        } catch (Exception e) {

                            Toast.makeText(getBaseContext(), "Não Atualizada A Conexão !!", Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            int i = 0; //config.getIndiceConexaoAtiva();

            if (i == -1) {

                spConexao.setSelection(1);

            } else {

                spConexao.setSelection(i);

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bemvindo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case android.R.id.home:

                finish();

                break;

            case R.id.action_cancelar:{

                finish();

                break;}

            case R.id.action_alocar:{

                try {

                    validar();

                    CadastrarDispositivo();



                } catch (Exception e){

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }

                break;}
            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        if (Result != -1) {

            Intent data = new Intent();

            data.putExtra("CODIGO",edUser.getText().toString().trim());

            setResult(Result, data);


        } else {

            Intent data = new Intent();

            data.putExtra("CODIGO","");

            setResult(Result, data);


        }

        super.finish();

    }

    private void validar() throws Exception{

        String msgerro = "";

        try{

            try {

                Integer codigo = Integer.valueOf(edUser.getText().toString());

            } catch (Exception e){

                msgerro += "\nCódigo Usuário Inválido !";

            }

            if (edPassWord.getText().toString().isEmpty()){

                msgerro += "\nSenha Inválida !";

            }

            try {

                Integer codigo = Integer.valueOf(edAtivo.getText().toString());

            } catch (Exception e){

                msgerro += "\nCódigo Do Imobilizado Inválido !";

            }

            if (!msgerro.isEmpty()){

                msgerro = "VERIFIQUE OS ERROS:" + msgerro;

                throw  new Exception(msgerro);
            }

        } catch (Exception e){

            throw new Exception(e.getMessage());

        }


    }

    private void CadastrarDispositivo() throws Exception{

        Integer imobiliza = 0;

        Boolean erro = false;

        try {

            try {

                imobiliza = Integer.valueOf(edAtivo.getText().toString());


            } catch (Exception e) {

                throw new Exception("Código Do Imobilizado Inválido !");

            }

            inclusao = new Inclusao();

            mobparameter =
                    new MOBPARAMETER(" ",
                                     " ",
                                     " ",
                                     dispositivo.getVERSAO(),
                                     dispositivo.getBUILD(),
                                     dispositivo.getIMEI(),
                                     edAtivo.getText().toString(),
                                     dispositivo.getSTATUS(),
                                     dispositivo.getFABRICANTE(),
                                     " ",
                                     dispositivo.getMODELO(),
                                     dispositivo.getMARCA(),
                                     " ",
                                     edUser.getText().toString(),
                                     "",
                                     edPassWord.getText().toString(),
                                     "",
                                     "1",
                                     "");


            PropertyInfo pi = new PropertyInfo();
            pi.setName("mobparameter");
            pi.setValue(mobparameter);
            pi.setType(mobparameter.getClass());



            AccessWebInfo acessoWeb2 = new AccessWebInfo(mHandlerDispositivo, getBaseContext(), App.user, "INCLUDIP", "INCLUDIP", AccessWebInfo.RETORNO_TIPO_ESTUTURADO, AccessWebInfo.PROCESSO_CUSTOM, config,inclusao,-1);

            //enviando um objeto
            acessoWeb2.addInfo(pi);
            acessoWeb2.addParam("CCODUSER", edUser.getText().toString());
            acessoWeb2.addParam("CPASSUSER",edPassWord.getText().toString());


            acessoWeb2.addObjeto("MOBPARAMETER",new MOBPARAMETER());

            acessoWeb2.start();


        } catch (Exception e) {

            throw new Exception("Falha Na Gravação Do Dispositivo");

        }

    }





    public void Alocar() throws Exception {

        try {

            alocacao = new Alocacao();

            AccessWebInfo acessoWeb3 = new AccessWebInfo(mHandlerAlocar, getBaseContext(), App.user, "ALOCADISP", "ALOCADISP", 1, 1, config, alocacao,-1);

            acessoWeb3.addParam("CCODUSER", edUser.getText().toString().trim());

            acessoWeb3.addParam("CPASSUSER", edPassWord.getText().toString().trim());

            acessoWeb3.addParam("CIMEI", dispositivo.getIMEI().trim());

            acessoWeb3.addParam("CTOKEN", dispositivo.getTOKEN());

            acessoWeb3.start();

        } catch (Exception e){

            throw new Exception(e.getMessage());
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    try {

                        verrede.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(BemVindoActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };


    private Handler mHandlerAlocar = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG,"NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(BemVindoActivity.this, "Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                    processado = true;
                }


                if (msg.getData().getString("CERRO").equals("MMM")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle(msg.getData().getString("CMSGERRO"));

                        }

                    }

                    processado = true;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {


                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    try {

                        alocacao.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    processado = true;

                }

                //ERRO 10 - Dispositivo Não Cadastrado

                if ((msg.getData().getString("CERRO").equals("010"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }


                    processado = true;
                }



                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(BemVindoActivity.this, "Erro: "+msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(BemVindoActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };

    private Handler mHandlerDispositivo = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Boolean processado = false;

            try {

                if ( !msg.getData().containsKey("CERRO") || !msg.getData().containsKey("CMSGERRO") ){

                    Log.i(LOG, "NAO CONTEM AS CHAVES..");

                    return;

                }

                if (msg.getData().getString("CERRO").equals("---")) {

                    dialog = ProgressDialog.show(BemVindoActivity.this, "Dispositivo.Inicio De Processamento !!!", "Acessando Servidores.Aguarde !!", false, true);
                    dialog.setCancelable(false);
                    dialog.show();

                    processado = true;
                }


                if (msg.getData().getString("CERRO").equals("MMM")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.setTitle(msg.getData().getString("CMSGERRO"));

                        }

                    }

                    processado = true;

                }


                if (msg.getData().getString("CERRO").equals("EXE")) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    try {

                        inclusao.processa();

                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    processado = true;

                }


                if ((msg.getData().getString("CERRO").equals("FEC"))) {

                    if ((dialog != null)) {

                        if (dialog.isShowing()) {

                            dialog.dismiss();

                        }

                    }

                    processado = true;
                }


                if (!processado) {


                    Toast.makeText(BemVindoActivity.this, "Erro: "+msg.getData().getString("CMSGERRO"), Toast.LENGTH_LONG).show();


                }

            }
            catch (Exception E){

                Log.d(LOG, "MENSAGEM", E);

                Toast.makeText(BemVindoActivity.this, "Erro Handler: " + E.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    };



//inner class

    private class conexaoAdapter extends ArrayAdapter {

        private int escolha = -1;

        private String status = "";

        private Boolean visible = false;

        private boolean connected = false;

        public conexaoAdapter(Context context, int textViewResourceId, List<Config> objects) {

            super(context, textViewResourceId, objects);

        }

        public void setEscolha(int escolha) {

            this.escolha = escolha;

            notifyDataSetChanged();

        }

        public int getEscolha() {
            return escolha;
        }

        public void setStatusRede(Boolean visible,String msg, Boolean connected){

            if (!(connected == null)){

                this.connected = connected;

            }

            this.visible = visible;

            this.status  = msg;

            notifyDataSetChanged();

        }

        public boolean getConnected(){

            return this.connected;
        }
        public View getOpcoesView(final int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.conexoes_opcoes, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_15);

            tvOpcao.setText(conexoes.get(position).getDESCRICAO());

            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_15);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            return layout;
        }


        public View getEscolhaView(int position, View convertView, ViewGroup parent) {

            // Infla layout customizado
            LayoutInflater inflater = getLayoutInflater();

            View layout = inflater.inflate(R.layout.conexoes_escolha, parent, false);

            TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_16);
            TextView tvRede  = (TextView) layout.findViewById(R.id.tvAtualizando_16);
            ProgressBar bpProcesso = (ProgressBar) layout.findViewById(R.id.img_atualizando_16);

            if (escolha == -1) {

                tvOpcao.setText("Escolha Uma Conexão");
                tvRede.setText("");
                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                }

            } else {

                tvOpcao.setText(conexoes.get(escolha).getDESCRICAO());
                tvRede.setText(this.status);
                if (visible) {
                    bpProcesso.setVisibility(View.VISIBLE);
                } else {
                    bpProcesso.setVisibility(View.INVISIBLE);
                }

            }


            tvOpcao.setTextColor(Color.rgb(75, 180, 225));

            ImageView img = (ImageView) layout.findViewById(R.id.img_16);

            if (position == 0) {

                img.setImageResource(R.drawable.wifi);

            } else {

                img.setImageResource(R.drawable.nuvem_ok);

            }

            // Setting Special atrributes for 1st element
            if (position == escolha) {

                tvOpcao.setTextSize(20f);

                tvOpcao.setTextColor(Color.BLACK);
            }

            return layout;
        }

        // Mostra as Opções
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return getOpcoesView(position, convertView, parent);

        }

        // Mostra o item selecionado
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getEscolhaView(position, convertView, parent);

        }
    }

    private class verRede extends HandleSoap {


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

                cmsgerro = "Conexão Ativa.";

                conexaoadapter.setStatusRede(false, cmsgerro, true);

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
                if (cmsgerro.contains("failed to connect")) {

                    cmsgerro = "Falha de Conexão.";

                } else {

                    cmsgerro = cmsgerro;

                }

                conexaoadapter.setStatusRede(false,cmsgerro,false);
            }


        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    private class Inclusao extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            try{

                String CERRO    = result.getPropertyAsString("CERRO");
                String CMSGERRO = result.getPropertyAsString("CMSGERRO");
                String CCHAPA   = result.getPropertyAsString("U51_CHAPA");

                if (CERRO.equals("000")) {

                    DispositivoDAO dao = new DispositivoDAO();

                    dao.open();

                    dispositivo = dao.seek(new String[]{"000000"});

                    dispositivo.setCHAPA(CCHAPA);

                    if (dispositivo != null) {

                        if (dao.Update(dispositivo)) {

                            dao.close();

                            Toast.makeText(getApplicationContext(),"Nro Do Ativo Atualizado !",Toast.LENGTH_SHORT).show();

                        } else {

                            dao.close();

                            throw new Exception("Falha Na Gravação Do Nro Da Chapa.");

                        }
                    } else {

                        dao.close();

                        throw new Exception("Falha Na Gravação Do Nro Da Chapa.");
                    }


                    Alocar();

                } else {

                    throw new Exception(CMSGERRO);
                }

            } catch (Exception e){

                throw new Exception(e.getMessage());

            }


        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }

    private class Alocacao extends HandleSoap {


        @Override
        public void processa() throws Exception {

            if (this.result == null) {

                return;

            }

            try {

                String cerro = result.getPropertyAsString("CERRO");
                String cmsgerro = result.getPropertyAsString("CMSGERRO");

                if (cerro.equals("000")) {
                    {
                        //registra o usuario
                        UsuarioDAO dao = new UsuarioDAO();

                        dao.open();

                        Usuario user = dao.seek(new String[]{edUser.getText().toString()});

                        if (user == null) {

                            user = new Usuario();
                            user.setCOD(result.getPropertyAsString("U50_COD"));
                            user.setNOME(result.getPropertyAsString("U50_NOME"));
                            user.setSENHA(result.getPropertyAsString("U50_SENHA"));
                            user.setEXPIRA(result.getPropertyAsString("U50_EXPIRA"));
                            user.setCODPRO(result.getPropertyAsString("U50_CODPRO"));
                            user.setCODVEN(result.getPropertyAsString("U50_CODVEN"));
                            user.setNIVEL(result.getPropertyAsString("U50_NIVEL"));
                            user.setCLASS(result.getPropertyAsString("U50_CLASS"));
                            user.setMODULO(result.getPropertyAsString("U50_MODULO"));
                            user.setCODDIS(result.getPropertyAsString("U50_CODDIS"));
                            user.setSTATUS("M");
                            user.setCODSUP(result.getPropertyAsString("U50_CODSUP"));

                            if (dao.insert(user) == null){

                                throw new Exception("Falha Na Gravação Do Usuário No Tablete");

                            } else {

                                Toast.makeText(getApplicationContext(),"Usuário CADASTRADO !!",Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            user.setSTATUS("M");

                            if (!dao.Update(user)){

                                throw new Exception("Falha Na Atualização Do Usuário No Tablete");

                            }  else {

                                Toast.makeText(getApplicationContext(),"Usuário Atualizado !!",Toast.LENGTH_SHORT).show();
                            }

                        }


                        dao.close();

                        App.user = user;

                        Result = 1;

                        StatusDAO daoST = new StatusDAO();

                        daoST.open();

                        Status st = daoST.seek(null);

                        if (!(st == null)){

                                st.setLOGADO("S");
                                st.setHORALOG("");
                                st.setUSERLOG(user.getCOD());

                                daoST.Update(st);


                        } else {

                            App.user = null;


                        }

                        daoST.close();

                        finish();

                    }

                } else {

                    Toast.makeText(getApplicationContext(),cerro + "-" + cmsgerro,Toast.LENGTH_SHORT).show();

                    throw new Exception(cerro + "-" + cmsgerro);
                }
            } catch (Exception e){

                throw new Exception(e.getMessage());
            }
        }

        @Override
        public void processaArray() throws Exception {

            SoapObject registro ;

            if (this.result == null) {

                return;

            }

        }
    }





}
