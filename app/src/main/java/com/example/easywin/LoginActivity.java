package com.example.easywin;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.network.ErrorState;
import com.example.network.NetworkProvider;
import com.example.network.PendingState;
import com.example.network.RequestState;
import com.example.network.SuccessState;
import com.example.network.UserData;

import java.util.Observable;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    @Inject
    NetworkProvider networkProvider;

    UserData userData;
    Button logginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((EasyWinApp) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_loggin);

        setUpEnterAnimations();

        logginButton = findViewById(R.id.login_button);
    }

    public void onLoginButtonClick(View view) {
        String login = ((EditText)findViewById(R.id.login_field)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_field)).getText().toString();

        networkProvider.login(login, password).observe(this, new Observer<RequestState>() {
            @Override
            public void onChanged(RequestState requestState) {
                ProgressBar bar = findViewById(R.id.progress_bar);
                logginButton.setText("");
                bar.setVisibility(View.VISIBLE);
                if (requestState instanceof ErrorState) {
                    logginButton.setText(getString(R.string.enter));
                    bar.setVisibility(View.GONE);
                } else if (requestState instanceof SuccessState) {
                    userData = (UserData) ((SuccessState) requestState).getPayload();
                    logginButton.setText(getString(R.string.enter));
                    bar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setUpEnterAnimations() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(300);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LinearLayout ll = findViewById(R.id.login_container);
                        ll.setTranslationY(200.0f * (1 - valueAnimator.getAnimatedFraction()));
                    }
                }
        );
        animator.start();

        ValueAnimator animatorAlpha = ValueAnimator.ofFloat(0, 1);
        animatorAlpha.setDuration(300);
        animatorAlpha.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LinearLayout ll = findViewById(R.id.login_container);
                        ll.setAlpha(valueAnimator.getAnimatedFraction());
                    }
                }
        );
        animatorAlpha.start();
    }
}
