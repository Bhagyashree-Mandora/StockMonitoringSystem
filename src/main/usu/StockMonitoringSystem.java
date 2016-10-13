package main.usu;

import main.usu.udp_communication.UdpClient;

public class StockMonitoringSystem {

    public static void main(String[] args) {
        UdpClient udpClient = new UdpClient();
        Thread thread = new Thread(udpClient);
        thread.start();
    }
}