package ch.hearc.heg.scl.remote;

import ch.hearc.heg.scl.business.Mesure;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.db.dao.MesureDAO;
import ch.hearc.heg.scl.db.dao.VilleDAO;
import ch.hearc.heg.scl.web.OpenWeatherMapService;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceExchangeImp implements ServiceExchange{
    public ServiceExchangeImp(){ }

    @Override
    public Ville getLastMesure(Ville v) throws RemoteException {
        int numeroVille = traitementWebandDB(v);

        Ville villeOutput = VilleDAO.researchByNumero(numeroVille);

        Mesure m = villeOutput.getDerniereMesure();

        villeOutput.clearMesures();

        villeOutput.addMesure(m);

        return villeOutput;
    }

    @Override
    public Ville getAllMesures(Ville v) throws RemoteException {
        int numeroVille = traitementWebandDB(v);

        Ville villeOutput = VilleDAO.researchByNumero(numeroVille);

        return villeOutput;
    }

    private int traitementWebandDB(Ville v){
        //Traitement
        Ville WebResponse = OpenWeatherMapService.getData(v);

        // Affichage de la réponse
        System.out.println(WebResponse);

        // Vérification de la présence de la ville dans la Base de données
        Ville villeDB = VilleDAO.research(WebResponse.getOpenWeatherMapId());
        int numeroVille = -1;
        // Si la ville n'est pas dans la base de données, on l'ajoute
        if(villeDB == null){
            numeroVille = VilleDAO.create(WebResponse);
        }else{
            numeroVille = villeDB.getNumero();
            System.out.println("La ville est déjà dans la base de données, inutile de l'ajouter");
        }

        // Est-ce que la mesure existe déjà dans la base de données ?
        Mesure m = MesureDAO.research(numeroVille, WebResponse.getDerniereMesure().getDateTime());

        // Si la mesure n'est pas dans la base de données, on l'ajoute
        if(m == null){
            MesureDAO.create(WebResponse.getDerniereMesure(), numeroVille);
        }else{
            System.out.println("La mesure est déjà dans la base de données, inutile de l'ajouter");
        }

        return numeroVille;
    }
}
