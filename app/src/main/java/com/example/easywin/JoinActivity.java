package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.network.PendingState;
import com.example.network.RequestState;
import com.example.network.RoomInfo;
import com.example.network.SuccessState;

import javax.inject.Inject;

import kotlin.NotImplementedError;

public class JoinActivity extends AppCompatActivity {
    TextView topBarTextView;

    static int QR_REQUEST_CODE = 12;

    @Inject
    RoomHolder roomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ((EasyWinApp) getApplicationContext()).getAppComponent().inject(this);

        topBarTextView = findViewById(R.id.kek);
        topBarTextView.setText("Оплатить долю");
    }

    public void onJoinButtonClick(View view) {
        try {
            join(Integer.valueOf(((EditText) findViewById(R.id.meeting_id)).getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onQrButtonClick(View view) {
        Intent intent = new Intent(this, QrActivity.class);
        intent.putExtra("mode", "join");
        startActivityForResult(intent, QR_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null)
            return;
        int roomId = intent.getIntExtra("roomId", -1);
        if (roomId != -1) {
            ((EditText) findViewById(R.id.meeting_id)).setText(Integer.valueOf(roomId).toString());
            join(roomId);
        }
    }

    private void join(int roomId) {
        roomHolder.join(roomId).observe(this, new Observer<RequestState<RoomInfo>>() {
            @Override
            public void onChanged(RequestState<RoomInfo> roomInfoRequestState) {
                if (roomInfoRequestState instanceof SuccessState) {
                    goToNextStep();
                }
            }
        });
    }

    private void goToNextStep() {
        startActivity(new Intent(this, EndOfCreatingMeeting.class));
    }
}
