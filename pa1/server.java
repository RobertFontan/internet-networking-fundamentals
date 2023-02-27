import java.io.*;
import java.net.*;

public class server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    private static final int PORT = 1234;
    private static final String[] JOKE_FILENAMES = {"joke1.txt", "joke2.txt", "joke3.txt"};
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            createJokeFiles();
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
                
                // Send initial "Hello!" message to client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Hello!");
                
                // Receive command from client and respond accordingly
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String command = "";
                while (true) {
                    command = in.readLine();
                    System.out.println("Received command from client: " + command);
                        
                    if(command == null){
                        continue;
                    }
                    if (command.equals("Joke 1") || command.equals("Joke 2") || command.equals("Joke 3")) {
                            int jokeNum = Integer.parseInt(command.substring(5));
                            if (jokeNum >= 1 && jokeNum <= JOKE_FILENAMES.length) {
                                String jokeFilename = JOKE_FILENAMES[jokeNum - 1];
                                sendFile(jokeFilename);
                                System.out.println("BUV");
                            }
                            else {
                                out.println("Error: Invalid joke number");
                            }
                    }             
                     
                    
                   
                    else { //this happens on null
                        out.println("Error: Invalid command");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createJokeFiles() throws IOException{
        // Jokes to write in the files
        String joke1 = "Why do programmers prefer dark mode? Because light attracts bugs.";
        String joke2 = "Why did the programmer quit his job? He didn't get arrays.";
        String joke3 = "What's the object-oriented way to become wealthy? Inheritance.";
    
        // Create joke1.txt file and write joke1 to it
        try (FileWriter writer = new FileWriter("joke1.txt")) {
            writer.write(joke1);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to joke1.txt");
            e.printStackTrace();
        }
    
        // Create joke2.txt file and write joke2 to it
        try (FileWriter writer = new FileWriter("joke2.txt")) {
            writer.write(joke2);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to joke2.txt");
            e.printStackTrace();
        }
    
        // Create joke3.txt file and write joke3 to it
        try (FileWriter writer = new FileWriter("joke3.txt")) {
            writer.write(joke3);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to joke3.txt");
            e.printStackTrace();
        }
    }


    private static void sendFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File not found or is not a file: " + path);
        }
    
        FileInputStream fileInputStream = new FileInputStream(file);
    
        try {
            long fileSize = file.length();
            dataOutputStream.writeLong(fileSize);
    
            byte[] buffer = new byte[1024 * 64]; // 64 KB buffer size
            int bytesRead;
            long bytesSent = 0;
    
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
                bytesSent += bytesRead;
            }
    
            if (bytesSent != fileSize) {
                throw new IOException("Could not send entire file: " + path);
            }
    
            dataOutputStream.flush();
        } finally {
            fileInputStream.close();
        }
    }

}

