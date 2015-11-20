package pl.pelotasplus.rt_05_login_screen;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.pelotasplus.rt_05_login_screen.dagger.Injector;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alek on 26/09/14.
 */
public class LoginFragment extends Fragment {
    @Bind(R.id.passwordEditText)
    EditText passwordEditText;

    @Bind(R.id.usernameEditText)
    EditText usernameEditText;

    @Bind(R.id.loginButton)
    public Button loginButton;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.errorTextView)
    TextView errorTextView;

    @Bind(R.id.inputsContainer)
    LinearLayout inputsContainer;

    @Inject
    ApiInterface apiInterface;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.get().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, rootView);

        passwordEditText.addTextChangedListener(loginButtonStateTextWatcher);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    doLogin();
                    handled = true;
                }
                return handled;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        usernameEditText.addTextChangedListener(loginButtonStateTextWatcher);

        return rootView;
    }

    public Callback<AuthLoginResponse> loginResponseCallback = new Callback<AuthLoginResponse>() {
        @Override
        public void success(AuthLoginResponse authLoginResponse, Response response) {
            onSuccess(authLoginResponse);
        }

        @Override
        public void failure(RetrofitError error) {
            onFailure();
        }
    };

    public void doLogin() {
        String username = getUsername();
        String password = getPassword();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.error_empty_username_or_password,
                    Toast.LENGTH_LONG).show();
            return;
        }

        hideErrors();
        showProgress();

        apiInterface.auth_login(username, password, loginResponseCallback);
    }

    void onSuccess(AuthLoginResponse authLoginResponse) {
        hideProgress();

        if (TextUtils.isEmpty(authLoginResponse.status)) {
            showError("API error");
        } else if (authLoginResponse.status.equals("ok")) {
            Toast.makeText(getActivity(), "Login OK!", Toast.LENGTH_SHORT).show();
        } else {
            showError(authLoginResponse.message);
        }
    }

    void onFailure() {
        hideProgress();
        showError("Network error");
    }

    void showError(String error) {
        errorTextView.setText(error);
        errorTextView.setVisibility(View.VISIBLE);
    }

    public TextWatcher loginButtonStateTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            hideErrors();
            enableLoginButtonIfReady();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    /**
     * Enables/disables login button if both username and password are provided.
     */
    void enableLoginButtonIfReady() {
        if (!TextUtils.isEmpty(getPassword()) &&
                !TextUtils.isEmpty(getUsername())) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

    public String getPassword() {
        Editable editable = passwordEditText.getText();
        return editable.toString();
    }

    public String getUsername() {
        Editable editable = usernameEditText.getText();
        return editable.toString();
    }

    /**
     * Hides 'login in progress'-related UI elements
     */
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        inputsContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Shows 'login in progress'-related UI elements
     */
    public void showProgress() {
        inputsContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hides information about errors during logging in
     */
    public void hideErrors() {
        errorTextView.setVisibility(View.INVISIBLE);
        errorTextView.setText("");
    }
}
