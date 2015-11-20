package pl.pelotasplus.rt_05_login_screen.dagger;

import android.app.Application;
import android.content.Context;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.pelotasplus.rt_05_login_screen.ApiInterface;
import retrofit.RestAdapter;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public ApiInterface provideApiInterface() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiInterface.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(ApiInterface.class);
    }

    @Provides
    public Integer provideInteger() {
        return new Random().nextInt(1000);
    }
}