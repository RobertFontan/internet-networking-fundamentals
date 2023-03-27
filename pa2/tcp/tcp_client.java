
import java.io.*;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/* -- Removes all received files
 * rm *received* 
 * 
 */

public class tcp_client {


    public static void main(String[] args) {

        // this will be changed later
        String serverURL = "localhost";
        int port = 5414;


        try {
            System.out.println("Connecting to " + serverURL + " on port " + port);
            Socket clientSocket = new Socket(serverURL, port);
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
            
            
            for (int i = 1; i <= 10; i++) {
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                int length = dataInputStream.readInt();
                byte[] buffer = new byte[length];
                dataInputStream.readFully(buffer, 0, length);
                
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(buffer));
                ImageIO.write(image, "png", new File("received_sample" + i + ".png"));
                
                System.out.println("Image " + i + " received from server and saved to file.");
                //clientSocket.shutdownInput();
                //dataInputStream.close();

            }
            clientSocket.shutdownInput();            
            clientSocket.close();    
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
                