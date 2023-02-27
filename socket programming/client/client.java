package client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class client {
    static DataOutputStream dataOutputStream = null;
    static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws IOException{
        Socket clientSocket;
        PrintWriter outputToServer;
        BufferedReader inputFromServer;
        int port = 5414;

        clientSocket = new Socket("localhost", port);
        OutputStream outputStream = clientSocket.getOutputStream();
        outputToServer = new PrintWriter(outputStream, true);
        InputStream inputStream = clientSocket.getInputStream();
        inputFromServer = new BufferedReader(new InputStreamReader(inputStream));
        Scanner sc = new Scanner(System.in); // to write in the terminal
        // each line is scanned and sent to server

        // String fileName = "joke1.txt";
        // FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        // String requestedFile = "file.txt";
        // OutputStream outputStream = clientSocket.getOutputStream();
        // outputStream.write(requestedFile.getBytes());
        // byte[] buffer = new byte[1024];
        // int bytesRead;


        String toServer = "";
        while(!"bye".equalsIgnoreCase(toServer)){
            toServer = sc.nextLine();
            outputToServer.println(toServer);
            String responseFromServer = inputFromServer.readLine();

            System.out.println(responseFromServer); // here lies the reponse
            
            if(responseFromServer.contains("Send")){
                receiveFile("joke1.txt", inputStream, outputStream);
            }           

        }
        
        inputFromServer.close();
        outputToServer.close();
        clientSocket.close();
        sc.close();
    }

    private static void receiveFile(String fileName, InputStream inputStream, OutputStream outputStream) throws IOException{
        //String fileName = "joke1.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        String requestedFile = "file.txt";
        outputStream.write(requestedFile.getBytes());
        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer, 0, bytesRead);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        // inputStream.close();
        // outputStream.close();
    }
}

      