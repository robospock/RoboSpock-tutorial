package pl.pelotasplus.rt_06_login_screen

import pl.polidea.robospock.RoboSpecification

class WorkshopSpec extends RoboSpecification {
    def "setup"() {
    }

    def "activity should have isCreated set"() {
        given:
        "create an activity here"

        expect:
        "check if isCreated is set"
    }

    def "resumed activity should have isResumed set"() {
        given:
        "create an activity here"

        when:
        "resume activity"

        then:
        "check if isResumed is set"
    }

    def "should have right toast message"() {
        given:
        "create a fragment here"

        when:
        "show toast"

        then:
        "toast has right message"
    }

    def "should have all views set"() {
        given:
        "create a fragment here"

        expect:
        "check if all views are set"
    }

    def "should have empty views if not created view activity"() {
        given:
        "create a fragment here (not via an activity)"

        expect:
        "views are null"
    }

    def "should have empty views but mock will help us!"() {
        given:
        "create a fragment here (not via an activity)"

        and:
        "set view via mock"

        when:
        "click on change text button"

        then:
        "make sure that text is set correctly"
    }
}
