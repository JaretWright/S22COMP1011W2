module com.example.s22comp1011w2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.s22comp1011w2 to javafx.fxml;
    exports com.example.s22comp1011w2;
}