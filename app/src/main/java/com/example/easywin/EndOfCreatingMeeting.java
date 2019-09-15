package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.View;

public class EndOfCreatingMeeting extends AppCompatActivity {

    String[] names = {"Misha","Alekcey","Igor","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey"};
    TextView textViewOfNameLobby;
    LinearLayout listViewNames;
    TextView textViewIdent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_creating_meeting);

        // Name of lobby
        textViewOfNameLobby = findViewById(R.id.textViewOfNameLobby);
        textViewOfNameLobby.setText("Название лобби");
        // Identificator
        textViewIdent = findViewById(R.id.textViewIdentificator);
        textViewIdent.setText("Номер индефитикатора");

        listViewNames = findViewById(R.id.list_of_names);

    }

    public void onEndOfCreateingButtonClick(View view){
        // Загружать данные об участниках.
        findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
    }

    void FillNames(){
        for(Integer i = 0; i < names.length; i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View child = inflater.inflate(R.layout.user_row, listViewNames, false);

            ((TextView)child.findViewById(R.id.name)).setText(names[i]);
            listViewNames.addView(child);
        }
    }

    // For QR code.
    public void OnClick(View view){

    }
}
