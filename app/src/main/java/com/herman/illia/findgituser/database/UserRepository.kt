package com.herman.illia.findgituser.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.herman.illia.findgituser.model.UserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by illia on 8/27/2019.
 */
class UserRepository private constructor(
    private val userDao: UserDao,
    private val mIoExecutor: ExecutorService
) {

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(
                    UserDatabase.getInstance(context = context).userDao(),
                    Executors.newSingleThreadExecutor()
                ).also { INSTANCE = it }
            }

    }

    fun insert(userData: UserModel) {
        mIoExecutor.execute { userDao.insertUser(userData) }
    }

    fun insertAll(userList: List<UserModel>) {
        mIoExecutor.execute { userDao.insertAllUsers(userList) }
    }

    fun delete(userData: UserModel) {
        mIoExecutor.execute { userDao.deleteUser(userData) }
    }

    fun deleteAll() {
        mIoExecutor.execute { userDao.deleteAllUsers() }
    }

    fun getAllUsers(): LiveData<List<UserModel>> {
        return userDao.getAllUsers()
    }

    fun getUserById(id: Int): LiveData<UserModel> {
        return userDao.getUserById(id)
    }

    fun getUserByLogin(login: String): LiveData<UserModel> {
        return userDao.getUserByLogin(login)
    }

    fun update(userData: UserModel) {
        mIoExecutor.execute { userDao.updateUser(userData) }
    }
}