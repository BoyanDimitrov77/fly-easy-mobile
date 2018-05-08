package com.easy.fly.flyeasy.interfaces;

import com.easy.fly.flyeasy.db.models.UserDB;

public interface ViewModelInterface {

    public UserDB getUserFromDB(long userId);
}
