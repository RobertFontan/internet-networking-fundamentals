package udp;
import java.io.*;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class udp_client {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
        socket.send(new DatagramPacket(new byte[] { 0 }, 1, InetAddress.getLocalHost(), 5414));
        System.out.println("Requesting image from server...");
        
        socket.receive(packet);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (true) {
            outputStream.write(packet.getData(), 0, packet.getLength());
            if (packet.getLength() < buffer.length) {
                break; // End of image marker received
            }
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
        }
        
        byte[] imageBuffer = outputStream.toByteArray();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBuffer));
        if (image == null) {
            System.err.println("Error decoding image.");
            return;
        }
        
        File outputFile = new File("./received_image.png");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.err.println("Error writing image to file: " + e.getMessage());
            return;
        }
        
        System.out.println("Image received from server and saved to file " + outputFile.getPath() + ".");
        socket.close();
    }
}
