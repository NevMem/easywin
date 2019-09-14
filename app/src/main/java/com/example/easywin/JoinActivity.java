package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import kotlin.NotImplementedError;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void onJoinButtonClick(View view) {
        // TODO:
    }

    public void onQrButtonClick(View view) {
        // TODO:
    }


    private String getTextFromEditText(View view) {
        EditText editText = (EditText) findViewById(R.id.meeting_id);
        String meetingName = editText.getText().toString();
        return meetingName;
    }
}
