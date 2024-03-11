package com.example.demo.net;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public abstract class DatagramSocketClient {

    /**
     * Necesitamos usar la clase AnimationTimer para realizar peticiones
     * vacias continuas al servidor para poder actualizar el estado
     * del juego.
     */

    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;

    String nom;

    public DatagramSocketClient() {
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
    public void setSocket() throws SocketException {
        socket = new DatagramSocket();
    }
}
