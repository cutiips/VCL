package ch.hearc.heg.scl.remote;

import ch.hearc.heg.scl.business.Ville;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceExchange extends Remote {
    String getDate() throws RemoteException;
    int getDateCounter() throws RemoteException;
    Ville upperCaseName(Ville v) throws RemoteException;
}
