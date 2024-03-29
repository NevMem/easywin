package com.example.easywin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.View;

import com.example.network.RoomInfo;
import com.example.network.UserData;

import javax.inject.Inject;

public class EndOfCreatingMeeting extends AppCompatActivity {

    String[] names = {"Misha","Alekcey","Igor","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey","Alekcey"};
    TextView textViewOfNameLobby;
    LinearLayout listViewNames;
    TextView textViewIdent;

    @Inject
    RoomHolder roomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_creating_meeting);

        ((EasyWinApp) getApplicationContext()).getAppComponent().inject(this);

        // Name of lobby
        textViewOfNameLobby = findViewById(R.id.textViewOfNameLobby);
        // Identificator
        textViewIdent = findViewById(R.id.textViewIdentificator);

        listViewNames = findViewById(R.id.list_of_names);

        final LayoutInflater inflater = LayoutInflater.from(this);

        if (roomHolder.state() == RoomHolderState.JOINED) {
            findViewById(R.id.qrCodeButton).setVisibility(View.GONE);
            findViewById(R.id.finishButton).setVisibility(View.GONE);
            findViewById(R.id.explain3Text).setVisibility(View.VISIBLE);
        }

        roomHolder.currentRoom().observe(this, new Observer<RoomInfo>() {
            @Override
            public void onChanged(RoomInfo roomInfo) {
                textViewOfNameLobby.setText(roomInfo.getRoomName());

                textViewIdent.setText(Integer.toString(roomInfo.getRoomId()));

                if (!roomInfo.getState().equals("WaitingForUsers")) {
                    goToNextStep();
                    return;
                }

                listViewNames.removeAllViews();
                for (UserData user : roomInfo.getUsers()) {
                    View child = inflater.inflate(R.layout.user_row, listViewNames, false);

                    ((TextView)child.findViewById(R.id.name)).setText(user.getName());
                    listViewNames.addView(child);
                }
            }
        });

    }

    private void goToNextStep() {
        startActivity(new Intent(this, AssignTheMount.class));
    }

    public void onEndOfCreateingButtonClick(View view){
        roomHolder.gotoPickMoney();
    }

    // For QR code.
    public void OnClick(View view){
        View qrShowView = LayoutInflater.from(this).inflate(R.layout.qr_dialog, null, false);
        ImageView imageView = qrShowView.findViewById(R.id.qrImage);
        switch (roomHolder.currentRoomId()) {
            case 133756:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133756));
                break;
            case 133757:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133757));
                break;
            case 133758:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133758));
                break;
            case 133759:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133759));
                break;
            case 133770:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133770));
                break;
            default:
                imageView.setImageDrawable(getDrawable(R.drawable.qr_133771));
                break;
        }
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.TransparentDialogStyle)
            .setView(qrShowView)
            .create();
        qrShowView.findViewById(R.id.qrImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
