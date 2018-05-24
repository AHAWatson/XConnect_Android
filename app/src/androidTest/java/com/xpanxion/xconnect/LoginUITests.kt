package com.xpanxion.xconnect

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.AppCompatEditText
import android.widget.AutoCompleteTextView
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
public class LoginUITests {

    @get:Rule
    public val activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun testLoginFieldsDisplay() {
        onView(withId(R.id.email_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.password_edit_text)).check(matches(isDisplayed()))
        onView(withText(activityRule.activity.getString(R.string.login_button_text))).check(matches(isDisplayed()))
    }

    @Test
    fun testBlankEmailSubmissionFailsWithError() {
        onView(withText(activityRule.activity.getString(R.string.login_button_text))).perform(click())
        assertEquals(
                activityRule.activity.getString(R.string.error_field_required),
                activityRule.activity.findViewById<AutoCompleteTextView>(R.id.email_edit_text).error
        )
    }

    @Test
    fun testInvalidEmailSubmissionFailsWithError() {
        onView(withId(R.id.email_edit_text)).perform(typeText("Charles Martel"))
        onView(withText(activityRule.activity.getString(R.string.login_button_text))).perform(click())
        assertEquals(
                activityRule.activity.getString(R.string.error_invalid_email),
                activityRule.activity.findViewById<AutoCompleteTextView>(R.id.email_edit_text).error
        )
    }

    @Test
    fun testBlankPasswordSubmissionFailsWithError() {
        onView(withId(R.id.email_edit_text)).perform(typeText("CharlesMartel@Tours.org"))
        onView(withText(activityRule.activity.getString(R.string.login_button_text))).perform(click())
        assertEquals(
                activityRule.activity.getString(R.string.error_field_required),
                activityRule.activity.findViewById<AppCompatEditText>(R.id.password_edit_text).error
        )
    }

    @Test
    fun testInvalidPasswordSubmissionFailsWithError() {
        onView(withId(R.id.email_edit_text)).perform(typeText("JanSobieski@Vienna.com"))
        onView(withId(R.id.password_edit_text)).perform(typeText("1683"))
        onView(withText(activityRule.activity.getString(R.string.login_button_text))).perform(click())
        assertEquals(
                activityRule.activity.getString(R.string.error_invalid_password),
                activityRule.activity.findViewById<AppCompatEditText>(R.id.password_edit_text).error
        )
    }
}
