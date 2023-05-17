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
           

            // System.out.println("Welcome to the Bulletin Board!");
          
            String asciiArt =         " ____          _  _        _    _          ____                           _ \n" +
                                      "|  _ \\        | || |      | |  (_)        |  _ \\                         | |\n" +
                                      "| |_) | _   _ | || |  ___ | |_  _  _ __   | |_) |  ___    __ _  _ __   __| |\n" +
                                      "|  _ < | | | || || | / _ \\| __|| || '_ \\  |  _ < / _ \\  / _` || '__| / _` |\n" +
                                      "| |_) || |_| || || ||  __/| |_ | || | | | | |_) || (_) || (_| || |   | (_| |\n" +
                                      "|____/  \\__,_||_||_| \\___| \\__||_||_| |_| |____/ \\___/  \\__,_||_|    \\__,_|\n" +
                                      "                                                                            \n" +
                                      "                                                                            \n";
            
            
            System.out.println(asciiArt);

            String menu = "-- Avaliable Commands --\n"+
                          "new - add a thread\n" +
                          "post - add your message to thread\n" +
                          "display - show current messages \n" +
                          "clear - empty a thread\n" +
                          "delete - delete a thread\n"+
                          "kill - close the server\n" +
                          "quit - close the client\n" +
                          "help - show this message again\n";

            System.out.println(menu);
            


            while (!isQuit) {
                command = userInput.readLine();
                out.println(command);

                if(command.equalsIgnoreCase("quit")){
                    System.out.println("Disconnecting Client.");
                    break;
                }
                else if(command.equalsIgnoreCase("help")){
                    System.out.println(menu);
                    continue;
                }
                else if(command.equalsIgnoreCase("post")){
                    int threadSize = Integer.parseInt(in.readLine());
                    if(threadSize == 0){
                        System.out.println("No threads found, try adding one!");
                    }
                    else{
                        System.out.println("Choose one to post below");

                        System.out.println("\n--- Open Threads ---\n");
                        for(int j = 0; j < threadSize; j++){
                            System.out.println(in.readLine());
                        }
                        System.out.print("\n");
                        command = userInput.readLine(); // sending index
                        out.println(command);  // showing index

                        System.out.println(in.readLine());
                        continue;
                    }
                }
                else if(command.equalsIgnoreCase("kill")){
                    System.out.println(in.readLine());
                    break;
                }
                else if(command.equalsIgnoreCase("display")){
                    // read how many threads there are
                    int threadSize = Integer.parseInt(in.readLine());
                    if(threadSize == 0){
                        System.out.println("No threads found, try adding one!");
                    }
                    else{
                        System.out.println("Choose one to display below");

                        System.out.println("\n--- Open Threads ---\n");
                        for(int j = 0; j < threadSize; j++){
                            System.out.println(in.readLine());
                        }
                        System.out.print("\n");
                        command = userInput.readLine(); // sending index
                        out.println(command);  // showing index
                        //System.out.println(in.readLine()); // reading one

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
                        
                    }

                    System.out.print("\n");
                    continue;
                }
                else if(command.equalsIgnoreCase("clear")){
                    int threadSize = Integer.parseInt(in.readLine());
                    if(threadSize == 0){
                        System.out.println("No threads found, try adding one!");
                    }
                    else{
                        System.out.println("Choose one to clear below");

                        System.out.println("\n--- Open Threads ---\n");
                        for(int j = 0; j < threadSize; j++){
                            System.out.println(in.readLine());
                        }
                        System.out.print("\n");
                        command = userInput.readLine(); // sending index
                        out.println(command);  // showing index

                        System.out.println(in.readLine());
                        continue;
                    }
                }
                else if(command.equalsIgnoreCase("delete")){
                    int threadSize = Integer.parseInt(in.readLine());
                    if(threadSize == 0){
                        System.out.println("No threads found, try adding one!");
                    }
                    else{
                        System.out.println("Choose one to delete below");

                        System.out.println("\n--- Open Threads ---\n");
                        for(int j = 0; j < threadSize; j++){
                            System.out.println(in.readLine());
                        }
                        System.out.print("\n");
                        command = userInput.readLine(); // sending index
                        out.println(command);  // showing index

                        System.out.println(in.readLine());
                        continue;
                    }
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
                