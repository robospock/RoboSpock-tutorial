package pl.pelotasplus.rt_06_login_screen;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alek on 26/09/14.
 */
public class LoginFragment extends Fragment {
    EditText passwordEditText;
    EditText usernameEditText;
    Button loginButton;
    ProgressBar progressBar;
    TextView errorTextView;
    LinearLayout inputsContainer;
    private ApiInterface apiInterface;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiInterface = getApiInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        inputsContainer = (LinearLayout) rootView.findViewById(R.id.inputsContainer);

        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        passwordEditText.addTextChangedListener(textWatcher);
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
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideErrors();
                return false;
            }
        });

        loginButton = (Button) rootView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        errorTextView = (TextView) rootView.findViewById(R.id.errorTextView);

        usernameEditText = (EditText) rootView.findViewById(R.id.usernameEditText);
        usernameEditText.addTextChangedListener(textWatcher);
        usernameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideErrors();
                return false;
            }
        });

        return rootView;
    }

    public void doLogin() {
        String username = getUsername();
        String password = getPassword();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }

        hideErrors();
        showProgress();

        apiInterface.auth_login(username, password, new Callback<AuthLoginResponse>() {
            @Override
            public void success(AuthLoginResponse authLoginResponse, Response response) {
                onSuccess(authLoginResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                onFailure();
            }
        });
    }

    private void onSuccess(AuthLoginResponse authLoginResponse) {
        hideProgress();

        if (TextUtils.isEmpty(authLoginResponse.status)) {
            showError("API error");
        } else if (authLoginResponse.status.equals("ok")) {
            Toast.makeText(getActivity(), "Login OK!", Toast.LENGTH_SHORT).show();
        } else {
            showError(authLoginResponse.message);
        }
    }

    private void onFailure() {
        hideProgress();
        showError("Network error");
    }

    private void showError(String error) {
        errorTextView.setText(error);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private TextWatcher textWatcher = new TextWatcher() {
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
    public void enableLoginButtonIfReady() {
        if (!TextUtils.isEmpty(passwordEditText.getText()) &&
                !TextUtils.isEmpty(usernameEditText.getText())) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

    public String getPassword() {
        Editable editable = passwordEditText.getText();
        if (editable == null) {
            return null;
        }
        return editable.toString();
    }

    public String getUsername() {
        Editable editable = usernameEditText.getText();
        if (editable == null) {
            return null;
        }
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

    private static ApiInterface getApiInterface() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiInterface.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(ApiInterface.class);
    }
}
