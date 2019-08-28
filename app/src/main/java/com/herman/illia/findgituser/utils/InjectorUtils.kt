package com.herman.illia.findgituser.utils

import android.content.Context
import com.herman.illia.findgituser.database.UserRepository
import com.herman.illia.findgituser.viewmodel.UserViewModelFactory

/**
 * Created by illia on 8/27/2019.
 */
object InjectorUtils {

    private fun getUserRepository(context: Context) : UserRepository {
        return UserRepository.getInstance(context.applicationContext)
    }

    fun provideUserViewModelFactory(context: Context): UserViewModelFactory {
        val repository = getUserRepository(context)
        return UserViewModelFactory(repository)
    }
}