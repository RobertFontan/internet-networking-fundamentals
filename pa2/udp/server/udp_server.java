package udp.server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;


public class udp_server {
    public static void main(String[] args) throws IOException {

        //Random
        Random rand = new Random();
        int upper = 9;
        int int_rand = rand.nextInt(upper); // 0 - 9
        List<Integer> prev = new ArrayList<Integer>();


        DatagramSocket socket = new DatagramSocket(8888);
        byte[] buf = new byte[256];

        System.out.println("Waiting for client connection...");

        // Wait for connection message from the client
        DatagramPacket connectionPacket = new DatagramPacket(buf, buf.length);
        socket.receive(connectionPacket);
        String received = new String(connectionPacket.getData(), 0, connectionPacket.getLength());
        if (!"CONNECT".equals(received)) {
            System.out.println("Invalid connection message. Exiting.");
            socket.close();
            return;
        }

        System.out.println("Client connected. Sending images...");

        InetAddress clientAddress = connectionPacket.getAddress();
        int clientPort = connectionPacket.getPort();

        for (int i = 1; i <= 10; i++) {
            prev.add(int_rand);

            System.out.println("Attempting image " + int_rand);

            String fileName = "images/sample" + int_rand + ".png";
            byte[] imageData = Files.readAllBytes(Paths.get(fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(imageData.length);
            dos.write(imageData);
            dos.flush();
            byte[] dataToSend = baos.toByteArray();

            int offset = 0;
            int packetSize = 1024;
            // dataToSend.length = 170148
            while (offset < dataToSend.length) {
                int bytesToSend = Math.min(packetSize, dataToSend.length - offset);
                byte[] buffer = new byte[bytesToSend];
                //System.out.println(offset);
                System.arraycopy(dataToSend, offset, buffer, 0, bytesToSend);
                DatagramPacket packet = new DatagramPacket(buffer, bytesToSend, clientAddress, clientPort);
                socket.send(packet);
                offset += bytesToSend;

                
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Image " + int_rand + " sent");

            while(prev.contains(int_rand)){
                int_rand = rand.nextInt(upper);
            }

        }
        socket.close();
    }
}
