package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.NoData;

public class ChooseFileDialogActivity extends AppCompatActivity {

    private Toolbar     toolbar;

    private ListView     lv;

    private List<Object> lsLista;

    private Adapter      adapter;

    private String       ROOT;

    private String       PATCH;

    private String       NAME;

    private int          Result = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file_dialog);

        toolbar = findViewById(R.id.tb_arquivos_492);

        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Escolha Um Arquivo");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.menu_choosefiledialog);


        try {

//            Intent i = getIntent();
//
//            if (i != null) {
//
//                Bundle params = i.getExtras();
//
//
//            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        }


        lv = findViewById(R.id.lv_Arquivos_492);

        ROOT    =  "storage";

        getDir(ROOT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_choosefiledialog, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.action_chooseFileDialog_voltar: {

                finish();

                break;

            }


            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        lsLista = new ArrayList<>();

        if (Result == 1) {

            Intent data = new Intent();

            data.putExtra("PATCH", PATCH);

            data.putExtra("NAME", NAME);

            setResult(Result, data);

        } else {

            Intent data = new Intent();

            setResult(Result, data);

        }
        super.finish();
    }



    private void getDir(String dirPath)
    {

        List<String>  item = new ArrayList<>();
        List<String>  path = new ArrayList<>();

        lsLista = new ArrayList<>();

        if (dirPath.equals(ROOT)){

            File f       = new File(App.BasePath);

            lsLista.add(new objFile("D", "RAIZ", f));

        } else {

            File f = new File(dirPath);

            File[] files = f.listFiles();

            lsLista.add(new objFile("V", "VOLTAR !!", f));


            if (!(files == null)) {

                for (int i = 0; i < files.length; i++) {

                    String FileName = files[i].getName();

                    lsLista.add(new objFile(( files[i].isDirectory() ? "D" : "F"), FileName, files[i]));

                    if (!files[i].isHidden() && files[i].canRead()) {

                        if (files[i].isDirectory()) {

                            ((objFile) lsLista.get(lsLista.size() - 1)).setDescricao(FileName + "...");

                        }
                    }
                }
            }
        }


        adapter = new Adapter(this,lsLista);

        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private void toast(String mensagem){

        Toast.makeText(this,mensagem,Toast.LENGTH_LONG).show();


    }

    private class Adapter extends BaseAdapter {

        private List<Object> lsObjetos;

        Context context;

        final int ITEM_VIEW_CABEC   = 0;
        final int ITEM_VIEW_DETALHE = 1;
        final int ITEM_VIEW_NO_DATA = 2;
        final int ITEM_VIEW_COUNT   = 3;

        private LayoutInflater inflater;


        private HashMap<String, Integer> meMap=new HashMap<String, Integer>();


        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = pObjects;

            this.context    = context;

            this.meMap.put(".jpg",R.drawable.file_jpg_48);

            this.meMap.put(".jpeg",R.drawable.file_jpg_48);

            this.meMap.put(".mp4",R.drawable.file_jpg_48);

            this.meMap.put(".txt",R.drawable.file_txt_48);

            this.meMap.put(".doc",R.drawable.file_txt_48);

            this.meMap.put(".png",R.drawable.file_png_48);

            this.meMap.put(".pdf",R.drawable.file_pdf_48);

            this.meMap.put(".***",R.drawable.generic_file_48);

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        private String Cabec() {

            String retorno = "";

            int qtd = 0;

            for (Object obj : lsObjetos) {

                if (obj instanceof objFile) {

                    qtd = qtd + 1;

                }

            }

            retorno = "Total de Arquivos: " + String.valueOf(qtd);

            return retorno;
        }

        private String Extensao(String NameFile){

            String retorno = "";

            int pos = NameFile.indexOf(".");

            if (pos>-1) {

                retorno  = NameFile.substring(NameFile.lastIndexOf("."));

            }else {

                retorno = ".***";
            }
            if (meMap.get(retorno) == null){

                retorno = ".***";

            }

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

            if (lsObjetos.get(position) instanceof objFile) {

                retorno = ITEM_VIEW_DETALHE;

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


                        case ITEM_VIEW_DETALHE:

                            convertView = inflater.inflate(R.layout.doc_file, null);

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

                    case ITEM_VIEW_DETALHE: {

                        final objFile obj = (objFile) lsObjetos.get(pos);

                        ImageButton bt_file_408 = (ImageButton) convertView.findViewById(R.id.bt_file_408);

                        TextView txt_descricao_408 = (TextView) convertView.findViewById(R.id.txt_descricao_408);

                        Button   bt_anexar_408 = (Button) convertView.findViewById(R.id.bt_anexar_408);

                        bt_anexar_408.setVisibility(View.INVISIBLE);

                        if  ( (obj.getTipo().equals("D")) || (obj.getTipo().equals("V")) ){

                            if (obj.getTipo().equals("D")) {
                                bt_file_408.setImageResource(R.drawable.folder_arancio_48);

                                bt_file_408.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        getDir(obj.getFile().getPath());

                                    }
                                });
                            }

                            if (obj.getTipo().equals("V")) {
                                bt_file_408.setImageResource(R.drawable.upfolder_arancio_48);

                                bt_file_408.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        getDir(obj.getFile().getParent());

                                    }
                                });
                            }


                        } else {

                            bt_file_408.setImageResource(meMap.get(Extensao(obj.getFile().getName())));

                            bt_anexar_408.setVisibility(View.VISIBLE);

                            bt_anexar_408.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                    PATCH = obj.getFile().getPath();

                                    NAME  = obj.getFile().getName();

                                    Result = 1;


                                    finish();

                                }
                            });


                        }

                        txt_descricao_408.setText(obj.getDescricao());

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


        public void toast(String msg) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }

    private class objFile {

        private String tipo;
        private String descricao;
        private File file;

        public objFile(String tipo, String descricao, File file) {
            this.tipo = tipo;
            this.descricao = descricao;
            this.file = file;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String get_Tipo(){

            String retorno = "";
            if (this.tipo.isEmpty()){

                return "";

            }

            switch (this.tipo.charAt(0)){

                case 'D':

                    retorno = "PASTA";

                    break;

                case 'F':

                    retorno = "ARQUIVO";

                    break;

                default:

                    retorno = "";

            }

            return retorno;

        }
    }
}
