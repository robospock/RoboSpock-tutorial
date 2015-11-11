package pl.pelotasplus.rt_05_android

import android.widget.TextView
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.FragmentTestUtil
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import pl.pelotasplus.rt_04_android.MyActivity
import pl.pelotasplus.rt_04_android.PlaceholderFragment

@RunWith(GradleRoboSputnik)
class WorkshopSpec extends RoboSpecification {
    def activity

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().resume().get()
    }

    def "activity should have isCreated set"() {
        given:
        def a = Robolectric.buildActivity(MyActivity).create().get()

        expect:
        a.isCreated
    }

    def "resumed activity should have isResumed set"() {
        given:
        def a = Robolectric.buildActivity(MyActivity).create().resume().get()

        expect:
        a.isResumed
    }

    def "should have right toast message"() {
        given:
        def fragment = new PlaceholderFragment()

        when:
        FragmentTestUtil.startFragment(fragment)

        and:
        fragment.toastButton.performClick()

        then:
        ShadowToast.getTextOfLatestToast() == "Test toast!"
    }

    def "should have all views set"() {
        given:
        def fragment = new PlaceholderFragment()

        when:
        FragmentTestUtil.startFragment(fragment)

        then:
        fragment.textView
        fragment.toastButton
        fragment.activityButton
        fragment.textButton
    }

    def "should have empty views if not created view activity"() {
        given:
        def fragment = new PlaceholderFragment()

        expect:
        !fragment.textView
        !fragment.toastButton
        !fragment.activityButton
        !fragment.textButton
    }

    def "should have empty views but mock will help us!"() {
        given:
        def fragment = new PlaceholderFragment()

        and:
        fragment.textView = Mock(TextView)

        when:
        fragment.setLabel("label")

        then:
        1 * fragment.textView.setText("label")
    }
}