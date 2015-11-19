package pl.pelotasplus.rt_05_login_screen;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alek on 27/09/14.
 */
public interface ApiInterface {
    String ENDPOINT = "https://salty-lake-7009.herokuapp.com";

    @GET("/auth/login")
    void auth_login(@Query("email") String email, @Query("password") String password, Callback<AuthLoginResponse> cb);

    class Utils {
        public static ApiInterface getApiInterface() {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ApiInterface.ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            return restAdapter.create(ApiInterface.class);
        }
    }
}
