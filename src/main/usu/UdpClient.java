package main.usu;
/**
 * Java ECHO client with UDP sockets example
 * Silver Moon (m00n.silv3r@gmail.com)
 */

import java.io.*;
import java.net.*;

public class UdpClient {
    public static void main(String args[]) {
        DatagramSocket sock = null;
        int port = 12099;
        String s;

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new DatagramSocket();

            InetAddress host = InetAddress.getByName("52.39.118.200");

                //take input and send the packet
//                echo("Enter message to send : ");
                StreamStocksMessage message = new StreamStocksMessage();
                Portfolio portfolio = new Portfolio();
                portfolio.getSymbols().forEach(symbol-> message.Add(symbol));
//                message.Add("AAN");
//                message.Add("AIR");
//                message.Add("AAC");
//                s = (String) cin.readLine();
//                byte[] b = s.getBytes();

                byte[] b = message.Encode();
                DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
                sock.send(dp);

            while (true) {
                //now receive reply
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);

                byte[] data = reply.getData();
//                s = new String(data, 0, reply.getLength());

                TickerMessage tickerMessage = TickerMessage.Decode(data);
                portfolio.update(tickerMessage);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }
}