import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.reflect.InaccessibleObjectException;

public class client {
    public static void main(String[] args) throws IOException{
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;;
        BufferedWriter bufferedWriter = null;
    
        Socket s = new Socket("localhost", 5414);

        inputStreamReader = new InputStreamReader(s.getInputStream());
        outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        bufferedWriter = new BufferedWriter(outputStreamWriter);


        Scanner scanner = new Scanner(System.in);

        while(true){
            String msgToSend = scanner.nextLine();
            
            //this sends message from server
            bufferedWriter.write(msgToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush(); //flushes its string

            // reads from the server
            System.out.println(bufferedReader.readLine());

            // breaks from everything
            if(msgToSend.equals("bye")){
                break;
            }
        }    

      


    }
}
