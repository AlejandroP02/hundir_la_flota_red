package uf5.mp3.adivinafx.net;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public abstract class DatagramSocketClient {
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;
    Scanner sc;
    String nom;

    public DatagramSocketClient() {
        sc = new Scanner(System.in);
    }

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

        sendingData = getRequest();
        DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,serverIP,serverPort);
        socket.send(packet);
        packet = new DatagramPacket(receivedData,1024);
        socket.receive(packet);
        getResponse(packet.getData(), packet.getLength());

    }

    //Resta de conversa que se li envia al server
    public abstract void getResponse(byte[] data, int length);

    //primer missatge que se li envia al server
    public abstract byte[] getRequest();

    //Si se li diu adeu al server el client es desconnecta
    public abstract boolean mustContinue(byte [] data);

}
