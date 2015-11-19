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
        activity = Robolectric.buildActivity(MyActivity).create().resume().get()
        fragment = activity.getFragmentManager().findFragmentById(R.id.container) as PlaceholderFragment
    }

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
        def fragment = new PlaceholderFragment()

        expect:
        !fragment.textView
        !fragment.toastButton
        !fragment.activityButton
        !fragment.textButton
    }

    def "should have all views set when created via activity"() {
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

    def "should have text button set"() {
        expect:
        Shadows.shadowOf(fragment.toastButton).onClickListener

        and:
        fragment.toastButton.text == RuntimeEnvironment.application
                .getString(R.string.toast_button)
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
        ShadowToast.textOfLatestToast =~ "Hello world"
    }

    def "should have activity button set"() {
        expect:
        Shadows.shadowOf(fragment.activityButton).onClickListener

        and:
        fragment.activityButton.text == RuntimeEnvironment.application
                .getString(R.string.activity_button)
    }

    def "clicking on activity button should start activity"() {
        given:
        def shadowActivity = Shadows.shadowOf(activity)

        when:
        fragment.activityButton.performClick()

        then:
        Intent startedIntent = shadowActivity.peekNextStartedActivity();
        startedIntent.action == Intent.ACTION_VIEW
    }

    def "clicking on text button should change text"() {
        when:
        assert fragment.textView.text == "Hello world!"

        fragment.textButton.performClick()

        then:
        fragment.textView.text == "RoboSpock!"
    }

    def "should set label"() {
        when:
        fragment.setLabel("label text")

        then:
        fragment.textView.text == "label text"
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