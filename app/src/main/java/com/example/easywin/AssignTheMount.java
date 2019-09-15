package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.network.RoomInfo;
import com.example.network.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class AssignTheMount extends AppCompatActivity {
    final Map<String, Integer> values = new HashMap<String, Integer>();
    TextView textViewOfNameLobby;
    LinearLayout listViewNames;
    int total_summary = 0;

    @Inject
    RoomHolder roomHolder;

    RoomHolderState mode;

    RoomInfo lastRoomInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_the_mount);

        // Name of lobby
        textViewOfNameLobby = findViewById(R.id.textViewOfNameLobby);
        textViewOfNameLobby.setText("Название лобби");

        listViewNames = findViewById(R.id.list_of_names);
        fill_listview();

        ((EasyWinApp) getApplicationContext()).getAppComponent().inject(this);

        mode = roomHolder.state();

        roomHolder.currentRoom().observe(this, new Observer<RoomInfo>() {
            @Override
            public void onChanged(RoomInfo roomInfo) {
                if (roomInfo == null)
                    return;
                if (!roomInfo.getState().equals("WaitingForCash")) {
                    goToNextStep();
                }
                lastRoomInfo = roomInfo;
                if (mode == RoomHolderState.JOINED) {
                    for (UserData user : roomInfo.getUsers()) {
                        values.put(user.getName(), user.getAmount());
                    }
                }
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomHolder.gotoLastStage();
            }
        });
    }

    private boolean gone = false;
    private void goToNextStep() {
        if (!gone) {
            gone = true;
            startActivity(new Intent(this, WaitEveryoneActivity.class));
        }
    }

    private void updateValuesOnServer() {
        RoomInfo currentRoomInfo = lastRoomInfo;
        if (currentRoomInfo == null)
            return;
        for (UserData user : currentRoomInfo.getUsers()) {
            user.setAmount(values.get(user.getName()));
        }
        roomHolder.updateServer(currentRoomInfo);
    }

    public void fill_listview(){
        final Button baton = findViewById(R.id.button3);

        final AppCompatActivity thisActivity = this;
        final Set<String> names = values.keySet();
        listViewNames.removeAllViews();
        total_summary = Calculate();
        for(final String name : names){
            final LayoutInflater inflater = LayoutInflater.from(this);
            View child = inflater.inflate(R.layout.user_row, listViewNames, false);
            ((TextView)child.findViewById(R.id.name)).setText(name);
            final Button button = ((Button) child.findViewById(R.id.assignMoney));

            if (mode == RoomHolderState.JOINED) {
                if (!values.get(name).equals(0)) {
                    button.setText(Integer.valueOf(values.get(name)).toString());
                }
                button.setText(R.string.wait_string);
                continue;
            }

            if (!values.get(name).equals(0)) {
                button.setText(Integer.valueOf(values.get(name)).toString());
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final View view = inflater.inflate(R.layout.dialog_signin, null, false);

                    if (!values.get(name).equals(0)) {
                        ((EditText) view.findViewById(R.id.countText)).setText(Integer.valueOf(values.get(name)).toString());
                    }

                    AlertDialog dialog = new AlertDialog.Builder(thisActivity)
                            .setView(view)
                            .setPositiveButton("ПРИНЯТЬ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String value = ((EditText) view.findViewById(R.id.countText)).getText().toString();
                                    try {
                                        int k = Integer.parseInt(value);
                                        values.put(name, k);
                                        updateValuesOnServer();
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
        final Set<String> names = values.keySet();
        int n = names.size();
        List<String> aList = new ArrayList<String>(n);
        for (String x : names)
            aList.add(x);

        for(int i = 0; i < n;i++){
            if (values.get(aList.get(i)) == 0){
                return false;
            }
        }
        return true;
    }


    int Calculate(){
        int sum = 0;
        final Set<String> names = values.keySet();
        int n = names.size();
        List<String> aList = new ArrayList<String>(n);
        for (String x : names)
            aList.add(x);

        for(int i = 0; i < n;i++){
            sum += (values.get(aList.get(i)));
        }
        return sum;
    }
}

