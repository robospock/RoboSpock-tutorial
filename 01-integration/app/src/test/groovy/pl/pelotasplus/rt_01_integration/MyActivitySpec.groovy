package pl.pelotasplus.rt_01_integration

import pl.polidea.robospock.RoboSpecification

class MyActivitySpec extends RoboSpecification {
    def "setup"() {
    }

    def "should work like a boss"() {
        expect:
        1 == 1
    }
}