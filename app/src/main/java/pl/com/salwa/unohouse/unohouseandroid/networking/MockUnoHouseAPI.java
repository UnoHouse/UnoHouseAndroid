package pl.com.salwa.unohouse.unohouseandroid.networking;

import android.util.Log;

import pl.com.salwa.unohouse.unohouseandroid.models.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by Piotr Chebdowski on 18.04.2017.
 */

public class MockUnoHouseAPI implements UnoHouseAPI {
    private final BehaviorDelegate<UnoHouseAPI> delegate;

    public MockUnoHouseAPI(BehaviorDelegate<UnoHouseAPI> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<AuthenticationResponse> authenticate(@Field("login") String email, @Field("password") String password) {
        String response = "";
        return delegate.returningResponse(response).authenticate(email, password);
    }

    @Override
    public Call<String> latestVersion() {
        String response = "{}";
        return delegate.returningResponse(response).latestVersion();
    }
}
