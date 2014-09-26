package pl.pelotasplus.rt_05_android

import android.app.Activity
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

class WorkshopSpec extends RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity).create().get()
    }
}
