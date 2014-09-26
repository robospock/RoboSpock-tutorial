package pl.pelotasplus.rt_04_spock

import android.app.Activity
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

class WorkshopSpec extends RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().get()
    }

    // Mock Spocking!

    def "should fifth list element as a RoboSpock"() {
        given:
        "create a list here"

        when:
        "access the fifth element"

        then:
        "check if the value equals to RoboSpock"
    }

    def "should throw exception while accessing fifth element"() {
        given:
        "create a list here"

        when:
        "access the fifth element"

        then:
        "check if the IndexOutOfBoundsException is thrown"
    }

    def "should return multiple values while accessing fifth element"() {
        given:
        "create a list here"

        when:
        "access the fifth element and store to 1st variable"
        "access the fifth element and store to 2nd variable"
        "access the fifth element and store to 3rd variable"

        then:
        "check if 1st variable equals fifth1"
        "check if 2nd variable equals fifth2"
        "check if 3rd variable equals fifth3"
    }

    def "should call add method"() {
        given:
        "create a list here"

        when:
        "add element to list"

        then:
        "check if add method is called"
    }

    def "should call add method with various arguments"() {
        given:
        "create a list here"

        when:
        "add 1st argument to list"
        "add 2nd argument to list"

        then:
        "check if add method was called twice"
        "check if add method was called with 1st argument"
        "check if add method was called with 2nd argument"
    }

    // Data driven Spocking!

    def "should always return negative value on passing to negativeHelper method"(){
        expect:
        "value is negative when passing an argument"

        where:
        "pass array of arguments"
    }

    def negativeHelper(int a){
        a > 0 ? -a : a
    }

    def "should set TextView text always with capital letters"(){
        given:
        "create a TextView"

        expect:
        "check if TextView text equals to result"

        where:
        "express here arguments and results"
    }

    // Rewrite both methods using @Unroll annotation. Check out test names in command line!
}
