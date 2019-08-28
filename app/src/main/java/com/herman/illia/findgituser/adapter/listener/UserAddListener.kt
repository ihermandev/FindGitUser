package com.herman.illia.findgituser.adapter.listener

import com.herman.illia.findgituser.model.UserModel

/**
 * Created by illia on 8/27/2019.
 */
interface UserAddListener {
    fun userAdded(user: UserModel)
}