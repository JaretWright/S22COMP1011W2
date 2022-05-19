package com.example.s22comp1011w2;

import java.util.Arrays;
import java.util.List;

public class Phone {
    private String make, model, os;
    private int ram, backCameraMP;
    private double price;
    private int batteryLifeInHours, quantityInStock;

    /**
     * This is the standard constructor - we need all of these items to define a Phone object
     * @param make -i.e. Apple, Samsung, HTC, etc...
     * @param model -i.e. iPhone, Galaxy, etc...
     * @param os - the operating system (Android, iOS)
     * @param ram - how much ram is in the phone
     * @param backCameraMP - the megapixels of the rear main camera
     * @param price
     * @param batteryLifeInHours
     * @param quantityInStock
     */
    public Phone(String make, String model, String os, int ram, int backCameraMP, double price, int batteryLifeInHours, int quantityInStock) {
        setMake(make);

        this.model = model;
        this.os = os;
        this.ram = ram;
        this.backCameraMP = backCameraMP;
        this.price = price;
        this.batteryLifeInHours = batteryLifeInHours;
        this.quantityInStock = quantityInStock;
    }

    /**
     * This will set the manufacturer (or make) of the phone
     * @param make
     */
    public void setMake(String make) {
        List<String> manufacturers = Arrays.asList("Apple", "Samsung", "Nokia", "Realme", "Oppo", "Vivo",
                "Blackberry", "HTC", "Huawei", "Motorola","Google");
        if (manufacturers.contains(make))
            this.make = make;
        else
            throw new IllegalArgumentException(make + " is not valid, choose from " + manufacturers);
    }
}
