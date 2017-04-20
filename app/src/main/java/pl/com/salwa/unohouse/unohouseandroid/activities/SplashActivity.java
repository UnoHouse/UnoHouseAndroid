package pl.com.salwa.unohouse.unohouseandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.com.salwa.unohouse.unohouseandroid.callbacks.LatestVersionCallback;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPI;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPIClient;
import retrofit2.Call;

public class SplashActivity extends AppCompatActivity {

    private UnoHouseAPI apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apiService = UnoHouseAPIClient.getClient().create(UnoHouseAPI.class);
        checkAppVersion();
    }

    public void checkAppVersion() {
        Call<String> call = apiService.latestVersion();
        call.enqueue(new LatestVersionCallback(this));
    }

    /**
     * Inject custom API for testing purposes
     */
    public void setApi(UnoHouseAPI api) {
        apiService = api;
    }
}
