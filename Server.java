import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static String[] quotes = {
      "I just want you to feel you're doing well. I hate for people to die embarrassed.",
      "My way's not very sportsman-like.",
      "You keep using that word. I do not think it means what you think it means.",
      "I always think that everything could be a trap, which is why I'm still alive.",
      "Once you start down the dark path, forever will it dominate your destiny. —Yoda",
      // my favorite movie quote from - <Howl's Moving Castle>
      "Look. Survey. Inspect. My hair is ruined! I look like a pan of bacon and eggs!.",
      "Interesting things did seem to happen, but always to somebody else."
    };

    public static void main(String[] args) throws IOException {
      ServerSocket TCPServerSocket = new ServerSocket(17);
      DatagramSocket UDPServerSocket = new DatagramSocket(17);
      System.out.println("TCP server started on port 17");
      System.out.println("UDP server started on port 17");

      while (true) {
          Socket tcpSocket = TCPServerSocket.accept();
          Thread tcpThread = new Thread(() -> handleTCPConnection(tcpSocket));
          tcpThread.start();

          byte[] buffer = new byte[512];
          DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length);
          UDPServerSocket.receive(udpPacket);
          Thread udpThread = new Thread(() -> handleUDPConnection(udpPacket));
          udpThread.start();
      }
  }

  private static void handleTCPConnection(Socket socket) {
      try {
          System.out.println("New client connected with TCP");
          OutputStream output = socket.getOutputStream();
          PrintWriter writer = new PrintWriter(output, true);
          writer.println(getRandomQuote());
          socket.close();
      } catch (IOException ex) {
          ex.printStackTrace();
      }
  }

  private static void handleUDPConnection(DatagramPacket packet) {
      try {
        System.out.println("New client connected with UDP");
          InetAddress address = packet.getAddress();
          int port = packet.getPort();
          byte[] data = getRandomQuote().getBytes();
          DatagramPacket response = new DatagramPacket(data, data.length, address, port);
          DatagramSocket socket = new DatagramSocket();
          socket.send(response);
      } catch (IOException ex) {
          ex.printStackTrace();
      }
  }

  private static String getRandomQuote() {
      Random random = new Random();
      int index = random.nextInt(quotes.length);
      return quotes[index];
  }
}
