package mobileview.com.android.test2.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import mobileview.com.android.test2.R;
//import mobileview.com.android.test2.service.RegistrationService;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final int SELECT_IMAGE = 10;
    private RegisterViewModel mRegisterViewModel;
    private String mGender;
    private String mAvatarPath;
    private String[] gender = {"Male", "Female"};
    private AlertDialog.Builder registerStatusAlert;

    private ImageView imageView;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private CheckBox termsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mRegisterViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        usernameEditText = findViewById(R.id.a_signUp_editText_name);
        emailEditText = findViewById(R.id.a_signUp_editText_email);
        passwordEditText = findViewById(R.id.a_signUp_editText_password);
        confirmPasswordEditText = findViewById(R.id.a_signUp_editText_confirmPassword);
        termsCheckBox = findViewById(R.id.a_signUp_checkBox_terms);
        final Button registerButton = findViewById(R.id.a_signUp_button_register);
        final Button changeAvatarButton = findViewById(R.id.a_signUp_button_selectImage);
        imageView = findViewById(R.id.a_signUp_imageView_avatar);

        registerStatusAlert = new AlertDialog.Builder(this);

        Spinner genderSpin = findViewById(R.id.a_signUp_spinner_gender);
        genderSpin.setOnItemSelectedListener(this);
        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin.setAdapter(genderAdapter);

        mRegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {

                boolean isRegistrationFormValid = registerFormState.isDataValid();
                boolean isTermsChecked = termsCheckBox.isChecked();
                boolean isAvatarAvailable = mAvatarPath != null && mAvatarPath.length() > 0;

                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(isRegistrationFormValid && isTermsChecked && isAvatarAvailable);

                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getConfirmPasswordError() != null) {
                    confirmPasswordEditText.setError(getString(registerFormState.getConfirmPasswordError()));
                }

                if (registerFormState.getImageAvatarError() != null) {
                    Toast.makeText(SignUpActivity.this, registerFormState.getImageAvatarError(), Toast.LENGTH_SHORT).show();
                }

                if (registerFormState.getImageAvatarError() == null
                        && registerFormState.getCheckboxError() != null) {
                    Toast.makeText(SignUpActivity.this, registerFormState.getCheckboxError(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mRegisterViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                ///loadingProgressBar.setVisibility(View.GONE);
                if (registerResult.getError() != null) {
                    showLoginFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mRegisterViewModel.registerDataChanged(usernameEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString(), mAvatarPath, termsCheckBox.isChecked());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        termsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mRegisterViewModel.registerDataChanged(usernameEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString(), mAvatarPath, b);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterViewModel.register(usernameEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), mGender, mAvatarPath);
            }
        });

        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
            }
        });
    }

    private void updateUiWithUser(RegisteredInUserView model) {
        registerStatusAlert.setMessage("Successfully registered");
        registerStatusAlert.show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        registerStatusAlert.setMessage(errorString);
        registerStatusAlert.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mGender = gender[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    if (data.getData() != null) {
                        try {
                            Uri returnUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(returnUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            mAvatarPath = returnUri.getPath();
                            imageView.setImageBitmap(selectedImage);
                            mRegisterViewModel.registerDataChanged(usernameEditText.getText().toString(), emailEditText.getText().toString(),
                                    passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString(), mAvatarPath, termsCheckBox.isChecked());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }


}