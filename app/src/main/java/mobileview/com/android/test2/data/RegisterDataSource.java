package mobileview.com.android.test2.data;

import mobileview.com.android.test2.data.model.RegisteredInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

    public Result<RegisteredInUser> register(String username, String email, String password, String gender) {

        try {
            // TODO: handle loggedInUser authentication
            RegisteredInUser fakeUser =
                    new RegisteredInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}