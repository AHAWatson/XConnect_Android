package com.xpanxion.xconnect

import android.content.Context

internal object PasswordValidator {

    fun isValid(input: String):Boolean{
        return input.isNotBlank() && input.length > 4
    }

    fun resolveError(input:String, context: Context):String{
        return if(!isValid(input)){
            if(input.isBlank()){
                context.getString(R.string.error_field_required)
            } else{
                context.getString(R.string.error_invalid_password)
            }
        } else ""
    }
}