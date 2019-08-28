package com.herman.illia.findgituser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.herman.illia.findgituser.model.UserModel

/**
 * Created by illia on 8/27/2019.
 */
@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(usersList: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Query("DELETE from table_user")
    fun deleteAllUsers()

    @Query("SELECT * FROM table_user ORDER BY name desc")
    fun getAllUsers(): LiveData<List<UserModel>>

    @Query("SELECT * FROM table_user where id in (:id)")
    fun getUserById(id: Int): LiveData<UserModel>

    @Query("SELECT * FROM table_user where login in (:login)")
    fun getUserByLogin(login: String): LiveData<UserModel>
}