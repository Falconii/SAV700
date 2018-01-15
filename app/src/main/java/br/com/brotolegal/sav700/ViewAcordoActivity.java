package br.com.brotolegal.sav700;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.brotolegal.sav700.help20.Help20Activity;
import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;
import br.com.brotolegal.savdatabase.config.Mask;
import br.com.brotolegal.savdatabase.dao.AcordoDAO;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.dao.PreAcordoDAO;
import br.com.brotolegal.savdatabase.dao.VerbaDAO;
import br.com.brotolegal.savdatabase.database.ObjRegister;
import br.com.brotolegal.savdatabase.entities.Acordo;
import br.com.brotolegal.savdatabase.entities.Cliente;
import br.com.brotolegal.savdatabase.entities.PreAcordo;
import br.com.brotolegal.savdatabase.entities.Verba;

import static android.os.Build.ID;

public class ViewAcordoActivity extends AppCompatActivity {


    private String CODIGO;

    private Acordo acordo;

    private DecimalFormat format_02 = new DecimalFormat(",##0.00");
    private final int OperacaoInclusao  = 0;
    private final int OperacaoAlteracao = 1;
    private final int OperacaoConsulta  = 2;

    private int Operacao = 2;

    private Dialog dialog;

    defaultAdapter verbasadapter;
    defaultAdapter pagtoadapter;

    private TextView txt_id_714;
    private TextView txt_emissao_714;
    private TextView txt_status_714;
    private TextView txt_tablet_714;
    private TextView txt_codcli_714;
    private TextView txt_razao_714;
    private TextView txt_cnpj_714;
    private TextView txt_ie_714;
    private Spinner  sp_verba_714;
    private Spinner  sp_pagamento_714;
    private List<String[]> lsVerba;

    private TextView txt_dtinicial_714;
    private TextView txt_dtfinal_714;
    private TextView txt_dtpagto_714;
    private TextView txt_saldo_714;
    private TextView txt_valor_714;
    private TextView txt_descricao_714;
    private TextView txt_obs_714;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_acordo);

        acordo =  new Acordo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbacordo_view_714);
        toolbar.setTitle(getResources().getString(R.string.app_razao));
        toolbar.setSubtitle("Acordo Protheus");
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();

        if (i != null) {

            Bundle extras = i.getExtras();

            CODIGO       = extras.getString("CODIGO"      , "");

        } else {

            finish();

        }

        try {

            Init();

            loadVerbas();

            loadAcordo();

            if (lsVerba.size() == 0){

                toast("Cadastro de Verbas Inválido !!!");

                finish();

            }

        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            finish();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_view_acordo, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            case R.id.view_acordo_menu_cancela:

                finish();

                break;

            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void toast(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finish() {

        lsVerba = new ArrayList<String[]>();

        super.finish();

    }

    private void Init() {

        txt_id_714          = (TextView) findViewById(R.id.txt_id_714);
        txt_emissao_714     = (TextView) findViewById(R.id.txt_emissao_714);
        txt_status_714      = (TextView) findViewById(R.id.txt_status_714);
        txt_tablet_714      = (TextView) findViewById(R.id.txt_tablet_714);
        txt_codcli_714      = (TextView) findViewById(R.id.txt_codcli_714);
        txt_razao_714       = (TextView) findViewById(R.id.txt_razao_714);
        txt_cnpj_714        = (TextView) findViewById(R.id.txt_cnpj_714);
        txt_ie_714          = (TextView) findViewById(R.id.txt_ie_714);
        sp_verba_714        = (Spinner) findViewById(R.id.sp_verba_714);
        sp_pagamento_714    = (Spinner) findViewById(R.id.sp_pagamento_714);

        txt_dtinicial_714   = (TextView) findViewById(R.id.txt_dtinicial_714);
        txt_dtfinal_714     = (TextView) findViewById(R.id.txt_dtfinal_714);
        txt_dtpagto_714     = (TextView) findViewById(R.id.txt_dtpagto_714);
        txt_saldo_714       = (TextView) findViewById(R.id.txt_saldo_714);
        txt_valor_714       = (TextView) findViewById(R.id.txt_valor_714);
        txt_descricao_714   = (TextView) findViewById(R.id.txt_descricao_714);
        txt_obs_714         = (TextView) findViewById(R.id.txt_obs_714);



        txt_descricao_714.setMovementMethod(new ScrollingMovementMethod());

    }

    private void loadVerbas() throws Exception{

        List<Verba> lsregistros = null;

        lsVerba = new ArrayList<>();

        try {

            VerbaDAO dao = new VerbaDAO();

            dao.open();

            lsregistros = dao.getAllByTipo("A");

            dao.close();

            lsVerba.add(new String[]{"", "Defina Uma Verba Por Favor !!!"});

            if (lsregistros.size() > 0){

                for( Verba ver :lsregistros){

                    lsVerba.add(new String[]{ver.getCODIGO(),ver.getDESCRICAO()});

                }

            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }

    }

    private void loadAcordo() throws Exception {

        try {


            AcordoDAO dao = new AcordoDAO();

            dao.open();

            acordo = dao.seek(new String[]{CODIGO});

            dao.close();

            if (acordo == null) {


                throw new Exception("Registro Não Encontrado !!");

            }


            refresh();

        } catch (Exception e) {

            throw new Exception(e.getMessage());


        }
    }

    private void refresh() {

        int index = 0;


        final Drawable img = getResources().getDrawable(R.drawable.error5);

        img.setBounds(0, 0, 30, 30);


        txt_id_714.setText(acordo.getCODIGO());

        txt_emissao_714.setText((acordo.getDATA()));

        txt_status_714.setText(acordo.get_STATUS());

        txt_tablet_714.setText(acordo.getCODMOBILE());

        txt_codcli_714.setText(acordo.getCLIENTE()+"-"+acordo.getLOJA());

        txt_razao_714.setText(acordo.getRAZAO());

        txt_cnpj_714.setText(acordo.getCNPJ());

        txt_ie_714.setText(acordo.getIE());

        if (acordo.getCODVERBA().equals("")){

            index = 0;

        } else {

            for(int x=0;x < lsVerba.size();x++){

                if (lsVerba.get(x)[0].equals(acordo.getCODVERBA())){

                    index = x;

                    break;

                }

            }
        }


        sp_verba_714.setEnabled(false);

        verbasadapter = new defaultAdapter(ViewAcordoActivity.this, R.layout.choice_default_row, lsVerba, "Verba", true);

        sp_verba_714.setAdapter(verbasadapter);

        sp_verba_714.setSelection(index);

        sp_pagamento_714.setEnabled(false);

        pagtoadapter = new defaultAdapter(ViewAcordoActivity.this, R.layout.choice_default_row, lsVerba, "Pagto:", true);

        sp_pagamento_714.setAdapter(pagtoadapter);

        sp_pagamento_714.setSelection(index);

        pagtoadapter.setIsInicializacao(true);

        sp_pagamento_714.setSelection(index);



    txt_dtinicial_714.setText(acordo.getDATAINICIAL());

    txt_dtfinal_714.setText(acordo.getDATAFINAL());

    txt_dtpagto_714.setText(acordo.getDATAPAGTO());

    txt_saldo_714.setText(format_02.format(acordo.getSALDO()));

    txt_valor_714.setText(format_02.format(acordo.getVALOR()));

    txt_descricao_714.setText(acordo.getDESCRICAO());

    txt_obs_714.setText(acordo.getOBS());


}

private class defaultAdapter extends ArrayAdapter {

    private int escolha = 0;

    private List<String[]> lista;

    private String label;

    private boolean valido;

    private boolean isInicializacao = true;

    private defaultAdapter(Context context, int textViewResourceId, List<String[]> objects,String label, boolean valido) {

        super(context, textViewResourceId, objects);

        this.lista = objects;

        this.label = label;

        this.valido = valido;
    }

    public String getOpcao(int pos){


        if ( (pos < this.lista.size() )){


            return lista.get(pos)[0];

        }

        return "";

    }

    public void setEscolha(int escolha) {

        this.escolha = escolha;

        notifyDataSetChanged();

    }

    public View getOpcoesView(int position, View convertView, ViewGroup parent) {

        String col01 = lista.get(position)[0];
        String col02 = lista.get(position)[1];

        LayoutInflater inflater =  getLayoutInflater();

        View layout = inflater.inflate(R.layout.choice_01_row, parent, false);

        TextView tvcol01 = (TextView) layout.findViewById(R.id.txt_col01_898);

        TextView tvcol02 = (TextView) layout.findViewById(R.id.txt_col02_898);

        tvcol01.setTextSize(14f);

        tvcol02.setTextSize(14f);


        tvcol01.setText(col01);

        tvcol02.setText(col02);

        tvcol01.setTextColor(Color.RED);

        tvcol02.setTextColor(Color.RED);

        ImageView img = (ImageView) layout.findViewById(R.id.im_flag_898);

        if (position == escolha) {

            img.setVisibility(View.VISIBLE);

            tvcol01.setTextColor(Color.BLACK);

            tvcol02.setTextColor(Color.BLACK);

        }

        return layout;
    }

    public View getEscolhaView(int position, View convertView, ViewGroup parent) {

        String obj = lista.get(position)[1];

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

        TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

        tvlabel.setText(this.label);

        if (this.label.isEmpty()){

            tvlabel.setVisibility(View.GONE);

        }

        TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

        tvOpcao.setText(obj);

        ImageView img = (ImageView) layout.findViewById(R.id.img_22);

        if (valido){

            img.setVisibility(View.GONE);

        }   else
        {
            img.setImageResource(R.drawable.erro_20_vermelho);
        }

        if (position == escolha) {

            tvOpcao.setTextColor(getResources().getColor(R.color.dark_blue));

            tvOpcao.setGravity(Gravity.CENTER);

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

    public boolean isInicializacao() {
        return isInicializacao;
    }

    public void setIsInicializacao(boolean isInicializacao) {
        this.isInicializacao = isInicializacao;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}

}