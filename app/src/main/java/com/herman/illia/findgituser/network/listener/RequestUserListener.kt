package com.herman.illia.findgituser.network.listener

import com.herman.illia.findgituser.model.ErrorModel
import com.herman.illia.findgituser.model.UserModel

/**
 * Created by illia on 8/27/2019.
 */
interface RequestUserListener {
    fun responseUserListener(isSuccess: Boolean, userModelData: UserModel?, code: Int?)
    fun errorUserListener(errorModel: ErrorModel?)
}