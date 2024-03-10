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
        while (mustContinue()) {
            byte[] sendingData = getRequestData();
            sendDatagram(sendingData);

            byte[] receivedData = receiveDatagram();
            processResponse(receivedData);
        }
    }

    private byte[] getRequestData() {
        return getRequest();
    }

    private void sendDatagram(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, serverIP, serverPort);
        socket.send(packet);
    }

    private byte[] receiveDatagram() throws IOException {
        byte[] receivedData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);
        socket.receive(packet);
        return packet.getData();
    }

    private void processResponse(byte[] data) {
        getResponse(data, data.length);
    }

    private boolean mustContinue() {
        return !mustStop();
    }

    private boolean mustStop() {
        // Aquí puedes definir la lógica para determinar si el cliente debe detenerse.
        // Por ejemplo, puedes usar una condición basada en el estado del juego o una señal de salida.
        return false;  // En este ejemplo, el cliente nunca se detiene
    }

    //Resta de conversa que se li envia al server
    public abstract void getResponse(byte[] data, int length);

    //primer missatge que se li envia al server
    public abstract byte[] getRequest();

    //Si se li diu adeu al server el client es desconnecta
    public abstract boolean mustContinue(byte [] data);

}
