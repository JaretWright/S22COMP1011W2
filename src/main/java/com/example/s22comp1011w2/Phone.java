package com.example.s22comp1011w2;

import java.util.Arrays;
import java.util.Collections;
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
        setModel(model);
        setOs(os);
        setRam(ram);
        setBackCameraMP(backCameraMP);
        setPrice(price);
        setBatteryLifeInHours(batteryLifeInHours);
        setQuantityInStock(quantityInStock);
    }

    /**
     * This method returns an ordered list of valid manufacturers
     */
    public static List<String> getManufacturers()
    {
        List<String> manufacturers =  Arrays.asList("Apple", "Samsung", "Nokia", "Realme", "Oppo", "Vivo",
                "Blackberry", "HTC", "Huawei", "Motorola","Google");
        Collections.sort(manufacturers);
        return manufacturers;
    }

    /**
     * This will set the manufacturer (or make) of the phone
     * @param make
     */
    public void setMake(String make) {
        List<String> manufacturers = getManufacturers();
        if (manufacturers.contains(make))
            this.make = make;
        else
            throw new IllegalArgumentException(make + " is not valid, choose from " + manufacturers);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        model = model.trim();
        if (model.length()>=2)
            this.model = model;
        else
            throw new IllegalArgumentException("invalid model, must be 2 or more characters");
    }

    public String getOs() {
        return os;
    }

    /**
     * setOs defines the operating system for the phone
     * @param os must be iOS or Android
     */
    public void setOs(String os) {
        if (os.equalsIgnoreCase("iOS"))
            this.os = "iOS";
        else if (os.equalsIgnoreCase("android"))
            this.os = "Android";
        else
            throw new IllegalArgumentException("os must be iOS or Android");
    }

    public int getRam() {
        return ram;
    }

    /**
     * This method returns a list of the valid Memory (RAM) sizes for the phone
     */
    public static List<Integer> getMemorySizes()
    {
        return Arrays.asList(64,128,256,512,1028,2056);
    }

    /**
     * How many Gigs of memory are  on the phone
     * @param ram - 64, 128, 256 or 512
     */
    public void setRam(int ram) {
        if (getMemorySizes().contains(ram))
            this.ram = ram;
        else
            throw new IllegalArgumentException("RAM must be 64, 128, 256 or 512");
    }

    public int getBackCameraMP() {
        return backCameraMP;
    }

    /**
     * The MegalPixel capacity of the rear main camera
     * @param backCameraMP - must be greater than 0
     */
    public void setBackCameraMP(int backCameraMP) {
        if (backCameraMP>0)
            this.backCameraMP = backCameraMP;
        else
            throw new IllegalArgumentException("back camera MP must be greater than 0");
    }

    public double getPrice() {
        return price;
    }

    /**
     * This is the MSRP (manufacturer suggested retail price)
     * @param price - greater than or equal to 0
     */
    public void setPrice(double price) {
        if (price >= 0)
            this.price = price;
        else
            throw new IllegalArgumentException("price must be greater than or equal to 0");
    }

    public int getBatteryLifeInHours() {
        return batteryLifeInHours;
    }

    /**
     * How long the phone should last with moderate use between charges
     * @param batteryLifeInHours >0 to 240
     */
    public void setBatteryLifeInHours(int batteryLifeInHours) {
        if (batteryLifeInHours> 0 && batteryLifeInHours<=240)
            this.batteryLifeInHours = batteryLifeInHours;
        else
            throw new IllegalArgumentException("Battery life must be >0 up to 240");
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        if (quantityInStock>=0)
            this.quantityInStock = quantityInStock;
        else
            throw new IllegalArgumentException("quantity in stock must be 0 or higher");
    }

    public int sellPhone(int numberSold)
    {
        if (numberSold>quantityInStock)
        {
            numberSold = quantityInStock;
            quantityInStock = 0;
        }
        else
        {
            quantityInStock -= numberSold;
        }
        return numberSold;
    }

    public void receiveInventory(int numOfNewPhones)
    {
        quantityInStock += numOfNewPhones;
    }
}
