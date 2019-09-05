import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        new Server();
    }

    private Server(){
        try {
            ArrayList<Thread> allClients = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(54321);
            System.out.println("Server started on port 54321");
            while(true){
                Socket sock = serverSocket.accept();
                Runnable r = () -> {
                    try{
                        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                        while(true){
                            out.write("It's server. You wrote: " + in.readLine() + "\n");
                            out.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
                allClients.add(new Thread(r));
                allClients.get(allClients.size()-1).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
