import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException{
        InputStreamReader in = null;
        OutputStreamWriter out = null;
        BufferedReader bufferedReader = null;;
        BufferedWriter bufferedWriter = null;
    
        Socket s = null;
        ServerSocket ss = new ServerSocket(5414);

        while(true){
            
            try{
                s = ss.accept();
                in = new InputStreamReader(s.getInputStream());
                out = new OutputStreamWriter(s.getOutputStream());

                bufferedReader = new BufferedReader(in);
                bufferedWriter = new BufferedWriter(out);

                while(true){
                    //reading from client
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgFromClient);
                    // this displays the command just sent
                    bufferedWriter.write(msgFromClient);
                    
                    if(msgFromClient.startsWith("Joke")){
                        try{
                            bufferedWriter.write("yes");
                        }
                        catch (IOException e) {
                            bufferedWriter.write("Invalid Format");
                        }
                    }
                    // ends client connection
                    else if(msgFromClient.equalsIgnoreCase("bye")){
                        break;
                    }
                    else{
                        bufferedWriter.write("Invalid Completely");
                    }
                }
                // this happens on break
                s.close(); // close socket
                in.close(); 
                out.close();
                bufferedReader.close();
                bufferedWriter.close();
                ss.close();

            }
            catch (IOException e){
                e.printStackTrace();
            }

        }


        //System.out.println("Client Connected");




    }
}
