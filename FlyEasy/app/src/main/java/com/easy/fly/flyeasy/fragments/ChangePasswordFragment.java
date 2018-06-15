package com.easy.fly.flyeasy.fragments;


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

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.LoginActivity;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.ChangeUserPasswordDto;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements Injectable {

    @BindView(R.id.old_password)
    EditText _oldPassword;

    @BindView(R.id.input_password)
    EditText _passwordText;

    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;

    @BindView(R.id.save_password)
    Button savePasswordButton;

    @BindView(R.id.profile_picture)
    CircleImageView profilePicture;

    private User user;

    private UserViewModel viewModel;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private SessionManager sessionManager;

    private String userAthenticationHeader;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_change_password, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        ButterKnife.bind(this,inflate);

        sessionManager = new SessionManager(getContext());

        initKey();

        Glide.with(getContext())
                .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture().getThumbnailPicture().getValue()))// GlideUrl is created anyway so there's no extra objects allocated
                .into(profilePicture);

        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    ChangeUserPasswordDto changeUserPasswordDto = new ChangeUserPasswordDto(_oldPassword.getText().toString(),_passwordText.getText().toString());
                    viewModel.changeUserPassword(userAthenticationHeader,changeUserPasswordDto);
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
                Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_LONG).show();
                sessionManager.logoutUser();
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;

            case ERROR:
                Toast.makeText(getContext(), "Something went wrong please try again! ", Toast.LENGTH_LONG).show();
                break;
        }
    }
    private boolean validate(){

        boolean valid = true;

        String oldPassword = _oldPassword.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (oldPassword.isEmpty() || oldPassword.length() < 4 || oldPassword.length() > 10 ) {
            _oldPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _oldPassword.setError(null);
        }

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

    public void initKey(){
        user = (User)getArguments().getParcelable("USER");
        userAthenticationHeader = userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
    }

}
