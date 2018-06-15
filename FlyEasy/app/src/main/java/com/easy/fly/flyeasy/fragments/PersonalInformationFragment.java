package com.easy.fly.flyeasy.fragments;


import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.BindingModel;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.UpdateUserInformationDto;
import com.easy.fly.flyeasy.utils.DateFormater;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;
import com.easy.fly.flyeasy.databinding.FragmentPersonalInformationBinding;
import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInformationFragment extends Fragment implements Injectable,DatePickerDialog.OnDateSetListener {

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

    private boolean isEdit;

    private User user;

    private UserViewModel viewModel;

    private SessionManager sessionManager;

    private String userAthenticationHeader;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public PersonalInformationFragment() {
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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        sessionManager = new SessionManager(getContext());

        initKey();

        FragmentPersonalInformationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_information, container, false);
        BindingModel<Boolean> editPesonalInformation = new BindingModel();
        editPesonalInformation.setData(isEdit);
        binding.setEditPesonalInformation(editPesonalInformation);

        ButterKnife.bind(this,binding.getRoot());

        userFullName.setText(user.getFullName() !=null ? user.getFullName() : "");
        userLocation.setText(user.getLocation()!= null ? user.getLocation().getName() : "");
        userEmail.setText(user.getEmail() !=null ? user.getEmail() : "");
        userBirthDate.setText(user.getBirthDate() != null ? DateFormater.formatDateForUI(user.getBirthDate().toString()): "");

        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogDatePicker =new DatePickerInFragment();
                dialogDatePicker.setTargetFragment(PersonalInformationFragment.this,0);
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
                Toast.makeText(getContext(), "Information changed successfully!", Toast.LENGTH_LONG).show();
                Observable.fromCallable(() -> viewModel.saveUserInDB(user)).subscribeOn(Schedulers.io()).subscribe();
                break;

            case ERROR:
                break;
        }


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

}
