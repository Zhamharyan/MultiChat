

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Server {
    private static List<Client> clients = new ArrayList<>();
    private static final DAO dao = new  DAO();
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        while(true){

            Socket accept = server.accept();
            Client curent = new Client(accept);
            clients.add(curent);
            System.out.println(curent.getFirstName() + " has connected ");
            sendAllMessagesBefore(curent);
            Thread thread = new Thread(curent);
            thread.start();
        }
    }
    private static void sendAllMessagesBefore(Client client){
        try {
            Gson gson = new Gson();
            ResultSet execute = dao.getStatement().executeQuery("select * from messages");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
            while(execute.next()){

                objectOutputStream.writeObject(gson.toJson(new UserInfo(execute.getString("autor"),execute.getString("message"))));

            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }
    public static void sendAll(String message,String name){
        clients = clients.stream().filter(each-> !each.getSocket().isClosed()).collect(Collectors.toList());
        Gson gson = new Gson();
        IntStream.range(0,clients.size()).forEach(each->{
            Socket currentSocket = clients.get(each).getSocket();
            try {
                OutputStream outputStream = currentSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                UserInfo userInfo = new UserInfo(name,message);
                String currentClient = gson.toJson(userInfo);
                objectOutputStream.writeObject(currentClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



}
