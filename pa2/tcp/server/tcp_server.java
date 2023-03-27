package server;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class tcp_server {

    static String joke1 = "Why do programmers prefer dark mode? Because light attracts bugs.";
    static String joke2 = "Why did the programmer quit his job? He didn't get arrays.";
    static String joke3 = "What's the object-oriented way to become wealthy? Inheritance.";


    public static void main(String[] args) {
        try {

            int PORT = 5414;
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Server on: " + serverSocket.getInetAddress());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
            

            for (int i = 1; i <= 10; i++) {
                BufferedImage image = ImageIO.read(new File("./images/sample" + i + ".png"));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                byte[] buffer = outputStream.toByteArray();
                
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeInt(buffer.length);
                dataOutputStream.write(buffer, 0, buffer.length);
                dataOutputStream.flush();
                
                System.out.println("Image " + i + " sent to client.");
            }

            clientSocket.shutdownOutput();
            serverSocket.close();

                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

