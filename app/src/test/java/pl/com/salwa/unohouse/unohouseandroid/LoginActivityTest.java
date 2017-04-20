package pl.com.salwa.unohouse.unohouseandroid;

import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import pl.com.salwa.unohouse.unohouseandroid.activities.BuildConfig;
import pl.com.salwa.unohouse.unohouseandroid.activities.LoginActivity;
import pl.com.salwa.unohouse.unohouseandroid.activities.R;
import pl.com.salwa.unohouse.unohouseandroid.networking.UnoHouseAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Piotr Chebdowski on 13.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private LoginActivity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        activity = Robolectric.setupActivity(LoginActivity.class);

        email = (EditText) activity.findViewById(R.id.email);
        password = (EditText) activity.findViewById(R.id.password);
        loginBtn = (Button) activity.findViewById(R.id.login_button);
    }

    @Test
    public void loginSuccess_ReturnsTrue() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UnoHouseAPI unoHouseAPI = retrofit.create(UnoHouseAPI.class);
        activity.setApi(unoHouseAPI);

        String contentType = "Content-type: application/json";
        MockResponse mockResponse = new MockResponse().setStatus("HTTP/1.1 200 OK")
                .setResponseCode(200)
                .setBody("{ result : true }")
                .addHeader(contentType);

        mockWebServer.enqueue(mockResponse);

        email.setText("test@gmail.com");
        password.setText("password");
        loginBtn.performClick();

        RecordedRequest request1 = mockWebServer.takeRequest();
        assertThat("Authenticate method has been called", request1.getPath(), is("/auth/authenticate"));

        mockWebServer.shutdown();
    }

    @Test
    public void loginWithEmptyUsernameAndPassword_ReturnsFalse() {
        loginBtn.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity should not started", application.getNextStartedActivity(), is(nullValue()));
        assertThat("Show error for email field", email.getError(), is(notNullValue()));
        assertThat("Show error for password field", password.getError(), is(notNullValue()));
    }

    @Test
    public void loginWithIncorrectData_ReturnsFalse() {
        email.setText("incorrectEmail");
        password.setText("pas");
        loginBtn.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("NextActivity should not started", application.getNextStartedActivity(), is(nullValue()));
        assertThat("Show error for email field", email.getError(), is(notNullValue()));
        assertThat("Show error for password field", password.getError(), is(notNullValue()));
    }
}
