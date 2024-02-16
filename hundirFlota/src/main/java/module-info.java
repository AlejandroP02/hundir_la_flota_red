module org.example.hundirflota {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.hundirflota to javafx.fxml;
    exports org.example.hundirflota;
}