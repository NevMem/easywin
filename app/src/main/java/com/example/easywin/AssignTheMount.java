package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
            ((Button) child.findViewById(R.id.assignMoney)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment am = new AssignMoney();
                    am.show(getSupportFragmentManager(), "missiles");
                }
            });
            child.findViewById(R.id.transOverNamesList).setVisibility(View.VISIBLE);
            listViewNames.addView(child);
        }
    }

    public  void onClick(View view){
        //findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
    }

    public static class AssignMoney extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomDialogStyle);
            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                    .setPositiveButton("Принять",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //LoginDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }

}

