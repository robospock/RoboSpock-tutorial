package pl.pelotasplus.rt_05_login_screen.dagger;

public class Injector {
    static ApplicationComponent component;

    public static void start(ApplicationModule daggerModule) {
        component = ApplicationComponent.Initializer.init(daggerModule);
    }

    public static ApplicationComponent get() {
        return component;
    }
}
