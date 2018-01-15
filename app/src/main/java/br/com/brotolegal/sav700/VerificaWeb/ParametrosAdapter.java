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

/**
 * Created by Falconi on 24/03/2017.
 */

public class ParametrosAdapter extends ArrayAdapter {


    private int escolha = 0;

    private List<Parametros> lista;

    private Context context;

    public ParametrosAdapter(Context context, int textViewResourceId, List<Parametros> objects) {

        super(context, textViewResourceId, objects);

        this.lista = objects;

        this.context = context;
    }


    public void setEscolha(int escolha) {

        this.escolha = escolha;

        notifyDataSetChanged();

    }



    public View getEscolhaView(int position, View convertView, ViewGroup parent) {

        Parametros obj = (Parametros) lista.get(position);

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.conexoes_escolha, parent, false);

        TextView tvOpcao = (TextView) layout.findViewById(R.id.tvOpcao_16);

        TextView tvRede  = (TextView) layout.findViewById(R.id.tvAtualizando_16);

        ProgressBar bpProcesso = (ProgressBar) layout.findViewById(R.id.img_atualizando_16);

        tvOpcao.setText(obj.getLinha01());

        tvRede.setText(obj.getLinha02());

        bpProcesso.setVisibility(View.INVISIBLE);

        tvOpcao.setTextColor(Color.rgb(75, 180, 225));

        ImageView img = (ImageView) layout.findViewById(R.id.img_16);

        if (position == 0) {

            img.setImageResource(R.drawable.ic_action_down_cloud_i);

        } else {

            img.setImageResource(R.drawable.ic_action_order_sales_i);

        }

        if (position == escolha) {

            tvOpcao.setTextSize(20f);

            tvOpcao.setTextColor(Color.BLACK);
        }

        return layout;
    }

    // Mostra as Opções
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getEscolhaView(position, convertView, parent);

    }

    // Mostra o item selecionado
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getEscolhaView(position, convertView, parent);

    }


}
