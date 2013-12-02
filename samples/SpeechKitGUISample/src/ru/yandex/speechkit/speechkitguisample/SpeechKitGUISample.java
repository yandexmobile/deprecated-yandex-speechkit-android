package ru.yandex.speechkit.speechkitguisample;

import ru.yandex.speechkit.speechkitguisample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;

public class SpeechKitGUISample extends Activity
{
    private static final String TAG = "SpeechKitGUISample";

    private Button startButton;
    private TextView resultView;    

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener (new OnClickListener() {
                public void onClick (View v) {
                    startVoiceRecognitionActivity();
                }
            });

        resultView = (TextView)findViewById(R.id.result_view);

        SpeechKit.getInstance().configure(getBaseContext(), "developers-simple-key");
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == RecognizerActivity.RESULT_OK) {
            onRecognitionDone(data.getStringExtra(RecognizerActivity.EXTRA_RESULT));
        }
        else if (resultCode == RecognizerActivity.RESULT_ERROR) {
            onError((Error) data.getSerializableExtra(RecognizerActivity.EXTRA_ERROR));
        }
    }

    public void onRecognitionDone(String result) {
    	Log.d(TAG, "onRecognitionDone " + result);

    	resultView.setText(result);
    }

    public void onError(Error error) {
    	Log.e(TAG, "onError " + error.toString());

    	resultView.setText("Error: " + error.toString());
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(this, RecognizerActivity.class);
        intent.putExtra(RecognizerActivity.EXTRA_LANGUAGE, "ru-RU");
        intent.putExtra(RecognizerActivity.EXTRA_MODEL, "general");
        startActivityForResult(intent, 0);
    }
}
