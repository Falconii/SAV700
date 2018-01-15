package br.com.brotolegal.sav700.VerificaWeb;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.brotolegal.sav700.R;
import br.com.brotolegal.savdatabase.entities.Config;

/**
 * Created by Falconi on 24/03/2017.
 */

public class ConexaoAdapter extends ArrayAdapter {

    private int escolha = -1;

    private String status     = "";

    private Boolean visible   = false;

    private boolean connected = false;

    private Context context;

    private Config config = new Config();

    private List<Config> conexoes;

    public ConexaoAdapter(Context context, int textViewResourceId, List<Config> objects) {

        super(context, textViewResourceId, objects);

        this.context = context;

        conexoes = objects;

        connected = false;

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
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


    public Config getConfig() {
        return config;
    }
}
