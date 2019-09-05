import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Client c = new Client();
    }

    Client(){
        try {
            Socket socket = new Socket("127.0.0.1", 54321);
            Runnable sendMessage = () -> {
                Scanner s = new Scanner(new InputStreamReader(System.in));
                PrintWriter out = null;
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println("Please, enter your nickname:");
                    out.write(s.nextLine() + "\n");
                    out.flush();
                    while (true){
                        if(s.hasNextLine()){
                            out.write(s.nextLine()+"\n");
                            out.flush();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            Runnable reciveMessage = () -> {
                Scanner in = null;
                try {
                    in = new Scanner(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        if(in.hasNextLine()) System.out.println(in.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            Thread outThread = new Thread(sendMessage);
            Thread inThread = new Thread(reciveMessage);
            inThread.start();
            outThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
