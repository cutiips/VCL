package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Pays;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.remote.ServiceExchange;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 22222;

        try {
            Registry reg = LocateRegistry.getRegistry(host, port);
            ServiceExchange se = (ServiceExchange) reg.lookup("ServicesIG");

            System.out.println("Date du jour : " + se.getDate());
            System.out.println("Nombre de fois que la date a été demandée : " + se.getDateCounter());

            Pays p = new Pays(215,"CH","Suisse");
            Ville v = new Ville(4, 2659496, "Neuchâtel", p, 46.9989, 6.9425, 7200);
            System.out.println("Ville envoyée : " + v);
            Ville vRet = se.upperCaseName(v);
            System.out.println("Ville retournée : " + vRet);
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}