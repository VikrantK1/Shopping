package com.creater.shopping.util;

public class Helper {
    String Product_Name;
    String Product_Distance;
    String Discription;

    public Helper(String productName, String product_Distance, String discription) {
        Product_Name = productName;
        Product_Distance = product_Distance;
        Discription = discription;
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
}
