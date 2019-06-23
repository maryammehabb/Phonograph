package com.example.android.phonograph;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
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
import android.widget.Chronometer;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.speech.SpeechRecognizer.ERROR_NO_MATCH;

public class callPage extends AppCompatActivity implements RecognitionListener , DataListener {
    TextView outputText;
    TextToSpeech toSpeech;
    int result;
    private SpeechRecognizer mSpeechRecognizer;
    TextView txtSpeechInput;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private  Intent intent;
    String intentt = "";
    String response = "";
    String complain="";
    private ServerDB serverdb = new ServerDB(this);
    String x ="";
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<String> details = new ArrayList<String>();
    String[] info= {" ", " ", " " , ""};
    int customerID = 0;
    int resID = 0;
    int hold = 1500;
    boolean rightMeal=true;
    String l = "";
    boolean f = false;
    int count = 0;
    boolean flag = true;
    private Chronometer counter;
    private ImageButton endcall;
    String[] info2= {" ", " ", " "};


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
        info2[0]=info[0];
        info2[1]=info[1];
        info2[2]=info[2];
        customerID = Integer.parseInt(info[0]);
        resID = Integer.parseInt(info[2]);
        requestRecordAudioPermission();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        counter = findViewById(R.id.counter);
        endcall = findViewById(R.id.ibEndCall);

        counter.setBase(SystemClock.elapsedRealtime());
        counter.start();

        endcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpeechRecognizer.cancel();
                counter.stop();
                Intent intent = new Intent(callPage.this, Sample_Restaurant.class);
                intent.putExtra("info",info2);
                startActivity(intent);
            }
        });

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

        outputText = (TextView) findViewById(R.id.outputText);

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

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSpeechRecognizer.cancel();
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

    int noOfPeople;
    String timeMade;
    String time;
    ArrayList<String[]> detailsList = new ArrayList<>();


    public void updateDatabase(){
        if (intentt.equals("\"make order\"")){
            Date date = new Date();
            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            timeMade=formattedDate;
            String meals = details.get(0);
            int numberOfMeals = Integer.valueOf(details.get(1));
            Order order = new Order(timeMade, true, 0, customerID, meals, numberOfMeals, resID);
            serverdb.makeOrder(order);
        }

        if (intentt.equals("\"delete order\""))
        {
            serverdb.deleteOrder(info[2],info[0]);

        }
        if (intentt.equals("\"edit order\""))
        {
            Date date = new Date();
            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            timeMade=formattedDate;
            String meals = details.get(0);
            int numberOfMeals = Integer.valueOf(details.get(1));
            Order order = new Order(timeMade, true, 0, customerID, meals, numberOfMeals, resID);
            serverdb.editOrder(order);

        }
        if(intentt.equals("\"make reservation\""))
        {
            Date date = new Date();
            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            timeMade=formattedDate;
            noOfPeople = Integer.parseInt(details.get(0));
                Reservation reserve = new Reservation(info[0], noOfPeople, info[3],info[2], details.get(2), timeMade , details.get(1));
                serverdb.Reserve(reserve);
        }
        if (intentt.equals("\"edit reservation\""))
        {
            Date date = new Date();
            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            timeMade=formattedDate;
            noOfPeople = Integer.parseInt(details.get(0));
            serverdb.updateReservation(info[3],info[2],info[0],details.get(2),timeMade,details.get(1),noOfPeople);


        }
        if (intentt.equals("\"delete reservation\""))
        {
            serverdb.cancelReservation(info[3],info[2],info[0]);
        }
        if (intentt.equals("\"is there kids menu\"")){

           serverdb.KidsMenu(info[2]);
        }
        if (intentt.equals("\"kids area\""))
        {
            serverdb.KidsArea(info[2],info[3]);
        }
        if (intentt.equals("\"smoking area\""))
        {
            serverdb.SmokingArea(info[2],info[3]);
        }
        if (intentt.equals("\"delivery\""))
        {
           serverdb.Delievrey(info[2],info[3]);
        }
        if (intentt.equals("\"open and close\""))
        {
            serverdb.getTime(info[2]);
        }

        if (intentt.equals("\"Complains\"") && count==2)
        {
            if (!complain.equals(""))
            {
                String[] complaint = {info[3], info[2], info[0], complain};
                serverdb.insertComplaint(complaint);
                intentt = "";
            }
        }
    }

    public void changeFlag(){
        for (int i = 0 ; i<details.size() ; i++ ){
            if (details.get(i).equals("[]")){
                flag=false;
                break;
            }
        }
    }

    public void cleanParams(){
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
    }


    // Create GetText Metod
    public String GetText(String query) throws IOException, JSONException {

        String text = "";
        BufferedReader reader = null;

        if(intentt.equals("\"Complains\"")){
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
        jsonParam.put("lang", "en");
        jsonParam.put("sessionId", "1234567890");


        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(jsonParam.toString());
        wr.flush();

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


        details = new ArrayList<String>();

        for (int i =0 ; i< parameters.length() ; i++){
            details.add(parameters.getString(parameters.names().get(i).toString()));
            params.add(parameters.names().get(i).toString());
            //Log.d("params" , parameters.names().get(i).toString() + " : " +parameters.getString(parameters.names().get(i).toString()));
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

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
        //Log.i("errror", String.valueOf(error));
        if(error==ERROR_NO_MATCH)
        {
            promptSpeechInput();
        }
        if(error==2 || error ==8)
        {
            //Log.i("Error", "restart listener");
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
        complain = matches.get(0);

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    String [] i = {info[0],info[1],info[2]};
    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            String s="";
                try {
                    s = GetText(voids[0]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return s;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           if(intentt.equals("\"Complains\"")) count++;
            flag = true;
            changeFlag();
            if(flag==true){
                cleanParams();
                updateDatabase();
                hold = 4000;
            }
            outputText.setText(s);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
            } else {
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.cancel();
                unmute();
                toSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(hold);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (intentt.equals("\"End of call\"")) {
                count = 0;
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

    @Override
    public void onLoginReceived(String id,String name)
    {}
    @Override
    public void onDataReceived(String response)
    {
        unmute();
        toSpeech.speak(response, TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(hold);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mute();


    }
    @Override
    public String oncancelReservation(String resp)
    {
        if(resp=="0")
            return "There is no Reservation with your name , it cant be deleted";
        else
            return "Your  Reservation is deleted";

    }

    @Override
    public Restaurant onOneRestaurant(Restaurant rest) {
        return null;
    }

    @Override
    public ArrayList<Restaurant> onAllRestaurants(ArrayList<Restaurant> rest) {
        return null;
    }

    @Override
    public void onDBResponse(String response) {
            if(details.size() > params.size()) {
                details = new ArrayList<>();
                detailsList = new ArrayList<>();

        }
        l = response;
        f = true;
        unmute();
        Log.i("before" , l);
        toSpeech.speak(response, TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(hold);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mute();
    }

    @Override
    public void onUpdateInfo(String response) {

    }

    @Override
    public void onRetriveUser(User user) {

    }
}








