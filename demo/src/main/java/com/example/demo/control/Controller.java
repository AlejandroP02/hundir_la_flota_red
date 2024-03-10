package com.example.demo.control;

import com.example.demo.HelloApplication;
import com.example.demo.model.GameState;
import com.example.demo.model.Jugada;
import com.example.demo.net.DatagramSocketClient;
import com.example.demo.net.DatagramSocketServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {


    @FXML
    private MenuItem actServer;

    @FXML
    private MenuItem connect;
    int barcos = 3;

    @FXML
    private Text textTurno;
    @FXML
    private RadioButton radioV, radioH, radioL2, radioL3;
    private boolean tres=true;
    private boolean vertical;

    @FXML
    private Rectangle r00, r01, r02, r03, r04,
            r10, r11, r12, r13, r14,
            r20, r21, r22, r23, r24,
            r30, r31, r32, r33, r34,
            r40, r41, r42, r43, r44,
            j00, j01, j02, j03, j04,
            j10, j11, j12, j13, j14,
            j20, j21, j22, j23, j24,
            j30, j31, j32, j33, j34,
            j40, j41, j42, j43, j44;
    Rectangle[][] tableroj;
    Rectangle[][] tableror;
    String[][] tablero = new String[5][5];
    String nombre;
    private GameState gameState=null;
    private Jugada jugada;
    private boolean puedeAtacar;

    @FXML
    private void initialize() {
        tableroj = new Rectangle[][]{
                {j00, j01, j02, j03, j04},
                {j10, j11, j12, j13, j14},
                {j20, j21, j22, j23, j24},
                {j30, j31, j32, j33, j34},
                {j40, j41, j42, j43, j44}
        };
        tableror = new Rectangle[][]{
                {r00, r01, r02, r03, r04},
                {r10, r11, r12, r13, r14},
                {r20, r21, r22, r23, r24},
                {r30, r31, r32, r33, r34},
                {r40, r41, r42, r43, r44}
        };
        colocarBarco();
        realizarAtaque();
    }

    /**
     * Cierra la aplicacion.
     */
    @FXML
    private void salir() {
        Platform.exit();
    }
    private static String extraerParte(String texto) {
        // Definir el patrón regex para encontrar la parte deseada
        Pattern patron = Pattern.compile("0x([a-fA-F0-9]+)ff");

        // Crear un objeto Matcher para buscar el patrón en el texto
        Matcher matcher = patron.matcher(texto);

        // Verificar si se encontró el patrón y obtener la parte deseada
        if (matcher.find()) {
            return matcher.group(1); // El grupo 1 contiene la parte deseada
        } else {
            return "No se encontró ninguna coincidencia";
        }
    }

    DatagramSocketClient client = new DatagramSocketClient() {
        String resp="";
        @Override
        public void getResponse(byte[] data, int length) {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            try {
                ObjectInputStream ois = new ObjectInputStream(is);
                gameState = (GameState) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }

        @Override
        public byte[] getRequest() {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;

            try {
                oos = new ObjectOutputStream(os);
                oos.writeObject(jugada);
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return os.toByteArray();
        }

        @Override
        public boolean mustContinue(byte[] data) {
            return !resp.equals("Correcte");
        }
    };
    @FXML
    public void menuItemConnection(ActionEvent actionEvent) {

        Dialog<Pair<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Client configuration");
        dialog.setHeaderText("Dades per la connexió al servidor");
        ButtonType conButton = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(conButton, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField txtIp = new TextField();
        txtIp.setPromptText("IP");
        TextField txtPort = new TextField();
        txtPort.setPromptText("Port");


        gridPane.add(new Label("IP:"), 0, 0);
        gridPane.add(txtIp, 1, 0);
        gridPane.add(new Label("Port:"), 0, 1);
        gridPane.add(txtPort, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(txtIp::requestFocus);

        dialog.setResultConverter(dButton -> {
            if(dButton == conButton) {
                return new Pair<>(txtIp.getText(),Integer.parseInt(txtPort.getText()));
            }
            return null;
        });

        Optional<Pair<String,Integer>> result = dialog.showAndWait();

        if(result.isPresent()) {
            try {
                nombre="player2";
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        tablero[x][y]= extraerParte(tableror[x][y].getFill().toString());
                    }
                }
                jugada = new Jugada(nombre, tablero);
                client.init(result.get().getKey(), result.get().getValue());
                client.runClient();
                puedeAtacar=false;
                Thread.sleep(500);
                textTurno.setText("Connectat, comença!");
                Thread.sleep(500);
                textTurno.setText("turno del otro jugador");
                /**if(!estatJoc.getTurn().equals(nom)) {
                    timer.start();
                }*/

            } catch (SocketException | UnknownHostException | InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void menuItemActiveServer(ActionEvent actionEvent) {
        showConfigServer();
    }
    public void showConfigServer() {
        TextInputDialog dialog = new TextInputDialog("5555");
        dialog.setTitle("Config Server");
        dialog.setHeaderText("Activació del servidor local");
        dialog.setContentText("Port");
        dialog.setGraphic(new ImageView(HelloApplication.class.getResource("images/server.png").toString()));
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            Thread thServer = new Thread(() -> {
                try {
                    DatagramSocketServer server = new DatagramSocketServer();
                    nombre = "player1";
                    for (int x = 0; x < 5; x++) {
                        for (int y = 0; y < 5; y++) {
                            tablero[x][y]= extraerParte(tableror[x][y].getFill().toString());
                        }
                    }
                    jugada = new Jugada(nombre, tablero);
                    textTurno.setText("tu turno");
                    server.init(Integer.parseInt(result.get()));
                    server.runServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thServer.start();
        }
    }

    // Metodos con relacion a los radio button.

    @FXML
    public void onOrientacionSelected(){
        vertical= !radioH.isSelected();
    }

    @FXML
    public void onTamanoSelected(){
        tres= !radioL2.isSelected();
    }

    public void realizarAtaque(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int filaActual = i;
                int columnaActual = j;
                Rectangle pieza = tableror[i][j];
                pieza.setOnMouseClicked(e -> {
                    if(puedeAtacar)atacar(filaActual, columnaActual);
                });
            }
        }
    }

    public void atacar(int x, int y){
        puedeAtacar=false;
        tableror[x][y].setStyle("-fx-fill: #FF9933");
        textTurno.setText("turno del otro jugador");
    }

    public void colocarBarco(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int filaActual = i;
                int columnaActual = j;
                Rectangle pieza = tableroj[i][j];
                pieza.setOnMouseClicked(e -> {
                    if(barcos>0)pintar(tres, vertical, filaActual, columnaActual);
                });
            }
        }
    }


    public void pintar(boolean tres, boolean vertical, int x, int y){
        barcos--;
        //odio mi vida.
        if(tres && vertical){
            if(x+2>=5){
                for (int i = x; i > x-3; i--) {
                    tableroj[i][y].setStyle("-fx-fill: #515151");
                }
            }else{
                for (int i = x; i < x+3; i++) {
                    tableroj[i][y].setStyle("-fx-fill: #515151");
                }
            }
        }
        if(tres && !vertical){
            if(y+2>=5){
                for (int i = y; i > y-3; i--) {
                    tableroj[x][i].setStyle("-fx-fill: #515151");
                }
            }else{
                for (int i = y; i < y+3; i++) {
                    tableroj[x][i].setStyle("-fx-fill: #515151");
                }
            }
        }

        if(!tres && vertical){
            if(x+1>=5){
                for (int i = x; i > x-2; i--) {
                    tableroj[i][y].setStyle("-fx-fill: #515151");
                }
            }else{
                for (int i = x; i < x+2; i++) {
                    tableroj[i][y].setStyle("-fx-fill: #515151");
                }
            }
        }
        if(!tres && !vertical){
            if(y+1>=5){
                for (int i = y; i > y-2; i--) {
                    tableroj[x][i].setStyle("-fx-fill: #515151");
                }
            }else{
                for (int i = y; i < y+2; i++) {
                    tableroj[x][i].setStyle("-fx-fill: #515151");
                }
            }
        }
    }
}