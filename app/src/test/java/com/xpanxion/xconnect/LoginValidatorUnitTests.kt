package com.xpanxion.xconnect

import org.junit.Test

import org.junit.Assert.*

class LoginValidatorUnitTests {
    @Test
    fun ensureValidEmailsValidate() {
        assertTrue(EmailValidator.isValid("1492@grenada.org"))
        assertTrue(EmailValidator.isValid("francis@assisi.it"))
        assertTrue(EmailValidator.isValid("st.therese@lisieux.fr"))
        assertTrue(EmailValidator.isValid("Mother.Theresa@calcutta.india.1234.com"))
    }

    @Test
    fun ensureInvalidEmailsDoNotValidate() {
        assertFalse(EmailValidator.isValid(""))
        assertFalse(EmailValidator.isValid("12345"))
        assertFalse(EmailValidator.isValid("abcd@qwerty"))
        assertFalse(EmailValidator.isValid("charlemagne@*.com"))
    }

    @Test
    fun ensureValidPasswordsValidate() {
        assertTrue(PasswordValidator.isValid("abc12"))
        assertTrue(PasswordValidator.isValid("M!@#$%^&*()qwerty123400"))
    }

    @Test
    fun ensureInvalidPasswordsDoNotValidate() {
        assertFalse(PasswordValidator.isValid(""))
        assertFalse(PasswordValidator.isValid("*"))
        assertFalse(PasswordValidator.isValid("1234"))
        assertFalse(PasswordValidator.isValid("abcd"))
    }
}
