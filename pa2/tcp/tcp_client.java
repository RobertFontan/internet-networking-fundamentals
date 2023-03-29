
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class tcp_client {


    public static void main(String[] args) {

         // check command line arguments
        if (args.length != 2) {
            System.err.println("java tcp_client.java <serverURL> <port number>");
            return;
        }

        // get server name and port number from command line arguments
        String serverURL = args[0];
        int port = Integer.parseInt(args[1]);

        // Measurements
        long beginTime, endTime, totalTime; //#1
        long beginConnection, endConnection, totalConnectionTime; //#2 Setup and Resolution time

        //Summary Statistics
        List<Integer> totalTimeList = new ArrayList<Integer>();
        

        try {
            // Socket information
            System.out.println("Connecting to " + serverURL + " on port " + port);
            beginConnection = System.currentTimeMillis(); // Measurement
            Socket clientSocket = new Socket(serverURL, port);
            endConnection = System.currentTimeMillis(); // Measurement
            totalConnectionTime = endConnection - beginConnection; 
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress() + " in: " + totalConnectionTime + "ms ");
            
            // getting all 10 images
            for (int i = 1; i <= 10; i++) {
                beginTime = System.currentTimeMillis(); // Measurement
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                int length = dataInputStream.readInt();
                byte[] buffer = new byte[length];
                dataInputStream.readFully(buffer, 0, length);
                
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(buffer));
                ImageIO.write(image, "png", new File("./received/received_sample" + i + ".png"));
                endTime = System.currentTimeMillis(); // Measurement
                totalTime = endTime - beginTime;
                totalTimeList.add((int)totalTime);
                System.out.println("Time to download the " + i + " image: " + totalTime + "ms");
            }

            // Summary Statistics 
            DoubleSummaryStatistics stats = calculateStats(totalTimeList);
        
            System.out.println("Minimum: " + stats.getMin() + "ms");
            System.out.println("Maximum: " + stats.getMax() + "ms");
            System.out.println("Mean: " + stats.getAverage() + "ms");

            double mean = stats.getAverage();
            double sumOfSquares = totalTimeList.stream().mapToDouble(i -> Math.pow(i - mean, 2)).sum();
            double variance = sumOfSquares / (double)(totalTimeList.size() - 1);
            double stdDev = Math.sqrt(variance);
    
            System.out.println("Standard deviation: " + stdDev + "ms");
            clientSocket.shutdownInput();            
            clientSocket.close();    

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DoubleSummaryStatistics calculateStats(List<Integer> intList) {
        DoubleSummaryStatistics stats = intList.stream().mapToDouble(Integer::intValue).summaryStatistics();
        return stats;
    }
}
                