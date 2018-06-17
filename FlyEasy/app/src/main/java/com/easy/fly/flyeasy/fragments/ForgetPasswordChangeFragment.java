package com.easy.fly.flyeasy.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.LoginActivity;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordChangeFragment extends Fragment implements Injectable {

    private UserViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;

    @BindView(R.id.change_password_button)
    Button changePasswordButton;

    String token;

    public ForgetPasswordChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        View inflate = inflater.inflate(R.layout.fragment_forget_password_change, container, false);
        ButterKnife.bind(this,inflate);

        intiKey();

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Password change...");

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    progressDialog.show();
                    viewModel.resetPassword(token,new UserDto("",_passwordText.getText().toString(),"","",null,null));
                }
            }
        });

        viewModel.response().observe(this,response -> processResposne(response,progressDialog));

        return inflate;
    }

    private void processResposne(Response response, ProgressDialog progressDialog) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                break;

            case ERROR:

                break;
        }
    }

    public boolean validate() {
        boolean valid = true;

        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void intiKey() {
        token = (String)getArguments().get("TOKEN");
    }

}
