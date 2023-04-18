import java.net.*;
import java.io.*;

public class UDPClient {
  public static void main(String[] args) {
    try (DatagramSocket socket = new DatagramSocket();) {
      InetAddress address = InetAddress.getByName("localhost");
      DatagramPacket request = new DatagramPacket(new byte[1], 1, address, 17);

      socket.send(request);

      byte[] buffer = new byte[512];
      DatagramPacket response = new DatagramPacket(buffer, buffer.length);
      socket.receive(response);

      String quote = new String(response.getData(), 0, response.getLength());
      System.out.println(quote);
    } catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
    }
  }
}
