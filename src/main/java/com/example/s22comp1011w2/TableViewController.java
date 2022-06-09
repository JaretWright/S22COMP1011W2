package com.example.s22comp1011w2;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewController {

    @FXML
    private TableColumn<?, ?> makeColumn;

    @FXML
    private TableColumn<?, ?> memoryColumn;

    @FXML
    private TableColumn<?, ?> modelColumn;

    @FXML
    private TableColumn<?, ?> phoneIDColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableView<?> tableview;

    @FXML
    private TableColumn<?, ?> unitsSoldColumn;

}
