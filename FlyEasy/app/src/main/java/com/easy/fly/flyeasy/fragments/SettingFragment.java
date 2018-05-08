package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.viewmodel.BookingViewModel;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements Injectable {


    @BindView(R.id.update_personal_information)
    TextView updatePesonalInformation;

    @BindView(R.id.chage_password)
    TextView changePassord;

    @BindView(R.id.profile_picture)
    CircleImageView profilePicture;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this,inflate);

        UserDB user = (UserDB)getArguments().getParcelable("USER");

        Glide.with(getContext())
                .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture()))// GlideUrl is created anyway so there's no extra objects allocated
                .into(profilePicture);

        updatePesonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("USER",user);
                bundle.putBoolean("IS_EDIT",true);

                UserPersonalInformationFragment userPersonalInformationFragment = new UserPersonalInformationFragment();
                userPersonalInformationFragment.setArguments(bundle);
                //start fragment
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int containerId = R.id.container;
                fragmentManager.beginTransaction()
                        .replace(containerId, userPersonalInformationFragment)
                        .commitAllowingStateLoss();

            }
        });

        changePassord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("USER",user);
                bundle.putBoolean("IS_EDIT",true);

                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                changePasswordFragment.setArguments(bundle);
                //start fragment
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int containerId = R.id.container;
                fragmentManager.beginTransaction()
                        .replace(containerId, changePasswordFragment)
                        .commitAllowingStateLoss();

            }
        });

        return inflate;
    }

}
