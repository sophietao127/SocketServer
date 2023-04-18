import java.net.*;
import java.io.*;

public class TCPClient {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Syntax: TCwPClient <hostname> <port>");
      return;
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);

    try (Socket socket = new Socket(host, port)) {

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String time = reader.readLine();
        System.out.println(time);

        socket.close();
    } catch (UnknownHostException ex) {

        System.out.println("Server not found: " + ex.getMessage());

    } catch (IOException ex) {

        System.out.println("I/O error: " + ex.getMessage());
    }
  }
}
