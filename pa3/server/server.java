import java.io.*;
import java.net.*;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
public class server {

    // pair class that makes posts easier to manage
    static class Pair{
        String name = "";
        String post = "";

        public Pair(String name, String post){
            this.name = name;
            this.post = post;
        }

        public Pair()
        {
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setPost(String post) {
            this.post = post;
        }
        public String getPair(){
            return name + ": " + post;
        }

    }
    // ArrayList holding all the posts 
    static ArrayList <Pair> posts = new ArrayList<Pair>();



    
    static boolean isKill = false;
  
    public static void main(String[] args) {
        try {
            if(args.length != 1){
                System.out.println("java server <port>");
                return;
            }
            // get port from command line arg if not then send back
            int PORT = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);        
            System.out.println("Server on: " + serverSocket.getInetAddress());
            
            
        
            while(true){
                // Loop restarts when client disconnects and continue when new one is accepted
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
                // getting input and output
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String command = "";
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


                while (!isKill){
                    command = in.readLine();
                    System.out.println("\nReceived Command from Client: "  + command);
                    // Post command
                    if(command.equals("post")){
                        System.out.println("Post Command");
                        // prompt for name
                        out.println("Enter your name below: ");
                        // prompt for post
                        String name = in.readLine();
                        out.println("Enter your post below: ");
                        String post = in.readLine();
                        
                        // save and display posts
                        Pair pair = new Pair(name, post);
                        System.out.println(pair.getPair());
                        out.println(pair.getPair());
                        posts.add(pair);
                    }
                    else if(command.equals("display")){
                        System.out.println("Display detected");
                        // sending post number
                        System.out.println("Post size: " + posts.size());
                        out.println(posts.size());
                        
                        // Iterate through posts
                        for(Pair pair : posts){
                            System.out.println("sending: " + pair.getPair());
                            out.println(pair.getPair());
                        }
                    }
                    else if(command.equals("quit")){
                        System.out.println("Quit Command");
                        break; // Break goes back to accept (to listen to another)
                    }
                    else if(command.equals("clear")){
                        System.out.println("clear Command");
                        // clear the wall
                        posts.clear();
                        out.println("Wall emptied.");
                    }
                    else if(command.equals("kill")){
                        System.out.println("Kill Command");
                        out.println("Goodbye! Killing server.");
                        isKill = true;
                        // close everything
                        serverSocket.close();
                        clientSocket.close();
                    }
                    else{ //invalid command
                        System.out.println("Invalid Command");
                        out.println("Commands are: post, kill, quit, display, clear");
                    }
                }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

