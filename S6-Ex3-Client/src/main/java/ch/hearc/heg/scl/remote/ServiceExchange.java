package ch.hearc.heg.scl.remote;

import ch.hearc.heg.scl.business.Ville;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceExchange extends Remote {
    Ville getLastMesure(Ville v) throws RemoteException;
    Ville getAllMesures(Ville v) throws RemoteException;
}
