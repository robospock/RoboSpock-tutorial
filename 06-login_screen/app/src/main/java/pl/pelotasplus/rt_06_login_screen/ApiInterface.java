package pl.pelotasplus.rt_06_login_screen;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alek on 27/09/14.
 */
public interface ApiInterface {
    public static final String ENDPOINT = "https://salty-lake-7009.herokuapp.com";

    @GET("/auth/login")
    void auth_login(@Query("email") String email, @Query("password") String password, Callback<AuthLoginResponse> cb);
}
