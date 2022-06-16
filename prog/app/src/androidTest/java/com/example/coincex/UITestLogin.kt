package com.example.coincex

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coincex.activity.BuyActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestLogin {

    lateinit var email: String

    @get:Rule
    var activityScenary: ActivityScenarioRule<BuyActivity> = ActivityScenarioRule(BuyActivity::class.java)

    @Before
    fun setUp() {
        email = "99fly25@gmail.com"
    }

    @Test
    fun test() {
        onView(withId(R.id.email_edit)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.button3)).perform(click())

        onView(withId(R.id.textView65)).check(matches(withText(email)))
    }
}