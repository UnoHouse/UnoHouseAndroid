package pl.com.salwa.unohouse.unohouseandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Piotr Chebdowski on 07.04.2017.
 */

public class Credentials {
    @SerializedName("login")
    String login;

    @SerializedName("password")
    String password;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
