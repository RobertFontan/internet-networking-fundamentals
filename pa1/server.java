import java.io.*;
import java.net.*;

public class server {
    private static final int port = 9090;
    //public static File[] JOKE_FILES;
    List<File> JOKE_FILES;
    //public static final String[] JOKE_FILES = {"joke1.txt", "joke2.txt", "joke3.txt"};

    public static void main(String[] args) {
        try{
            FileWriter joke1 = new FileWriter("joke1.txt");
            joke1.write("What do they call a group of network engineers? An outage. ");
            joke1.close();
            FileWriter joke2 = new FileWriter("joke2.txt");
            joke2.write("I would tell you a joke about UDP, but you probably wouldn't get it.");
            joke2.close();
            FileWriter joke3 = new FileWriter("joke3.txt");
            joke3.write("DNS is the root of all problems.");
            joke3.close();

            JOKE_FILES.toFile(joke1);
        }
        catch(IOException e){
            System.out.println("An error occured creating the jokes");
            e.printStackTrace();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from client " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                if (request.startsWith("Joke")) {
                    try {
                        int jokeNumber = Integer.parseInt(request.substring(5).trim());
                        if (jokeNumber < 1 || jokeNumber > JOKE_FILES.length) {
                            out.println("Invalid joke number. Please choose a number between 1 and " + JOKE_FILES.length);
                        } 
                        else {
                            String jokeFilename = JOKE_FILES[jokeNumber - 1];
                            File jokeFile = new File(jokeFilename);
                            if (!jokeFile.exists()) {
                                out.println("Joke file not found.");
                            } 
                            else {
                                BufferedReader jokeReader = new BufferedReader(new FileReader(jokeFile));
                                String line;
                                while ((line = jokeReader.readLine()) != null) {
                                    out.println(line);
                                }
                                jokeReader.close();
                            }
                        }
                    } 
                    catch (NumberFormatException e) {
                        out.println("Invalid joke number format. Please enter a number between 1 and " + JOKE_FILES.length);
                    }
                } 
                else if(request.startsWith("bye")){
                    clientSocket.close();
                }
                else {
                    out.println("Invalid request. Please enter a request in the form 'Joke <number>'.");
                }
//                clientSocket.close();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

