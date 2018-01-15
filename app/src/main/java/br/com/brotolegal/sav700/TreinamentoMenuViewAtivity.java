package br.com.brotolegal.sav700;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.config.HelpInformation;

public class TreinamentoMenuViewAtivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String TAG = "TREINAMENTOVIEW";

    private Bundle  savebundle;

    private Drawer.Result navigationDrawerLeft;

    private AccountHeader.Result headerNavigationLeft;

    private Map<String,String> mpVideos     = new TreeMap<String, String >();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinamento_menu_view_ativity);


        try {

            toolbar = (Toolbar) findViewById(R.id.tb_treinamento_menu);
            toolbar.setTitle(getResources().getString(R.string.app_razao));
            toolbar.setSubtitle("Treinamento Com Videos");
            toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.inflateMenu(R.menu.menu_treinamento_menu);

            try {


                savebundle = savedInstanceState;

                adicionaDrawer(savebundle);

                adicionaLeftNavigation();

                adicionaHeaderNavigation();

                if (savebundle != null) navigationDrawerLeft.setSelection(1);

                navigationDrawerLeft.openDrawer();

            } catch (Exception e) {

                System.out.println("Erro: " + e.getMessage());

            }



        } catch (Exception e){

            Log.i(TAG,e.getMessage());


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_treinamento_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }



    private String getFilmes(String  opcao){

        String retorno = "";

        if (opcao.equals("logoff")) {


        }


        if (opcao.equals("base")) {

        }


        if (opcao.equals("update")) {


        }



        if (opcao.equals("dashboard")) {


        }


        if (opcao.equals("dispositivo")) {


        }

        if (opcao.equals("usuario")) {



        }


        if (opcao.equals("pedidodistribuicao")) {


        }


        if (opcao.equals("pedidoplanejamento")) {



        }


        if (opcao.equals("pedidoavulso")) {



        }

        if (opcao.equals("pedidotransmitido")) {



        }


        if (opcao.equals("metas")) {


        }

        if (opcao.equals("agenda01")) {



        }


        if (opcao.equals("agenda02")) {



        }


        if (opcao.equals("gerencial01")) {

           retorno = "krA4jQG3NDQ";

        }

        if (opcao.equals("pedidosprotheus")) {


        }


        if (opcao.equals("nfsprotheus")) {


        }

        if (opcao.equals("acordosprotheus")) {


        }

        if (opcao.equals("preacordo")) {


        }


        if (opcao.equals("prospeccao")) {


        }


        if (opcao.equals("CC")) {


        }


        if (opcao.equals("sincronizacao")) {


        }


        if (opcao.equals("carga")) {



        }


        return retorno;


    }

    private void adicionaDrawer(final Bundle savedInstanceState){

        adicionaLeftNavigation();

        adicionaHeaderNavigation();

    }

    private void adicionaLeftNavigation(){

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savebundle)
                .withSelectedItem(1)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {


                        int mPositionClicked = i;

                        String opcao = (String) iDrawerItem.getTag();

                        navigationDrawerLeft.getAdapter().notifyDataSetChanged();

                        if (opcao.equals("sair")) {


                            finish();

                            opcao = "";


                        }


                        if (!opcao.isEmpty()){

                            if (!getFilmes(opcao).isEmpty()) {

                                Intent intent = new Intent(TreinamentoMenuViewAtivity.this, TreinamentoActivity.class);

                                Bundle params = new Bundle();
                                params.putString("VIDEO",getFilmes(opcao));
                                intent.putExtras(params);

                                startActivity(intent,params);

                            } else {

                                Toast.makeText(TreinamentoMenuViewAtivity.this, "Video Não Encontrado !", Toast.LENGTH_SHORT).show();


                            }
                        }




                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

                        return false;
                    }
                })
                .build();

        /*

          define a base

         */

        String base;
        if (App.base.equals(HelpInformation.BaseProducao)){

            base = "PRODUÇÃO";

        } else {

            base = "HOMOLOGAÇÃO";

        }
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Aplicação").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("aplicacao"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Instalação").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("instalacao"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Atualização").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("update"));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("sair"));


        int indice = 4;

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Principal").withTextColor(getResources().getColor(R.color.md_red_200)).withTag("main"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Dash Board").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("dashboard"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Usuário").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Dispositivo").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("dispositivo"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Usuário").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("usuario"));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Tablet").withTextColor(getResources().getColor(R.color.md_red_200)));

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedido De Distribuição").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("pedidodistribuicao"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedidos Do Planejamento").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("pedidoplanejamento"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedido Avulso").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("pedidoavulso"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedido Transmitidos").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("pedidotransmitido"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pré-Cliente").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("precliente"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pré-Acordo").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("preacordo"));


        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Planejamento").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Visualização Das Agendas").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("agenda02"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Relatório Das Agendas").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("gerencial01"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Protheus").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pedidos").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("pedidosprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Notas Fiscais").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("nfsprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Acordos").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("acordosprotheus"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Metas").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("metas"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Gerencial").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Rel. Pedidos").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("gerencial01"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Clientes").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Prospecção").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("prospeccao"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Clientes").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("cliente"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Conta Corrente").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("CC"));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Transmissão").withTextColor(getResources().getColor(R.color.md_red_200)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sincronizar Pedidos").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("sincronizacao"));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Carga De Dados").withIcon(getResources().getDrawable(R.drawable.youtube_vermelho_48)).withTag("carga"));

        Boolean ativo = true;


        for(int count = indice; count < navigationDrawerLeft.getDrawerItems().size(); count++) {

            if (navigationDrawerLeft.getDrawerItems().get(count) instanceof PrimaryDrawerItem) {

                ( (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(count)).setEnabled(ativo);

            }

        }

        navigationDrawerLeft.getAdapter().notifyDataSetChanged();



    }

    private void adicionaHeaderNavigation(){

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savebundle)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.menu_treinamento)
                .build();

    }


}
