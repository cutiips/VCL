package ch.hearc.heg.scl.remote;

import ch.hearc.heg.scl.business.Ville;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface ServiceExchange extends Remote {
    Ville getLastMesure(Ville v, UUID sessionID) throws RemoteException;
    Ville getAllMesures(Ville v, UUID sessionID) throws RemoteException;

    UUID login(String username, String password) throws RemoteException;
    void logout(String username, UUID sessionID) throws RemoteException;
}
