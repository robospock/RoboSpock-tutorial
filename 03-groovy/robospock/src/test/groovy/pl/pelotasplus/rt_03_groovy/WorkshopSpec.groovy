package pl.pelotasplus.rt_03_groovy

import android.app.Activity
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

class WorkshopSpec extends RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().get()
    }

    def "activity should have an action bar"() {
        expect:
        1 == 0
    }

    def "should create a list of 10 'groovy rocks' strings"() {
        when:
        "create a list here"

        then:
        "make sure it has right size"
        "make sure it has right elements inside"
        1 == 0
    }

    def "should make sure that square() method works just fine"() {
        given:
        def m = ["a": 1, "b": 4, "c": 9]

        when:
        "we do count squares for each element"

        then:
        "each element has right value"
        1 == 0
    }

    def "should make a list without false values"() {
        given:
        def l = [null, "android", [], false, "ios"]
        def o = []

        when:
        "we do our magic"

        then:
        "we have right list in result"
        1 == 0
    }

    def "should make a list with placeholder for false values"() {
        given:
        def l = [null, "android", [], false, "ios"]

        when:
        "we do our magic"

        then:
        "we have right list in result"
        1 == 0
    }
}