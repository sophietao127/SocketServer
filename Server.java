import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static final int TCP_PORT = 17;
    private static final int UDP_PORT = 17;
    private static final String[] quotes = {
      "You can't handle the truth!",
      "You keep using that word. I do not think it means what you think it means.",
      "Hello. My name is Inigo Montoya. You killed my father. Prepare to die.",
      "Once you start down the dark path, forever will it dominate your destiny. —Yoda",
      "Mos Eisley, you will never find a more wretched hive of scum and villainy. —Obi-Wan Kenobi",
      "That Boy Is Alive. We Are Gonna' Send Somebody To Find Him. And We Are Gonna' Get Him The Hell ... Outta' There!"
    };

    public static void main(String[] args) throws IOException {
      ServerSocket tcpServerSocket = new ServerSocket(TCP_PORT);
      System.out.println("TCP server started on port " + TCP_PORT);

      DatagramSocket udpServerSocket = new DatagramSocket(UDP_PORT);
      System.out.println("UDP server started on port " + UDP_PORT);

      while (true) {
          Socket tcpSocket = tcpServerSocket.accept();
          Thread tcpThread = new Thread(() -> handleTCPConnection(tcpSocket));
          tcpThread.start();

          byte[] buffer = new byte[1024];
          DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length);
          udpServerSocket.receive(udpPacket);
          Thread udpThread = new Thread(() -> handleUDPConnection(udpPacket));
          udpThread.start();
      }
  }

  private static void handleTCPConnection(Socket socket) {
      try {
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          out.println(generateQuote());
          socket.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  private static void handleUDPConnection(DatagramPacket packet) {
      try {
          InetAddress address = packet.getAddress();
          int port = packet.getPort();
          byte[] data = generateQuote().getBytes();
          DatagramPacket response = new DatagramPacket(data, data.length, address, port);
          DatagramSocket socket = new DatagramSocket();
          socket.send(response);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  private static String generateQuote() {
      Random random = new Random();
      int index = random.nextInt(quotes.length);
      return quotes[index];
  }
}
