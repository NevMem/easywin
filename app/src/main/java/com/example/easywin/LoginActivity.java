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
import com.example.network.RequestState;
import com.example.network.SuccessState;
import com.example.network.UserData;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    @Inject
    UserHolder userHolder;

    UserData userData;
    Button loginButton;
    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((EasyWinApp) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_loggin);

        setUpEnterAnimations();
        loginButton = findViewById(R.id.login_button);
        bar = findViewById(R.id.progress_bar);
    }

    public void onLoginButtonClick(View view) {
        String login = ((EditText)findViewById(R.id.login_field)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_field)).getText().toString();

        userHolder.tryLogin(login, password).observe(this, new Observer<RequestState>() {
            @Override
            public void onChanged(RequestState requestState) {

                loginButton.setText("");
                bar.setVisibility(View.VISIBLE);
                if (requestState instanceof ErrorState) {
                    loginButton.setText(getString(R.string.enter));
                    bar.setVisibility(View.GONE);
                } else if (requestState instanceof SuccessState) {
                    userData = (UserData) ((SuccessState) requestState).getPayload();
                    loginButton.setText(getString(R.string.enter));
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
