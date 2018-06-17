package com.easy.fly.flyeasy.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.SignUpActivity;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmailForgetPasswordFragment extends Fragment implements Injectable {

    private UserViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.input_email)
    EditText _emailText;

    @BindView(R.id.send_email_button)
    Button sendEmailForForgotPassword;


    public SendEmailForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        View inflate = inflater.inflate(R.layout.fragment_send_email_forgot_password, container, false);
        ButterKnife.bind(this,inflate);


        sendEmailForForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    viewModel.resetPasswordSendEmail(new UserDto(_emailText.getText().toString(),"","","",null,null));
                }
            }
        });

        viewModel.response().observe(this,response -> processResponse(response));

        return inflate;

    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                Toast.makeText(getContext(), "Please check your email for resetting password link!", Toast.LENGTH_LONG).show();
                break;

            case ERROR:

                break;
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        return valid;
    }

}
