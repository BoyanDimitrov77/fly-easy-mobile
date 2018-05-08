package com.easy.fly.flyeasy.fragments;


import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.HomeActivity;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.databinding.FragmentUserPersonalInformationBinding;
import com.easy.fly.flyeasy.db.models.BindingModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.UpdateUserInformationDto;
import com.easy.fly.flyeasy.utils.DateFormater;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPersonalInformationFragment extends Fragment implements Injectable,DatePickerDialog.OnDateSetListener {

    @BindView(R.id.user_fullName)
    EditText userFullName;

    @BindView(R.id.user_location)
    EditText userLocation;

    @BindView(R.id.user_email)
    EditText userEmail;

    @BindView(R.id.user_birth_data)
    EditText userBirthDate;

    @BindView(R.id.user_save_btn)
    Button userSaveButton;

    @BindView(R.id.profile_picture)
    CircleImageView profilePicture;

    private boolean isEdit;

    private User user;

    private UserViewModel viewModel;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    private String accessTockeGD;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;



    public UserPersonalInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        // Inflate the layout for this fragment

        sessionManager = new SessionManager(getContext());

        initKey();

        FragmentUserPersonalInformationBinding binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_user_personal_information,container,false);
        BindingModel<Boolean> editUserPersonalInformation = new BindingModel();
        editUserPersonalInformation.setData(isEdit);
        binding.setEditUserPesonalInformation(editUserPersonalInformation);

        ButterKnife.bind(this,binding.getRoot());

        userFullName.setText(user.getFullName());
        userLocation.setText(user.getLocation().getName());
        userEmail.setText(user.getEmail());
        userBirthDate.setText(DateFormater.formatDateForUI(user.getBirthDate().toString()));

        Glide.with(getContext())
                .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture().getThumbnailPicture().getValue()))// GlideUrl is created anyway so there's no extra objects allocated
                .into(profilePicture);

        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogDatePicker =new DatePickerInFragment();
                dialogDatePicker.setTargetFragment(UserPersonalInformationFragment.this,0);
                dialogDatePicker.show(getFragmentManager(),"selectedDate picker");
            }
        });

        userSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = userFullName.getText().toString();
                String location = userLocation.getText().toString();
                String email = userEmail.getText().toString();
                String birthDate = userBirthDate.getText().toString();

                if(!validateEmail(email)){
                    Toast.makeText(getContext(), "Enter valid email", Toast.LENGTH_LONG).show();
                    return;
                }
                UpdateUserInformationDto updateUserInformationDto = new UpdateUserInformationDto(fullname,location,email, birthDate);

                viewModel.updatePesonalInformation(userAthenticationHeader,updateUserInformationDto);
                viewModel.response().observe(getActivity(),response -> processResponse(response));

            }
        });



        return binding.getRoot();
    }


    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                User user = (User)response.data;
                //save user in Database
                Observable.fromCallable(() -> viewModel.saveUserInDB(user)).subscribeOn(Schedulers.io()).subscribe();
                Toast.makeText(getContext(), "Information is update", Toast.LENGTH_LONG).show();
                break;

            case ERROR:
                break;
        }


    }

    private void initKey(){
        isEdit = getArguments().getBoolean("IS_EDIT");
        user = (User)getArguments().getParcelable("USER");
        userAthenticationHeader = UserUtil.getUserAthenticationHeader(sessionManager.getUserDeatails());
    }

    private boolean validateEmail(String email){
        boolean valid = true;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("enter a valid email address");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        return valid;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        userBirthDate.setText(DateFormater.formatDate(currentDate));
    }
}
