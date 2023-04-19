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


          //  while(true){

             // init
             out.print("Wall Contents \n -------------");
             while (isKill == false){
                 out.print(enterCommand);
                 command = in.readLine();
                 System.out.println("Received Command from Client: "  + command);
                if(command.equals("post")){
                    System.out.print("post detected");

                }
                else if(command.equals("clear")){
                    System.out.print("clear detected");
                }
                else if(command.equals("quit")){
                    
                    System.out.print("quit detected");
                }
                else if(command.equals("kill")){
                    System.out.print("kill detected");
                    
                }
                else{
                    System.out.println("invalid command");
                }
                
                
                 //  switch(command){
                //      case "post":
                //          System.out.println("post detected");
                //      case "quit":
                //          System.out.println("quit detected");
                //          break;
                //      case "kill":
                //          System.out.println("kill detected");
                //          serverSocket.close();
                //          break;
                //      case "clear":
                //          System.out.println("clear detected");
                //          break;
                //      default:
                //          System.out.println("invalid command");
                //  }
                }

           // }
            //     while (true){
            //         try{
            //         command = in.readLine();
            //         System.out.println("Received command from client: " + command);
            //         if (command.startsWith("Joke")) {
            //             try{
            //                 int jokeNum = Integer.parseInt(command.substring(5));
            //                 switch(jokeNum) {
            //                     case 1:
            //                         out.println(joke1);
            //                         break;
            //                     case 2:
            //                         out.println(joke2);
            //                         break;
            //                     case 3:
            //                         out.println(joke3);
            //                         break;
            //                     default:
            //                         out.println("Invalid Joke Number. Try 1-3");
            //                   }
            //             }
            //             catch(Exception e){
            //                 out.println("Error: Invalid command. Try inputting Joke <number>");
            //             }    
            //         }             
            //         else { //this happens on null
            //             out.println("Error: Invalid command. Try inputting Joke <number>");
            //         }
            //     } catch (NullPointerException e){
            //         System.out.println("No connection established");
            //         break;
            //     }
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

