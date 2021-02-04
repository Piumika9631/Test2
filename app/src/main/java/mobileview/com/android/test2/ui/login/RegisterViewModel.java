package mobileview.com.android.test2.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mobileview.com.android.test2.data.RegisterRepository;
import mobileview.com.android.test2.data.Result;
import mobileview.com.android.test2.data.model.RegisteredInUser;
import mobileview.com.android.test2.R;
import mobileview.com.android.test2.network.RegisterAPI;
import mobileview.com.android.test2.network.RegisterResponse;
import mobileview.com.android.test2.network.RemoteDataSource;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository mRegisterRepository;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.mRegisterRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String email, String password, String gender, String imagePath) {


        Retrofit retrofit = RemoteDataSource.getRetrofit();
        RegisterAPI registerAPI = retrofit.create(RegisterAPI.class);
        File file = new File(imagePath);
        RequestBody reqFile = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("imageParameterName", file.getName(), reqFile);

        Call<RegisterResponse> call = registerAPI.create_account(username, email, password, gender, image);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body().getApiStatus() == 200) {
                    registerResult.setValue(new RegisterResult(new RegisteredInUserView(response.message())));
                } else {
                    registerResult.setValue(new RegisterResult(R.string.register_failed));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registerResult.setValue(new RegisterResult(R.string.register_failed));
            }
        });


    }

    public void registerDataChanged(String username, String email, String password, String confirmPassword, String avatarPath, boolean checkBoxState) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null, null, null));
        } else if (!isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_email, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password, null, null, null));
        } else if (!isConfirmPasswordValid(password, confirmPassword)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_confirm_password, null, null));
        } else if (!isAvatarPathValid(avatarPath)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, null, R.string.avatar_error));
        } else if (!isCheckBoxValid(checkBoxState)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.unchecked_error, null));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isAvatarPathValid(String avatarPath) {
        if (avatarPath == null) {
            return false;
        } else {
            return !avatarPath.trim().isEmpty();
        }
    }

    private boolean isCheckBoxValid(boolean checked) {
        return checked;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 2;
    }
}