package com.easy.fly.flyeasy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.User;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Database(entities = {User.class}, version = 1)
public abstract class FlyEasyDatabase extends RoomDatabase {

    abstract public UserDao userDao();
}
