import com.github.javafaker.Faker;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public final class Client implements Runnable{


    private final Socket socket;
    private final String firstName;
    public Client(Socket socket) {
        this.socket = socket;
        this.firstName = (new Faker()).name().firstName();
    }
    public Socket getSocket() {
        return socket;
    }
    public String getFirstName() {
        return firstName;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while(true){
                String message = (String)objectInputStream.readObject();
                if(message != null){
                    if(message.equals("bye")){
                        /*Server.sendAll(message,firstName);*/
                        Thread.sleep(6000);
                        socket.close();
                        break;
                    }
                    Server.sendAll(message,firstName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
