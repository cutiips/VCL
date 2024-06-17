package ch.francillon.samples;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocketTCP {
    public static void main(String[] args) {
        try {
            System.out.println("Connexion au serveur");
            Socket dataSocket = new Socket("127.0.0.1", 2222);

            //Initialisation des flux
            System.out.println("Initialisation des flux");
            InputStream is = dataSocket.getInputStream();
            OutputStream os = dataSocket.getOutputStream();

            //Ecriture
            System.out.println("Envoi de texte");
            Writer w = new OutputStreamWriter(os,"UTF-8");
            String outputString = "Requête de test";
            w.write(outputString + "\n");
            w.flush();

            //Lecture
            System.out.println("Lecture de texte en entrée");
            Scanner scan = new Scanner(is);
            String inputString = scan.nextLine();
            System.out.println("Réponse du serveur : " + inputString);

            //Fermeture des flux
            System.out.println("Fermeture des flux");
            os.close();
            is.close();
            dataSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocketTCP.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
}