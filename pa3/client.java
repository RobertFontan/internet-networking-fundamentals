import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;



public class client {
    

    public static void main(String[] args) {
        // check command line arguments
        if (args.length != 2) {
            System.err.println("java client <serverURL> <port>");
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
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream)); //reads server response
            PrintWriter out = new PrintWriter(outputStream, true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String command = ""; // command to send to server
      

            boolean isQuit = false;
           

            System.out.println("Welcome to the Bulletin Board!");
            // Idea for a menu
            // "\nType post to add your message" + 
            // "\nType display to show current messages" + 
            // "\nType clear to empty the wall" +
            // "\nType kill to close the server" + 
            // "\nType quit to close the client");


            while (!isQuit) {
                command = userInput.readLine();
                out.println(command);

                if(command.equalsIgnoreCase("quit")){
                    System.out.println("Disconnecting Client.");
                    break;
                }
                else if(command.equalsIgnoreCase("kill")){
                    System.out.println(in.readLine());
                    break;
                }
                else if(command.equalsIgnoreCase("display")){
                    int postsSize = Integer.parseInt(in.readLine()); // first reads how many posts are in list
                    if(postsSize == 0){
                        System.out.println("Wall is empty! Try posting something!"); // if empty display this
                    }
                    else{
                        System.out.println("\n--- Bulletin Board --- \n"); 
                    }
                    // priting entire post line from server
                    for(int i = 0; i < postsSize; i++){
                        System.out.println(in.readLine());
                    }
                    System.out.print("\n");
                    continue;
                }
                else{
                    System.out.println(in.readLine());
                    continue;
                }
            }
            clientSocket.close(); //if quit is done (at top of while) goes to this quitting client socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
                