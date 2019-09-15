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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssignTheMount extends AppCompatActivity {

    //String[] names = {"Misha","Alekcey","Igor","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey"};
    final Map<String, Integer> costumers = new HashMap<String, Integer>();
    TextView textViewOfNameLobby;
    LinearLayout listViewNames;
    int total_summary = 0;

    void FillCostumers(){
        costumers.put("Tolya", 0);
        costumers.put("Fedya", 0);
        costumers.put("Katya", 0);
        costumers.put("Lesha", 0);
        costumers.put("Ira", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_the_mount);

        // Name of lobby
        textViewOfNameLobby = findViewById(R.id.textViewOfNameLobby);
        textViewOfNameLobby.setText("Название лобби");

        listViewNames = findViewById(R.id.list_of_names);
        FillCostumers();
        fill_listview();
    }

    public void fill_listview(){
        final Button baton = findViewById(R.id.button3);

        final AppCompatActivity thisActivity = this;
        final Set<String> names = costumers.keySet();
        listViewNames.removeAllViews();
        total_summary = Calculate();
        for(final String name : names){
            final LayoutInflater inflater = LayoutInflater.from(this);
            View child = inflater.inflate(R.layout.user_row, listViewNames, false);
            ((TextView)child.findViewById(R.id.name)).setText(name);
            final Button button = ((Button) child.findViewById(R.id.assignMoney));

            if (!costumers.get(name).equals(0)) {
                button.setText(Integer.valueOf(costumers.get(name)).toString());
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final View view = inflater.inflate(R.layout.dialog_signin, null, false);

                    if (!costumers.get(name).equals(0)) {
                        ((EditText) view.findViewById(R.id.CountText)).setText(Integer.valueOf(costumers.get(name)).toString());
                    }

                    AlertDialog dialog = new AlertDialog.Builder(thisActivity)
                            .setView(view)
                            .setPositiveButton("ПРИНЯТЬ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String value = ((EditText) view.findViewById(R.id.CountText)).getText().toString();
                                    try {
                                        int k = Integer.parseInt(value);
                                        costumers.put(name, k);
                                        fill_listview();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).create();

                    dialog.show();
                }
            });
            child.findViewById(R.id.transOverNamesList).setVisibility(View.VISIBLE);
            listViewNames.addView(child);

            if(checkTotalSum()){
                //final Button baton = ((Button) child.findViewById(R.id.button3));
                //baton.setEnabled(true);
                baton.setEnabled(true);

                //((Button) child.findViewById(R.id.button3)).setEnabled(true);
            }
        }
        final LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.user_row, listViewNames, false);
        ((TextView)child.findViewById(R.id.name)).setText("Итог");
        ((Button) child.findViewById(R.id.assignMoney)).setText(Integer.valueOf(total_summary).toString());
        child.findViewById(R.id.transOverNamesList).setVisibility(View.VISIBLE);
        listViewNames.addView(child);

    }

    public  void onClick(View view){
        findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
    }

    boolean checkTotalSum(){
        final Set<String> names = costumers.keySet();
        int n = names.size();
        List<String> aList = new ArrayList<String>(n);
        for (String x : names)
            aList.add(x);

        for(int i = 0; i < n;i++){
            if (costumers.get(aList.get(i)) == 0){
                return false;
            }
        }
        return true;
    }


    int Calculate(){
        int sum = 0;
        final Set<String> names = costumers.keySet();
        int n = names.size();
        List<String> aList = new ArrayList<String>(n);
        for (String x : names)
            aList.add(x);

        for(int i = 0; i < n;i++){
            sum += (costumers.get(aList.get(i)));
        }
        return sum;
    }
}

