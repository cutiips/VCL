package ch.hearc.heg.scl;

import ch.hearc.heg.scl.remote.ServiceExchange;
import ch.hearc.heg.scl.remote.ServiceExchangeImp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            ServiceExchangeImp sei = new ServiceExchangeImp();
            ServiceExchange se = (ServiceExchange) UnicastRemoteObject.exportObject(sei,0);
            Registry reg = LocateRegistry.createRegistry(22222);
            reg.rebind("ServicesIG",se);

            System.out.println("Serveur en attente ...");
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}