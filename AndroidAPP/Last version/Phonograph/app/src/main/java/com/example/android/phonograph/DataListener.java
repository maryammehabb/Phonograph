package com.example.android.phonograph;

import java.util.ArrayList;
import java.util.HashMap;

public interface DataListener
{
    void onDataReceived(String response);
    void onLoginReceived(String id,String name);
    String oncancelReservation(String resp);
    Restaurant onOneRestaurant(Restaurant rest);
    ArrayList<Restaurant> onAllRestaurants(ArrayList<Restaurant> rest);
    void onDBResponse(String response);
    void onUpdateInfo(String response);
    void onRetriveUser(User user);
}