package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.NoData;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeAgendamentoAtrasado;
import br.com.brotolegal.savdatabase.regrasdenegocio.ExceptionValidadeTabelaPreco;

public class ChooseFileDialogActivity extends AppCompatActivity {

    Toolbar   toolbar;

    ListView  lv;

    List<Object> lsLista;

    Adapter adapter;

    private String       ROOT;
    private String       STORAGE;

    HashMap<String, Integer> meMap=new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file_dialog);

        lv = findViewById(R.id.lv_Arquivos_492);


        meMap.put(".jpg",R.drawable.file_jpg_48);
        meMap.put(".jpeg",R.drawable.file_jpg_48);
        meMap.put(".mp4",R.drawable.file_jpg_48);
        meMap.put(".txt",R.drawable.file_txt_48);
        meMap.put(".doc",R.drawable.file_txt_48);
        meMap.put(".png",R.drawable.file_png_48);
        meMap.put(".pdf",R.drawable.file_pdf_48);
        meMap.put(".***",R.drawable.generic_file_48);

        ROOT    =  App.BasePath;

        getDir(ROOT);
    }

    private void getDir(String dirPath)
    {
        File file;

        List<String>  item = new ArrayList<>();
        List<String>  path = new ArrayList<>();

        File f       = new File(dirPath);
        File[] files = f.listFiles();

        lsLista = new ArrayList<>();

        if (dirPath.equals(ROOT)) {
            lsLista.add(new objFile("D", "RAIZ", f));
            item.add(ROOT);
            path.add(ROOT);
            item.add("../");
            path.add(f.getParent());
        } else {

            item.add(ROOT);
            path.add(ROOT);
            item.add("../");
            path.add(f.getParent());

            for(int i=0; i < files.length; i++)
            {
                file = files[i];
                String FileName = file.getName();
                if (FileName.equals("emulated")) {
                    continue;
                }

                if (FileName.equals("sdcard0")) {

                    FileName = "Aparelho";

                    File dir = new File(file.getPath()+"/SAV800");

                    try {

                        if (!dir.mkdir()) {

                            throw new Exception("Erro Na Criação Do Diretório Da Aplicação.");
                        }


                    } catch (Exception e) {

                        toast(e.getMessage());

                    }


                }

                if (FileName.equals("extSdCard")) {
                    FileName = "Cartão De Memória";
                }

                lsLista.add(new objFile("F",FileName,file));

                if(!file.isHidden() && file.canRead()){

                    if(file.isDirectory()){

                        ((objFile) lsLista.get(lsLista.size()-1)).setDescricao(FileName + "...");

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

        public Adapter(Context context, List<Object> pObjects) {

            this.lsObjetos  = pObjects;

            this.context    = context;

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

                        if (obj.getTipo().equals("D")){

                            bt_file_408.setImageResource(R.drawable.folder_arancio_48);

                        } else {

                            bt_file_408.setImageResource(R.drawable.folder_arancio_48);

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
