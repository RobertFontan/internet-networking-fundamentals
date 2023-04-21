import java.io.*;
import java.net.*;



import java.util.ArrayList;

    
    





public class server {


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
    
    static ArrayList <Pair> posts = new ArrayList<Pair>();



    
    static String enterCommand = "Enter Command: ";
    static boolean isKill = false;
    // public static void main(String[] args) {
    //     try {
    //         if(args.length != 1){
    //             System.out.println("java server <port>");
    //             return;
    //         }
    //         int PORT = Integer.parseInt(args[0]);
    //         ServerSocket serverSocket = new ServerSocket(PORT);
    //         System.out.println("Server started on port " + PORT);
    public static void main(String[] args) {
        try {
            // if(args.length != 1){
            //     System.out.println("java server <port>");
            //     return;
            // }
            int PORT = 1234;
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);        
            System.out.println("Server on: " + serverSocket.getInetAddress());
            
            
            // Socket clientSocket = serverSocket.accept();
            // System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
    
            // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // String command = "";
            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            
            // while(!posts.isEmpty()){

            //     out.println(posts.getPair());
            // }
            
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
    
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String command = "";
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            while (!isKill){
                command = in.readLine();
                System.out.println("\nReceived Command from Client: "  + command);
                
                if(command.equals("post")){
                    System.out.println("post detected");
                    // save and display posts

                    // prompt for name
                    out.println("Enter your name below: ");
                    // prompt for post
                    String name = in.readLine();

                    out.println("Enter your post below: ");
                    String post = in.readLine();
                    


                    Pair pair = new Pair(name, post);
                    System.out.println(pair.getPair());
                    out.println(pair.getPair());
                    
            
                    posts.add(pair);




                }
                else if(command.equals("display")){
                    System.out.println("display detected");
                    //out.println(posts.size());
                    System.out.println("Post size: " + posts.size());
                    out.println(posts.size());
                    
                    // this idk 
                    for(Pair pair : posts){
                        System.out.println("sending: " + pair.getPair());
                        out.println(pair.getPair());
                    }
                }
                else if(command.equals("quit")){
                    System.out.println("quit detected");
                    // listen for another?
                }
                else if(command.equals("clear")){
                    System.out.println("clear detected");

                    // clear the wall
                    out.println("Wall emptied.");
                }
                else if(command.equals("kill")){
                    System.out.println("kill detected");
                    out.println("Goodbye! Killing server.");
                    isKill = true;
                }
                else{
                    System.out.println("invalid detected");
                    out.println("Try post, kill, quit, display, and clear");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static ArrayList NamePost(String name, String post) {
        
    // }

}

