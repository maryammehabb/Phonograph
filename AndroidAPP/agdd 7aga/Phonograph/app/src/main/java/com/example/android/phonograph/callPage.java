package com.example.android.phonograph;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



import com.example.android.phonograph.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.speech.SpeechRecognizer.ERROR_NO_MATCH;

public class callPage extends AppCompatActivity implements RecognitionListener {
    TextView outputText;
    TextToSpeech toSpeech;
    int result;
    private SpeechRecognizer mSpeechRecognizer;
    TextView txtSpeechInput;
    ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private  Intent intent;
    String intentt = "";
    String response = "";
    String complain;
    String x ="";
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<String> details = new ArrayList<String>();
    String[] info= {" ", " ", " " , ""};
    int customerID = 0;
    int resID = 0;
    int hold = 2000;
    boolean rightMeal=true;


    public TextView getTxtSpeechInput() {
        return txtSpeechInput;
    }

    public String getintent(){
        return intentt;
    }

    //to mute the beep
    public void mute(){
        AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        amanager.setStreamMute(AudioManager.STREAM_RING, true);
        amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
    }
    public void unmute(){
        AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        amanager.setStreamMute(AudioManager.STREAM_RING, false);
        amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_page);

        Intent intent1 = getIntent();
        info = intent1.getStringArrayExtra("info");
        //  cusId   cusName  restID
        Log.d("NAGHAMINFO" , info[0] + " " + info[1] + " " + info[2]+ " "+info[3]);
        customerID = Integer.parseInt(info[0]);
        resID = Integer.parseInt(info[2]);
      //  btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        requestRecordAudioPermission();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

        outputText = (TextView) findViewById(R.id.outputText);

        // hide the action bar
        // getActionBar().hide();

        toSpeech = new TextToSpeech(callPage.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    result=toSpeech.setLanguage(Locale.UK);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Handler().postDelayed(new Runnable()
        {
            public void run(){
                promptSpeechInput();
            }
        }, 1000);

       /* btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });*/
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        toSpeech.shutdown();
    }

    private void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO))
                {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }

    private void promptSpeechInput() {

        mute();
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        mSpeechRecognizer.setRecognitionListener(this);
        mSpeechRecognizer.startListening(intent);

    }

    int cusID;
    int noOfPeople;
    int tableID;
    String timeReserved;
    String timeMade;
    String o = "Hi";
    String time;
    ArrayList<String[]> detailsList = new ArrayList<String[]>();

    public float calcPrice() {
        float price = 0;
        dbHelper db = new dbHelper(this);
        for (int i=0 ; i<detailsList.get(0).length ; i++){
            Log.d("PRICE" , detailsList.get(0)[i] + " * " + detailsList.get(1)[i] );

            price += db.calcPrice(detailsList.get(0)[i], info[2])*Integer.parseInt(detailsList.get(1)[i]);
        }

        return  price;
    }

    public String updateDatabase(String intent){
        String s = "";
        int ret = -1;
        if (intentt.equals("\"make order\"")){
            Log.d("NAGHAM", "MAKE ORDER");
            Log.d("info", details.get(0));
            Log.d("info", details.get(1));
            String meals = details.get(0);
            float price = 0;
            price = calcPrice();
            int numberOfMeals = Integer.valueOf(details.get(1));
            //leave cusId & resturaunt_id to the integration (we get it from prev page)
            //make function in dbHelper to access items table and calc price
            //make a function in dbHelper insertOrde

            Order order = new Order(time, true, price, customerID, meals, numberOfMeals, resID);

            //order.setMeals(meals);
            //order.setNumberOfMeals(numberOfMeals);
            dbHelper d = new dbHelper(getApplicationContext());
            //d.createDatabase();
            ret = d.insertOrder(order);
            if (ret == -1) return "We had a problem making you order pleas try again";
            return "Order was done";
        }

        if (intentt.equals("\"delete order\""))
        {
            dbHelper d=new dbHelper(getApplicationContext());
            s = d.deleteOrder(info[0],info[2]);
            return s;

        }
        if (intentt.equals("\"edit order\""))
        {
            String meals = details.get(0);
            float price = 0;
            price = calcPrice();
            int numberOfMeals = Integer.valueOf(details.get(1));
            Order order = new Order(time, true, price, customerID, meals, numberOfMeals, resID);
            dbHelper d=new dbHelper(getApplicationContext());
            s = d.updateData(order, info[2], info[0]);
            return s;

        }
        //make if condition for each intent
        //if Customer make a reservation
        if(intentt.equals("\"make reservation\""))
        {
            Date date = new Date();
            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            timeMade=formattedDate;
            dbHelper db= new dbHelper(this);
            noOfPeople = Integer.parseInt(details.get(0));
            ArrayList<String> tables=db.CheckTables(info[3],noOfPeople);
            if(tables!=null)
            {
                String TableID="";
                for(int i=0 ; i<tables.size();i++)
                {
                    TableID+=tables.get(i)+" ";
                }

                timeReserved = details.get(1)+" "+details.get(2);
                Reservation reserve = new Reservation(info[0], noOfPeople, TableID,info[3],info[2], timeReserved, timeMade);
                db.reserve(reserve);
                db.getReservation(info[0],info[2],info[3]);
                return "Your Reservation is done";
            }
            else
            {
                return "There isnt empty Tables";
            }
        }
        if (intentt.equals("\"edit reservation\""))
        {
            dbHelper db = new dbHelper(this);
            Reservation reserve = db.getReservation(info[0],info[2],info[3]);
            if(reserve==null)
            {
                return "There is no Reservation with your name";
            }
            else
            {
              reserve.setNoOfPeople( Integer.parseInt(details.get(0)));
              reserve.setTimeReserved(details.get(1)+" "+details.get(2));
              Date date = new Date();
              String strDateFormat = "hh:mm:ss a";
              DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
              String formattedDate= dateFormat.format(date);
              timeMade=formattedDate;
              db.updateReservation(reserve,String.valueOf(reserve.getID()) );
              return "Your Reservation is edited";
            }
        }
        if (intentt.equals("\"delete reservation\""))
        {
           // String id =details.get(0);
            dbHelper db=new dbHelper(getApplicationContext());
            int i=db.deleteReservation(info[0],info[2],info[3]);
            if(i==0)
                return "There is no Reservation with your name , it cant be deleted";
            else
                return "Your  Reservation is deleted";
        }
        if (intentt.equals("\"is there kids menu\"")){
            boolean r;
            dbHelper d = new dbHelper(getApplicationContext());
            Log.d("", info[3]);
            r = d.kidsMenu(info[2]);
            if (r == true){
                return "Yes";
            }
            return "No";
        }
        if (intentt.equals("\"kids area\""))
        {
            boolean r;
            dbHelper d = new dbHelper(getApplicationContext());
          //  Log.d("", info[4]);
            r = d.kidsArea(info[3],info[2]);
            if (r == true){
                return "Yes";
            }
            return "No";
        }
        if (intentt.equals("\"smoking area\""))
        {
            boolean r;
            dbHelper d = new dbHelper(getApplicationContext());
           // Log.d("", info[4]);
            r = d.smokingArea(info[3],info[2]);
            if (r == true)
            {
                return "Yes";
            }
            return "No";
        }
        if (intentt.equals("\"delivery\""))
        {
            boolean r;
            dbHelper d = new dbHelper(getApplicationContext());
            // Log.d("", info[4]);
            r = d.delivery(info[3],info[2]);
            if (r == true)
            {
                return "Yes";
            }
            return "No";
        }
        if (intentt.equals("\"open and close\""))
        {
            dbHelper d = new dbHelper(getApplicationContext());
            s= d.openAndCloseTime(info[2]);
            return s;
        }
        return null;
    }


    // Create GetText Metod
    public String GetText(String query) throws IOException, JSONException {

        String text = "";
        BufferedReader reader = null;

        if(intentt.equals("\"Complains\"")){
            Toast.makeText(getBaseContext(), "Call has ended", Toast.LENGTH_SHORT).show();
            complain = query;
            return "";
        }

        // Send data


        // Defined URL  where to send data
        URL url = new URL("https://api.api.ai/v1/query?v=20150910");

        // Send POST data request

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setRequestProperty("Authorization", "Bearer cd4fbf5bf0bb489784266935eb751421");
        conn.setRequestProperty("Content-Type", "application/json");

        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        JSONArray queryArray = new JSONArray();
        queryArray.put(query);
        jsonParam.put("query", queryArray);
//            jsonParam.put("name", "order a medium pizza");
        jsonParam.put("lang", "en");
        jsonParam.put("sessionId", "1234567890");


        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        //Log.d("karma", "after conversion is " + jsonParam.toString());
        wr.write(jsonParam.toString());
        wr.flush();
        //Log.d("karma", "json is " + jsonParam);


        // Get the server response

        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Map<String, String> map = new HashMap<String, String>();
        String[] arrOfStr1 ;
        String[] arrOfStr2 = {};
        ArrayList<String> keys = new ArrayList<String>();


        // Read Server Response
        while ((line = reader.readLine()) != null) {
            arrOfStr1 = line.split(",");
            for (int i =0 ; i<arrOfStr1.length ; i++ ){
                arrOfStr2 = arrOfStr1[i].trim().split(":");
                //Log.d("RECORD" , arrOfStr1[i]);
                if(arrOfStr2.length > 1) {
                    map.put(arrOfStr2[0], arrOfStr2[1].trim());
                    keys.add(arrOfStr2[0].trim());
                }
            }
            // Append server response in string
            sb.append(line + "\n");
        }

        for(int i = 0 ; i<keys.size() ; i++){
        }
        intentt = map.get("\"intentName\"");
        if(!intentt.equals("\"End of call\""))
            x = intentt;
        response = map.get("\"speech\"");

        Log.d("Intent" ,map.get("\"intentName\""));

        text = sb.toString();

        JSONObject object1 = new JSONObject(text);
        JSONObject object = object1.getJSONObject("result");
        JSONObject fulfillment = null;
        JSONObject parameters = null;
        String speech = null;
        fulfillment = object.getJSONObject("fulfillment");
        parameters = object.getJSONObject("parameters");

        if(intentt.equals("\"make order\"") && !parameters.getString(parameters.names().get(0).toString()).equals("[]") ||
                intentt.equals("\"edit order\"") && !parameters.getString(parameters.names().get(0).toString()).equals("[]")){
            dbHelper db = new dbHelper(this);

            rightMeal= db.checkMeal(parameters.getString(parameters.names().get(0).toString()).substring(2,parameters.getString(parameters.names().get(0).toString()).length()-2), info[2]);
            Log.i("CHECK" , parameters.getString(parameters.names().get(0).toString())+" : " +rightMeal);
        }

        details = new ArrayList<String>();

        for (int i =0 ; i< parameters.length() ; i++){
            details.add(parameters.getString(parameters.names().get(i).toString()));
            params.add(parameters.names().get(i).toString());
            Log.d("params" , parameters.names().get(i).toString() + " : " +parameters.getString(parameters.names().get(i).toString()));
        }
        for (int i =0 ; i< details.size() ; i++){
            Log.d("FEEEN" , details.get(i));
        }

        speech = fulfillment.optString("speech");

        Log.d("karma ", "response is " + text);
        return speech;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Activity", "Granted!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Activity", "Denied!");
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("lllll",result.get(0));
                    txtSpeechInput.setText(result.get(0));
                    complain = result.get(0);
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

        Toast.makeText(getBaseContext(), "Voice recording starts", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //Log.d("VOLUME", Float.toString(rmsdB));

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
        Log.i("errror", String.valueOf(error));
        if(error==ERROR_NO_MATCH)
        {
            promptSpeechInput();
        }
        if(error==2 || error ==8)
        {
            Log.i("Nagham", "restart listener");
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.setRecognitionListener(this);
            mSpeechRecognizer.startListening(intent);
        }

        if(error==6)
        {
            promptSpeechInput();
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        txtSpeechInput.setText(matches.get(0));
        new RetrieveFeedTask().execute(txtSpeechInput.getText().toString());
        Log.i("LLLLL",matches.get(0));
        complain = matches.get(0);


    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    boolean saveComplain = false;

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            Log.i("HIIII" , intentt);
            String s = "Thank you.";
            if (intentt.equals("\"Complains\"")) {
                Log.d("nagham", intentt);
            } else {
                try {
                    s = GetText(voids[0]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                return s;

        }


        String [] i = {info[0],info[1],info[2]};
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Thank you.")) {
                Log.d("nagham", "Go to database " + complain);
                dbHelper db=new dbHelper(getApplicationContext());
                s=db.insertCompliant(complain,info[3],info[2],info[0]);
                unmute();
                hold = 3000;
                Toast.makeText(getBaseContext(), "Call has ended", Toast.LENGTH_SHORT).show();
                mSpeechRecognizer.cancel();
                Intent intent = new Intent(callPage.this, Sample_Restaurant.class);
                intent.putExtra("info",i);
                startActivity(intent);


            }
            boolean flag = true;
            for (int i = 0 ; i<details.size() ; i++ ){
                Log.d("??" , details.get(i));
                if (details.get(i).equals("[]")){
                    flag=false;
                    break;
                }
            }
            if(flag==true && !intentt.equals("\"Complains\"")){
                for (int i = 0 ; i<details.size(); i++){
                    details.set(i, details.get(i).substring(1,details.get(i).length()-1));
                    String split [] = details.get(i).split(",");
                    if(split[0].startsWith("\"")){
                        for (int j=0 ; j<split.length ; j++){
                            split[j]=split[j].substring(1,split[j].length()-1);
                        }
                    }
                    detailsList.add(split);
                }
                details = new ArrayList<>();
                for (int i=0 ; i<detailsList.size() ; i++){
                    String p = "";
                    for (int j=0 ; j<detailsList.get(i).length ; j++){
                        if (j==detailsList.get(i).length-1) p += detailsList.get(i)[j];
                        else p += detailsList.get(i)[j]+ ", ";
                    }
                    details.add(p);

                }
                Log.d("FINALPARAMS","*********************************************");
                for(int i=0 ; i<details.size() ; i++){
                    Log.d("FINALPARAMS",details.get(i));
                }


                Log.d("FLAG " , x);
                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String time=dateFormat.format(date);

                if(!rightMeal) {

                    Log.d("NAGHAM" , "GALAAAAT!!!");
                    //unmute();
                    //toSpeech.speak("the meal you chose is not in the menu, select another item, please" , TextToSpeech.QUEUE_FLUSH, null);
                    //Toast.makeText(getBaseContext(), "Call has ended", Toast.LENGTH_SHORT).show();
                    //return;
                    s = "the meal you chose is not in the menu, select another item, please";
                    details = new ArrayList<>();
                    detailsList = new ArrayList<>();
                    hold=4000;
                }
                else {
                    s =  updateDatabase(x);
                    hold = 3000;
                }

            }
            outputText.setText(s);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
            } else {
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.cancel();
                //Thread thread = new Thread();
                unmute();
                toSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(hold);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (intentt.equals("\"End of call\"")) {
                toSpeech.speak("bye.", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(getBaseContext(), "Call has ended", Toast.LENGTH_SHORT).show();
                mSpeechRecognizer.cancel();
                Intent intent = new Intent(callPage.this, Sample_Restaurant.class);
                intent.putExtra("info",i);
                startActivity(intent);

            } else {
                mute();
                mSpeechRecognizer.startListening(intent);
            }
        }
    }
}








