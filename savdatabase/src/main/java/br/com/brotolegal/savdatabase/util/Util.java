package br.com.brotolegal.savdatabase.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Falconi on 21/10/2015.
 */
public class Util {

    public Util(){}

    public String Ip(String hostname){

        String ip;

        try {

            InetAddress ipaddress = InetAddress.getByName(hostname);

            ip = ipaddress.getHostAddress();

        } catch ( UnknownHostException e ) {

            ip = "0.0.0.0";

        }

        return ip;

    }


}
