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
            ArrayList<Thread> allThreads = new ArrayList<>();
            ArrayList<Socket> allSockets = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(54321);
            System.out.println("Server started on port 54321");
            while(true){
                Socket sock = serverSocket.accept();
                Runnable r = () -> {
                    String nickname = "";
                    try{
                        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                        nickname=in.readLine();
                        out.write("It's server. Hello, " + nickname +", now you can chat with others: \n");
                        out.flush();
                        while(true){
                            String message = in.readLine();
                            for(Socket s: allSockets){
                                PrintWriter o = new PrintWriter(s.getOutputStream());
                                o.write(nickname + " wrote: " + message + "\n");
                                o.flush();
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(nickname + " has disconnected");
                    }
                };
                allThreads.add(new Thread(r));
                allThreads.get(allThreads.size()-1).start();
                allSockets.add(sock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
