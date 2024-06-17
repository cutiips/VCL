package ch.hearc.heg.scl.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceExchange extends Remote {
    String getDate() throws RemoteException;
    int getDateCounter() throws RemoteException;
}
