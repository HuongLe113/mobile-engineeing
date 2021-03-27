package com.example.bookfood_sms;

public class ItemsList {
  private int id;
  private String foodName, foodLink, foodLocation, foodDescription;
  private int foodImage;
  private double foodGia;

  public ItemsList() {
  }

  public ItemsList(int id, int foodImage, String foodLink, String foodName, String foodLocation, double foodGia, String foodDescription) {
    this.id = id;
    this.foodImage = foodImage;
    this.foodLink = foodLink;
    this.foodName = foodName;
    this.foodGia = foodGia;
    this.foodDescription = foodDescription;
    this.foodLocation = foodLocation;
  }

  public String getFoodName() {
    return foodName;
  }

  public String getFoodLink() {
    return foodLink;
  }

  public String getFoodLocation() {
    return foodLocation;
  }

  public int getFoodImage() {
    return foodImage;
  }

  public void setFoodImage(int foodImage) {
    this.foodImage = foodImage;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public void setFoodLink(String foodLink) {
    this.foodLink = foodLink;
  }

  public void setFoodLocation(String foodLocation) {
    this.foodLocation = foodLocation;
  }

  public double getFoodGia() {
    return foodGia;
  }

  public void setFoodGia(double foodGia) {
    this.foodGia = foodGia;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFoodDescription() {
    return foodDescription;
  }

  public void setFoodDescription(String foodDescription) {
    this.foodDescription = foodDescription;
  }


}
