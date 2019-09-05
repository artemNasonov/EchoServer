import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Client c = new Client();
    }

    Client(){
        try {
            Socket socket = new Socket("127.0.0.1", 54321);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Opened threads for read and write. Now you can write message here:");
            while(true){
                out.write(reader.readLine() + "\n");
                out.flush();
                String answer = in.readLine();
                System.out.println(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
