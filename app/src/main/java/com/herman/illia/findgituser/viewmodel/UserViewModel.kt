package com.herman.illia.findgituser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.herman.illia.findgituser.database.UserRepository
import com.herman.illia.findgituser.model.UserModel

/**
 * Created by illia on 8/27/2019.
 */
class UserViewModel internal constructor(userRepository: UserRepository) : ViewModel() {

    private val repository: UserRepository = userRepository

    fun getAllUsersList(): LiveData<List<UserModel>> {
        return repository.getAllUsers()
    }

    fun addUser(userData: UserModel) {
        repository.insert(userData)
    }

    fun updateUser(userData: UserModel) {
        repository.update(userData)
    }

    fun deleteUser(userData: UserModel) {
        repository.delete(userData)
    }

    fun getUserById(userId: Int): LiveData<UserModel> {
        return repository.getUserById(userId)
    }

    fun getUserByLogin(userLogin: String): LiveData<UserModel> {
        return repository.getUserByLogin(userLogin)
    }

    fun deleteAllUsers() = repository.deleteAll()

    fun insertAllUsers(UserModelList: List<UserModel>) {
        repository.insertAll(UserModelList)
    }

}