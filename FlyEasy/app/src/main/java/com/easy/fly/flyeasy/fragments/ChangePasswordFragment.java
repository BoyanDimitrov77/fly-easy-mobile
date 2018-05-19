package com.easy.fly.flyeasy.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.di.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements Injectable {


    @BindView(R.id.input_password)
    EditText _passwordText;

    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;

    @BindView(R.id.save_password)
    Button savePasswordButton;

    @BindView(R.id.profile_picture)
    CircleImageView profilePicture;

    private UserDB user;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_change_password, container, false);

        ButterKnife.bind(this,inflate);

        initKey();

        Glide.with(getContext())
                .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture()))// GlideUrl is created anyway so there's no extra objects allocated
                .into(profilePicture);

        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                }
            }
        });

        return inflate;
    }

    private boolean validate(){

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

    public void initKey(){
        user = (UserDB)getArguments().getParcelable("USER");
    }

}
