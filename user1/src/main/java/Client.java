import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",8080);
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        Thread waitInput = new Thread(()->{
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                while(true){
                   String message = (String)objectInputStream.readObject();

                    if(message != null){
                        UserInfo userInfo = gson.fromJson(message,UserInfo.class);
                       /* if(userInfo.getName().equals("bye")){
                            break;
                        }*/
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
            /*if(message.equals("bye")){
                socket.shutdownInput();
            }*/
       /*socket.shutdownInput();*/
        Thread.sleep(100);
            objectOutputStream.writeObject(message);
           break;

        }

    }
}
