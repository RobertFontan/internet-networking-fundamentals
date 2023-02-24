import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException{
        // creating joke files
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

          //  JOKE_FILES.toFile(joke1);
        }
        catch(IOException e){
            System.out.println("An error occured creating the jokes");
            e.printStackTrace();
        }


        ServerSocket serverSocket;
        int port = 5414;
        Socket clientSocket;
        PrintWriter outputToClient;
        BufferedReader inputFromClient;

        System.out.println("Start Socket Server on Port: " + port);
        serverSocket = new ServerSocket(port); 
        clientSocket = serverSocket.accept();
        System.out.println("Connection: " + serverSocket.getInetAddress());
        
        //i/o from client
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = "";
        
        // communication will continue forever until bye
        outputToClient.println("Hello!");

        while(!"bye".equalsIgnoreCase(message)){
            message = inputFromClient.readLine(); //reading one line at a time from client
            System.out.println("Client: " + message);

            if(message.contains("bye"))
                break;
            if(message == null)
                continue;
            if(message.contains("Joke")){
                // reading the joke number
                try{
                    int jokeNumber = Integer.parseInt(message.substring(5).trim());
                    
                    //String jokeFilename = JOKE_FILES[jokeNumber - 1];
                    String jokeFilename = "";

                    switch(jokeNumber){
                        case 1:
                            jokeFilename = "joke1.txt";
                            break;
                        case 2:
                            jokeFilename = "joke2.txt";
                            break;
                        case 3:
                            jokeFilename = "joke3.txt";
                            break;
                        default:
                            outputToClient.println("Try numbers 1-3");
                    }
                    
                    File jokeFile = new File(jokeFilename);
                
                    
                    BufferedReader jokeReader = new BufferedReader(new FileReader(jokeFile));
                    String line;
                    while ((line = jokeReader.readLine()) != null) {
                        outputToClient.println(line);
                    }
                    jokeReader.close();
                }
                catch (Exception e){
                    outputToClient.println("Try Joke <number>");
                }
                
            }
            else {
                outputToClient.println("Invalid command :<");
            }

        }
        outputToClient.println("Bye!");
        // breaking from while closes everything
        inputFromClient.close();
        outputToClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server Socket closed");
    
    }
}








        /*
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;;
        BufferedWriter bufferedWriter = null;
    
        Socket s = null;
        ServerSocket ss = new ServerSocket(5414);
        System.out.println("Server Started on Port: " + ss.getLocalPort());

        while(true){
            try{
                s = ss.accept();
                System.out.println("Accepted connection from client " + s.getInetAddress());

                inputStreamReader = new InputStreamReader(s.getInputStream());
                outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while(true){
                    //reading from client
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgFromClient);
                    // this displays the command just sent
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    
                    if(msgFromClient.startsWith("Joke")){
                        try{
                            bufferedWriter.write("this is where a joke would appear");
                        }
                        catch (IOException e) {
                            bufferedWriter.write("Invalid Format. Use Joke <1-3>");
                        }
                    }
                    else if(msgFromClient.equalsIgnoreCase("bye")){
                        break;
                        // ends client connection
                    }
                    else{
                        bufferedWriter.write("Invalid Format. Use Joke <number>");
                    }
                }

                s.close(); // close socket
                inputStreamReader.close(); 
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

*/