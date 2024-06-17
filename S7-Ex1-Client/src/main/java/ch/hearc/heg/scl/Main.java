package ch.hearc.heg.scl;

import ch.hearc.heg.scl.business.Pays;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.io.Saisie;
import ch.hearc.heg.scl.remote.ServiceExchange;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 22222;
        int choix = -1;
        String username = "";
        String password = "";
        UUID sessionID;

        Scanner clavier;

        try {
            clavier = new Scanner(System.in);

            Registry reg = LocateRegistry.getRegistry(host, port);
            ServiceExchange se = (ServiceExchange) reg.lookup("ServicesIG");

            System.out.print("Entrez votre nom d'utilisateur : ");
            username = clavier.nextLine();

            System.out.print("Entrez votre mot de passe : ");
            password = clavier.nextLine();

            sessionID =  se.login(username,password);

            if(sessionID == null){
                System.out.println("Problème d'identification");
            }else{
                System.out.println("Vous êtes connecté");

                do{
                    choix = displayMenu();

                    Ville vInput = null;
                    Ville vOutput = null;

                    if(choix==1){
                        vInput = Saisie.saisirLatitudeLongitude();
                        vOutput = se.getLastMesure(vInput,sessionID);
                        System.out.println(vOutput.toString());
                    }else if(choix == 2){
                        vInput = Saisie.saisirLatitudeLongitude();
                        vOutput = se.getAllMesures(vInput,sessionID);
                        System.out.println(vOutput.toString());
                    }
                }while (choix == 1 || choix == 2);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    private static int displayMenu(){
        System.out.println("Que voulez-vous faire ?");
        System.out.println("");
        System.out.println("(1) Afficher les informations et la dernière mesure d'une ville");
        System.out.println("(2) Afficher les informations et toutes les mesures d'une ville");
        System.out.println("(3) Quitter");
        System.out.println("");

        Scanner clavier = new Scanner(System.in);
        System.out.print("Choix : ");
        int choix = Integer.parseInt(clavier.nextLine());

        return choix;
    }
}