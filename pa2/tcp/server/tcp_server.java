package server;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;

public class tcp_server {
    public static void main(String[] args) {

        if(args.length != 1){
            System.out.println("java tcp_server.java <port>");
            return;
        }
        int PORT = Integer.parseInt(args[0]);

        Random rand = new Random();
        int upper = 10;
        int int_rand = rand.nextInt(upper); // 0 - 9
        List<Integer> prev = new ArrayList<Integer>();

        // Measurements
        long beginAccess, endAccess, totalAccessTime;
        
        //Summary Statistics
        List<Integer> accessTimeList = new ArrayList<Integer>();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Server on: " + serverSocket.getInetAddress());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted incoming connection from " + clientSocket.getInetAddress());
            

            for (int i = 1; i <= 10; i++) {
                
                prev.add(int_rand);

                beginAccess = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(new File("./images/sample" + int_rand + ".png"));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                byte[] buffer = outputStream.toByteArray();
                
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeInt(buffer.length);
                dataOutputStream.write(buffer, 0, buffer.length);
                endAccess = System.currentTimeMillis();
                totalAccessTime = endAccess - beginAccess;
                accessTimeList.add((int)totalAccessTime);
                dataOutputStream.flush();
                
                System.out.println("Image " + int_rand + " sent to client and read into byte array in: " + totalAccessTime + "ms");

                
                while(prev.contains(int_rand)){
                    int_rand = rand.nextInt(upper);
                    if(i == 10){
                        break;
                    }
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

            clientSocket.shutdownOutput();
            serverSocket.close();       
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DoubleSummaryStatistics calculateStats(List<Integer> intList) {
        // Calculate summary statistics using Java 8 Stream API
        DoubleSummaryStatistics stats = intList.stream().mapToDouble(Integer::intValue).summaryStatistics();
        return stats;
    }
}

