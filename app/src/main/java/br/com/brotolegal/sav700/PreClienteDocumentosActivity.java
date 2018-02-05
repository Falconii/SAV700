package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.dao.DAO;
import br.com.brotolegal.savdatabase.dao.DocClienteDAO;
import br.com.brotolegal.savdatabase.dao.PreClienteDAO;
import br.com.brotolegal.savdatabase.entities.DocCliente;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.entities.PreCliente;

public class PreClienteDocumentosActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView im_comercial;
    private ImageView im_logistica;
    private ImageView im_documentos;

    private TextView tv_comercial;
    private TextView tv_logistica;
    private TextView tv_documentos;

    private String CODCLIENTE = "";
    private String OPERACAO   = "";

    private Adapter adapter;

    private ListView lv;

    private List<Object> lsLista;

    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cliente_documentos);
        try {
            toolbar = (Toolbar) findViewById(R.id.tb_precliente_documentos);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Documentos");
            toolbar.setLogo(R.mipmap.ic_launcher);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            toolbar.inflateMenu(R.menu.menu_precliente);

            Intent i = getIntent();

            if (i != null) {

                Bundle params = i.getExtras();

                CODCLIENTE = params.getString("CODCLIENTE");

                OPERACAO  = params.getString("OPERACAO");


            }

            im_comercial = (ImageView) findViewById(R.id.im_comercial);

            im_logistica = (ImageView) findViewById(R.id.im_logistica);

            im_documentos = (ImageView) findViewById(R.id.im_documentos);

            tv_comercial = (TextView) findViewById(R.id.tv_comercial);

            tv_logistica = (TextView) findViewById(R.id.tv_logistica);

            tv_documentos = (TextView) findViewById(R.id.tv_documentos);

            lv = (ListView) findViewById(R.id.lvPreClienteDocumentos);

            fab = (FloatingActionButton) findViewById(R.id.plus_preclientedoc);

            navegador();

            loadPreClienteDocs();


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(PreClienteDocumentosActivity.this,ChooseFileDialogActivity.class);
                    Bundle params = new Bundle();
                    params.putString("CODCLIENTE", "");
                    params.putString("OPERACAO"  , "NOVO");
                    intent.putExtras(params);
                    startActivity(intent);

                    // Pega as fotos do tablet
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent,"Escolha Uma Foto"), HelpInformation.Help_SELECT_PICTURE);

                    //Busca foto da camera
                    //Busca O Diretóriodo do usuario
//                String path = App.PathDB+"/"+App.user.getCOD();

                 //Verifica a existencia do diretorio

//                File dir = new File(path);
//
//                if ( !dir.exists()){
//
//                    if (!dir.mkdirs()) {
//
//                        toast("Falha Na Criação do Diretorio");
//
//                        return;
//
//                    }
//
//                }
//
//                toast(path);
//
//
//                String file = path+"/"+"foto.jpg";
//
//                File newfile = new File(file);
//
//                try {
//
//                    if (newfile.exists()){
//
//                        newfile.delete();
//
//                    }
//
//                    newfile.createNewFile();
//                }
//                catch (IOException e)
//                {
//                }
//
//                Uri outputFileUri = Uri.fromFile(newfile);
//
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//
//                startActivityForResult(cameraIntent, HelpInformation.Help_TAKE_PHOTO_CODE);
                }
            });




        } catch (Exception e) {

            finish();
        }

    }

    private void toast(String Mensagem){


        Toast.makeText(this, Mensagem, Toast.LENGTH_SHORT).show();



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HelpInformation.Help_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {

            File file = new File(App.PathDB+"/"+App.user.getCOD()+ "/foto.jpg");

            if (file.exists()) {

                try {
                    Uri uri = Uri.fromFile(file);

                    DocClienteDAO dao = new DocClienteDAO();

                    dao.open();

                    DocCliente doc = new DocCliente("1000", 1, "TESTE DE ESCOLHA DE FOTO", file.toString(), "1", "2");

                    dao.insert(doc);

                    dao.close();

                    if (doc != null) {

                        toast("Arquivo Anexado Com Sucesso.");

                    }

                    loadPreClienteDocs();

                    toast("Foto Atualizada !!!");

                } catch (Exception e){



                }
            } else {

                toast("Foto Não Atualizada !!!");

            }

        }

        if (requestCode == HelpInformation.Help_SELECT_PICTURE && resultCode == RESULT_OK) {

            InputStream in;

            OutputStream out;

            Uri selectedImageUri = data.getData();

            String selectedImagePath = getPath(selectedImageUri);

            String path = App.PathDB;

            //Verifica a existencia do diretorio

            File dir = new File(path);

            if ( !dir.exists()){

                if (!dir.mkdirs()) {

                    toast("Falha Na Criação do Diretorio");

                    return;

                }

            }

            String file = path+"/"+"foto.jpg";

            File newfile = new File(file);

            try {

                if (newfile.exists()){

                    newfile.delete();

                }

                newfile.createNewFile();
            }
            catch (IOException e)
            {

                toast(e.getMessage());

                return;

            }

            final int chunkSize = 1024;

            byte[] imageData = new byte[chunkSize];

            try {

                int bytesRead;

                in = getContentResolver().openInputStream(selectedImageUri);

                out = new FileOutputStream(newfile);

                while ((bytesRead = in.read(imageData)) > 0) {

                    out.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)));

                }

                in.close();

                out.close();

                DocClienteDAO dao = new DocClienteDAO();

                dao.open();

                DocCliente doc = new DocCliente("1000",1,"TESTE DE ESCOLHA DE FOTO",path,"1","2");

                dao.insert(doc);

                dao.close();

                if ( doc != null){

                    toast("Arquivo Anexado Com Sucesso.");

                }

                loadPreClienteDocs();

            } catch (IOException e) {

                toast(path);

            }

            catch (Exception e){

                toast(e.getMessage());

            }

        }
    }


    @Override
    public void onResume() {

        loadPreClienteDocs();

        super.onResume();
    }


    @Override
    public void finish() {

        lsLista            = new ArrayList<Object>();

        super.finish();

    }


    private void loadPreClienteDocs(){

        try {

            lsLista = new ArrayList<>();

            lsLista.add("DOCUMENTOS");

            DocClienteDAO dao = new DocClienteDAO();

            dao.open();

            lsLista.addAll(dao.getAll());

            dao.close();

            if (lsLista.size() == 1){

                lsLista.add(new NoData("Nenhem Documento Encontrado !!!"));

            }

            adapter = new Adapter(getBaseContext(),lsLista);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        } catch (Exception e){

            toast(e.getMessage());

        }

    }


    private void navegador(){

        tv_comercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteDocumentosActivity.this, PreClienteComercialActivity.class);
                Bundle params = new Bundle();
                params.putString("CODIGO", "");
                intent.putExtras(params);
                startActivity(intent);
                finish();

            }
        });

        tv_logistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreClienteDocumentosActivity.this, PreClienteLogisticaActivity.class);
                Bundle params = new Bundle();
                params.putString("CODCLIENTE", "");
                params.putString("OPERACAO"  , "DIGITANDO");
                intent.putExtras(params);
                startActivity(intent);
                finish();
            }
        });



    }


    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private class Adapter extends BaseAdapter {

        private List<Object> lsObjetos;

        private Context context;

        final int ITEM_VIEW_CABEC    = 0;
        final int ITEM_VIEW_DOC      = 1;
        final int ITEM_VIEW_NO_DATA  = 2;
        final int ITEM_VIEW_COUNT    = 4;

        private LayoutInflater inflater;

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = pObjects;

            this.context    = context;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof DocCliente) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Documentos: " + String.valueOf(qtd);

            return retorno;
        }

        @Override
        public int getCount() {
            return lsObjetos.size();
        }

        @Override
        public Object getItem(int position) {
            return lsObjetos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_COUNT;
        }

        @Override
        public int getItemViewType(int position) {

            int retorno = -1;


            if (lsObjetos.get(position) instanceof String) {

                retorno = ITEM_VIEW_CABEC;
            }

            if (lsObjetos.get(position) instanceof DocCliente) {

                retorno = ITEM_VIEW_DOC;

            }


            if (lsObjetos.get(position) instanceof NoData) {

                retorno = ITEM_VIEW_NO_DATA;

            }

            return retorno;


        }

        @Override
        public boolean isEnabled(int position) {

            boolean retorno = false;

            return retorno;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            try {

                final int pos = position;

                final int type = getItemViewType(position);

                if (convertView == null) {

                    switch (type) {

                        case ITEM_VIEW_CABEC:

                            convertView = inflater.inflate(R.layout.defaultdivider, null);

                            break;


                        case ITEM_VIEW_DOC:

                            convertView = inflater.inflate(R.layout.doccliente_row, null);

                            break;


                        case ITEM_VIEW_NO_DATA:

                            convertView = inflater.inflate(R.layout.no_data_row, null);

                            break;

                    }

                }

                switch (type) {

                    case ITEM_VIEW_CABEC: {

                        TextView tvCabec = (TextView) convertView.findViewById(R.id.separador);

                        tvCabec.setText(Cabec());

                        break;
                    }

                    case ITEM_VIEW_DOC: {

                        final DocCliente obj = (DocCliente) lsObjetos.get(pos);

                        ImageButton tipo_documento_427    = (ImageButton) convertView.findViewById(R.id.tipo_documento_427);

                        TextView txt_status_arquvo_427    = (TextView) convertView.findViewById(R.id.txt_status_arquvo_427);

                        TextView txt_descricao_arquvo_427 = (TextView) convertView.findViewById(R.id.txt_descricao_arquvo_427);

                        TextView txt_nome_arquvo_427 = (TextView) convertView.findViewById(R.id.txt_nome_arquvo_427);

                        final File file = new File(obj.getCAMINHO());

                        if (file.exists()) {

                            Uri uri = Uri.fromFile(file);

                            tipo_documento_427.setImageURI(uri);

                            tipo_documento_427.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Uri uri =  Uri.fromFile(file);
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                    String mime = "*/*";
                                    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                                    if (mimeTypeMap.hasExtension(mimeTypeMap.getFileExtensionFromUrl(uri.toString()))) {
                                        mime = mimeTypeMap.getMimeTypeFromExtension(mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
                                    }
                                    intent.setDataAndType(uri,mime);
                                    startActivity(intent);
                                }
                            });

                        }

                        txt_status_arquvo_427.setText(obj.getSTATUS());

                        txt_descricao_arquvo_427.setText(obj.getDESCRICAO());

                        txt_nome_arquvo_427.setText(obj.getCAMINHO());





                        break;

                    }


                    case ITEM_VIEW_NO_DATA: {

                        final NoData obj = (NoData) lsObjetos.get(pos);

                        TextView tvTexto = (TextView) convertView.findViewById(R.id.no_data_row_texto);

                        tvTexto.setText(obj.getMensagem());

                        break;

                    }


                    default:
                        break;
                }

            } catch (Exception e) {

                toast("Erro No Adapdador =>" + e.getMessage());

            }

            return convertView;

        }

        private void clickMemo(String label, final String campo,PreCliente precliente) {

            final Dialog dialog = new Dialog(PreClienteDocumentosActivity.this);

            dialog.setContentView(R.layout.getmemoviewpadrao);

            dialog.setTitle(label);

            final Button confirmar = (Button) dialog.findViewById(R.id.btn_577_ok);
            final TextView edCampo = (TextView) dialog.findViewById(R.id.txt_edcampo_577);


            edCampo.setText((String) precliente.getFieldByName(campo));

            confirmar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    dialog.dismiss();

                }


            });

            dialog.show();
        }

    }


}
