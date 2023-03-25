
import java.io.*;
import java.net.*;


public class client {


    public static void main(String[] args) {

        // check command line arguments
        if (args.length != 2) {
            System.err.println("java client <serverURL> <port number>");
            return;
        }

        // get server name and port number from command line arguments
        String serverURL = args[0];
        int port = Integer.parseInt(args[1]);
       
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
            //Scanner sc = new Scanner(System.in);
            String command = "";
            System.out.println("Hello!");
            while (true) {
            
                try{
                    command = userInput.readLine();
                    out.println(command);
                    String response = in.readLine();
                    
                    if(command.equalsIgnoreCase("bye")){
                        System.out.println("Disconnected. Good bye!");
                        break;
                    }
                    if(command.equals("Joke 1") || command.equals("Joke 2") || command.equals("Joke 3")) {
                        
                        try{
                            String filename = "joke" + command.substring(5) + ".txt";
                            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                            System.out.println(response);
                            writer.write(response);
                            writer.close();

                            //System.out.println("Joke saved to file: " + filename);
                        }
                        catch (IOException e){
                            System.out.println("Failure");
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println(response);
                        continue;
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid after Joke, try putting a number.");
                }                   
            }
            clientSocket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
                