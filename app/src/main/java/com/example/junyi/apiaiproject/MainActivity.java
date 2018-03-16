package com.example.junyi.apiaiproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import com.google.gson.JsonElement;
import java.util.Map;



public class MainActivity extends Activity implements AIListener {

    Button listenButton;
    TextView resultTextView;
    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listenButton = findViewById(R.id.listenButton);
        resultTextView = findViewById(R.id.resultTextView);


        // Configuration for using system speech recognition
        final AIConfiguration config = new AIConfiguration("c242c3b1725d49b5bf71b306b04831d5",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        // Initialize AI Service
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    public void listenButtonOnClick (final View view) {
        aiService.startListening();
        Log.d("listen", "start");
    }

    public void onResult(final AIResponse response) {
        /*Result result = response.getResult();
        Log.d("result","positive");

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String,JsonElement> entry:result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ")";
            }
        }
        else {
            Log.d("empty","parameter");
        }

        resultTextView.setText("Query: " + result.getResolvedQuery() +
        "\nAction: " + result.getAction() +
        "\nParameters: " + parameterString);*/

        Log.d("result","positive");
        Result result = response.getResult();

        // result.getResolvedQuery() = what is said
        // result.getAction() = action of intent in dialogflow (goto.page)
        resultTextView.setText(result.getResolvedQuery()+ " action: " + result.getAction());
    }

    public void onError(final AIError error) {
        resultTextView.setText(error.toString());
    }

    @Override
    public void onListeningStarted() { }

    @Override
    public void onListeningCanceled() {

    }

    ;

    @Override
    public void onListeningFinished() { };

    public void onAudioLevel(final float level) { };
}
