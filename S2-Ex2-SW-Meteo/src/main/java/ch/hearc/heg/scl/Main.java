package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.io.Saisie;
import ch.hearc.heg.scl.web.OpenWeatherMapService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Saisie et affichage d'une ville");

        // Saisie d'une ville
        Ville ville = Saisie.saisirLatitudeLongitude();

        String WebResponse = OpenWeatherMapService.getJSON(ville);

        // Affichage de la réponse
        System.out.println(WebResponse);
    }
}