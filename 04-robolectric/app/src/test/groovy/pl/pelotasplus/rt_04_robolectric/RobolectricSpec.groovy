package pl.pelotasplus.rt_04_robolectric

import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.FragmentTestUtil
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik

@RunWith(GradleRoboSputnik)
class RobolectricSpec extends RoboSpecification {
    MyActivity activity
    PlaceholderFragment fragment

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity.class).create().resume().get()
        fragment = activity.getFragmentManager().findFragmentById(R.id.container) as PlaceholderFragment
    }

    def "activity can be easily built"() {
        when:
        def act = Robolectric.buildActivity(MyActivity).create().get()

        then:
        act instanceof MyActivity
        act.wasCreated
        !act.wasResumed
    }

    def "and even resumed"() {
        given:
        def act = Robolectric.buildActivity(MyActivity).create().resume().get()

        expect:
        act instanceof MyActivity

        and:
        act.wasCreated
        act.wasResumed
    }

    def "fragments could be created with helper utility"() {
        given:
        def fragment = new PlaceholderFragment()

        when:
        FragmentTestUtil.startFragment(fragment)

        then:
        fragment instanceof PlaceholderFragment
    }

    def "or found via fragment manager"() {
        given:
        def activity = Robolectric.buildActivity(MyActivity).create().resume().get()

        when:
        def fragment = activity.fragmentManager.findFragmentById(R.id.container)

        then:
        fragment instanceof PlaceholderFragment
    }

    def "context is available thanks to robolectric"() {
        given:
        def editText = new EditText(RuntimeEnvironment.application)

        when:
        editText.text = "text to be edited"

        then:
        editText.text.toString() == "text to be edited"
    }

    def "should have empty views if not created via activity"() {
        given:
        def f = PlaceholderFragment.newInstance()

        expect:
        !f.textView
        !f.activityButton
    }

    def "should have all views set when created via activity"() {
        given:
        def f = PlaceholderFragment.newInstance()

        when:
        FragmentTestUtil.startFragment(f)

        then:
        f.textView
        f.textView.text == "Hello world"
    }

    def "should have text button set"() {
        expect:
        1
        // FIXME button should has click listener

        and:
        // FIXME and also correct label (getText())
        1 == 1
    }

    def "should show toast"() {
        when:
        fragment.toastButton.performClick()

        then:
        ShadowToast.latestToast
    }

    def "should have contains hello world in toast message"() {
        when:
        fragment.toastButton.performClick()

        then:
        ShadowToast.textOfLatestToast == "hrll"
    }

    def "should have activity button set"() {
        expect:
        Shadows.shadowOf(fragment.activityButton).getOnClickListener()

        and:
        fragment.activityButton.text == RuntimeEnvironment.application
                .getString(R.string.activity_button)
    }

    def "clicking on activity button should start activity"() {
        given:
        1
        // FIXME maybe some preparation is needed ;-)

        when:
        fragment.activityButton.performClick()

        then:
        // FIXME an intent was started!
        1 == 1
    }

    def "clicking on text button should change text"() {
        when:
        1
        // FIXME i click on a button

        then:
        // FIXME text label changes
        1 == 1
    }

    def "should set label"() {
        when:
        1
        // FIXME i call fragments method to set label

        then:
        // FIXME a label is set
        1 == 1
    }

    def "should have empty views but mock will help us!"() {
        given:
        1
        // FIXME we have a fragment but we don't run it through a lifecycle

        when:
        1
        // FIXME i call fragments method to set label

        then:
        // FIXME a label is set
        1 == 1
    }
}