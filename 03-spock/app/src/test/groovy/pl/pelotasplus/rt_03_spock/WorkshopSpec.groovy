package pl.pelotasplus.rt_03_spock

import android.app.Activity
import android.content.Context
import android.widget.TextView
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import spock.lang.Unroll

@RunWith(GradleRoboSputnik)
class WorkshopSpec extends RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().get()
    }

    // Mock Spocking!

    def "should fifth list element as a RoboSpock"() {
        given:
        def list = Mock(List) {
            get(5) >> "Robospock"
        }

        when:
        def value = list.get(5)

        then:
        value == "Robospock"
    }

    def "should throw exception while accessing fifth element"() {
        given:
        def list = Mock(List) {
            get(5) >> {
                throw new IndexOutOfBoundsException("Mock doesn't like 5th element")
            }
        }

        when:
        list.get(5)

        then:
        def e = thrown(IndexOutOfBoundsException)
        e.message == "Mock doesn't like 5th element"
    }

    def "should return multiple values while accessing fifth element"() {
        given:
        def list = Mock(List) {
            get(5) >>> ["Korben Dallas", "Jean-Baptiste Emanuel Zorg", "Leeloo"]
        }

        when:
        def first = list.get(5)
        def second = list.get(5)
        def third = list.get(5)

        then:
        first == "Korben Dallas"
        second == "Jean-Baptiste Emanuel Zorg"
        third == "Leeloo"
    }

    def "should call add method"() {
        given:
        def list = Mock(List)

        when:
        list.add("Leeloo")

        then:
        1 * list.add(_)
    }

    def "should call add method with various arguments"() {
        given:
        def list = Mock(List)

        when:
        list.add("Leeloo")
        list.add("Korben Dallas")

        then:
        1 * list.add("Leeloo")
        1 * list.add("Korben Dallas")
        0 * list.add("Jean-Baptiste Emanuel Zorg")
    }

    // Data driven Spocking!
    @Unroll
    def "should always return negative value on passing #argument to negativeHelper method"() {
        expect:
        negativeHelper(argument) < 0

        where:
        argument << [1, 3, -4, -5, 7, -3]
    }

    def negativeHelper(int a) {
        a > 0 ? -a : a
    }

    @Unroll
    def "should set TextView text capitalise #argument"() {
        given:
        def textView = new CapitaliseTextView(RuntimeEnvironment.application)
        textView.capitalise(argument)

        expect:
        textView.text == result

        where:
        argument     | result
        "allsmall"   | "ALLSMALL"
        "allbig"     | "ALLBIG"
        "MiXeD"      | "MIXED"
        "MixeDWitH1" | "MIXEDWITH1"
    }

    class CapitaliseTextView extends TextView {

        CapitaliseTextView(Context context) {
            super(context)
        }

        def capitalise(String text) {
            setText(text.toUpperCase())
        }
    }

    // Rewrite both methods using @Unroll annotation. Check out test names in command line!
}