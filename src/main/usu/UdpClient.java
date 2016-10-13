package main.usu;

import java.io.*;
import java.net.*;

public class UdpClient {

    public static final int PORT_NUMBER = 12099;
    public static final String HOST_IP = "52.39.118.200";

    public static void main(String args[]) {
        DatagramSocket socket;
        int port = PORT_NUMBER;

        try {
            socket = new DatagramSocket();

            InetAddress host = InetAddress.getByName(HOST_IP);

            StreamStocksMessage message = new StreamStocksMessage();
            Portfolio portfolio = new Portfolio();
            portfolio.getSymbolsfromFile().forEach(symbol -> message.Add(symbol));
//                message.Add("AAN");
//                message.Add("AIR");
//                message.Add("MMM");

            byte[] b = message.Encode();
            DatagramPacket datagramPacket = new DatagramPacket(b, b.length, host, port);
            socket.send(datagramPacket);

            while (true) {
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                socket.receive(reply);

                byte[] data = reply.getData();

                TickerMessage tickerMessage = TickerMessage.Decode(data);
                portfolio.update(tickerMessage);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }
}