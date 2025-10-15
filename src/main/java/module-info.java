module com.example.ose_tetris {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ose_tetris to javafx.fxml;
    exports com.example.ose_tetris;
}