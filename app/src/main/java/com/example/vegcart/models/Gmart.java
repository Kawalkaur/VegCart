package com.example.vegcart.models;

public class Gmart {
    private String productID,productName,
    productPrice, product_weight, productNameHindi, Image_url;
 // private String ;
    public Gmart() {
    }

    public Gmart(String productID, String productName, String productPrice, String product_weight,
                 String productNameHindi, String image_url) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.product_weight = product_weight;
        this.productNameHindi = productNameHindi;
        Image_url = image_url;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProductNameHindi() {
        return productNameHindi;
    }

    public void setProductNameHindi(String productNameHindi) {
        this.productNameHindi = productNameHindi;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }
}
