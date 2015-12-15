package pl.pelotasplus.rt_05_login_screen

import android.app.Fragment
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.FragmentTestUtil
import org.robospock.RoboSpecification
import org.robospock.internal.GradleRoboSputnik
import pl.pelotasplus.rt_05_login_screen.dagger.Injector
import retrofit.RetrofitError
import retrofit.client.Response
import spock.lang.Unroll

@RunWith(GradleRoboSputnik)
class LoginFragmentSpec extends RoboSpecification {
    LoginFragment fragment
    ApiInterface apiInterfaceMock

    def "setup"() {
        apiInterfaceMock = Mock(ApiInterface)
        fragment = LoginFragment.newInstance()

        Injector.start(new TestModule() {
            @Override
            ApiInterface provideApiInterface() {
                apiInterfaceMock
            }
        })

        FragmentTestUtil.startFragment(fragment)
    }

    def "cleanup"() {
    }

    def "should hide errors"() {
        when:
        fragment.hideErrors()

        then:
        fragment.errorTextView.visibility == View.INVISIBLE

    }

    def "should show progress"() {
        given:
        fragment.inputsContainer = Mock(LinearLayout)
        fragment.progressBar = Mock(ProgressBar)

        when:
        fragment.showProgress()

        then:
        1 * fragment.inputsContainer.setVisibility(View.GONE)
        1 * fragment.progressBar.setVisibility(View.VISIBLE)
    }

    def "should hide progress"() {
        given:
        fragment.inputsContainer = Mock(LinearLayout)
        fragment.progressBar = Mock(ProgressBar)

        when:
        fragment.hideProgress()

        then:
        1 * fragment.inputsContainer.setVisibility(View.VISIBLE)
        1 * fragment.progressBar.setVisibility(View.GONE)
    }

    @Unroll
    def "should return password"() {
        when:
        fragment.passwordEditText.text = passwd

        then:
        fragment.getPassword() == passwd

        where:
        passwd << ["I", "<3", "Minsk"]
    }

    @Unroll
    def "should return username"() {
        when:
        fragment.usernameEditText.text = username

        then:
        fragment.getUsername() == username

        where:
        username << ["Oksana", "Pavel", "Alina"]
    }

    def "should hide errors when chanigng login/password"() {
        given:
        def spy = Spy(LoginFragment, constructorArgs: [])
        FragmentTestUtil.startFragment(spy)


        when:
        spy.loginButtonStateTextWatcher.afterTextChanged(Mock(Editable))

        then:
        1 * spy.hideErrors()
    }

    def "should show error"() {
        given:
        def error = "Error #666"

        when:
        fragment.showError(error)

        then:
        fragment.errorTextView.setText(error)
        fragment.errorTextView.setVisibility(View.VISIBLE)
    }

    def "should report failure"() {
        given:
        def spy = Spy(LoginFragment, constructorArgs: [])

        and:
        FragmentTestUtil.startFragment(spy)

        when:
        spy.onFailure()

        then:
        1 * spy.hideProgress()
        1 * spy.showError(_)
    }

    def "should hide progress on success"() {
        when:
        fragment.onSuccess(Mock(AuthLoginResponse))

        then:
        fragment.progressBar.visibility == View.GONE
        fragment.inputsContainer.visibility == View.VISIBLE
    }

    def "should show error when empty status on success"() {
        given:
        def response = new AuthLoginResponse()
        response.status = ""

        when:
        fragment.onSuccess(response)

        then:
        fragment.errorTextView.text == "API error"
        fragment.errorTextView.visibility == View.VISIBLE
    }

    def "should show toast when status is OK on success"() {
        given:
        def response = new AuthLoginResponse()
        response.status = "ok"

        when:
        fragment.onSuccess(response)

        then:
        ShadowToast.textOfLatestToast =~ "Login OK"
    }

    def "should show error with message"() {
        given:
        def response = new AuthLoginResponse()
        response.status = "error"
        response.message = "message"

        when:
        fragment.onSuccess(response)

        then:
        fragment.errorTextView.text == "message"
        fragment.errorTextView.visibility == View.VISIBLE
    }

    def "should hide errors in doLogin"() {
        given:
        fragment.passwordEditText.text = "passwd"
        fragment.usernameEditText.text = "username"

        when:
        fragment.doLogin()

        then:
        fragment.errorTextView.visibility == View.INVISIBLE
        !fragment.errorTextView.text
    }

    @Unroll
    def "should show toast in doLogin when no username or password"() {
        given:
        fragment.passwordEditText.text = passwd
        fragment.usernameEditText.text = username

        when:
        fragment.doLogin()

        then:
        ShadowToast.textOfLatestToast =~ "Empty username or password"

        where:
        username | passwd
        null     | "pass"
        "user"   | ""
        ""       | ""
        ""       | "pass1"
    }

    def "should show progress in doLogin"() {
        given:
        fragment.passwordEditText.text = "passwd"
        fragment.usernameEditText.text = "username"

        when:
        fragment.doLogin()

        then:
        fragment.progressBar.visibility == View.VISIBLE
        fragment.inputsContainer.visibility == View.GONE
    }

    def "should call api in doLogin"() {
        given:
        fragment.passwordEditText.text = "passwd"
        fragment.usernameEditText.text = "username"

        when:
        fragment.loginButton.performClick()


        then:
        1 * apiInterfaceMock.auth_login("username", "passwd", _)
    }

    def "should call onSuccess on successful retrofit response"() {
        given:
        def spy = Spy(LoginFragment, constructorArgs: [])
        FragmentTestUtil.startFragment(spy)

        and:
        def loginResponse = Mock(AuthLoginResponse)
        def retrofitResponse = new Response("url", 200, "", [], null)

        when:
        spy.loginResponseCallback.success(loginResponse, retrofitResponse)

        then:
        1 * spy.onSuccess(loginResponse)
    }

    def "should call onFailure on retrofit error"() {
        given:
        def spy = Spy(LoginFragment, constructorArgs: [])
        FragmentTestUtil.startFragment(spy)

        and:
        def error = RetrofitError.unexpectedError("url", Mock(Throwable))

        when:
        spy.loginResponseCallback.failure(error)

        then:
        1 * spy.onFailure()
    }

    def "should have login button disabled by default"() {
        expect:
        !fragment.loginButton.enabled
    }

    def "should have click listener set on login button"() {
        expect:
        Shadows.shadowOf(fragment.loginButton).onClickListener
    }

    def "should try to login when pressing login button"() {
        given:
        def spy = Spy(LoginFragment, constructorArgs: [])
        FragmentTestUtil.startFragment(spy)

        when:
        spy.loginButton.performClick()

        then:
        1 * spy.doLogin()
    }
}