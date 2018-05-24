package com.xpanxion.xconnect

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v7.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private val DUMMY_CREDENTIALS = arrayOf("awatson@xpanxion.com:password")
    }

    private var authTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password_edit_text.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        password_layout.isPasswordVisibilityToggleEnabled = true
        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {
        if (authTask != null) {
            return
        }

        email_edit_text.error = null
        password_edit_text.error = null

        // Store values at the time of the login attempt.
        val emailInput = email_edit_text.text.toString()
        val passwordInput = password_edit_text.text.toString()

        var cancel = false
        var fieldWithError: View? = null

        if(!PasswordValidator.isValid(passwordInput)){
            password_edit_text.setError(PasswordValidator.resolveError(passwordInput, this), null)
            fieldWithError = password_edit_text
            cancel = true
        }

        if(!EmailValidator.isValid(emailInput)){
            email_edit_text.setError(EmailValidator.resolveError(emailInput, this), null)
            fieldWithError = email_edit_text
            cancel = true
        }

        if (cancel) {
            fieldWithError?.requestFocus()
        } else {
            showProgress(true)
            authTask = UserLoginTask(emailInput, passwordInput)
            authTask!!.execute(null as Void?)
        }
    }

    private fun showProgress(show: Boolean) {

        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    inner class UserLoginTask internal constructor(private val email: String, private val password: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            DUMMY_CREDENTIALS.map { it.split(":") }
                    .firstOrNull { it[0] == email }
                    ?.let {
                        // Account exists, return true if the password matches.
                        if(it[1] == password) {
                            return true
                        }
                    }
            return false
        }

        override fun onPostExecute(success: Boolean) {
            authTask = null
            showProgress(false)

            if (success) {
                //valid login
            } else {
                //invalid login
            }
        }

        override fun onCancelled() {
            authTask = null
            showProgress(false)
        }
    }
}
