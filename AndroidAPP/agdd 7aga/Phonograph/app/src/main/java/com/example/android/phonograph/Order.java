package com.example.android.phonograph;

public class Order {
    private int id;
    private String time;
    private boolean delivery;
    private float price;
    private int restaurant_id;
    private String time_delivered;
    private boolean done;
    private int cusId;
    private String meals;
    private  int numberOfMeals;


    public Order(String time, boolean delivery, float price, int cusId, String meals, int numberOfMeals, int restaurant_id) {
        this.time = time;
        this.delivery = delivery;
        this.price = price;
        this.cusId = cusId;
        this.meals = meals;
        this.numberOfMeals=numberOfMeals;
        this.done = false;
        this.time_delivered = null;
        this.restaurant_id=restaurant_id;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getTime_delivered() {
        return time_delivered;
    }

    public void setTime_delivered(String time_delivered) {
        this.time_delivered = time_delivered;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    public void setNumberOfMeals(int numberOfMeals) {
        this.numberOfMeals = numberOfMeals;
    }
}
