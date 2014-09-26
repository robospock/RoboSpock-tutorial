package pl.pelotasplus.rt_03_groovy

import android.app.Activity
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

class MyActivitySpec extends RoboSpecification {
    Activity activity;

    def "setup"() {
        activity = Robolectric.buildActivity(MyActivity.class).create().get();
    }

    def "groovy finds getter itself; no need to call them directly"() {
        expect:
        activity.getActionBar() == activity.actionBar
    }

    def "it's easy to create a list of objects with collect"() {
        expect:
        (1..4).collect({ return 4 }).size() == 4
    }

    def "there is a magic it available to help us"() {
        when:
        def a = (1..4).collect({ return it })

        then:
        a.size() == 4
        a == [1, 2, 3, 4]
    }

    def "there is no need to use 'return' keyword to return values"() {
        expect:
        (1..4).collect({ it }).size() == 4
    }

    def "above code could be written in many lines as well"() {
        given:
        int cnt = 4

        when:
        def list = (1..cnt).collect({
            return it
        })

        then:
        list.size() == cnt
    }

    def "there is no need to declare variable types"() {
        given:
        def a = 4

        and:
        int b = 4

        expect:
        a == b
        a instanceof Integer
        b instanceof Integer
    }

    def "lists are easy"() {
        given:
        def l = [1, 2, 3, 4]

        expect:
        l.size() == 4
        l instanceof List
    }

    def "maps are sexy"() {
        given:
        def m = ["apple": "ios", "google": "android", "microsoft": "windows phone"]

        expect:
        m.apple == "ios"
        m.keySet().size() == 3
        m instanceof Map
    }

    def "different types are not equal"() {
        expect:
        1 == 1
        1 != "1"
        "1" == "1"
    }

    def "empty list, false boolean value and null all have same 'false' value"() {
        given:
        def emptyList = []
        def nullVal = null
        def falseVal = false

        expect:
        !emptyList
        !nullVal
        !falseVal
    }

    def listFunc() {
        return []
    }

    def nullFunc() {
        return null
    }

    def "it's better to compare return type to protect against NPE"() {
        expect:
        listFunc() == []
        !listFunc()

        nullFunc() != []
        !nullFunc()
    }

    def "groovy has a nickname -- a closure guy"() {
        given:
        def outString = ""
        def l = ["android", "ios", "windows phone"]

        when:
        l.each {
            outString += it
        }

        then:
        outString == "androidioswindows phone"
    }

    def "more on closures"() {
        given:
        def map = ["China": 1 , "India" : 2, "USA" : 3]
        def sum = 0

        when:
        map.keySet().each({
            sum += map[it]
        })

        then:
        sum == 6
    }
}