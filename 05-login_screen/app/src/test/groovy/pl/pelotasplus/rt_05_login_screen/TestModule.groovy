package pl.pelotasplus.rt_05_login_screen

import org.robolectric.RuntimeEnvironment
import pl.pelotasplus.rt_05_login_screen.dagger.ApplicationModule

class TestModule extends ApplicationModule {
    TestModule() {
        super(RuntimeEnvironment.application)
    }
}