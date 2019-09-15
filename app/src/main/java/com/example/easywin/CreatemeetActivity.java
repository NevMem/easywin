package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatemeetActivity extends AppCompatActivity {


    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeet);

        editText = findViewById(R.id.editText1);
        button = findViewById(R.id.button);
    }

    public void onCreateButtonClick(View view){
        String str = editText.getText().toString();
        findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
    }

}
