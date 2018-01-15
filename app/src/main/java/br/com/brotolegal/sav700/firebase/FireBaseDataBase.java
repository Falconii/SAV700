package br.com.brotolegal.sav700.firebase;

import android.util.Log;

import java.util.List;
import java.util.UUID;

import br.com.brotolegal.savdatabase.app.App;
import br.com.brotolegal.savdatabase.dao.ClienteDAO;
import br.com.brotolegal.savdatabase.entities.Cliente_fast;
import br.com.brotolegal.savdatabase.entities.Cliente_fb;
import br.com.brotolegal.savdatabase.entities.Usuario_fast;
import br.com.brotolegal.savdatabase.regrasdenegocio.Empresa;

//import static br.com.brotolegal.sav700.SAVActivity.databaseReference;

/**
 * Created by Falconi on 16/10/2017.
 */

public class FireBaseDataBase {

    public FireBaseDataBase() {}

    public static void setUsuario() {

//        if (App.user.getCOD().equals("")) return;
//
//        Usuario_fast user = new Usuario_fast();
//
//        user.importacao(App.user);
//
//        databaseReference.child("USUARIOS").child(user.getCOD()).setValue(user);
    }


    public static void setEmpresa() {
//
//        Empresa empresa = new Empresa();
//
//        empresa.setCODIGO("001");
//
//        empresa.setCARDATA("17/10/2017");
//
//        empresa.setCARHORA("08:00");
//
//        empresa.setPEDDATA("31/10/2017");
//
//        empresa.setPEDHORA("17:00");
//
//        databaseReference.child("EMPRESA").child(empresa.getCODIGO()).setValue(empresa);
    }


    public static void setClientes() {
//
//        try {
//
//            ClienteDAO dao = new ClienteDAO();
//
//            dao.open();
//
//            List<Cliente_fast> clientes = dao.getAll_fast("","");
//
//            dao.close();
//
//            for(Cliente_fast cli : clientes) {
//
//                Cliente_fb cliente = new Cliente_fb();
//
//                cliente.importacao(cli);
//
//                databaseReference.child("CLIENTES").child(cliente.getCODIGO()+"-"+cliente.getLOJA()).setValue(cliente);
//
//            }
//        } catch (Exception e){
//
//            Log.i("SAV",e.getMessage());
//
//        }
    }


}
