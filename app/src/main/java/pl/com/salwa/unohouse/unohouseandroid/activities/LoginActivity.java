package pl.com.salwa.unohouse.unohouseandroid.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import pl.com.salwa.unohouse.unohouseandroid.models.AuthenticationResponse;
import pl.com.salwa.unohouse.unohouseandroid.models.Credentials;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPI;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via login/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String APP_TAG = "Uno";

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginView = (EditText) findViewById(R.id.login);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.login_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        checkAppVersion();
    }

    private void checkAppVersion() {
        showProgress(true);

        UnoHouseAPI apiService = UnoHouseAPIClient.getClient().create(UnoHouseAPI.class);

        Call<String> call = apiService.latestVersion();
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();
                Log.d(APP_TAG, statusCode + " status code");
                String s = response.body();

                    Log.d(APP_TAG, s + " result");
                showProgress(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(APP_TAG, t.toString());
                showProgress(false);
            }
        });
    }

    /**
     * Attempts to sign in.
     * If there are form errors (invalid login, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login address.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isLoginValid(login)) {
            mLoginView.setError(getString(R.string.error_invalid_login));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and perform the user login attempt.
            showProgress(true);

            UnoHouseAPI apiService = UnoHouseAPIClient.getClient().create(UnoHouseAPI.class);
            Credentials credentials = new Credentials(login, password);
            Call<AuthenticationResponse> call = apiService.authenticate(login, password);
            call.enqueue(new Callback<AuthenticationResponse>() {

                @Override
                public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                    int statusCode = response.code();
                    Log.d(APP_TAG, statusCode + " status code");
                    AuthenticationResponse authResponse = response.body();
                    if (authResponse != null)
                        Log.d(APP_TAG, authResponse.getResult() + " result");
                    showProgress(false);

                    if (authResponse.getResult()) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                    Log.d(APP_TAG, t.toString());
                    showProgress(false);
                }
            });
        }
    }

    private boolean isLoginValid(String login) {
        //TODO: Replace this with your own logic
        return login.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}