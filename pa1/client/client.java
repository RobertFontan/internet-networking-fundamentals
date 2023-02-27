import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;


public class client {

    static DataInputStream dataInputStream = null;

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
            dataInputStream = new DataInputStream(clientSocket.getInputStream());

            // Send a "Hello!" message to the server
            out.println("Hello!");
            
            // Read and print the server's response
            //System.out.println("Server response: " + response);
            
            // Prompt the user for a joke command
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            Scanner sc = new Scanner(System.in);
            String command = "";
            while (true) {
            System.out.print("Enter a joke command (e.g. Joke 1, bye): ");
            try{
                command = sc.nextLine();
                out.println(command);
                String response = in.readLine();
                System.out.println("XD.");
                
                if(command.equals("bye")){
                    break;
                }
                if(command.equals("Joke 1") || command.equals("Joke 2") || command.equals("Joke 3")) {
                    // Write the joke to a file
                    //System.out.println(response);
                    try{
                        String filename = "joke" + command.substring(5) + ".txt";
                        System.out.println("MANE");
                        receiveFile(filename);
                        //System.out.println("Joke saved to file: " + filename);
                    }
                    catch (IOException e){
                        System.out.println("fail");
                        e.printStackTrace();
                    }
                }
                else{
                    //System.out.println("this was called");
                    System.out.println(response);
                    continue;
                }
            }
            catch (Exception e){
                System.out.println("borther");
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

    // public static void receiveFile(InputStream inputStream, String fileName) throws IOException {
    //     byte[] buffer = new byte[1024];
    //     int bytesRead;
    //     FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    
    //     try {
    //         while ((bytesRead = inputStream.read(buffer)) != -1) {
    //             fileOutputStream.write(buffer, 0, bytesRead);
    //             fileOutputStream.flush();
    //             //System.out.println(buffer.toString());
                // String str = Arrays.toString(buffer);
                // String[] strArr = str.substring(1, str.length() - 1).split(", ");
                // for (String s : strArr) {
                //     //System.out.print(s);
                //     if(s.endsWith("0")){
                //         //System.out.println("Mathc");
                //         return;
                //     }
                // }
                
    //             //System.out.println(Arrays.toString(buffer)); // Print buffer contents as an array
    //         }
    //     } finally {
    //         // Close resources in a finally block to ensure they are always closed
    //         fileOutputStream.close();
    //         //inputStream.close();
    //     }
    // }
    private static void receiveFile(String fileName) throws IOException{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size;
        while (dataInputStream.available() < Long.BYTES) {
        // wait for more data to be available
        }
        size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size            
            String str = Arrays.toString(buffer);
            String[] strArr = str.substring(1, str.length() - 1).split(", ");
            for (String s : strArr) {
                //System.out.println(s);
                if(s.startsWith("0")){
                    fileOutputStream.close();
                    return;
                }
            }
        }

        
        
        //fileOutputStream.close();
        
    }
}