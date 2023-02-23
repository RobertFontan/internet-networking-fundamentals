import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException{

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
