package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Ville;
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
            System.out.println("(2) Afficher les villes de la map");
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

        if(villes.containsKey(ville.getOpenWeatherMapId())){
            villes.get(WebResponse.getOpenWeatherMapId()).addMesure(WebResponse.getDerniereMesure());
        }else{
            villes.put(WebResponse.getOpenWeatherMapId(),WebResponse);
        }
    }
}