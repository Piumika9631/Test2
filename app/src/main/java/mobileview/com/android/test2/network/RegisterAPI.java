package mobileview.com.android.test2.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RegisterAPI {
    @Multipart
    @POST("create_account")
    Call<RegisterResponse> create_account(@Part("username") String username,
                                          @Part("email") String email, @Part("username") String password,
                                          @Part("gender") String gender, @Part MultipartBody.Part image);
}


