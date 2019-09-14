package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AssignTheMount extends AppCompatActivity {

    String[] names = {"Misha","Alekcey","Igor","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey"};
    TextView textViewOfNameLobby;
    LinearLayout listViewNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_the_mount);

        // Name of lobby
        textViewOfNameLobby = findViewById(R.id.textViewOfNameLobby);
        textViewOfNameLobby.setText("Название лобби");

        listViewNames = findViewById(R.id.list_of_names);
        fill_listview();
    }

    void fill_listview(){
        for(Integer i = 0; i < names.length; i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View child = inflater.inflate(R.layout.user_row, listViewNames, false);

            ((TextView)child.findViewById(R.id.name)).setText(names[i]);
            child.findViewById(R.id.transOverNamesList).setVisibility(View.VISIBLE);
            listViewNames.addView(child);
        }
    }

    public  void onClick(View view){
        findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
    }

}
