import java.io.*;
import java.net.*;

public class server {

    private static final int PORT = 1234;
    private static final String[] JOKE_FILENAMES = {"joke1.txt", "joke2.txt", "joke3.txt"};
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            createJokeFiles();
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
                
                // Send initial "Hello!" message to client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Hello!");
                
                // Receive command from client and respond accordingly
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String command;
                while ((command = in.readLine()) != "bye") {
                    System.out.println("Received command from client: " + command);
                    if (command.startsWith("Joke ")) {
                        int jokeNum = Integer.parseInt(command.substring(5));
                        if (jokeNum >= 1 && jokeNum <= JOKE_FILENAMES.length) {
                            String jokeFilename = JOKE_FILENAMES[jokeNum - 1];
                            File jokeFile = new File(jokeFilename);
                            if (jokeFile.exists() && jokeFile.isFile()) {
                                // Send joke file to client
                                FileInputStream fileIn = new FileInputStream(jokeFile);
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = fileIn.read(buffer)) != -1) {
                                    clientSocket.getOutputStream().write(buffer, 0, bytesRead);
                                }
                                fileIn.close();
                            } else {
                                out.println("Error: Joke file not found");
                            }
                        } else {
                            out.println("Error: Invalid joke number");
                        }
                    } else if (command.equals("bye")) {
                        // Close socket on "bye" command
                        clientSocket.close();
                        break;
                    } else {
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


    

}

