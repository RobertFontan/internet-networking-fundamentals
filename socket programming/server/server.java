package server;
import java.net.*;

import javax.swing.plaf.multi.MultiFileChooserUI;

import java.io.*;


public class server {
    static DataOutputStream dataOutputStream = null;
    static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws IOException{
        // creating joke files
        // try{
        



        //     FileWriter joke2 = new FileWriter("joke2.txt");
        //     joke2.write("I would tell you a joke about UDP, but you probably wouldn't get it.");
        //     joke2.close();
        //     FileWriter joke3 = new FileWriter("joke3.txt");
        //     joke3.write("DNS is the root of all problems.");
        //     joke3.close();

        //    JOKE_FILES.toFile(joke1);
        // }
        // catch(IOException e){
        //     System.out.println("An error occured creating the jokes");
        //     e.printStackTrace();
        // }


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
        OutputStream outputStream = clientSocket.getOutputStream();
        outputToClient = new PrintWriter(outputStream, true);
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
                outputToClient.println("Sending Joke File");
                sendFile("joke1.txt", outputToClient, outputStream);
                // joke file time                
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


    private static void sendFile(String fileName, PrintWriter outputToClient, OutputStream outputStream) throws IOException{
        File file = new File(fileName);
        FileWriter joke1 = new FileWriter(file);
        joke1.write("What do they call a group of network engineers? An outage. ");
//        joke1.flush();
        joke1.close();

        if (!file.exists()) {
            outputToClient.println("File not found");
            return;
        }
        // this sends the file (i hope)
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
        outputStream.flush();

        // this reads the joke out
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;        
        
        while ((line = fileReader.readLine()) != null) {
            outputToClient.println(line);
        }

        fileReader.close();
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