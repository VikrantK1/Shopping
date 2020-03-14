package com.creater.shopping.util;

public class Helper {
    String Product_Name;
    String Product_Distance;
    String Discription;
    String floor;


    public Helper(String product_Name, String product_Distance, String discription, String floor) {
        Product_Name = product_Name;
        Product_Distance = product_Distance;
        Discription = discription;
        this.floor = floor;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Distance() {
        return Product_Distance;
    }

    public void setProduct_Distance(String product_Distance) {
        Product_Distance = product_Distance;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
