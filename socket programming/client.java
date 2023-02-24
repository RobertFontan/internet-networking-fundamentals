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
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Scanner sc = new Scanner(System.in); // to write in the terminal
        // each line is scanned and sent to server
        String toServer = "";
        while(!"bye".equalsIgnoreCase(toServer)){
            toServer = sc.nextLine();
            outputToServer.println(toServer);
            String responseFromServer = inputFromServer.readLine();

            System.out.println(responseFromServer); // here lies the reponse

            // receving file
            if(responseFromServer.contains("joke1")){
                byte[] contents = new byte[10000]; 
                FileOutputStream fileOutputStream = new FileOutputStream("joke1compl.txt");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                InputStream inputStream = clientSocket.getInputStream();
                int bytesRead = 0;
                while((bytesRead= inputStream.read(contents)) != -1)
                    bufferedOutputStream.write(contents, 0, bytesRead);
                bufferedOutputStream.flush();
            }


        }
        inputFromServer.close();
        outputToServer.close();
        clientSocket.close();
        sc.close();
    }

    // private static void receiveFile(String fileName) throws Exception{
    //     int bytes = 0;
    //     FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
    //     long size = dataInputStream.readLong();     // read file size
    //     byte[] buffer = new byte[4*1024];
    //     while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
    //         fileOutputStream.write(buffer,0,bytes);
    //         size -= bytes;      // read upto file size
    //     }
    //     fileOutputStream.close();
    // }

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

        /*
        Socket s = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;;
        BufferedWriter bufferedWriter = null;
    
        try{
            s = new Socket("localhost", 5414);
            inputStreamReader = new InputStreamReader(s.getInputStream());
            outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);


            Scanner scanner = new Scanner(System.in);
            String msgTosend = scanner.nextLine();                
            while(true){
                //this sends message from server
                bufferedWriter.write(msgTosend);   
                bufferedWriter.newLine();            
                bufferedWriter.flush(); //flushes its string

                // reads from the server
                System.out.println(bufferedReader.readLine());

                // breaks from everything
                if(msgTosend.equals("bye")){
                    break;
                }
                msgTosend = scanner.nextLine();
            }    
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try{
                if(s != null)
                    s.close();
                if(inputStreamReader != null)
                    inputStreamReader.close();
                if(outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if(bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
      


    }
}
*/