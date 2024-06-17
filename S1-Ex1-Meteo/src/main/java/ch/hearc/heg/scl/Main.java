package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.io.Saisie;

public class Main {
    public static void main(String[] args) {
        System.out.println("Saisie et affichage d'une ville");

        // Saisie d'une ville
        Ville ville = Saisie.saisirVille();

        // Affichage de la ville
        System.out.println(ville);
    }
}