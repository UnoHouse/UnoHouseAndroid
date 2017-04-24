package pl.com.salwa.unohouse.unohouseandroid;

import org.junit.Test;

import pl.com.salwa.unohouse.unohouseandroid.utils.Validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by Piotr Chebdowski on 12.04.2017.
 */

public class ValidatorTest {

    @Test
    public void validator_correctEmail_ReturnsTrue() {
        assertThat(Validator.isEmailValid("test@gmail.com"), is(true));
    }

    @Test
    public void validator_incorrectEmail_ReturnsFalse() {
        assertEquals(Validator.isEmailValid("testgmail.com"), is(false));
    }

    @Test
    public void validator_correctPassword_ReturnsTrue() {
        assertEquals(Validator.isPasswordValid("password"), is(true));
    }

    @Test
    public void validator_incorrectPassword_ReturnsFalse() {
        assertEquals(Validator.isPasswordValid("pas"), is(false));
    }
}
