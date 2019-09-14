package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import kotlin.NotImplementedError;

public class JoinActivity extends AppCompatActivity {
    TextView topBarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        topBarTextView = findViewById(R.id.kek);
        topBarTextView.setText("Оплатить долю");
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
