package com.easy.fly.flyeasy.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.easy.fly.flyeasy.common.Resource;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.dto.UserDto;
import com.easy.fly.flyeasy.repositories.UserRepository;

import javax.inject.Inject;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

public class RegisterUserViewModel extends ViewModel{

    private LiveData<Resource<User>> user;
    private UserRepository userRepository;

    @Inject
    public RegisterUserViewModel(UserRepository userRepository){
        this.userRepository =userRepository;
    }
    public boolean init(UserDto userDto) {
        user = userRepository.registerUser(userDto);
        if(user!=null){
            return true;
        }
        return false;
    }

}
