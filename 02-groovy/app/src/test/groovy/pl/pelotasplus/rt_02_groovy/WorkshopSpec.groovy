package pl.pelotasplus.rt_02_groovy

import android.app.Activity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robospock.internal.GradleRoboSputnik
import pl.pelotasplus.rt_02_groovy.MyActivity

@RunWith(GradleRoboSputnik)
class WorkshopSpec extends org.robospock.RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().get()
    }

    def "activity should have an action bar"() {
        expect:
        activity.actionBar
    }

    def "should create a list of 10 'groovy rocks' strings"() {
        when:
        def l = (1..10).collect({ "groovy rocks" })

        then:
        l.size() == 10
        l.each({
            it == "groovy rocks"
        })
    }

    def "should make sure that square() method works just fine"() {
        given:
        def m = ["a": 1, "b": 4, "c": 9]

        when:
        m.keySet().each {
            m[it] = Math.sqrt(m[it])
        }

        then:
        m.a == 1
        m.b == 2
        m.c == 3
    }

    def "should make a list without false values"() {
        given:
        def l = [null, "android", [], false, "ios"]
        def o = []

        when:
        l.each {
            if (it) o += it
        }

        then:
        o == ["android", "ios"]
    }

    def "should make a list with placeholder for false values"() {
        given:
        def l = [null, "android", [], false, "ios"]

        when:
        def o = l.collect {
            if (it) {
                it
            } else {
                "XXX"
            }
        }

        then:
        o == ["XXX", "android", "XXX", "XXX", "ios"]
    }
}