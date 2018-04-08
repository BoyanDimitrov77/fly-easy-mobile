package com.easy.fly.flyeasy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.easy.fly.flyeasy.db.dao.UserDao;
import com.easy.fly.flyeasy.db.models.UserDB;

/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Database(entities = {UserDB.class}, version = 1,exportSchema = false)
public abstract class FlyEasyDatabase extends RoomDatabase {

    abstract public UserDao userDao();
}
