package ch.francillon.scl.samples;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] args) {
        try {
            ServerSocket srv = new ServerSocket(2222);

            while (true){
                Socket socket = srv.accept();

                InputStream is = socket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(is);

                Iguane ig = (Iguane) in.readObject();
                ig.setAge(ig.getAge() + 10);
                ig.setTaille(ig.getTaille() + 20 );

                OutputStream os = socket.getOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(os);

                out.writeObject(ig);
                out.flush();

                out.close();
                os.close();
                in.close();
                is.close();
                socket.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
