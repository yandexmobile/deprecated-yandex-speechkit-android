package ru.yandex.speechkit.speechkitsample;

import ru.yandex.speechkit.speechkitsample.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;

public class SpeechKitSample extends Activity
{
    private static final String TAG = "SpeechKitSample";

    private ProgressBar powerLevelBar;
    private Button startButton;
    private Button finishButton;
    private Button cancelButton;
    private ListView resultsList;
    private TextView statusTextView;

    private Recognizer recognizer;

    ArrayAdapter<String> emptyArrayAdapter;

    private class TestRecognitionListener implements RecognizerListener {
    	public void onRecordingBegin(Recognizer r) { 
    	    Log.v(TAG, "onRecordingBegin"); 
            statusTextView.setText("onRecordingBegin");
    	}
    	
    	public void onRecordingDone(Recognizer r) { 
    	    Log.v(TAG, "onRecordingDone"); 
            statusTextView.setText("onRecordingDone");
            powerLevelBar.setProgress(0);
    	}
    	
    	public void onSoundDataRecorded(Recognizer r, byte[] data) { 
    	    Log.v(TAG, "onSoundDataRecorded"); 
    	}
    	
    	public void onSpeechDetected(Recognizer r) { 
    	    Log.v(TAG, "onSpeechDetected"); 
            statusTextView.setText("onSpeechDetected");
    	}
    	
    	public void onPowerUpdated(Recognizer r, float power) {
            Log.v(TAG, "onPowerUpdated: "+power);

            powerLevelBar.setProgress((int)(power * powerLevelBar.getMax()));
        }

        public void onRecognitionDone(Recognizer r, Recognition results) {
            Log.v(TAG, "onResults: " + results.getAllResults().length + " results");
            if (results.isResultConfident())
                statusTextView.setText(results.getResult().getText());
            else
                statusTextView.setText("" + results.getAllResults().length + " results");
            powerLevelBar.setProgress(0);

            ArrayList<String> matchReports = new ArrayList<String>();

            for (int i = 0; i < results.getAllResults().length; i++) {
            	matchReports.add(results.getAllResults()[i].toString());
            }

            resultsList.setAdapter (new ArrayAdapter<String>(getApplicationContext(),
                                                            android.R.layout.simple_expandable_list_item_1,
            												matchReports));

            recognizer = null;
        }

        public void onError(Recognizer r, Error error) {
            Log.v(TAG, "onError: " + error.getCode() + " (" + error.getString() + ")");
            statusTextView.setText(error.getString());
            powerLevelBar.setProgress(0);
            		
    	    recognizer = null;
        }
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        SpeechKit.getInstance().configure(getBaseContext(), "developers-simple-key");

        setContentView(R.layout.main);

        powerLevelBar = (ProgressBar)findViewById(R.id.powerLevelBar);

        resultsList = (ListView)findViewById(R.id.resultsList);
        emptyArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                                     R.id.resultsList,
                                                     new ArrayList<String>());
        resultsList.setAdapter(emptyArrayAdapter);

        statusTextView = (TextView)findViewById(R.id.statusTextView);
        statusTextView.setText("Ready");
        
        recognizer = null;

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener (new OnClickListener() {
                public void onClick (View v) {
                    resultsList.setAdapter(emptyArrayAdapter);

                    recognizer = Recognizer.create("ru-RU", "general", new TestRecognitionListener());

                    recognizer.start();
                }
            });

        finishButton = (Button)findViewById(R.id.finishButton);
        finishButton.setOnClickListener (new OnClickListener() {
                public void onClick (View v) {
                	recognizer.finishRecording();
                }
            });

        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	recognizer.cancel();
                }
            });
    }
}
