package pl.pelotasplus.rt_03_spock

import android.app.Activity
import android.widget.TextView
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import spock.lang.Ignore
import spock.lang.Unroll

@RunWith(GradleRoboSputnik)
class MyActivitySpec extends RoboSpecification {
    // in spec we have...

    // fields
    Activity activity;

    // global setup, run before every feature method
    // aka fixture method
    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity.class).create().get();
    }

    // global cleanup, run after every feature method
    // another so called fixture method
    def "cleanup"() {
    }

    // helper methods
    def helperMethod(int value) {
        value * 2
    }

    // feature methods, our tests
    def "minimal feature method is just one except block"() {
        expect:
        1 == 1
    }

    def "most of features have stimulus -- 'when' block, and response -- 'then' block"() {
        when:
        def val = helperMethod(4)

        then:
        val == 8
    }

    def "with 'setup' block we can prepare our feature to work"() {
        setup:
        def stack = new Stack()
        def elem = "push me"

        when:
        stack.push(elem)

        then:
        !stack.isEmpty()
        stack.size() == 1
        stack.peek() == elem
    }

    def "exceptions are easy to catch and verify"() {
        setup:
        def stack = new Stack()

        when:
        stack.pop()

        then:
        def e = thrown(EmptyStackException)
        e.cause == null

        stack.empty
    }

    def "exception are also easy to be check if they weren't thrown"() {
        setup:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)
    }

    def "mocks are super easy"() {
        setup:
        def textView = Mock(TextView)

        expect:
        textView.setText("test")
    }

    // helper method
    def setText(def textView, def text) {
        textView.setText(text)
    }

    def "interaction on mocks is even easier"() {
        setup:
        def textView = Mock(TextView)
        def textView2 = Mock(TextView)

        when:
        setText(textView, "random string")
        setText(textView2, "random string")

        then:
        1 * textView.setText("random string")
        1 * textView2.setText(_)
    }

    @Ignore
    def "errors look pretty"() {
        setup:
        def tv = new TextView(RuntimeEnvironment.application)

        when:
        tv.text = "android"

        then:
        tv.text == "windows"
    }

    def "where blocks are important part od data driven testing"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    def "where blocks could look sexy too"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        5 | 1 || 5
        3 | 9 || 9
    }

    @Unroll
    def "where blocks work fine with just one value (#label) to be substituted"() {
        setup:
        def tv = new TextView(RuntimeEnvironment.application)

        when:
        tv.setText(label)

        then:
        tv.getText() == label

        where:
        label << ["a", "list", "of", "values", "to", "be", "substituted"]
    }

    def "specifications could work as documentation, so describe your blocks!"() {
        setup: "create ui widget"
        def tv = new TextView(RuntimeEnvironment.application)

        when: "set label to #value"
        tv.text = value

        then: "label value is #value"
        tv.text == value

        where: "label is a name of mobile OS"
        value << ["android", "ios", "windows phone"]
    }
}