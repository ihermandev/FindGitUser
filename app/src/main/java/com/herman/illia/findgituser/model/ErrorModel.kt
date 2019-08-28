package com.herman.illia.findgituser.model

import com.herman.illia.findgituser.R

/**
 * Created by illia on 8/27/2019.
 */
data class ErrorModel (
    var errorTitle : Int = R.string.error_dialog_message,
    var errorMessage : Int? = R.string.generic_error,
    var exception : Throwable? = null,
    var errorTitleText: String? = null,
    var errorMessageText: String? = null
)