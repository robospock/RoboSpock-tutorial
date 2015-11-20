package pl.pelotasplus.rt_05_login_screen.dagger;

import javax.inject.Singleton;

import dagger.Component;
import pl.pelotasplus.rt_05_login_screen.ApiInterface;
import pl.pelotasplus.rt_05_login_screen.LoginFragment;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    final class Initializer {
        public static ApplicationComponent init(ApplicationModule daggerModule) {
            return DaggerApplicationComponent
                    .builder()
                    .applicationModule(daggerModule)
                    .build();
        }
    }

    void inject(LoginFragment fragment);

    ApiInterface getApiInterface();

    Integer getInteger();
}
