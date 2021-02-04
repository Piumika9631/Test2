package mobileview.com.android.test2.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class RegisteredInUserView {
    private String displayMessage;
    //... other data fields that may be accessible to the UI

    RegisteredInUserView(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    String getDisplayMessage() {
        return displayMessage;
    }
}