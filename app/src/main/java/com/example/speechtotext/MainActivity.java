package com.example.speechtotext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH = 100;

    final Context context= this;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView btnSpeak = findViewById(R.id.button);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.notes_layout);

                Button btnActivateSpeak = dialog.findViewById(R.id.speak);
                btnActivateSpeak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     activateSpeechInput();
                    }
                });
                dialog.show();
            }
        });
    }

    public void activateSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH);
        }catch(ActivityNotFoundException e){

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH && resultCode == RESULT_OK && data != null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            EditText editText = dialog.findViewById(R.id.editText);
            editText.setText(result.get(0));
        }
    }
}
