package pl.com.salwa.unohouse.unohouseandroid.callbacks;

import android.content.Intent;

import pl.com.salwa.unohouse.unohouseandroid.activities.LoginActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.SplashActivity;
import pl.com.salwa.unohouse.unohouseandroid.models.CheckNewVersionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Piotr Chebdowski on 19.04.2017.
 */

public class LatestVersionCallback implements Callback<CheckNewVersionResponse> {

    private SplashActivity activity;

    public LatestVersionCallback(SplashActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<CheckNewVersionResponse> call, Response<CheckNewVersionResponse> response) {
        int statusCode = response.code();

        CheckNewVersionResponse body = response.body();

        if (statusCode == 200) {
            Intent intent = new Intent(activity.getBaseContext(), LoginActivity.class);
            activity.startActivity(intent);
        } else {
            //TODO handle new version
        }
    }

    @Override
    public void onFailure(Call<CheckNewVersionResponse> call, Throwable t) {
        //TODO handle failure
    }
}

