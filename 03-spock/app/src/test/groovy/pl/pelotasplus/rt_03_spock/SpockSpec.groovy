package pl.pelotasplus.rt_03_spock

import android.accounts.Account
import org.junit.runner.RunWith
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import spock.lang.Ignore
import spock.lang.Unroll

@RunWith(GradleRoboSputnik)
class SpockSpec extends RoboSpecification {
    // in spec we have...

    // fields
    Group group

    // global setup, run before every feature method
    // aka fixture method
    def "setup"() {
        group = new Group()
    }

    // global cleanup, run after every feature method
    // another so called fixture method
    def "cleanup"() {
    }

    // helper methods
    def helperMethod(int value) {
        value * 2
    }

    // feature methods, our tests
    def "minimal feature method is just one except block"() {
        expect:
        1 == 1
    }

    def "most of features have stimulus -- 'when' block, and response -- 'then' block"() {
        when:
        def val = helperMethod(4)

        then:
        val == 8
    }

    def "with 'setup' block we can prepare our feature to work"() {
        setup:
        def stack = new Stack()
        def elem = "push me"

        when:
        stack.push(elem)

        then:
        !stack.isEmpty()
        stack.size() == 1
        stack.peek() == elem
    }

    def "exceptions are easy to catch and verify"() {
        setup:
        def stack = new Stack()

        when:
        stack.pop()

        then:
        def e = thrown(EmptyStackException)
        e.cause == null

        and:
        stack.empty
    }

    def "exception are also easy to be check if they weren't thrown"() {
        setup:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)
    }

    def "mocks are super easy"() {
        setup:
        def memberMock = Mock(Member)

        when:
        group.addMember(memberMock)

        then:
        group.members.size() == 1
    }

    def "interaction on mocks is even easier"() {
        setup:
        group.members = Mock(List)

        when:
        group.addMember(Mock(Member))

        then:
        1 * group.members.add(_)
    }

    def "by default mocks return empty values"() {
        given:
        def list = Mock(List)

        expect:
        list.size() == 0
        list.get(10) == null
    }

    @Ignore
    def "errors look pretty"() {
        setup:
        def member = new Member()

        when:
        member.age = 40

        then:
        member.age == 50
    }

    @Ignore
    def "errors look pretty**2"() {
        setup:
        def member = new Member()

        when:
        group.members.add(member)

        then:
        group.members.size() == 2
    }

    def "where blocks are important part od data driven testing"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    def "where blocks could look sexy too"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        5 | 1 || 5
        3 | 9 || 9
    }

    def "specification acts as documentation"() {
        given: "an empty account"
        def account = Mock(Account)

        when: "the account name is set"
        account.name = "Robospock"

        then: "the account's name has value"
        account.name == "Robospock"
    }

    // Mock Spocking!

    def "should call add method"() {
        given:
        // FIXME mock the list
        def list = []

        when:
        // FIXME add element
        list

        then:
        // FIXME make sure it was added
        1 == 1
    }

    def "should add member to group"() {
        given:
        def group = new Group()
        def member = new Member()

        and:
        // FIXME mock something in a Group instance
        member

        when:
        group.addMember(member)

        then: // FIXME check if member was added
        1 == 1
    }

    def "should have 'Robospock' as fifth element of the list"() {
        when:
        def list = [] // FIXME mock this list

        then:
        list.get(5) == "Robospock"
    }

    def "should throw exception while accessing fifth element"() {
        given:
        def list = [] // FIXME we have a mocked list

        when:
        list.get(5)

        then:
        // FIXME make sure an exception out of bounds was thrown
        1 == 1
    }

    def "should return multiple values while accessing fifth element"() {
        given:
        // FIXME we have a mock with three different return values
        def list = []

        when:
        // FIXME when we make a three calls
        list

        then:
        // FIXME we will get three different values
        1 == 1
    }

    def "should call add method with various arguments"() {
        given:
        // FIXME we have a list mock
        def list = []

        when:
        // FIXME we add few elements
        list

        then:
        // FIXME we can check all the add() calls
        1 == 1
    }

    // Data driven Spocking!

    @Unroll
    def "should make strings upper case"() {
        expect:
        // FIXME make string upper case

        where:
        argument     | result
        "allsmall"   | "ALLSMALL"
        "allbig"     | "ALLBIG"
        "MiXeD"      | "MIXED"
        "MixeDWitH1" | "MIXEDWITH1"
    }

    @Unroll
    def "should set age to #age years"() {
        setup:
        // FIXME with a member
        def member

        when:
        // FIXME when we set age
        member

        then:
        // FIXME it is set, wow!
        member

        where:
        // FIXME for many different age values
        age << []
    }

    @Unroll
    def "should and #cnt members to group"() {
        given:
        // FIXME with a group
        def group = new Group()

        when:
        // FIXME when we add a members
        group

        then:
        // FIXME then group size is right, wow!
        group.members.size()

        where:
        // FIXME for many different members counts
        cnt << []
    }

    def "should set both age and name"() {
        given:
        // FIXME we have 'special' kind of mock
        def spy = []

        when:
        // FIXME we set a name and age
        spy

        then:
        // FIXME then both fields were set
        spy

        and:
        // FIXME and setters where called
        spy
    }
}