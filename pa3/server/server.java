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
    // static ArrayList <Pair> posts = new ArrayList<Pair>();

    static class Thread{
        String title = "";
        ArrayList <Pair> posts = new ArrayList<Pair>();

        public Thread(String title, ArrayList<Pair> posts){
            this.title = title;
            this.posts = posts;
        }

        public String getTitle(){
            return title;
        }

        public ArrayList<Pair> getPosts(){
            return posts;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public void setPosts(ArrayList <Pair> posts){
            this.posts = posts;
        }

        public void addPost(Pair post){
            posts.add(post);
        }

        public void clear(){
            posts.clear();
        }

    }

    static ArrayList <Thread> threads = new ArrayList<Thread>();

    
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

                        // sending thread number
                        System.out.println("Threads: " + threads.size());
                        out.println(threads.size());

                        int i = 0;
                        for(Thread thread : threads){
                            System.out.println("Sending: " + thread.getTitle());
                            out.println(i + ". " + thread.getTitle()); // this is displayed in client
                            i++;
                        }

                        String index = in.readLine(); // suppose to get index
                        System.out.println("Received: " + index);
                        
                        String ind = index.trim();
                        int requested = Integer.parseInt(ind);
                        Thread thread = threads.get(requested);
                        System.out.println("Posting on: " + thread.getTitle());


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

                        // adding it to thread
                        thread.addPost(pair);
                        
                        //System.out.println(thread.getPosts());
                        //out.println("Made Post");

                    }
                    else if(command.equals("help")){
                        continue;
                    }
                    // thread NEW
                    else if(command.equals("new")){
                        System.out.println("New Thread Command");
                        // prompt for name
                        out.println("Enter the new thread title below: ");


                        // prompt for post
                        String title = in.readLine();


                        System.out.println("Making post");
                        // prompt for name
                        out.println("Enter your name below: ");
                        // prompt for post
                        String name = in.readLine();
                        out.println("Enter your post below: ");
                        String post = in.readLine();
                        
                        Pair pair = new Pair(name, post);
                        out.println(pair.getPair());

                        ArrayList<Pair> newPosts = new ArrayList<Pair>();
                        newPosts.add(pair);


                        // System.out.println(pair.getPair());
                        // out.println(pair.getPair());
                        // posts.add(pair);

                        Thread thread = new Thread(title, newPosts);

                        threads.add(thread);


                    }
                    else if(command.equals("display")){
                        System.out.println("Display detected");

                        // sending thread number
                        System.out.println("Threads: " + threads.size());
                        out.println(threads.size());

                        int i = 0;
                        for(Thread thread : threads){
                            System.out.println("Sending: " + thread.getTitle());
                            out.println(i + ". " + thread.getTitle()); // this is displayed in client
                            i++;
                        }
                        // error happens here 
                        if(threads.size() == 0){
                            continue;
                        }
                        else{
                            String index = in.readLine(); // suppose to get index
                            String ind = index.trim();
                            int requested = Integer.parseInt(ind);
                            ArrayList<Pair> posts = threads.get(requested).getPosts();


                            // sending post number
                            System.out.println("Post size: " + posts.size());
                            out.println(posts.size());
                            
                            // Iterate through posts
                            for(Pair pair : posts){
                                System.out.println("sending: " + pair.getPair());
                                out.println(pair.getPair());
                            }
                        }
                    }
                    else if(command.equals("quit")){
                        System.out.println("Quit Command");
                        break; // Break goes back to accept (to listen to another)
                    }
                    else if(command.equals("clear")){
                        System.out.println("clear Command");
                        // clear the wall
                        // sending thread number
                        System.out.println("Threads: " + threads.size());
                        out.println(threads.size());

                        int i = 0;
                        for(Thread thread : threads){
                            System.out.println("Sending: " + thread.getTitle());
                            out.println(i + ". " + thread.getTitle()); // this is displayed in client
                            i++;
                        }

                        String index = in.readLine(); // suppose to get index
                        System.out.println("Received: " + index);
                        
                        String ind = index.trim();
                        int requested = Integer.parseInt(ind);
                        Thread thread = threads.get(requested);
                        System.out.println("Clearing: " + thread.getTitle());


                        // clearing the thread it to thread
                        thread.clear();
                        
                        out.println("Thread emptied");
                    }
                    else if(command.equals("kill")){
                        System.out.println("Kill Command");
                        out.println("Goodbye! Killing server.");
                        isKill = true;
                        // close everything
                        serverSocket.close();
                        clientSocket.close();
                    }
                    else if(command.equals("delete")){
                        System.out.println("Delete Command");
                        // clear the wall
                        // sending thread number
                        System.out.println("Threads: " + threads.size());
                        out.println(threads.size());

                        int i = 0;
                        for(Thread thread : threads){
                            System.out.println("Sending: " + thread.getTitle());
                            out.println(i + ". " + thread.getTitle()); // this is displayed in client
                            i++;
                        }

                        String index = in.readLine(); // suppose to get index
                        System.out.println("Received: " + index);
                        
                        String ind = index.trim();
                        int requested = Integer.parseInt(ind);
                        Thread thread = threads.get(requested);
                        System.out.println("Deleting: " + thread.getTitle());


                        // clearing the thread it to thread
                        threads.remove(requested);
                        
                        out.println("Thread deleted");
                    }
                    else{ //invalid command
                        System.out.println("Invalid Command");
                        out.println("Commands are: new, post, display, clear, delete, kill, quit, and help (for more detail)");
                    }
                }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

