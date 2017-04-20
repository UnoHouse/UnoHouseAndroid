package pl.com.salwa.unohouse.unohouseandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.com.salwa.unohouse.unohouseandroid.activities.BuildConfig;
import pl.com.salwa.unohouse.unohouseandroid.activities.MainActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Piotr Chebdowski on 13.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void onCreateShouldSetSupportActionBar_ReturnsTrue() {
        AppCompatActivity activity = Robolectric.setupActivity(MainActivity.class);

        ActionBar toolbar = activity.getSupportActionBar();
        assertThat("Toolbar is set up", toolbar, is(notNullValue()));
    }

}
