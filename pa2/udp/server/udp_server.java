package udp.server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;


public class udp_server {
    public static void main(String[] args) throws IOException {
        // Input validation 
        if(args.length != 1){
            System.out.println("java udp_server.java <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);

        
        //Random
        Random rand = new Random();
        int upper = 10;
        int int_rand = rand.nextInt(upper); // 0 - 9
        List<Integer> prev = new ArrayList<Integer>();

        //Measurements
        long beginAccess, endAccess, totalAccessTime;

        //Summary Statistics
        List<Integer> accessTimeList = new ArrayList<Integer>();



        DatagramSocket socket = new DatagramSocket(port);
        byte[] buf = new byte[256];
        System.out.println("Waiting for client connection in port " + port + "...");

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

            String fileName = "images/sample" + int_rand + ".png";

            beginAccess = System.currentTimeMillis();
            byte[] imageData = Files.readAllBytes(Paths.get(fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(imageData.length);
            dos.write(imageData);
            endAccess = System.currentTimeMillis();

            totalAccessTime = endAccess - beginAccess;
            int toAdd = (int)totalAccessTime;
            accessTimeList.add(toAdd);
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
            System.out.println("Image " + int_rand + " sent and read into byte array in: " + totalAccessTime + "ms");

            while(prev.contains(int_rand)){
                int_rand = rand.nextInt(upper);
                if(i == 10){
                    break;
                }
                //System.out.println(i);
            }

        }
        DoubleSummaryStatistics stats = calculateStats(accessTimeList);
        
        System.out.println("Minimum: " + stats.getMin() + "ms");
        System.out.println("Maximum: " + stats.getMax() + "ms");
        System.out.println("Mean: " + stats.getAverage() + "ms");

        double mean = stats.getAverage();
        double sumOfSquares = accessTimeList.stream().mapToDouble(i -> Math.pow(i - mean, 2)).sum();
        double variance = sumOfSquares / (double)(accessTimeList.size() - 1);
        double stdDev = Math.sqrt(variance);

        System.out.println("Standard deviation: " + stdDev + "ms");

        
        //socket.close();
    }

    public static DoubleSummaryStatistics calculateStats(List<Integer> intList) {
        // Calculate summary statistics using Java 8 Stream API
        DoubleSummaryStatistics stats = intList.stream().mapToDouble(Integer::intValue).summaryStatistics();
        return stats;
    }
}


