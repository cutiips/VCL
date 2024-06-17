package ch.hearc.heg.scl;

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
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}