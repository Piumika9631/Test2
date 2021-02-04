package mobileview.com.android.test2.network;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse implements Parcelable
{

    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<RegisterResponse> CREATOR = new Creator<RegisterResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RegisterResponse createFromParcel(Parcel in) {
            return new RegisterResponse(in);
        }

        public RegisterResponse[] newArray(int size) {
            return (new RegisterResponse[size]);
        }

    }
            ;

    protected RegisterResponse(Parcel in) {
        this.apiStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RegisterResponse() {
    }

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(apiStatus);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}
