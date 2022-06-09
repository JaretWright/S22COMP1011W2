package com.example.s22comp1011w2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableColumn<Phone, String> makeColumn;

    @FXML
    private TableColumn<Phone, Integer> memoryColumn;

    @FXML
    private TableColumn<Phone, String> modelColumn;

    @FXML
    private TableColumn<Phone, Integer> phoneIDColumn;

    @FXML
    private TableColumn<Phone, String> priceColumn;

    @FXML
    private TableView<Phone> tableview;

    @FXML
    private TableColumn<Phone, Integer> unitsSoldColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //configure the columns to know where they can get their data from
        //the "make" is actually calling the getMake() method
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        memoryColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        phoneIDColumn.setCellValueFactory(new PropertyValueFactory<>("phoneID"));
        unitsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("unitsSold"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
