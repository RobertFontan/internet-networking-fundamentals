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
        outputToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStream inputStream = clientSocket.getInputStream();
        inputFromServer = new BufferedReader(new InputStreamReader(inputStream));
        Scanner sc = new Scanner(System.in); // to write in the terminal
        // each line is scanned and sent to server

        String fileName = "joke1.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        String requestedFile = "file.txt";
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(requestedFile.getBytes());
        byte[] buffer = new byte[1024];
        int bytesRead;


        String toServer = "";
        while(!"bye".equalsIgnoreCase(toServer)){
            toServer = sc.nextLine();
            outputToServer.println(toServer);
            String responseFromServer = inputFromServer.readLine();

            System.out.println(responseFromServer); // here lies the reponse
            while((bytesRead = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            

        }
        inputFromServer.close();
        outputToServer.close();
        clientSocket.close();
        sc.close();
    }

    private static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }

    // private static void receiveFile(String fileName) throws Exception{
    //     byte[] contents = new byte[10000]; 
    //     FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    //     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    //     InputStream inputStream = clientSocket.getInputStream();
    //     int bytesRead = 0;
    //     while((bytesRead=inputStream.read(contents)) != -1)
    //         bufferedOutputStream.write(contents, 0, bytesRead);
    //     bufferedOutputStream.flush();
    // }


}

      