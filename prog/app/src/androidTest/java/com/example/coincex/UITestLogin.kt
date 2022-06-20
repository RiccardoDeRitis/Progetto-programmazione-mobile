package com.example.coincex

import android.util.Log
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.coincex.fragment_menu.LoginFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestLogin {

    private lateinit var email: String
    private lateinit var password: String

    @Before
    fun setUp() {
        email = "99fly25@gmail.com"
        password = "riccardo"
        val scenary = launchFragmentInContainer<LoginFragment>()
        scenary.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun test() {
        onView(withId(R.id.email_edit)).perform(replaceText(email))
        onView(withId(R.id.pass_edit)).perform(replaceText(password))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button3)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.textView65)).check(matches(withText(email)))
    }
}