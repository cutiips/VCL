package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Mesure;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.db.dao.MesureDAO;
import ch.hearc.heg.scl.db.dao.VilleDAO;
import ch.hearc.heg.scl.io.Saisie;
import ch.hearc.heg.scl.web.OpenWeatherMapService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<Integer, Ville> villes;

    public static void main(String[] args) {
        villes = new HashMap<>();

        Scanner clavier = new Scanner(System.in);

        boolean exit = false;

        while(!exit){
            int choice = 1;
            System.out.println("Que voulez-vous faire ?");
            System.out.println("(1) Saisir une nouvelle ville");
            System.out.println("(2) Afficher les villes présentes dans la base de données");
            System.out.println("(3) Sortir du programme");
            System.out.println();
            System.out.print("Mon choix : ");
            choice = Integer.parseInt(clavier.nextLine());

            switch (choice){
                case 1 : saisieVille();
                         break;
                case 2 : afficherVilles();
                         break;
                case 3 : exit = true;
                         break;
                default: System.out.println("Veuillez faire un choix entre 1 et 3");
            }
        }
    }

    private static void afficherVilles(){
        System.out.println("Affichage des villes");

        villes = VilleDAO.list();

        //Afficher un message d'erreur si il n'y a pas de villes dans la Map
        if(villes.isEmpty()){
            System.out.println("Il n'y a pas de villes dans la Map");
        }else{
            for (Ville ville : villes.values()) {
                System.out.println(ville);
            }
        }
    }

    private static void saisieVille(){
        System.out.println("Saisie et affichage d'une ville");

        // Saisie d'une ville
        Ville ville = Saisie.saisirLatitudeLongitude();

        Ville WebResponse = OpenWeatherMapService.getData(ville);

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
    }
}