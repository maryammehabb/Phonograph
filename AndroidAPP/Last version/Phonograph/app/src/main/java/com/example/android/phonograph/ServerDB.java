package com.example.android.phonograph;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerDB {
    Context mContext;
    boolean flag;

    public ServerDB(Context context) {
        mContext = context;
    }

    public void saveToServerUser(final User user) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String customer_ID;
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK")) {
                                    Log.d("SERVER", "saved!");
                                    customer_ID = jsonObject.getString("ID");
                                    ;
                                } else customer_ID = "-1";
                                ((DataListener) mContext).onDataReceived(customer_ID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", user.getName());
                    params.put("mail", user.getMail());
                    params.put("address", user.getAddress());
                    params.put("Phone", user.getPhone());
                    params.put("password", user.getPassword());
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }


    public void getAllRestaurants(final ListView lstview, final DataListener dataListener) {
        //    ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getAllResURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
                                //JSONObject ob1 = new JSONObject(response);
                                JSONObject jsonObject = new JSONObject();
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    Restaurant restaurant = new Restaurant(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("timeStart"), jsonObject.getString("timeEnd"));
                                    temp.add(restaurant);
                                    Log.i("rows", jsonArray.getString(i));
                                    Log.i("names", jsonObject.getString("name"));
                                }
                                ((DataListener) mContext).onAllRestaurants(temp);
                                //        dataListener.onDataReceived(temp);
                                //LstViewAdapter adapter = new LstViewAdapter(mContext, R.layout.activity_resturaunt_list, R.id.txt, temp);
                                // Bind data to the ListView
                                //lstview.setAdapter(adapter);

//                                dataListener.onDataReceived(temp);
                                //Log.i("RESTAURANTS", Response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("rest", "");
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else {
            Log.i("NNN", "not connected");
        }
    }

    public Restaurant get_one_restaurant(final String id) {
        Restaurant r = null;
        if (checkNetworkConnection()) {

            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getOneResURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                ArrayList<Branch> b = new ArrayList<Branch>();
                                ArrayList<item> items = new ArrayList<item>();
                                JSONObject restData = new JSONObject();
                                JSONArray branches = new JSONArray();
                                JSONArray menu = new JSONArray();
                                JSONArray jsonArray = new JSONArray(response);
                                restData = jsonArray.getJSONArray(0).getJSONObject(0);
                                branches = jsonArray.getJSONArray(1);
                                menu = jsonArray.getJSONArray(2);
                                for (int i = 0; i < branches.length(); i++) {
                                    JSONObject x = branches.getJSONObject(i);
                                    Branch br = new Branch(x.getString("id"), x.getString("address"));
                                    b.add(br);
                                }
                                for (int i = 0; i < menu.length(); i++) {
                                    JSONObject x = menu.getJSONObject(i);
                                    item it = new item(x.getString("id"), "1", x.getString("name"), Float.valueOf(x.getString("price")));
                                    Log.i(x.getString("id"), restData.getString("id") + " " + x.getString("name") + " " + Float.valueOf(x.getString("price")));
                                    items.add(it);
                                }
                                Restaurant r = new Restaurant(restData.getString("id"), restData.getString("name"), restData.getString("timeStart"), restData.getString("timeEnd"), b, items);
                                ((DataListener) mContext).onOneRestaurant(r);
//                                dataListener.onDataReceived(temp);
                                //Log.i("RESTAURANTS", Response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else {
            Log.i("NNN", "not connected");
        }
        //r=temp;
        return r;
    }

    public void makeOrder(final Order order) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            Log.i("??", order.getMeals());
            Log.i("??", order.getTime());
            Log.i("??", Integer.toString(order.getCusId()));
            Log.i("??", Integer.toString(order.getId()));
            Log.i("??", Float.toString(order.getPrice()));
            Log.i("??", Integer.toString(order.getRestaurant_id()));

            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.makeOrderURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK"))
                                    ((DataListener) mContext).onDBResponse("Ordre is done.");
                                else if (Response.equals("orderMade"))
                                    ((DataListener) mContext).onDBResponse("You have already placed an order, you can update it.");
                                else if (Response.equals("mealNotAvailable"))
                                    ((DataListener) mContext).onDBResponse("The meal you chose isn't in the menu, please select another item");
                                else
                                    ((DataListener) mContext).onDBResponse("We had a problem saving your order, Please try again.");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("timee", order.getTime());
                    params.put("delivery", Boolean.toString(order.isDelivery()));
                    params.put("restaurant_ID", Integer.toString(order.getRestaurant_id()));
                    params.put("CustID", Integer.toString(order.getCusId()));
                    params.put("items", order.getMeals());
                    params.put("noOfmeals", Integer.toString(order.getNumberOfMeals()));

                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void editOrder(final Order order) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.editOrderURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK"))
                                    ((DataListener) mContext).onDBResponse("Ordre is updated.");
                                else if (Response.equals("mealNotAvailable"))
                                    ((DataListener) mContext).onDBResponse("The meal you chose isn't in the menu, please select another item");
                                else
                                    ((DataListener) mContext).onDBResponse("We could not find your order.");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("timee", order.getTime());
                    params.put("delivery", Boolean.toString(order.isDelivery()));
                    params.put("restaurant_ID", Integer.toString(order.getRestaurant_id()));
                    params.put("done", Boolean.toString(order.isDone()));
                    params.put("CustID", Integer.toString(order.getCusId()));
                    params.put("items", order.getMeals());
                    params.put("noOfmeals", Integer.toString(order.getNumberOfMeals()));

                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void deleteOrder(final String restID, final String custID) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.deleteOrderURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK"))
                                    ((DataListener) mContext).onDBResponse("Ordre is deleted.");
                                else
                                    ((DataListener) mContext).onDBResponse("We could not find your order.");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("cusID", custID);
                    params.put("resID", restID);

                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }


    public void updateUserInfo(final User user) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.updateUserURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                ((DataListener) mContext).onUpdateInfo(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("ID", user.getID());
                    params.put("mail", user.getMail());
                    params.put("password", user.getPassword());
                    params.put("name", user.getName());
                    params.put("phone", user.getPhone());
                    params.put("address", user.getAddress());

                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void getUserInfo(final String id) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.retriveUserURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                User user = new User();
                                JSONObject jsonObject = new JSONObject(response);
                                user.setName(jsonObject.getString("name"));
                                user.setMail(jsonObject.getString("mail"));
                                user.setPassword(jsonObject.getString("password"));
                                user.setPhone(jsonObject.getString("phone"));
                                user.setAddress(jsonObject.getString("address"));
                                ((DataListener) mContext).onRetriveUser(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);

                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void insertComplaint(final String[] complaint) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.complaintURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK")) {
                                    ((DataListener) mContext).onDBResponse("Your complain will be dealt with");
                                } else
                                    ((DataListener) mContext).onDBResponse("We had a problem saving your complaint, please try again.");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("branchID", complaint[0]);
                    params.put("resID", complaint[1]);
                    params.put("userID", complaint[2]);
                    params.put("file", complaint[3]);
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public boolean checkNetworkConnection() {
        Log.i("NN", "2bl");
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.i("NN", "b3d");
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void check_login(final String username, final String password) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            Log.i("uuu", username);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getLoginURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String customer_ID;
                            String name;
                            try {
                                Log.i("pppp",response);
                                JSONObject jsonObject = new JSONObject(response);
                                customer_ID = jsonObject.getString("response");
                                Log.i("oooooo",customer_ID);
                                name = jsonObject.getString("name");
                                ((DataListener) mContext).onLoginReceived(customer_ID, name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("mail", username);
                    params.put("password", password);
                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        }
    }

    public void getTime(final String RestID) {
        Log.i("pppppp", RestID);
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            //hghyr el url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getTimeOpenAndEndUR,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String resp;
                                JSONObject jsonObject = new JSONObject(response);
                                resp = jsonObject.getString("time");
                                Log.i("oooo", resp);
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("RestID", RestID);
                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void Delievrey(final String RestID, final String brancID) {
        Log.i("PARAMsssssS", brancID + " " + RestID);
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getKidsMenuURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                String resp = null;
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject = jsonArray.getJSONObject(0);
                                resp = jsonObject.getString("answer");
                                Log.i("oooo", resp);
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("RestID", RestID);
                    params.put("branchID", brancID);
                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void KidsMenu(final String RestID) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            //hghyr el url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getKidsMenuURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                String resp = null;
                                JSONObject jsonObject = new JSONObject(response);
                                resp = jsonObject.getString("kidsmenu");
                                Log.i("oooo", resp);
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("RestID", RestID);
                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void KidsArea(final String RestID, final String BranchID) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            //hghyr el url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getKidsAreaURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("RESPONSE", response);
                                String resp = null;
                                JSONObject jsonObject = new JSONObject(response);
                                resp = jsonObject.getString("KidsArea");
                                Log.i("oooo", resp);
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("RestID", RestID);
                    params.put("branchID", BranchID);

                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void SmokingArea(final String RestID, final String BranchID) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            //hghyr el url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.getSmokingAreaURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.i("RESPONSE", response);
                                String resp = null;
                                JSONObject jsonObject = new JSONObject(response);
                                resp = jsonObject.getString("SMokingArea");
                                Log.i("oooo", resp);
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("RestID", RestID);
                    params.put("branchID", BranchID);

                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }

    public void Reserve(final Reservation reserve) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.ResverveURl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String resp;
                                Log.i("lllll",response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK"))
                                {
                                    Log.d("SERVER", "saved!");
                                    resp = "you have reserved a table ";
                                }
                                else if(Response.equals("already_Reserved"))
                                    {
                                        resp="You can only make on reservation at a time and you already have one";
                                    }
                                else if (Response.equals("notime"))
                                    resp="The time you choose is out of the restaurant open and close time";
                                else
                                    resp = "Sorry there isn't an empty table in this date and time";
                                ((DataListener) mContext).onDataReceived(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userID", reserve.getCusID());
                    params.put("numOfPeople", Integer.toString(reserve.getNoOfPeople()));
                    params.put("branchID", reserve.getBranchID());
                    params.put("resID", reserve.getResID());
                    params.put("timeReserved", reserve.getTimeReserved());
                    params.put("timeMade", reserve.getTimeMade());
                    params.put("date", reserve.getDate());
                    return params;
                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }


    public void cancelReservation(final String branchID, final String RestID, final String custID) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.deleteResvervationURl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String resp;
                                Log.i("oooooo",response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK")) {
                                    Log.d("SERVER", "saved!");
                                    resp = "your reservation has been cancelled successfully ";
                                }
                                else if (Response.equals("no Reserve"))
                                {
                                    resp = "Sorry there is no reservation with your name at this time";
                                }
                                else resp = "Sorry there is no reservation with your name";
                                ((DataListener) mContext).onDBResponse(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userID", custID);
                    params.put("branchID", branchID);
                    params.put("resID", RestID);
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");
    }


    public void updateReservation(final String branchID, final String RestID, final String custID , final String timeReserved , final String timeMade , final  String date , final int numOfPeople) {
        if (checkNetworkConnection()) {
            Log.i("connnected", "connnected");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, dbInfo.updateReservationURl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String resp;
                                Log.i("iiiii", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("OK")) {
                                    Log.d("SERVER", "saved!");
                                    resp = "your reservation has been updated successfully ";
                                } else if (Response.equals("no table"))
                                    resp = "Sorry there is no place to add another people";

                                else if (Response.equals("no Reserve"))
                                {
                                    resp = "Sorry there is no reservation with your name at this time";
                                }
                                else resp = "Sorry there is no reservation with your name to edit";
                                ((DataListener) mContext).onDBResponse(resp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVERerror", "failed!");
//                    Log.i("ERROR", error.getMessage());
                    VolleyLog.e("Error: ", error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d("jsonERROR", jsonError);
                        // Print Error!
                    }

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userID", custID);
                    params.put("branchID", branchID);
                    params.put("resID", RestID);
                    params.put("numOfPeople",Integer.toString(numOfPeople));
                    params.put("timeReserved", timeReserved);
                    params.put("timeMade", timeMade);
                    params.put("date", date);
                    return params;

                }
            };
            MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        } else Log.i("NNN", "not connected");


    }
}


