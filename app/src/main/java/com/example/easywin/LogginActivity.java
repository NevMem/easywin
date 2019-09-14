package com.example.easywin;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.LinearLayout;

public class LogginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        
        setUpEnterAnimations();
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
