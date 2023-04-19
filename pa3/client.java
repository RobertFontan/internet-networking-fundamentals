import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class client {
    

    public static void main(String[] args) {

        // check command line arguments
        if (args.length != 1) {
            System.err.println("java client <port number>");
            return;
        }

        // get server name and port number from command line arguments
        String serverURL = "localhost";
        //serverURL = "localhost";
        int port = Integer.parseInt(args[0]);
       



        try {
            System.out.println("Connecting to " + serverURL + " on port " + port);
            Socket clientSocket = new Socket(serverURL, port);
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
            
            // Create input and output streams for the socket
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter out = new PrintWriter(outputStream, true);
            
            
            // Read and print the server's response
            
            // Prompt the user for a joke command
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String command = "";

            while (true) {
            
                try{
                    command = userInput.readLine();
                    out.println(command);
                    String response = in.readLine();
                }
                catch (Exception e){
                    System.out.println("Invalid after Joke, try putting a number.");
                }                   
            }
            //clientSocket.close();
            //System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
                