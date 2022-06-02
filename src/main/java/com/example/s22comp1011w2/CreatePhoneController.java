package com.example.s22comp1011w2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatePhoneController implements Initializable {

    @FXML
    private RadioButton androidRadioButton;

    @FXML
    private Label batteryLabel;

    @FXML
    private Slider batterySlider;

    @FXML
    private Spinner<Integer> cameraMpSpinner;

    @FXML
    private RadioButton iosRadioButton;

    @FXML
    private ComboBox<String> makeComboBox;

    @FXML
    private Spinner<Integer> memorySpinner;

    @FXML
    private TextField modelTextField;

    @FXML
    private Label msgLabel;

    @FXML
    private TextField priceTextField;

    @FXML
    private Spinner<Integer> unitsInStockSpinner;

    private ToggleGroup osToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgLabel.setText("");
        makeComboBox.getItems().addAll(Phone.getManufacturers());
        makeComboBox.setPromptText("Select a Brand");
        makeComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            if (newValue.equalsIgnoreCase("Apple"))
            {
                iosRadioButton.setSelected(true);
                modelTextField.setText("iPhone ");
            }
            else
            {
                androidRadioButton.setSelected(true);
                modelTextField.setText("");
            }
        });

        //configure the RadioButtons to belong in a ToggleGroup so only 1 is selected at a time
        osToggleGroup = new ToggleGroup();
        iosRadioButton.setToggleGroup(osToggleGroup);
        androidRadioButton.setToggleGroup(osToggleGroup);
        androidRadioButton.setSelected(true);  //sets the radiobutton to be selected

        //configure the memorySpinner to only accept valid memory sizes
        ObservableList<Integer> ramSizes = FXCollections.observableList(Phone.getMemorySizes());  //convert List<Integer> into ObservableList<Integer>
        SpinnerValueFactory<Integer> ramValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory(ramSizes);  //Add the list into the ValueFactory
        memorySpinner.setValueFactory(ramValueFactory);  //add the ValueFactory to the Spinner

        //configure the slider
        batterySlider.setMin(12);  //set the min
        batterySlider.setMax(48);   //set the max
        batterySlider.setValue(24); //set the default
        updateSlider(batterySlider.getValue()); //show the default value
        //this is using a named class
//        BatteryChangeListener bcl = new BatteryChangeListener();
//        batterySlider.valueProperty().addListener(bcl);  //attach a listener object to the slider

        //this is implementing a listener using an anonymous inner class
//        batterySlider.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                batteryLabel.setText(newValue.toString());
//            }
//        });

        //this is implementing a listener using a lambda expression
        batterySlider.valueProperty().addListener(((observableValue, oldValue, newValue) ->{
            updateSlider(newValue);  //update the current value from the slider to the label
        }));

        //configure the camera MP to be 0-100 (min = 0, max = 100, default = 12)
        SpinnerValueFactory<Integer> cameraMPFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,12);
        cameraMpSpinner.setValueFactory(cameraMPFactory);
        cameraMpSpinner.setEditable(true);
        TextField cameraTextField = cameraMpSpinner.getEditor();
        //this listener will only allow for an integer value to be typed
        cameraTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            try{
                Integer.parseInt(newValue);
            } catch(Exception e)
            {
                cameraTextField.setText(oldValue);
            }
        });

        //configure the number of units in stock (min=0, max=1000, default amount = 50)
        SpinnerValueFactory<Integer> unitsInStockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,50);
        unitsInStockSpinner.setValueFactory(unitsInStockFactory);
        unitsInStockSpinner.setEditable(true);
        TextField unitsInStockTextField = unitsInStockSpinner.getEditor();
        unitsInStockTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            try{
                Integer.parseInt(newValue);
            }
            catch(NumberFormatException e)
            {
                unitsInStockTextField.setText(oldValue);
            }
        });
    }

    private void updateSlider(Number sliderValue)
    {
        batteryLabel.setText(String.format("%.0f Hours",sliderValue));
    }

    //This is an "inner" class called BatteryChangeListener
    private class BatteryChangeListener implements ChangeListener{

        @Override
        public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
            batteryLabel.setText(newValue.toString());
        }
    }

    /**
     * This method will create a Phone object based on the user inputs
     */
    @FXML
    private void createPhone()
    {
        double price = -1;
        try {
            String priceText = priceTextField.getText().replaceAll("[^0-9.]*", "");
            price = Double.parseDouble(priceText);
            msgLabel.setText("");
        } catch (NumberFormatException e)
        {
            msgLabel.setText("Price must be a valid number (i.e. $549.99)");
        }

        String make = makeComboBox.getValue();
        String model = modelTextField.getText();

        //casting the "Toggle" to be a RadioButton object
        RadioButton radioButtonSelected = (RadioButton) osToggleGroup.getSelectedToggle();
        String os = radioButtonSelected.getText();

        int ram = memorySpinner.getValue();
        int backCameraMP = cameraMpSpinner.getValue();
        int batteryLifeInHours = Integer.parseInt(batteryLabel.getText().replaceAll("[^0-9]*",""));
        int quantityInStock = unitsInStockSpinner.getValue();

        try {
            Phone newPhone = new Phone(make, model, os, ram, backCameraMP, price, batteryLifeInHours, quantityInStock);
            msgLabel.setText(newPhone.toString());
        } catch (Exception e)
        {
            msgLabel.setText(e.getMessage());
        }
    }

}
