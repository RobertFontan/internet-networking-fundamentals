import java.io.*;
import java.net.*;
import java.util.Arrays;

public class client {
    public static void main(String[] args) {
        //String serverName = args[0];
        //int port = Integer.parseInt(args[1]);
        String serverName = "localhost";
        int port = 1234;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket clientSocket = new Socket(serverName, port);
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
            
            // Create input and output streams for the socket
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter out = new PrintWriter(outputStream, true);
            
            // Send a "Hello!" message to the server
            out.println("Hello!");
            
            // Read and print the server's response
            String response = in.readLine();
            System.out.println("Server response: " + response);
            
            // Prompt the user for a joke command
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter a joke command (e.g. Joke 1, bye): ");
                String command = userInput.readLine();
                out.println(command);
                response = in.readLine();
                
                // Check if the server has closed the socket
                // if (command.equalsIgnoreCase("bye")) {
                //     break;
                // }
                // if (command == null) {
                //     continue;
                //     //System.out.println("Server closed the connection.");
                //     //break;
                // }
                // // Print the server's response
                // if (response.startsWith("ERROR")) {
                //     System.out.println("Server error: " + response);
                // }
                // if(response == null){
                //     continue;
                // } 
                if(command.equalsIgnoreCase("bye"))
                    break;
                if(command.startsWith("Joke")) {
                    // Write the joke to a file
                    try{
                        String filename = "joke" + command.substring(5) + ".txt";
                        receiveFile(inputStream, filename);
                        
                    // try (PrintWriter writer = new PrintWriter(filename)) {
                    //     writer.println(response);
                        System.out.println("Joke saved to file: " + filename);
                    // } catch (IOException e) {
                    //     System.out.println("Failed to save joke to file: " + filename);
                    // }
                    }
                    catch (IOException e){
                        System.out.println("failed");
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println(response);
                    continue;
                }

                
                
                // Check if the user wants to exit
                
            }
            
            // Close the socket
            clientSocket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveFile(InputStream inputStream, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                fileOutputStream.flush();
                //System.out.println(buffer.toString());
                String str = Arrays.toString(buffer);
                String[] strArr = str.substring(1, str.length() - 1).split(", ");
                for (String s : strArr) {
                    System.out.print(s);
                    if(s.endsWith("0")){
                        System.out.println("Mathc");
                        return;
                    }
                }
                
                //System.out.println(Arrays.toString(buffer)); // Print buffer contents as an array
            }
        } finally {
            // Close resources in a finally block to ensure they are always closed
            fileOutputStream.close();
            inputStream.close();
        }
    }
}