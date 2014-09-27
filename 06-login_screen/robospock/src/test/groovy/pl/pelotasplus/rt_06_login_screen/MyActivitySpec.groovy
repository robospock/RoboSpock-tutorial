package pl.pelotasplus.rt_06_login_screen

import android.app.Activity
import android.content.Intent
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.FragmentTestUtil
import pl.polidea.robospock.RoboSpecification

class MyActivitySpec extends RoboSpecification {
    def "activity can be easily built"() {
        when:
        def act = Robolectric.buildActivity(MyActivity).create().get()

        then:
        act instanceof MyActivity
    }

    def "and even resumed"() {
        given:
        def act = Robolectric.buildActivity(MyActivity).create().resume().get()

        expect:
        act instanceof MyActivity
    }

    def "fragments could be created with helper utility"() {
        given:
        fragment = new PlaceholderFragment()

        when:
        FragmentTestUtil.startFragment(fragment)

        then:
        fragment instanceof PlaceholderFragment
    }

    def "or found via fragment manager"() {
        given:
        def activity = Robolectric.buildActivity(MyActivity).create().resume().get()

        when:
        fragment = activity.getFragmentManager().findFragmentById(R.id.container)

        then:
        fragment instanceof PlaceholderFragment
    }

    def activity
    def fragment

    // normally goes at the beginning of spec file
    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().resume().get()
        fragment = activity.getFragmentManager().findFragmentById(R.id.container)
    }

    def "clicking on toast button should show toast"() {
        when:
        fragment.toastButton.performClick()

        then:
        ShadowToast.latestToast
    }

    def "clicking on activity button should start activity"() {
        given:
        def shadowActivity = Robolectric.shadowOf(activity as Activity)

        when:
        fragment.activityButton.performClick()

        then:
        Intent startedIntent = shadowActivity.peekNextStartedActivity();
        startedIntent.action == Intent.ACTION_VIEW
    }

    def "clicking on text button should change text"() {
        when:
        fragment.textButton.performClick()

        then:
        fragment.textView.text == "RoboSpock!"
    }
}
