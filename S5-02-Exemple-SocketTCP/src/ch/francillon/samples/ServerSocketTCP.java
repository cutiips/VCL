package ch.francillon.samples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketTCP {
    public static void main(String[] args) {
        try {
            ServerSocket listeningSocket = new ServerSocket(2222);
            System.out.println("Serveur lancé");

            while (true){
                System.out.println("Serveur en attente de connexion");

                Socket dataSocket = listeningSocket.accept();
                System.out.println("Connexion acceptée");

                System.out.println("Initialisation des flux");
                InputStream is = dataSocket.getInputStream();
                OutputStream os = dataSocket.getOutputStream();

                //Lecture
                System.out.println("Lecture de texte en entrée");
                Scanner scan = new Scanner(is);
                String inputString = scan.nextLine();
                System.out.println("Chaîne reçue : " + inputString);

                //Ecriture
                System.out.println("Envoi de texte");
                Writer w = new OutputStreamWriter(os, "UTF-8");
                String outputString = inputString.toUpperCase();
                w.write(outputString + "\n");
                w.flush();
                System.out.println("Chaîne envoyée : " + outputString);

                //Fermeture des flux
                System.out.println("Fermeture des flux");
                os.close();
                is.close();
                dataSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSocketTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

