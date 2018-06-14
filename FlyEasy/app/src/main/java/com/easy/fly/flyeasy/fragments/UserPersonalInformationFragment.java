package com.easy.fly.flyeasy.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.HomeActivity;
import com.easy.fly.flyeasy.activities.LoginActivity;
import com.easy.fly.flyeasy.activities.SettingActivity;
import com.easy.fly.flyeasy.common.HeaderAtuhenticationGlide;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.databinding.FragmentUserPersonalInformationBinding;
import com.easy.fly.flyeasy.db.models.BindingModel;
import com.easy.fly.flyeasy.db.models.FlightBooking;
import com.easy.fly.flyeasy.db.models.PictureResolution;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.dto.UpdateUserInformationDto;
import com.easy.fly.flyeasy.utils.DateFormater;
import com.easy.fly.flyeasy.utils.UserUtil;
import com.easy.fly.flyeasy.viewmodel.UserViewModel;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPersonalInformationFragment extends Fragment implements Injectable,DatePickerDialog.OnDateSetListener {

    private static final int MY_PERMISSION_REQUEST = 100 ;

    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

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

    ProgressBar progressBar;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == getActivity().RESULT_OK && data!=null && data.getData()!=null){
            Uri uri = data.getData();
            uploadFile(uri);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSION_REQUEST:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{

                }
            }
            return;
        }
    }

    private void uploadFile(Uri uri) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        File originalFile = FileUtils.getFile(getContext(),uri);

        RequestBody filePart = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(uri)),originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("file",originalFile.getName(),filePart);

        viewModel.uploadProfilePicture(userAthenticationHeader,file);
        viewModel.response().observe(this,response -> processResponsePicture(response,progressDialog));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        // Inflate the layout for this fragment

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
        }
        sessionManager = new SessionManager(getContext());

        initKey();

        FragmentUserPersonalInformationBinding binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_user_personal_information,container,false);
        BindingModel<Boolean> editUserPersonalInformation = new BindingModel();
        editUserPersonalInformation.setData(isEdit);
        binding.setEditUserPesonalInformation(editUserPersonalInformation);

        ButterKnife.bind(this,binding.getRoot());

        userFullName.setText(user.getFullName() !=null ? user.getFullName() : "");
        userLocation.setText(user.getLocation()!= null ? user.getLocation().getName() : "");
        userEmail.setText(user.getEmail() !=null ? user.getEmail() : "");
        userBirthDate.setText(user.getBirthDate() != null ? DateFormater.formatDateForUI(user.getBirthDate().toString()): "");

        if(user.getProfilePicture()!=null){
            Glide.with(getContext())
                    .load(HeaderAtuhenticationGlide.loadUrl(user.getProfilePicture().getThumbnailPicture().getValue()))// GlideUrl is created anyway so there's no extra objects allocated
                    .into(profilePicture);
        }

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

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);
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

    private void processResponsePicture(Response response, ProgressDialog progressDialog) {
        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                progressDialog.dismiss();
                PictureResolution profileUserPicture = (PictureResolution)response.data;

                Glide.with(getContext())
                        .load(HeaderAtuhenticationGlide.loadUrl(profileUserPicture.getThumbnailPicture().getValue()))// GlideUrl is created anyway so there's no extra objects allocated
                        .into(profilePicture);
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
