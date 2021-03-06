package pl.com.salwa.unohouse.unohouseandroid.networking;

import pl.com.salwa.unohouse.unohouseandroid.models.AuthenticationResponse;
import pl.com.salwa.unohouse.unohouseandroid.models.CheckNewVersionResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Piotr Chebdowski on 07.04.2017.
 */

public interface UnoHouseAPI {

    @FormUrlEncoded
    @POST("auth/authenticate")
    Call<AuthenticationResponse> authenticate(@Field("email") String email, @Field("password") String password);

    @GET("app/latest/version")
    Call<CheckNewVersionResponse> latestVersion();
}
