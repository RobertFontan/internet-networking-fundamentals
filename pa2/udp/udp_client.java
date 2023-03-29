package udp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class udp_client {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(9999);

        // Send connection message to the server
        byte[] buf = "CONNECT".getBytes();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        DatagramPacket connectionPacket = new DatagramPacket(buf, buf.length, serverAddress, 8888);
        socket.send(connectionPacket);

        for (int i = 1; i <= 10; i++) {
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // byte[] buffer = new byte[1024];
            // int totalBytesRead = 0;
            // int bytesRead;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int expectedSize = -1;
            int totalBytesRead = 0;



            while (true) 
            {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                int bytesRead = packet.getLength();
                baos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                if (expectedSize == -1 && totalBytesRead >= 4) {
                    expectedSize = ByteBuffer.wrap(baos.toByteArray(), 0, 4).getInt();
                }

                if (expectedSize != -1 && totalBytesRead >= expectedSize + 4) {
                    byte[] imageData = new byte[expectedSize];
                    System.arraycopy(baos.toByteArray(), 4, imageData, 0, expectedSize);

                    ByteArrayInputStream imageBais = new ByteArrayInputStream(imageData);
                    BufferedImage image = ImageIO.read(imageBais);
                    ImageIO.write(image, "png", new File("received_image" + i + ".png"));

                    break;
                }
            }

            System.out.println("Image " + i + " recieved");

        }
        socket.close();
    }
}
