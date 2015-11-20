package pl.pelotasplus.rt_05_login_screen;

import android.app.Application;

import pl.pelotasplus.rt_05_login_screen.dagger.ApplicationModule;
import pl.pelotasplus.rt_05_login_screen.dagger.Injector;

public class MyApplication extends Application {
    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.start(new ApplicationModule(this));
    }
}