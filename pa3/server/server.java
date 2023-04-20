import java.io.*;
import java.net.*;
import java.rmi.ConnectIOException;

public class server {

    // static String joke1 = "Why do programmers prefer dark mode? Because light attracts bugs.";
    // static String joke2 = "Why did the programmer quit his job? He didn't get arrays.";
    // static String joke3 = "What's the object-oriented way to become wealthy? Inheritance.";

    // static String wallContent = "Wall Contents\n-------------";
    static String enterCommand = "Enter Command: ";
    static boolean isKill = false;
    public static void main(String[] args) {
        try {
            if(args.length != 1){
                System.out.println("java server <port>");
                return;
            }
            int PORT = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Server on: " + serverSocket.getInetAddress());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
    
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String command = "";
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.print("Wall Contents \n -------------");
            while (!isKill){
                // we should init here 
                                
                //do {
                    command = in.readLine();
                    System.out.println("Received Command from Client: "  + command);
                    
                    if(!command.isBlank()){
                        if(command.equals("post")){
                            // save and display posts 
                            out.println("post detected");
                        }
                        else if(command.equals("quit")){
                            out.println("quit detected");
                        }
                        else if(command.equals("clear")){
                            out.println("clear detected");
                        }
                        else if(command.equals("kill")){
                            out.println("kill detected");
                            isKill = true;
                        }
                        else{
                            out.println("invalid command");
                        }
                    }
                    else{
                        out.println("well you should type something...");
                    }
                //} while(!isKill);
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

