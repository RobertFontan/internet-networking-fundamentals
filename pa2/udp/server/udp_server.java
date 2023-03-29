package udp.server;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

/*
 * /* -- Removes all received files
 * rm *received* 
 *  
 */
public class udp_server {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(5414);

        System.out.println("Server listening on port 5414...");
        
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            System.out.println("Connection from client " + clientAddress + " on port " + clientPort);
            
            for(int i = 1; i <= 10; i++){

                BufferedImage img = ImageIO.read(new File("./images/sample" + i + ".png"));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                ImageIO.write(img, "png", byteArrayOutputStream);

                byte[] imageData = byteArrayOutputStream.toByteArray();
                int packetSize = 1024; // Maximum packet size
                int packetCount = (int) Math.ceil((double) imageData.length / packetSize);
                
                for (int j = 0; j < packetCount; j++) {
                    int offset = j * packetSize;
                    int length = Math.min(packetSize, imageData.length - offset);
                    byte[] data = new byte[length];
                    System.arraycopy(imageData, offset, data, 0, length);
                    DatagramPacket sendPacket = new DatagramPacket(data, length, clientAddress, clientPort);
                    datagramSocket.send(sendPacket);
                }
                
                System.out.println("Image "+ i + " sent to client " + clientAddress + " on port " + clientPort);
        }


        }
    }
}
