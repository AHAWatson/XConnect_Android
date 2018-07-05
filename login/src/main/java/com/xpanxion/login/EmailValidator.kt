package com.xpanxion.login

import android.content.Context
import java.util.regex.Pattern

private const val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

internal object EmailValidator {

    fun isValid(input: String):Boolean{
        return input.isNotBlank() && Pattern.compile(EMAIL_PATTERN).matcher(input).matches()
    }

    fun resolveError(input:String, context: Context):String{
        return if(!isValid(input)){
            if(input.isBlank()){
                context.getString(R.string.error_field_required)
            } else{
                context.getString(R.string.error_invalid_email)
            }
        } else ""
    }
}