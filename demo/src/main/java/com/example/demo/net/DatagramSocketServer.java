package com.example.demo.net;

import com.example.demo.model.GameState;
import com.example.demo.model.Jugada;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.*;
import java.util.Arrays;

/**
 * Created by jordi on 26/02/17.
 * Exemple Servidor UDP extret dels apunts IOC i ampliat
 * El seu CLient és DatagramSocketClient
 */
public class DatagramSocketServer {
    private DatagramSocket socket;
    private int fi;
    private boolean acabat;
    private GameState gameState = new GameState();

    public GameState getGameState() {
        return gameState;
    }


    //Instàciar el socket
    public void init(int port) throws SocketException {
        socket = new DatagramSocket(port);
        //ns = new SecretNum(100);
        acabat = false;
        gameState = new GameState();
        fi=-1;
        System.out.printf("Servidor obert pel port %d%n",port);
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[2048];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        while(!acabat) {
            DatagramPacket packet = new DatagramPacket(receivingData,2048);
            socket.receive(packet);
            sendingData = processData(packet.getData(),packet.getLength());
            //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
            clientIP = packet.getAddress();
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData,sendingData.length,clientIP,clientPort);
            socket.send(packet);
        }
    }

    //El server retorna al client el mateix missatge que li arriba però en majúscules
    public byte[] processData(byte[] data, int lenght) {
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            Jugada jugada = (Jugada) ois.readObject();
            updateGameState(jugada);
            //System.out.println(jugada + "torn:" + estatJoc.getTurn());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(gameState);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return os.toByteArray();
    }

    private void updateGameState(Jugada jugada){
        if(jugada.getPlayer().equals("player1")){
            gameState.setPlayer1(jugada.getPlayer());;
            gameState.setTablero2(jugada.getTablero());
            gameState.setTableroj1(jugada.getTableroDelJugador());
        }else{
            gameState.setPlayer2(jugada.getPlayer());
            gameState.setTablero1(jugada.getTablero());
            gameState.setTableroj2(jugada.getTableroDelJugador());
        }
    }

}
