package pl.com.salwa.unohouse.unohouseandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import pl.com.salwa.unohouse.unohouseandroid.activities.BuildConfig;
import pl.com.salwa.unohouse.unohouseandroid.activities.SplashActivity;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Piotr Chebdowski on 20.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashActivityTest {

    @Test
    public void checkAppVersion_ReturnsTrue() throws Exception {
        SplashActivity activity = Robolectric.buildActivity(SplashActivity.class).get();

        MockWebServer mockWebServer = new MockWebServer();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UnoHouseAPI unoHouseAPI = retrofit.create(UnoHouseAPI.class);

        String contentType = "Content-type: application/json";
        MockResponse mockResponse = new MockResponse().setStatus("HTTP/1.1 200 OK")
                .setResponseCode(200)
                .setBody("version : 1.0")
                .addHeader(contentType);

        mockWebServer.enqueue(mockResponse);

        activity.setApi(unoHouseAPI);
        activity.checkAppVersion();

        RecordedRequest request1 = mockWebServer.takeRequest();
        assertThat("'checkAppVersion' method has been called", request1.getPath(), equalTo("/app/latest/version"));

        mockWebServer.shutdown();
    }
}
