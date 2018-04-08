package com.easy.fly.flyeasy.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;



import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;


/**
 * Created by boyan.dimitrov on 18.3.2018 г..
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(UserDB user);

    @Query("SELECT * FROM userdb WHERE id = :userId")
    LiveData<UserDB> load(String userId);

}
