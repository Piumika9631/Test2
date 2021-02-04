package mobileview.com.android.test2.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import mobileview.com.android.test2.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpOnboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_onboard);

        Button nextButton = findViewById(R.id.a_signUpOnboard_button_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpOnboardActivity.this, SignUpActivity.class));
            }
        });
    }
}