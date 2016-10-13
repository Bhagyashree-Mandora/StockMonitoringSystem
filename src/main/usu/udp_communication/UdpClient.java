package main.usu.udp_communication;

import main.usu.Portfolio;
import main.usu.models.StreamStocksMessage;
import main.usu.models.TickerMessage;

import java.io.*;
import java.net.*;

public class UdpClient implements Runnable {

    public static final int PORT_NUMBER = 12099;
    public static final String HOST_IP = "52.39.118.200";

    public void run() {
        DatagramSocket socket;

        try {
            socket = new DatagramSocket();
            Portfolio portfolio = requestTickerData(socket);

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

    private Portfolio requestTickerData(DatagramSocket socket) throws IOException {
        InetAddress host = InetAddress.getByName(HOST_IP);

        StreamStocksMessage message = new StreamStocksMessage();
        Portfolio portfolio = new Portfolio();
        portfolio.getSymbolsfromFile().forEach(symbol -> message.Add(symbol));

        byte[] b = message.Encode();
        DatagramPacket datagramPacket = new DatagramPacket(b, b.length, host, PORT_NUMBER);
        socket.send(datagramPacket);
        return portfolio;
    }
}