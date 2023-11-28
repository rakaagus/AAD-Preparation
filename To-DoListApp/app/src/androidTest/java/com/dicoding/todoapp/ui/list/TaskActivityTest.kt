package com.dicoding.todoapp.ui.list

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import com.dicoding.todoapp.R

//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
class TaskActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(TaskActivity::class.java)

    @Test
    fun clickAddButton(){
        onView(withId(R.id.fab)).perform(click())
        onView(withText("Add Text!")).check(matches(isDisplayed()))
    }
}