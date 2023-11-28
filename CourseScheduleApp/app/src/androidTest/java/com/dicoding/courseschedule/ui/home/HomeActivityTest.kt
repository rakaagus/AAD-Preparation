package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import com.dicoding.courseschedule.R

class HomeActivityTest{
    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun DisplayedAddCourseActivity(){
        onView(withId(R.id.action_add)).perform(click())
        onView(withText("Add Course")).check(matches(isDisplayed()))
    }
}