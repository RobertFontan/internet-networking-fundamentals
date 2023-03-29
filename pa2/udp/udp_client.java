package udp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class udp_client {
    public static void main(String[] args) throws IOException {
        // Input validation
        if (args.length != 2) {
            System.err.println("java udp_client.java <serverURL> <port number>");
            return;
        }

        // get server name and port number from command line arguments
        String serverURL = args[0];
        int port = Integer.parseInt(args[1]);



        DatagramSocket socket = new DatagramSocket(9999);

        //Measurements
        long beginTime, endTime, totalTime; //#1
        long beginConnection, endConnection, totalConnectionTime; //#2

        //Summary Statistics
        List<Integer> totalTimeList = new ArrayList<Integer>();
        
        // Send connection message to the server
        byte[] buf = "CONNECT".getBytes();
        beginConnection = System.currentTimeMillis();
        //InetAddress serverAddress = InetAddress.getByName("localhost");
        InetAddress serverAddress = InetAddress.getByName(serverURL);
        
        endConnection = System.currentTimeMillis();
        totalConnectionTime = endConnection - beginConnection;
        System.out.println("Connected to " + serverAddress + " in: " + totalConnectionTime + "ms");
        //DatagramPacket connectionPacket = new DatagramPacket(buf, buf.length, serverAddress, 8888);
        DatagramPacket connectionPacket = new DatagramPacket(buf, buf.length, serverAddress, port);
        socket.send(connectionPacket);

        for (int i = 1; i <= 10; i++) {
         

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int expectedSize = -1;
            int totalBytesRead = 0;


            beginTime = System.currentTimeMillis();
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
                    ImageIO.write(image, "png", new File("./received/received_image" + i + ".png"));

                    break;
                }
            }
            endTime = System.currentTimeMillis();
            totalTime = endTime - beginTime;
            totalTimeList.add((int)totalTime);
            System.out.println("Time to download the " + i + " image: " + totalTime + "ms");
        }
        DoubleSummaryStatistics stats = calculateStats(totalTimeList);
        
        System.out.println("Minimum: " + stats.getMin() + "ms");
        System.out.println("Maximum: " + stats.getMax() + "ms");
        System.out.println("Mean: " + stats.getAverage() + "ms");

        double mean = stats.getAverage();
        double sumOfSquares = totalTimeList.stream().mapToDouble(i -> Math.pow(i - mean, 2)).sum();
        double variance = sumOfSquares / (double)(totalTimeList.size() - 1);
        double stdDev = Math.sqrt(variance);

        System.out.println("Standard deviation: " + stdDev + "ms");

        socket.close();
    }


    public static DoubleSummaryStatistics calculateStats(List<Integer> intList) {
        // Calculate summary statistics using Java 8 Stream API
        DoubleSummaryStatistics stats = intList.stream().mapToDouble(Integer::intValue).summaryStatistics();
        return stats;
    }

}
