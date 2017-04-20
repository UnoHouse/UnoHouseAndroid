package pl.com.salwa.unohouse.unohouseandroid.callbacks;

import android.content.Intent;

import pl.com.salwa.unohouse.unohouseandroid.activities.LoginActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.MainActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.R;
import pl.com.salwa.unohouse.unohouseandroid.models.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Piotr Chebdowski on 19.04.2017.
 */

public class AuthenticateCallback implements Callback<AuthenticationResponse> {

    private LoginActivity activity;

    public AuthenticateCallback(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
        int statusCode = response.code();
        AuthenticationResponse authResponse = response.body();

        if (authResponse != null) {

            activity.showProgress(false);

            if (statusCode == 200) {
                Intent intent = new Intent(activity.getBaseContext(), MainActivity.class);
                activity.startActivity(intent);
            } else {
                activity.mPasswordView.setError(activity.getString(R.string.error_incorrect_password));
                activity.mPasswordView.requestFocus();
            }
        }
    }

    @Override
    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
        activity.showProgress(false);
    }
}
