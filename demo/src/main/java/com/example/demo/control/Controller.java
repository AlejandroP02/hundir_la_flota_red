package com.example.demo.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    private MenuItem cerrar;

    @FXML
    private MenuItem actServer;

    @FXML
    private MenuItem connect;

    @FXML
    private Label turno;

    @FXML
    private Rectangle r00,r01,r02,r03,r04,
            r10,r11,r12,r13,r14,
            r20,r21,r22,r23,r24,
            r30,r31,r32,r33,r34,
            r40,r41,r42,r43,r44,
            j00,j01,j02,j03,j04,
            j10,j11,j12,j13,j14,
            j20,j21,j22,j23,j24,
            j30,j31,j32,j33,j34,
            j40,j41,j42,j43,j44;
    Rectangle[][] tableroj;
    Rectangle[][] tableror;

    @FXML
    private void initialize(){
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
    }

    /**
     * Cierra la aplicacion.
     */
    @FXML
    private void salir(){
        Platform.exit();
    }
}