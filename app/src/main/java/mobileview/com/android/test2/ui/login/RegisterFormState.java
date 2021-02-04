package mobileview.com.android.test2.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    @Nullable
    private Integer imageAvatarError;
    @Nullable
    private Integer checkboxError;

    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer emailError,
                      @Nullable Integer passwordError, @Nullable Integer confirmPasswordError,
                      @Nullable Integer checkboxError, @Nullable Integer imageAvatarError) {
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.imageAvatarError = imageAvatarError;
        this.checkboxError = checkboxError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.checkboxError = null;
        this.imageAvatarError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    @Nullable
    public Integer getImageAvatarError() {
        return imageAvatarError;
    }

    @Nullable
    public Integer getCheckboxError() {
        return checkboxError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}