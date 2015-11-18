package pl.pelotasplus.rt_03_spock

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
        given: "an empty bank account"
        // def account = Mock(Account)

        when: "the account is credited \$10"
        // account.setCredit(10)

        then: "the account's balance is \$10"
        // account.getCredit() == 10
    }

    // Mock Spocking!

    def "should call add method"() {
        given:
        def list = Mock(List)

        when:
        list.add("Leeloo")

        then:
        1 * list.add(_)
    }

    def "should add member to group"() {
        given:
        def group = new Group()
        def member = new Member()

        and:
        group.members = Mock(List)

        when:
        group.addMember(member)

        then:
        1 * group.members.add(member)
    }

    def "should have 'Robospock' as fifth element of the list"() {
        given:
        def list = Mock(List) {
            get(5) >> "Robospock"
        }

        when:
        def value = list.get(5)

        then:
        value == "Robospock"
    }

    def "should throw exception while accessing fifth element"() {
        given:
        def list = Mock(List) {
            get(5) >> {
                throw new IndexOutOfBoundsException("Mock doesn't like 5th element")
            }
        }

        when:
        list.get(5)

        then:
        def e = thrown(IndexOutOfBoundsException)
        e.message == "Mock doesn't like 5th element"
    }

    def "should return multiple values while accessing fifth element"() {
        given:
        def list = Mock(List) {
            get(5) >>> ["Korben Dallas", "Jean-Baptiste Emanuel Zorg", "Leeloo"]
        }

        when:
        def first = list.get(5)
        def second = list.get(5)
        def third = list.get(5)

        then:
        first == "Korben Dallas"
        second == "Jean-Baptiste Emanuel Zorg"
        third == "Leeloo"
    }

    def "should call add method with various arguments"() {
        given:
        def list = Mock(List)

        when:
        list.add("Leeloo")
        list.add("Korben Dallas")

        then:
        1 * list.add("Leeloo")
        1 * list.add("Korben Dallas")
        0 * list.add("Jean-Baptiste Emanuel Zorg")
    }

    // Data driven Spocking!

    @Unroll
    def "should make strings upper case"() {
        expect:
        argument.toUpperCase() == result

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
        def member = new Member()

        when:
        member.age = age

        then:
        member.age == age

        where:
        age << [0, 10, 20, 40]
    }

    @Unroll
    def "should and #cnt members to group"() {
        given:
        def group = new Group()

        when:
        cnt.times { group.addMember(Mock(Member)) }

        then:
        group.members.size() == cnt

        where:
        cnt << [0, 5, 10, 20]
    }

    def "should set both age and name"() {
        given:
        def spy = Spy(Member, constructorArgs: [])

        when:
        spy.setNameAndAge("Alex", 40)

        then:
        1 * spy.setName("Alex")
        1 * spy.setAge(40)

        and:
        spy.name == "Alex"
        spy.age == 40
    }
}