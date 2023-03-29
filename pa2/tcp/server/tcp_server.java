package server;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class tcp_server {
    public static void main(String[] args) {

        Random rand = new Random();
        int upper = 9;
        int int_rand = rand.nextInt(upper); // 0 - 9

        //int[] sentInt;

        List<Integer> prev = new ArrayList<Integer>();

        try {
            int PORT = 5414;
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Server on: " + serverSocket.getInetAddress());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
            

            for (int i = 1; i <= 10; i++) {
                
                prev.add(int_rand);
                BufferedImage image = ImageIO.read(new File("./images/sample" + int_rand + ".png"));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                byte[] buffer = outputStream.toByteArray();
                
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeInt(buffer.length);
                dataOutputStream.write(buffer, 0, buffer.length);
                dataOutputStream.flush();
                
                System.out.println("Image " + int_rand + " sent to client.");

                
                while(prev.contains(int_rand)){
                    int_rand = rand.nextInt(upper);
                }

            }

            clientSocket.shutdownOutput();
            serverSocket.close();       
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

