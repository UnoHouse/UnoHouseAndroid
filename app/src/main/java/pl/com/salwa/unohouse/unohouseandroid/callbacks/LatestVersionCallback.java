package pl.com.salwa.unohouse.unohouseandroid.callbacks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pl.com.salwa.unohouse.unohouseandroid.activities.LoginActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.MainActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.R;
import pl.com.salwa.unohouse.unohouseandroid.activities.SplashActivity;
import pl.com.salwa.unohouse.unohouseandroid.models.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Piotr Chebdowski on 19.04.2017.
 */

public class LatestVersionCallback implements Callback<String> {

    private SplashActivity activity;

    public LatestVersionCallback(SplashActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        int statusCode = response.code();

        Log.d(LoginActivity.APP_TAG, statusCode + " status code");
        String s = response.body();
        System.out.println(statusCode + " " + response.body() + " aaaaa");
        Log.d(LoginActivity.APP_TAG, s + " result");

        if (statusCode == 200) {
            Intent intent = new Intent(activity.getBaseContext(), LoginActivity.class);
            activity.startActivity(intent);
        } else {
            Log.d(LoginActivity.APP_TAG, s + " result");
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d(LoginActivity.APP_TAG, t.toString());
    }
}

