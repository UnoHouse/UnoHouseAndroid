package pl.com.salwa.unohouse.unohouseandroid.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Piotr Chebdowski on 07.04.2017.
 */

public class UnoHouseAPIClient {

    static final String BASE_URL = "http://195.181.209.40/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
