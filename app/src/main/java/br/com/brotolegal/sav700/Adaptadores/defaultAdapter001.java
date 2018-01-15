package br.com.brotolegal.sav700.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.brotolegal.sav700.R;

/**
 * Created by Falconi on 08/05/2017.
 */


public class defaultAdapter001 extends ArrayAdapter {

    private int escolha = 0;

    private List<String[]> lista;

    private String label;

    private boolean isInicializacao = true;

    private Context context;

    public defaultAdapter001(Context context, int textViewResourceId, List<String[]> objects,String label) {

        super(context, textViewResourceId,objects);

        this.lista = objects;

        this.label = label;

        this.context = context;
    }


    public String getOpcao(int pos){


        if ( (pos < this.lista.size() )){


            return lista.get(pos)[1];

        }

        return "";

    }

    public void setEscolha(int escolha) {

        this.escolha = escolha;

        notifyDataSetChanged();

    }

    public View getOpcoesView(int position, View convertView, ViewGroup parent) {

        String obj = lista.get(position)[1];

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.choice_default_row, parent, false);


        TextView label   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

        label.setVisibility(View.GONE);

        TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

        tvOpcao.setTextSize(18f);

        tvOpcao.setText(obj);

        tvOpcao.setTextColor(Color.RED);

        tvOpcao.setBackgroundResource(R.color.white);

        ImageView img = (ImageView) layout.findViewById(R.id.img_22);

        img.setVisibility(View.GONE);

        if (position == escolha) {

            tvOpcao.setTextColor(Color.BLACK);
        }

        return layout;
    }

    public View getEscolhaView(int position, View convertView, ViewGroup parent) {

        String obj = lista.get(position)[1];

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.choice_default_row, parent, false);

        TextView tvlabel   = (TextView) layout.findViewById(R.id.lbl_titulo_22);

        tvlabel.setText(this.label);

        if (this.label.isEmpty()){

            tvlabel.setVisibility(View.GONE);

        }

        TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_22);

        tvOpcao.setText(obj);

        ImageView img = (ImageView) layout.findViewById(R.id.img_22);

        img.setVisibility(View.GONE);

        if (position == escolha) {

            tvOpcao.setTextColor(context.getResources().getColor(R.color.dark_blue));

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


}
