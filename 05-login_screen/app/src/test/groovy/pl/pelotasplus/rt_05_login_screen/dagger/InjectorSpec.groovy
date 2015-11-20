package pl.pelotasplus.rt_05_login_screen.dagger

import org.robolectric.shadows.ShadowLog
import org.robospock.RoboSpecification
import pl.pelotasplus.rt_05_login_screen.TestModule

class InjectorSpec extends RoboSpecification {
    def "setup"() {
        Injector.component = null

        ShadowLog.getLogs().clear()

        Injector.start(new TestModule())
    }

    def "should provide api interface as singleton"() {
        expect:
        Injector.get().getApiInterface().is(Injector.get().getApiInterface())
    }

    def "should provide different integer next time"() {
        expect:
        Injector.get().getInteger() != Injector.get().getInteger()
    }
}