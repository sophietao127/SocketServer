import java.net.*;
import java.io.*;

public class UDPClient {
  public static void main(String[] args) {
    try (DatagramSocket socket = new DatagramSocket()) {
        byte[] buffer = new byte[1024];
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket requestPacket = new DatagramPacket(new byte[1], 1, address, 17);
        socket.send(requestPacket);
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(responsePacket);
        String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println("Received response: " + response);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
