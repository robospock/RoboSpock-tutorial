package pl.pelotasplus.rt_02_groovy

import org.junit.runner.RunWith
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik

@RunWith(GradleRoboSputnik)
class GroovySpec extends RoboSpecification {
    def "setup"() {
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
        !nullFunc()
        !listFunc()

        and:
        nullFunc() != []
        listFunc() == []
    }

    def "lists are easy"() {
        given:
        def list = [1, 2, 3, 4]

        expect:
        list.size() == 4
        list instanceof List
    }

    def "maps are sexy"() {
        given:
        def map = ["apple": "ios", "google": "android", "microsoft": "windows phone"]

        expect:
        map.apple == "ios"
        map.keySet().size() == 3
        map instanceof Map
    }

    def "groovy finds getter/setters itself; no need to call them directly"() {
        given:
        def person = new Person()

        when:
        person.age = 40

        then:
        person.age == person.getAge()

        and:
        person.age == 40
    }

    def "should set name"() {
        given:
        def person = new Person();

        when:
        person.name = "Alex"

        then:
        person.name == "Alex"
    }

    def "should set age and later height without changing age"() {
        given:
        def person = new Person();

        when:
        person.age = 40
        person.@height = 149

        then:
        person.age == 40
        person.height == 149
    }

    def "it's easy to create a list of objects with collect"() {
        given:
        def list = (1..4).collect({ 4 })

        expect:
        list instanceof List
        list.size() == 4
    }

    def "shout have a list of names in upper case"() {
        given:
        def names = ["alex", "tomas", "juri"]

        when: // FIXME create new list based on names
        def list = []

        then:
        list == ["ALEX", "TOMAS", "JURI"]
    }

    def "should be a list of first 4 odd numbers"() {
        when: // FIXME create new list
        def list = []

        then:
        list == [0, 2, 4, 6]
    }

    def "should increase age 10 times"() {
        given:
        def person = new Person()

        and:
        person.age = 40

        when:
        // FIXME bumpAge a few times

        then:
        person.age == 50
    }

    def "should join list"() {
        given:
        def list = ["android", "ios", "windows phone"]

        when: // FIXME convert list to a string
        def string = ""

        then:
        string == "android ios windows phone"
    }

    def "should sum map values"() {
        given:
        def map = ["China": 1, "India": 2, "USA": 3]
        def sum = 0

        when:
        // FIXME iterate over a map

        then:
        sum == 6
    }

    def "should create a list of 10 'groovy rocks' strings"() {
        when: // FIXME create a list
        def list = []

        then: // FIXME make sure it has right size and correct elemnts
        list == []
    }

    def "should make sure that square() method works just fine"() {
        given:
        def map = ["a": 1, "b": 4, "c": 9]

        when:
        // FIXME update map in place

        then:
        map.a == 1
        map.b == 2
        map.c == 3
    }

    def "should make a list without false values"() {
        given:
        def list = [null, "android", [], false, "ios"]

        when: // FIXME filter list somehow
        def positive = []

        then:
        positive == ["android", "ios"]
    }

    def "should make a list with placeholder for false values"() {
        given:
        def list = [null, "android", [], false, "ios"]

        when: // FIXME crate a new list based on original one
        def outList = []

        then:
        outList == ["XXX", "android", "XXX", "XXX", "ios"]
    }

    def "should make sure that list contains only nexus devices"() {
        given:
        def devices = ["nexus 4", "nexus 10", "nexus 7", "tomek"]

        when: // FIXME filter devices list to create output one
        def nexuses = []

        then:
        nexuses.every { it.startsWith("nexus") }
    }
}