package udp;
import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class udp_client {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Connected to server.");
        
        byte[] buffer = new byte[65507]; // Maximum UDP packet size
        
        for (int i = 1; i <= 10; i++) {
            byte[] imageBuffer = new byte[0];
            
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                if (packet.getData()[0] == -1) {
                    break; // End of image
                }
                
                imageBuffer = Arrays.copyOf(imageBuffer, imageBuffer.length + packet.getLength());
                System.arraycopy(packet.getData(), 0, imageBuffer, imageBuffer.length - packet.getLength(), packet.getLength());
            }
            
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBuffer));
            ImageIO.write(image, "png", new File("received_sample" + i + ".png"));
            
            System.out.println("Image " + i + " received from server and saved to file.");
        }
        
        socket.close();
    }
}
