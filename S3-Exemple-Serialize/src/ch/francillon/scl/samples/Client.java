package ch.francillon.scl.samples;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Iguane ig = new Iguane("Joe", 12, 110.3);
        System.out.println(ig);

        try {
            Socket socket = new Socket("localhost", 2222);

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);

            out.writeObject(ig);
            out.flush();

            InputStream is = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);

            Iguane bl = (Iguane) in.readObject();
            System.out.println(bl);

            in.close();
            is.close();
            out.close();
            os.close();
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
