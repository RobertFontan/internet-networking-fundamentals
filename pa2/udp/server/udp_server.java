package udp.server;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class udp_server {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(5414);
        System.out.println("Server listening on port 5414...");
        
        byte[] buffer = new byte[65507]; // Maximum UDP packet size
        
        for (int i = 1; i <= 10; i++) {
            BufferedImage image = ImageIO.read(new File("./images/sample" + i + ".png"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] imageBuffer = outputStream.toByteArray();
            
            int imageLength = imageBuffer.length;
            int packetCount = (int) Math.ceil(imageLength / (double) buffer.length);
            
            for (int j = 0; j < packetCount; j++) {
                int offset = j * buffer.length;
                int length = Math.min(buffer.length, imageLength - offset);
                System.arraycopy(imageBuffer, offset, buffer, 0, length);
                
                DatagramPacket packet = new DatagramPacket(buffer, length, InetAddress.getLocalHost(), 5414);
                socket.send(packet);
            }
            
            System.out.println("Image " + i + " sent to client.");
        }
        
        socket.close();
    }
}
