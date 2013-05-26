package au.com.grouch.android;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements TextToSpeech.OnInitListener {

    // UI Elements
    Button btnSpeak;
    EditText txtQuery;
    TextView lblPostcode;

    private int SPEECH_REQUEST_CODE = 26513;
    private TextToSpeech textToSpeech;

    @Inject
    android.location.LocationManager locationManager;


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)
        {
            // start searching
            // TODO : start searching
        }
        else
        {
            //failed to init
            finish();
        }
    }

    private void determineClosestCouncil(){


       DeterminePostcodeRequest determinePostcodeRequest = new DeterminePostcodeRequest(){
           @Override
           protected void onPostExecute(String s) {
               lblPostcode.setText(s);
               super.onPostExecute(s);
           }
       };
        String lat = "-33.918";
        String lng = "151.231";
        determinePostcodeRequest.execute(lat,lng);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializing the UI Elements
        setContentView(R.layout.activity_main);
        btnSpeak = (Button) findViewById(R.id.bt_speakonce);
        txtQuery = (EditText) findViewById(R.id.txtQuery);
        lblPostcode = (TextView) findViewById(R.id.lblPostcode);

        // initializing text to speech, in case the user prefers to use listening function
        textToSpeech
                = new TextToSpeech(this,this);

        determineClosestCouncil();


        // setting up the listeners
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startRecognitionIntent();
            }
        });

    }

    private void startRecognitionIntent(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you want to recycle?");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0)
                {
                    textToSpeech.speak("Oops I missed that! Can you say it again?", TextToSpeech.QUEUE_FLUSH, null);
                }
                else
                {
                  txtQuery.setText(matches.get(0));
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy()
    {
        if (textToSpeech != null)
        {
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
    
}
