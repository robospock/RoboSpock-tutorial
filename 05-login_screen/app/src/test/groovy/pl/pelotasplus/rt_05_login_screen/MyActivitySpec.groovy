package pl.pelotasplus.rt_05_login_screen

import android.app.Activity
import android.view.MenuItem
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.ActivityController
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import pl.pelotasplus.rt_05_login_screen.dagger.Injector

@RunWith(GradleRoboSputnik)
class MyActivitySpec extends RoboSpecification {
    Activity activity
    ActivityController controller

    def "setup"() {
        controller = Robolectric.buildActivity(MyActivity)
        activity = controller.get()

        Injector.start(new TestModule())
    }

    def "cleanup"() {
    }

    def "should have login fragment"() {
        given:
        controller.create().resume()

        expect:
        activity.fragmentManager.findFragmentById(R.id.container) instanceof LoginFragment
    }

    def "should notify that settings are not implemented"() {
        given:
        controller.create().resume()

        and:
        def menuItem = Mock(MenuItem) {
            getItemId() >> R.id.action_settings
        }

        when:
        activity.onOptionsItemSelected(menuItem)

        then:
        ShadowToast.textOfLatestToast == "Settings not implemented yet"
    }
}