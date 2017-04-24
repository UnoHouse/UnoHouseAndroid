package pl.com.salwa.unohouse.unohouseandroid.utils;

import java.util.regex.Pattern;

/**
 * Created by Piotr Chebdowski on 12.04.2017.
 */

/**
 * Final class can not be extended
 */
public final class Validator {

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /**
     * Private constructor prevents from instantiation
     */
    private Validator() {
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }
}
