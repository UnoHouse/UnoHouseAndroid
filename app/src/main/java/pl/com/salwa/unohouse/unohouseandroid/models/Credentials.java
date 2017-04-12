package pl.com.salwa.unohouse.unohouseandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Piotr Chebdowski on 07.04.2017.
 */

public class Credentials {
    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
