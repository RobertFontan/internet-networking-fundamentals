//javaCopy code
import java.io.*;
import java.net.*;

public class client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int port = 9090;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Hello!")
            System.out.println("Enter a joke number (1-" + server.JOKE_FILES.length + "): ");
            String request = inputReader.readLine();
            out.println(request);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
            socket.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

