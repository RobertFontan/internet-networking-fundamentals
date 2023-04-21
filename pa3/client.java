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
// public static void main(String [] args){}
        // check command line arguments
        // if (args.length != 1) {
        //     System.err.println("java client < number>");
        //     return;
        // }

        // get server name and port number from command line arguments
        String serverURL = "localhost";
        //serverURL = "localhost";
        //int port = Integer.parseInt(args[0]);
        int port = 1234;



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
      

            boolean isQuit = false;
            // while(in.readLine() != null){
            //     System.out.println(in.readLine());
            // }

            System.out.println("Welcome to the Bulletin Board!");
            // "\nType post to add your message" + 
            // "\nType display to show current messages" + 
            // "\nType clear to empty the wall" +
            // "\nType kill to close the server" + 
            // "\nType quit to close the client");

            while (!isQuit) {
                
                command = userInput.readLine();
                out.println(command);

                //String response = in.readLine();                }
                if(command.equalsIgnoreCase("quit")){
                    System.out.println("Disconnecting Client.");
                    break;
                }
                else if(command.equalsIgnoreCase("display")){
                    int postsSize = Integer.parseInt(in.readLine());
                    

                    if(postsSize == 0){
                        System.out.println("Wall is empty! Try posting something!");
                    }
                    else{
                        System.out.println("Bulletin Board: ");
                    }


                    // this idk
                    for(int i = 0; i < postsSize; i++){
                        System.out.println(in.readLine());
                    }
                    continue;
                }
                else{
                    System.out.println(in.readLine());
                    continue;
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
                