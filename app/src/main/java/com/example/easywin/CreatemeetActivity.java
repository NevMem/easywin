package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.network.ErrorState;
import com.example.network.PendingState;
import com.example.network.RequestState;
import com.example.network.RoomInfo;
import com.example.network.SuccessState;

import javax.inject.Inject;

public class CreatemeetActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    @Inject
    RoomHolder roomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeet);

        ((EasyWinApp) getApplicationContext()).getAppComponent().inject(this);

        editText = findViewById(R.id.editText1);
        button = findViewById(R.id.button);
    }

    public void onCreateButtonClick(View view){
        String str = editText.getText().toString();

        roomHolder.createRoom(str).observe(this, new Observer<RequestState<RoomInfo>>() {
            @Override
            public void onChanged(RequestState<RoomInfo> roomInfoRequestState) {
                findViewById(R.id.transOverlay).setVisibility(View.GONE);
                if (roomInfoRequestState == null)
                    return;
                if (roomInfoRequestState instanceof PendingState) {
                    findViewById(R.id.transOverlay).setVisibility(View.VISIBLE);
                } else if (roomInfoRequestState instanceof SuccessState) {
                    goToNextStep();
                } else if (roomInfoRequestState instanceof ErrorState) {
                    Log.d("error", ((ErrorState) roomInfoRequestState).getError());
                }
            }
        });
    }

    private void goToNextStep() {
        startActivity(new Intent(this, EndOfCreatingMeeting.class));
    }
}
