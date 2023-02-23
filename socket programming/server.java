import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException{
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

