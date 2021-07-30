import com.google.gson.Gson;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",8080);
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        Thread waitInput = new Thread(()->{
            try {
                while(true){
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    String message = (String)objectInputStream.readObject();
                    if(message != null){
                        UserInfo userInfo = gson.fromJson(message,UserInfo.class);
                        System.out.println(userInfo.getName() + " - "+userInfo.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitInput.start();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        while(true){
            String message = scanner.nextLine();
            objectOutputStream.writeObject(message);
        }



    }
}
