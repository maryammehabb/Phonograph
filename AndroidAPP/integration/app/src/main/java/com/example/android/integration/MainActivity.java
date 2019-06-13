package com.example.android.integration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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

import com.example.android.Integration.R;

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
import java.util.Map;

import static android.speech.SpeechRecognizer.ERROR_NO_MATCH;

public class MainActivity extends AppCompatActivity implements RecognitionListener {
    TextView outputText;
    //EditText editText;
    private SpeechRecognizer mSpeechRecognizer;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private  Intent intent;
    String o = "Hi";
    String intentt = "";
    String response = "";
    String complain;
    String time;
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<String> details = new ArrayList<String>();


    public TextView getTxtSpeechInput() {
        return txtSpeechInput;
    }

    public String getintent(){
        return intentt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestRecordAudioPermission();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        outputText = (TextView) findViewById(R.id.outputText);

        // hide the action bar
        // getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

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

        //to mute the beep

        AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        amanager.setStreamMute(AudioManager.STREAM_RING, true);
        amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        mSpeechRecognizer.setRecognitionListener(this);
        mSpeechRecognizer.startListening(intent);
    }



    public boolean updateDatabase(String intent){
        if (intentt.equals("\"make order\"")){
          Order order = new Order(time, true , 0 , 1 , "0" );
            //meals = details.get(0)
            //numberOfMeals = details.get(1)
            //leave cusId & resturaunt_id to the integration (we get it from prev page)
            //make function in dbHelper to access items table and calc price
            //make a function in dbHelper insertOrder
            //DBHelper d
            // d.insertOrder(order)
        }
        //make if condition for each intent
        return false;
    }



    // Create GetText Metod
    public String GetText(String query) throws IOException, JSONException {

        String text = "";
        BufferedReader reader = null;

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
        for (int i =0 ; i< parameters.length() ; i++){
            params.add(parameters.getString(parameters.names().get(i).toString()));
            details.add(parameters.names().get(i).toString());
            Log.d("params" , parameters.names().get(i).toString() + " : " +parameters.getString(parameters.names().get(i).toString()));
        }

        speech = fulfillment.optString("speech");
        Calendar cal = Calendar. getInstance();
        Date date=cal. getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        time=dateFormat. format(date);
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
    public void onError(int error)
    {
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
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            String s = "Thank you.";
            Log.d("Naghamfen" , intentt);
            if (intent.equals("\"Complains\"")) {
                Log.d("nagham" , intentt);
                return s;
            } else{
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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Thank you.")){
                Log.d("nagham", "Go to database " + complain);
            }
            outputText.setText(s);
            if (intentt.equals("\"End of call\"")) {
                Toast.makeText(getBaseContext(), "Call has ended", Toast.LENGTH_SHORT).show();
                mSpeechRecognizer.cancel();
            }
            else
                mSpeechRecognizer.startListening(intent);
        }
    }
}
