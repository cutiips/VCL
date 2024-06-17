package ch.hearc.heg.scl.remote;

import ch.hearc.heg.scl.business.Ville;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceExchangeImp implements ServiceExchange{
    private int counter;

    public ServiceExchangeImp(){
        counter = 0;
    }

    @Override
    public String getDate() throws RemoteException {
        counter++;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy – HH:mm:ss");
        String date = df.format(new Date());
        System.out.println("Date demandée : " + date);
        return date;
    }

    @Override
    public int getDateCounter() throws RemoteException {
        System.out.println("Compteur demandé : " + counter);
        return counter;
    }

    @Override
    public Ville upperCaseName(Ville v) throws RemoteException {
        System.out.println("Ville reçue : " + v);
        String villeName = v.getNom();
        String villeNameUpper = villeName.toUpperCase();
        v.setNom(villeNameUpper);
        System.out.println("Ville renvoyée : " + v);
        return v;
    }
}
